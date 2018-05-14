/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thesoftwareguild.dvdlibraryweb.tests.dao;

import com.thesoftwareguild.dvdlibraryweb.dao.DvdDao;
import com.thesoftwareguild.dvdlibraryweb.dto.Dvd;

import java.util.Date;
import java.util.List;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author paulharding
 */
public class DvdDaoJUnitTest {

    private DvdDao dvdDao;

    private Dvd miracle;
    private Dvd moreThanAGame;
    private Dvd zootopya;
    private Dvd GodsNotDead;

    public DvdDaoJUnitTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-persistence.xml");
        dvdDao = ctx.getBean("dvdDao", DvdDao.class);
    }

    @Before
    public void setUp() {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-persistence.xml");

        JdbcTemplate cleaner = (JdbcTemplate) ctx.getBean("jdbcTemplate");
        cleaner.execute("DELETE FROM dvd");

        miracle = new Dvd();
        miracle.setTitle("Miracle");
        miracle.setReleaseDate( new Date( (2004 - 1900) , (02 - 1) , 6) );
        miracle.setMpaaRating("PG");
        miracle.setDirector("Gavin O'Connor");
        miracle.setStudio("Walt Disney Pictures");
        miracle.setUserRating(8);

        moreThanAGame = new Dvd();
        moreThanAGame.setTitle("More Than A Game");
        moreThanAGame.setReleaseDate( new Date( (2008 - 1900), (10 - 1) , 2 ) );
        moreThanAGame.setMpaaRating("PG");
        moreThanAGame.setDirector("Kristopher Belman");
        moreThanAGame.setStudio("Harvey Mason Jr");
        moreThanAGame.setUserRating(7);

        zootopya = new Dvd();
        zootopya.setTitle("Zootopya"); // spelled wrong on purpose
        zootopya.setReleaseDate( new Date( (2016 - 1900), (3 - 1) , 4 ) );
        zootopya.setMpaaRating("PG");
        zootopya.setDirector("Byron Howard");
        zootopya.setStudio("Walt Disney Pictures");
        zootopya.setUserRating(8);

        GodsNotDead = new Dvd();
        GodsNotDead.setTitle("God's Not Dead");
        GodsNotDead.setReleaseDate( new Date( (2014 - 1900), (3 - 1) , 21 ) );
        GodsNotDead.setMpaaRating("PG");
        GodsNotDead.setDirector("Harold Cronk");
        GodsNotDead.setStudio("Michael Scott");
        GodsNotDead.setUserRating(10);

    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAddDvd() {

        Dvd addedDvd = dvdDao.insert(miracle);

        Assert.assertNotNull(addedDvd);
        Assert.assertNotNull(addedDvd.getDvdId());

        Integer expectedYear = 2004;
        Integer actualYear = addedDvd.getReleaseDate().getYear() + 1900;

        Assert.assertEquals("Miracle", addedDvd.getTitle());
        Assert.assertEquals(expectedYear, actualYear);

    }

    @Test
    public void testReadDvd() {

        Dvd addedDvd = dvdDao.insert(moreThanAGame);

        Dvd readDvd = dvdDao.retrieve(addedDvd.getDvdId());

        Assert.assertNotNull(readDvd);
        Assert.assertNotNull(readDvd.getDvdId());

        Integer expectedYear = 2008;
        Integer actualYear = readDvd.getReleaseDate().getYear() + 1900;

        Assert.assertEquals("More Than A Game", readDvd.getTitle());
        Assert.assertEquals(expectedYear, actualYear);


    }

    @Test
    public void testUpdateDvd() {

        Dvd addedDvd = dvdDao.insert(zootopya);

        Dvd readDvd = dvdDao.retrieve(addedDvd.getDvdId());
        readDvd.setTitle("Zootopia");

        dvdDao.update(readDvd);

        Dvd updatedDvd = dvdDao.retrieve(readDvd.getDvdId());

        Assert.assertNotNull(updatedDvd);
        Assert.assertEquals(readDvd.getDvdId(), updatedDvd.getDvdId());
        Assert.assertNotSame(addedDvd, updatedDvd);

        Assert.assertEquals("Zootopia", updatedDvd.getTitle());

    }

    @Test
    public void testDeleteDvd() {

        Dvd addedDvd = dvdDao.insert(GodsNotDead);

        Dvd readDvd = dvdDao.retrieve(addedDvd.getDvdId());

        dvdDao.delete(readDvd);

        try {
        Dvd deletedDvd = dvdDao.retrieve(addedDvd.getDvdId());
        Assert.assertNull(readDvd); // I want to put something here that is NOT Null
                                    // (if it reaches this point, then the test should fail)

        } catch(EmptyResultDataAccessException ex) {

        }

    }

    @Test
    public void testList() {

        Dvd addedDvd1 = dvdDao.insert(GodsNotDead);
        Dvd addedDvd2 = dvdDao.insert(zootopya);

        List<Dvd> dvdList = dvdDao.all();

        Assert.assertEquals(2, dvdList.size());

        dvdList.add(miracle);

        List<Dvd> dvdList2 = dvdDao.all();

        Assert.assertNotSame(dvdList.size(), dvdList2.size());

    }

    @Test
    public void testSearchLastNYears5Yrs() {

        dvdDao.insert(miracle);
        dvdDao.insert(moreThanAGame);
        dvdDao.insert(zootopya);
        dvdDao.insert(GodsNotDead);

        List<Dvd> last5Yrs = dvdDao.searchLastNYears(5);
        Assert.assertEquals(2, last5Yrs.size());

    }

    @Test
    public void testSearchLastNYears10Yrs() {

        dvdDao.insert(miracle);
        dvdDao.insert(moreThanAGame);
        dvdDao.insert(zootopya);
        dvdDao.insert(GodsNotDead);

        List<Dvd> last10Yrs = dvdDao.searchLastNYears(10);
        Assert.assertEquals(3, last10Yrs.size());

    }

    @Test
    public void testSearchLastNYears20Yrs() {

        dvdDao.insert(miracle);
        dvdDao.insert(moreThanAGame);
        dvdDao.insert(zootopya);
        dvdDao.insert(GodsNotDead);

        List<Dvd> last20Yrs = dvdDao.searchLastNYears(20);
        Assert.assertEquals(4, last20Yrs.size());

    }

    @Test
    public void testSearchByMPAARatingG() {

        dvdDao.insert(miracle);
        dvdDao.insert(moreThanAGame);
        dvdDao.insert(zootopya);
        dvdDao.insert(GodsNotDead);

        List<Dvd> gRatedDvds = dvdDao.searchByMPAARating("G");
        Assert.assertEquals(0, gRatedDvds.size());

    }

    @Test
    public void testSearchByMPAARatingPG() {

        dvdDao.insert(miracle);
        dvdDao.insert(moreThanAGame);
        dvdDao.insert(zootopya);
        dvdDao.insert(GodsNotDead);

        List<Dvd> pgRatedDvds = dvdDao.searchByMPAARating("PG");
        Assert.assertEquals(4, pgRatedDvds.size());

    }

    @Test
    public void testSearchByDirector() {

        dvdDao.insert(miracle);
        dvdDao.insert(moreThanAGame);
        dvdDao.insert(zootopya);
        dvdDao.insert(GodsNotDead);

        List<Dvd> johnLasseterDvds = dvdDao.searchByDirector("Harold Cronk");
        Assert.assertEquals(1, johnLasseterDvds.size());

        List<Dvd> byronHowardDvds = dvdDao.searchByDirector("Byron Howard");
        Assert.assertEquals(1, byronHowardDvds.size());

    }
/*
    @Test
    public void testSearchByDirectorSort() {

        // Create some Dvd objects
        Dvd lasseterTest = new Dvd();
        lasseterTest.setDirector("John Lasseter");
        lasseterTest.setMpaaRating("PG");

        Dvd toyStory = new Dvd();
        toyStory.setTitle("Toy Story");
        toyStory.setDirector("John Lasseter");
        toyStory.setMpaaRating("G");

        Dvd toyStory2 = new Dvd();
        toyStory2.setTitle("Toy Story 2");
        toyStory2.setDirector("John Lasseter");
        toyStory2.setMpaaRating("G");

        Dvd verbinskiTest = new Dvd();
        verbinskiTest.setDirector("Gore Verbinski");
        verbinskiTest.setMpaaRating("G");

        Dvd pirates = new Dvd();
        pirates.setTitle("Pirates 1");
        pirates.setDirector("Gore Verbinski");
        pirates.setMpaaRating("PG-13");

        Dvd pirates2 = new Dvd();
        pirates2.setTitle("Pirates 2");
        pirates2.setDirector("Gore Verbinski");
        pirates2.setMpaaRating("PG-13");

        // Add them to the Dao's all
        dvdDao.insert(lasseterTest);
        dvdDao.insert(toyStory);
        dvdDao.insert(toyStory2);
        dvdDao.insert(verbinskiTest);
        dvdDao.insert(pirates);
        dvdDao.insert(pirates2);

        List<Dvd> johnLasseterDVDs = dvdDao.searchByDirector("John Lasseter");
        List<Dvd> goreVerbinskiDVDs = dvdDao.searchByDirector("Gore Verbinski");

        Assert.assertEquals(3, johnLasseterDVDs.size());
        Assert.assertEquals(3, goreVerbinskiDVDs.size());

        Assert.assertEquals("G", johnLasseterDVDs.get(0).getMpaaRating());
        Assert.assertEquals("G", johnLasseterDVDs.get(1).getMpaaRating());
        Assert.assertEquals("PG", johnLasseterDVDs.get(2).getMpaaRating());

        Assert.assertEquals("G", goreVerbinskiDVDs.get(0).getMpaaRating());
        Assert.assertEquals("PG-13", goreVerbinskiDVDs.get(1).getMpaaRating());
        Assert.assertEquals("PG-13", goreVerbinskiDVDs.get(2).getMpaaRating());

    }
*/


    @Test
    public void testSearchByStudio() {

        dvdDao.insert(miracle);
        dvdDao.insert(moreThanAGame);
        dvdDao.insert(zootopya);
        dvdDao.insert(GodsNotDead);

        List<Dvd> disneyPixar = dvdDao.searchByStudio("Walt Disney Pictures");
        Assert.assertEquals(2, disneyPixar.size());

        List<Dvd> disney = dvdDao.searchByStudio("Michael Scott");
        Assert.assertEquals(1, disney.size());

    }

    @Test
    public void testFindAverageAge() {

        dvdDao.insert(miracle);
        dvdDao.insert(moreThanAGame);
        dvdDao.insert(zootopya);
        dvdDao.insert(GodsNotDead);

        Double avgAge = dvdDao.averageAge();

        Assert.assertEquals(5.5, avgAge);

    }


    @Test
    public void testFindNewestDVD() {

        dvdDao.insert(miracle);
        dvdDao.insert(moreThanAGame);
        dvdDao.insert(zootopya);
        dvdDao.insert(GodsNotDead);

        List<Dvd> newestDvd = dvdDao.findNewestDvd();

        Assert.assertEquals(newestDvd.get(0).getTitle(), zootopya.getTitle());

    }

    @Test
    public void testFindOldestDVD() {

        dvdDao.insert(miracle);
        dvdDao.insert(moreThanAGame);
        dvdDao.insert(zootopya);
        dvdDao.insert(GodsNotDead);

        List<Dvd> oldestDvd = dvdDao.findOldestDvd();

        Assert.assertEquals(oldestDvd.get(0).getTitle(), miracle.getTitle());

    }

}
