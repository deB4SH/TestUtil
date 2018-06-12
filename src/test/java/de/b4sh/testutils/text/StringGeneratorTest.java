package de.b4sh.testutils.text;

import org.junit.Assert;
import org.junit.Test;

public class StringGeneratorTest {

    @Test
    public void generateAlphaNumericString(){
        final String alphanum = StringGenerator.generateAlphaNumericString(10);
        Assert.assertNotNull(alphanum);
        Assert.assertEquals(10,alphanum.length());
        Assert.assertTrue(alphanum.matches("[A-Z0-9]+"));
    }

    @Test
    public void generateAlphaString(){
        final String alpha = StringGenerator.generateAlphaString(10);
        Assert.assertNotNull(alpha);
        Assert.assertEquals(10,alpha.length());
        Assert.assertTrue(alpha.matches("[A-Z]+"));
    }

    @Test
    public void generateNumericString(){
        final String num = StringGenerator.generateNumericString(10);
        Assert.assertNotNull(num);
        Assert.assertEquals(10,num.length());
        Assert.assertTrue(num.matches("[0-9]+"));
    }

    @Test
    public void generateNullAlphaNumeric(){
        final String alphanum = StringGenerator.generateAlphaNumericString(0);
        Assert.assertNull(alphanum);
    }

    @Test
    public void generateNullAlpha(){
        final String alpha = StringGenerator.generateAlphaString(0);
        Assert.assertNull(alpha);
    }

    @Test
    public void generateNullNumeric(){
        final String num = StringGenerator.generateNumericString(0);
        Assert.assertNull(num);
    }

}
