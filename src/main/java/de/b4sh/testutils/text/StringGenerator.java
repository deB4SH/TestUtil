package de.b4sh.testutils.text;

import java.util.Random;

public final class StringGenerator {

    private final static Random rand = new Random();
    private final static String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final static String numeric = "0123456789";
    private final static String alphaNumeric = alpha+numeric;

    public static String generateAlphaString(final int length){
        if(length == 0)
            return null;
        final StringBuilder sb = new StringBuilder(length);
        while (sb.length() < length){
            sb.append(alpha.charAt(rand.nextInt(alpha.length())));
        }
        return sb.toString();
    }

    public static String generateNumericString(final int length){
        if(length == 0)
            return null;
        final StringBuilder sb = new StringBuilder(length);
        while (sb.length() < length){
            sb.append(numeric.charAt(rand.nextInt(numeric.length())));
        }
        return sb.toString();
    }

    public static String generateAlphaNumericString(final int length){
        if(length == 0)
            return null;
        final StringBuilder sb = new StringBuilder(length);
        while (sb.length() < length){
            sb.append(alphaNumeric.charAt(rand.nextInt(alphaNumeric.length())));
        }
        return sb.toString();
    }

}
