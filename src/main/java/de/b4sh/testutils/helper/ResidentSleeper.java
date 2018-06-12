package de.b4sh.testutils.helper;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Sleep Manager for TestUtils.
 */
public final class ResidentSleeper {

    private static final Logger log = Logger.getLogger(ResidentSleeper.class.getName());

    /**
     * private constructor.
     */
    private ResidentSleeper(){
        //nop
    }

    /**
     * Sleep function that shades the try catch jibber related to a thread sleep.
     * @param time time to sleep in ms.
     */
    public static void sleep(final long time){
        try{
            Thread.sleep(time);
        } catch (final InterruptedException e) {
            log.log(Level.WARNING, "Cannot sleep!",e);
        }
    }
}
