package de.b4sh.testutils.network;

import java.util.logging.Level;
import java.util.logging.Logger;

import de.b4sh.testutils.helper.ResidentSleeper;
import org.junit.Assert;
import org.junit.Test;

public class PortScannerTest {

    private static final Logger log = Logger.getLogger(PortScannerTest.class.getName());

    @Test
    public void testValidPortResponse(){
        final int port;
        port = PortScanner.findNextOpenPort(64800,3);
        Assert.assertNotEquals(0,port);
        Assert.assertEquals(64800,port);
        log.log(Level.INFO, "Port is " + port);
    }

    @Test
    public void testBlockedPortResponse(){
        final int port;
        final int portToBlock = 64800;
        //block and test port
        final PortBlocker pb = new PortBlocker(portToBlock);
        ResidentSleeper.sleep(100); //give system a bit time to block the port
        port = PortScanner.findNextOpenPort(64800,3);
        Assert.assertNotEquals(0,port);
        Assert.assertNotEquals(-1,port);
        Assert.assertEquals(64801,port);
        log.log(Level.INFO, "Port is " + port);
        pb.unblockPort();
    }
}
