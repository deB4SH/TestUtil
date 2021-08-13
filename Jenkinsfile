pipeline{
    agent none

    stages {
        stage('build') {
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
            steps {
                sh 'mvn clean package'
            }
            post {
                always {
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                    junit 'target/reports/**/*.xml'
                }
            }
        }
    }

}
