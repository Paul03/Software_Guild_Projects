/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dvdlibrary.tests;

import com.mycompany.dvdlibrary.dto.DVD;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author paulharding
 */
public class DVDJUnitTest {

    public DVDJUnitTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testCompare1() {

        DVD lasseterTest = new DVD();
        lasseterTest.setMpaaRating("R");

        DVD toyStory = new DVD();
        toyStory.setMpaaRating("G");

        List<DVD> testList = new ArrayList();
        testList.add(lasseterTest);
        testList.add(toyStory);

        Collections.sort(testList, new DVD());
        Assert.assertEquals("G", testList.get(0).getMpaaRating());
        Assert.assertEquals("R", testList.get(1).getMpaaRating());

    }

    @Test
    public void testCompare2() {

        DVD toyStory = new DVD();
        toyStory.setMpaaRating("G");

        DVD lasseterTest = new DVD();
        lasseterTest.setMpaaRating("R");

        List<DVD> testList = new ArrayList();
        testList.add(toyStory);
        testList.add(lasseterTest);

        Collections.sort(testList, new DVD());
        Assert.assertEquals("G", testList.get(0).getMpaaRating());
        Assert.assertEquals("R", testList.get(1).getMpaaRating());

    }

    @Test
    public void testCompare3() {

        DVD verbinskiTest = new DVD();
        verbinskiTest.setMpaaRating("R");

        DVD pirates = new DVD();
        pirates.setMpaaRating("PG-13");

        List<DVD> testList = new ArrayList();
        testList.add(verbinskiTest);
        testList.add(pirates);

        Collections.sort(testList, new DVD());
        Assert.assertEquals("PG-13", testList.get(0).getMpaaRating());
        Assert.assertEquals("R", testList.get(1).getMpaaRating());

    }

    @Test
    public void testCompare4() {

        DVD pirates = new DVD();
        pirates.setMpaaRating("PG-13");

        DVD verbinskiTest = new DVD();
        verbinskiTest.setMpaaRating("R");

        List<DVD> testList = new ArrayList();
        testList.add(pirates);
        testList.add(verbinskiTest);

        Collections.sort(testList, new DVD());
        Assert.assertEquals("PG-13", testList.get(0).getMpaaRating());
        Assert.assertEquals("R", testList.get(1).getMpaaRating());

    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
