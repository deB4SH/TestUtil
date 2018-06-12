package de.b4sh.testutils.io;

import java.io.File;

import de.b4sh.testutils.exception.RootFolderException;

/**
 * FolderManager serves function related to managing folder and everything around this topic.
 * With the FolderManager you could easly create a new test-folder for each unit test passed.
 */
public final class FolderManager {

    /**
     * private constructor.
     */
    private FolderManager() {
        //nop
    }

    /**
     * Get the base directory for the FolderManager as File.
     *
     * @return Path as file object
     */
    public static File getBaseDir() {
        return new File(getWorkingDirectory(), "testSpace");
    }

    /**
     * Get the current working directory of this application.
     *
     * @return Path to working directory as file object.
     */
    public static File getWorkingDirectory() {
        return new File(System.getProperty("user.dir"));
    }

    /**
     * Creates or Gets the requested test folder for given testName.
     *
     * @param testName the name of the test which is used for the folder qry
     * @return path to directory as file object
     */
    public static File createTestFolderEnviornment(final String testName) {
        final File baseDir = initalizeBaseDirectory();
        final File newTestDir = new File(baseDir, testName);
        if (newTestDir.exists())
            return newTestDir;
        if (!baseDir.canWrite())
            throw new RootFolderException(RootFolderException.RootFolderCommonException.MissingWritePower.getReason());
        if (!newTestDir.mkdirs())
            throw new RootFolderException(RootFolderException.RootFolderCommonException.MissingWritePower.getReason());
        return newTestDir;
    }

    private static File initalizeBaseDirectory() {
        final File baseDir = getBaseDir();
        if (baseDir.exists())
            return baseDir;
        //check if write rights are granted
        final File workingDir = getWorkingDirectory();
        if (!workingDir.canWrite())
            throw new RootFolderException(RootFolderException.RootFolderCommonException.MissingWritePower.getReason());
        //create folder
        if (!baseDir.mkdirs())
            throw new RootFolderException(RootFolderException.RootFolderCommonException.RootFolderNotExisting.getReason());
        return baseDir;
    }


}
