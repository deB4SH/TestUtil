package de.b4sh.testutils.network;

import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.b4sh.testutils.helper.ResidentSleeper;

/**
 * PortScanner hosts functions for getting the next free port.
 */
public final class PortScanner {

    private static final Logger log = Logger.getLogger(PortScanner.class.getName());

    /**
     * Constructor for Portscanner.
     */
    private PortScanner(){
        //nop
    }

    /**
     * Find the next open port by given startport and range.
     * If no port is free it returns -1 as "issue" flag.
     *
     * @param startPort port to start from
     * @param range     range to scan for free ports
     * @return Integer with the actual free port number or minus one.
     */
    public static int findNextOpenPort(final int startPort, final int range) {
        final int serivcePoolSize = 5;
        final ExecutorService service = Executors.newFixedThreadPool(serivcePoolSize);
        final int serviceTimeout = 250;
        final List<Future<Port>> futures = new ArrayList<>();

        for (int p = startPort; p < startPort + range; p++) {
            futures.add(portChecker(service, p, serviceTimeout));
        }
        service.shutdown();//issue shutdown when all scans are done
        ResidentSleeper.sleep(1000);//wait a second for the portscan to fulfill.
        for (Future<Port> f : futures) {
            try {
                if (f.get().isFree()) {
                    return f.get().getPort();
                }
            } catch (InterruptedException | ExecutionException e) {
                log.log(Level.WARNING, "Exception during port scanning. Check Stacktrace for more information: ", e);
            }
        }

        return -1;
    }

    /**
     * Function to check for open ports.
     *
     * @param service Threadpool to use
     * @param port    port to check for
     * @param timeout socket timeout to use
     * @return private class Port with flag if given port is free or not
     */
    private static Future<Port> portChecker(final ExecutorService service, final int port, final int timeout) {
        return service.submit(() -> {
            try {
                final Socket s = new Socket();
                s.connect(new InetSocketAddress("127.0.0.1", port), timeout);
                s.close();//if this is executed there was a service running on given port
                return new Port(false, port);
            } catch (SocketTimeoutException | ConnectException e) {
                return new Port(true, port);
            }
        });
    }

    /**
     * private class for keeping the required data in one place.
     */
    private static class Port {
        private boolean isFree;
        private int port;

        /**
         * Port constructor.
         *
         * @param isFree flag that represents if the port is free or not
         * @param port   port number itself
         */
        Port(final boolean isFree, final int port) {
            this.isFree = isFree;
            this.port = port;
        }

        /**
         * get the flag if the port is free or not.
         *
         * @return boolean with true or false
         */
        boolean isFree() {
            return isFree;
        }

        /**
         * get the port number.
         *
         * @return integer with the checked port number
         */
        int getPort() {
            return port;
        }
    }

}
