package com.thesoftwareguild.flooringmastery.utility;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

/**
 * Created by paulharding on 9/2/16.
 */
public class FlooringMasteryUtilityTest {

    Date date1;

    @Before
    public void setUp() {

        date1 = new Date(116, 8, 3);

    }

    @After
    public void tearDown() {

    }

    @Test
    public void testFormatDateForFiles() {

        String result = FlooringMasteryUtility.formatDateForFiles(date1);

        Assert.assertEquals("09032016", result);

    }

    @Test
    public void testFormatDateForDisplay() {

        String result = FlooringMasteryUtility.formatDateForDisplay(date1);

        Assert.assertEquals("09/03/2016", result);

    }

    @Test
    public void testDecoding() {

        String currentLine = "1,\"Harding, Paul\",OH,6.25,Wood,100.0,5.15,4.75,515.0,475.0,61.87,1051.86";

        List<String> orderObject = FlooringMasteryUtility.generateArrayToDecode(currentLine, ",");

        Assert.assertEquals(12, orderObject.size());

    }


}