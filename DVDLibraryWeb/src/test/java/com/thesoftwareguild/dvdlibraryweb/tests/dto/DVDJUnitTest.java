/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thesoftwareguild.dvdlibraryweb.tests.dto;

import com.thesoftwareguild.dvdlibraryweb.dto.Dvd;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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

        Dvd lasseterTest = new Dvd();
        lasseterTest.setMpaaRating("R");

        Dvd toyStory = new Dvd();
        toyStory.setMpaaRating("G");

        List<Dvd> testList = new ArrayList();
        testList.add(lasseterTest);
        testList.add(toyStory);

        testList.stream()
                .sorted(Dvd::compareTo)
                .close();
        Assert.assertEquals("G", testList.get(0).getMpaaRating());
        Assert.assertEquals("R", testList.get(1).getMpaaRating());

    }

    @Test
    public void testCompare2() {

        Dvd toyStory = new Dvd();
        toyStory.setMpaaRating("G");

        Dvd lasseterTest = new Dvd();
        lasseterTest.setMpaaRating("R");

        List<Dvd> testList = new ArrayList();
        testList.add(toyStory);
        testList.add(lasseterTest);

        testList.stream()
                .sorted(Dvd::compareTo)
                .close();
        Assert.assertEquals("G", testList.get(0).getMpaaRating());
        Assert.assertEquals("R", testList.get(1).getMpaaRating());

    }

    @Test
    public void testCompare3() {

        Dvd verbinskiTest = new Dvd();
        verbinskiTest.setMpaaRating("R");

        Dvd pirates = new Dvd();
        pirates.setMpaaRating("PG-13");

        List<Dvd> testList = new ArrayList();
        testList.add(verbinskiTest);
        testList.add(pirates);

        testList.stream()
                .sorted(Dvd::compareTo)
                .close();
        Assert.assertEquals("PG-13", testList.get(0).getMpaaRating());
        Assert.assertEquals("R", testList.get(1).getMpaaRating());

    }

    @Test
    public void testCompare4() {

        Dvd pirates = new Dvd();
        pirates.setMpaaRating("PG-13");

        Dvd verbinskiTest = new Dvd();
        verbinskiTest.setMpaaRating("R");

        List<Dvd> testList = new ArrayList();
        testList.add(pirates);
        testList.add(verbinskiTest);

        testList.stream()
                .sorted(Dvd::compareTo)
                .close();
        Assert.assertEquals("PG-13", testList.get(0).getMpaaRating());
        Assert.assertEquals("R", testList.get(1).getMpaaRating());

    }
}
