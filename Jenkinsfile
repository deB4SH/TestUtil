pipeline{
    //global agent - its just an maven jar
    agent {
        kubernetes {
            containerTemplate {
                name 'maven'
                image 'maven:3.6.3-jdk-8'
                command 'sleep'
                args 'infinity'
            }
            defaultContainer 'maven'
        }
    }
    //define trigger
    triggers{
        pollSCM 'H/30 * * * *'
    }
    //stages to build and deploy
    stages {
        stage ('check: prepare') {
            steps {
                sh '''
                    mvn -version
                    export MAVEN_OPTS="-Xmx1024m"
                '''
            }
        }
        //merge against integration to check for merge-issues
        stage ('source: merge integration') {
            when {
                not {
                    branch 'release/*'
                }
                not {
                    branch 'integration'
                }
                not {
                    branch 'main'
                }
            }
            steps{
                echo 'Merge feature Branch with integration'
                sh '''
                    git merge origin/integration
                '''
            }
        }
        stage ('build: setVersion') {
            when {
                branch 'release/*'
            }
            environment {
                BRANCHVERSION = sh(
                    script: "echo ${env.BRANCH_NAME} | sed -E 's/release\\/([0-9a-zA-Z.\\-]+)/\\1/'",
                    returnStdout: true
                ).trim()
            }
            steps {
                echo 'Setting release version'
                echo "${BRANCHVERSION}"
                sh 'mvn versions:set -DnewVersion=${BRANCHVERSION} -f ./pom.xml'
            }
        }
        stage('build') {
            steps {
                sh 'mvn clean package'
            }
            post {
                always {
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                    junit 'target/surefire-reports/**/*.xml'
                }
            }
        }
    }

}
