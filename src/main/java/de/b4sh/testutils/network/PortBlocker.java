package de.b4sh.testutils.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * PortBlocker is a class for blocking ports temporary.
 * Mainly used to check what happens inside an application if a specific port is blocked.
 */
public final class PortBlocker {

    private static final Logger log = Logger.getLogger(PortBlocker.class.getName());
    private final int port;
    private PortBlockRunner runner;

    /**
     * Constructor for PortBlocker.
     * Constructing this also blocks the port immediately.
     * @param port port to block
     */
    public PortBlocker(final int port) {
        this.port = port;
        this.blockPort(port);
    }

    /**
     * Blocks given port with PortBlockRunner.
     * @param port Port to block
     */
    private void blockPort(final int port){
        try{
            final ServerSocket s = new ServerSocket(port);
            s.setReuseAddress(true);
            this.runner = new PortBlockRunner(s);
            final Thread t1 = new Thread(this.runner);
            t1.start();
        } catch (final IOException e) {
            log.log(Level.WARNING, "Couldn't block requested port. Check Stacktrace for more information.",e);
        }
    }

    /**
     * Unblock the blocked port.
     */
    public void unblockPort(){
        this.runner.setRunBlock(false);
        this.simulateCloseConnection();
    }

    /**
     * Function to connect to listening ServerSocket and get it out of the blocking state.
     */
    private void simulateCloseConnection(){
        final String localhost = "127.0.0.1";
        try {
            final Socket s = new Socket(localhost,this.port);
            s.close();
        } catch (IOException e) {
            log.log(Level.WARNING, "IO Exception during unblocking of PortBlockRunner.");
        }
    }

    /**
     * Runner Class for PortBlocker.
     */
    static final class PortBlockRunner implements Runnable{
        private static final Logger log = Logger.getLogger(PortBlockRunner.class.getName());
        private final ServerSocket socket;
        private boolean runBlock;

        PortBlockRunner(final ServerSocket socket) {
            this.socket = socket;
            this.runBlock = true;
        }

        @Override
        public void run() {
            while(runBlock){
                try{
                    this.socket.accept();
                } catch (IOException e) {
                    log.log(Level.WARNING, "IO Exception during accepting socket on server socket.");
                }
            }
            log.log(Level.INFO, "Shutting down PortBlocker on port: " + this.socket.getLocalPort());
        }

        /**
         * Set the flag for keeping the port blocker alive.
         * False means that it stops blocking the port after the next connection.
         * @param runBlock flag to set
         */
        void setRunBlock(final boolean runBlock) {
            this.runBlock = runBlock;
        }
    }
}
