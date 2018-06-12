package de.b4sh.testutils.exception;

/**
 * Exception that is possible due to errors with the root folder.
 * The reason could be different.
 * Checking the Stacktrace if this is thrown is strongly advertised.
 */
public final class RootFolderException extends RuntimeException {

    /**
     * Constructor for RootFolderException.
     *
     * @param reason the reason why this Exception is thrown
     */
    public RootFolderException(final String reason) {
        super(reason);
    }

    /**
     * Common Error Enum.
     * This enum contains common errors
     */
    public enum RootFolderCommonException {
        RootFolderNotExisting("The base directory /testSpace isn't available or couldn't be created."),
        MissingWritePower("TestUtils can't write inside /testSpace");


        private final String reason;

        /**
         * constructor for enum value.
         *
         * @param reason reason why exception is thrown.
         */
        RootFolderCommonException(final String reason) {
            this.reason = reason;
        }

        /**
         * Get the reason why the exception occured.
         *
         * @return String with reason.
         */
        public String getReason() {
            return reason;
        }
    }

}
