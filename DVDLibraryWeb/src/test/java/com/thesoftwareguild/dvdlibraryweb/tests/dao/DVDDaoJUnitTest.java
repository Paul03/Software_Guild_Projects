/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thesoftwareguild.dvdlibraryweb.tests.dao;

import com.thesoftwareguild.dvdlibraryweb.dao.DVDDao;
import com.thesoftwareguild.dvdlibraryweb.dto.DVD;

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
public class DVDDaoJUnitTest {

    private DVDDao dvdDao;

    private DVD miracle;
    private DVD moreThanAGame;
    private DVD zootopya;
    private DVD GodsNotDead;

    public DVDDaoJUnitTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-persistence.xml");
        dvdDao = ctx.getBean("dvdDao", DVDDao.class);
    }

    @Before
    public void setUp() {
        
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-persistence.xml");

        JdbcTemplate cleaner = (JdbcTemplate) ctx.getBean("jdbcTemplate");
        cleaner.execute("DELETE FROM dvd");

        miracle = new DVD();
        miracle.setTitle("Miracle");
        miracle.setReleaseDate( new Date( (2004 - 1900) , (02 - 1) , 6) );
        miracle.setMpaaRating("PG");
        miracle.setDirector("Gavin O'Connor");
        miracle.setStudio("Walt Disney Pictures");
        miracle.setUserRating(8);

        moreThanAGame = new DVD();
        moreThanAGame.setTitle("More Than A Game");
        moreThanAGame.setReleaseDate( new Date( (2008 - 1900), (10 - 1) , 2 ) );
        moreThanAGame.setMpaaRating("PG");
        moreThanAGame.setDirector("Kristopher Belman");
        moreThanAGame.setStudio("Harvey Mason Jr");
        moreThanAGame.setUserRating(7);

        zootopya = new DVD();
        zootopya.setTitle("Zootopya"); // spelled wrong on purpose
        zootopya.setReleaseDate( new Date( (2016 - 1900), (3 - 1) , 4 ) );
        zootopya.setMpaaRating("PG");
        zootopya.setDirector("Byron Howard");
        zootopya.setStudio("Walt Disney Pictures");
        zootopya.setUserRating(8);

        GodsNotDead = new DVD();
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

        DVD addedDVD = dvdDao.create(miracle);

        Assert.assertNotNull(addedDVD);
        Assert.assertNotNull(addedDVD.getDvdId());

        Integer expectedYear = 2004;
        Integer actualYear = addedDVD.getReleaseDate().getYear() + 1900;

        Assert.assertEquals("Miracle", addedDVD.getTitle());
        Assert.assertEquals(expectedYear, actualYear);

    }

    @Test
    public void testReadDvd() {

        DVD addedDVD = dvdDao.create(moreThanAGame);

        DVD readDVD = dvdDao.read(addedDVD.getDvdId());

        Assert.assertNotNull(readDVD);
        Assert.assertNotNull(readDVD.getDvdId());

        Integer expectedYear = 2008;
        Integer actualYear = readDVD.getReleaseDate().getYear() + 1900;

        Assert.assertEquals("More Than A Game", readDVD.getTitle());
        Assert.assertEquals(expectedYear, actualYear);


    }

    @Test
    public void testUpdateDvd() {

        DVD addedDVD = dvdDao.create(zootopya);

        DVD readDVD = dvdDao.read(addedDVD.getDvdId());
        readDVD.setTitle("Zootopia");

        dvdDao.update(readDVD);

        DVD updatedDVD = dvdDao.read(readDVD.getDvdId());

        Assert.assertNotNull(updatedDVD);
        Assert.assertEquals(readDVD.getDvdId(), updatedDVD.getDvdId());
        Assert.assertNotSame(addedDVD, updatedDVD);

        Assert.assertEquals("Zootopia", updatedDVD.getTitle());

    }

    @Test
    public void testDeleteDvd() {

        DVD addedDVD = dvdDao.create(GodsNotDead);

        DVD readDVD = dvdDao.read(addedDVD.getDvdId());

        dvdDao.delete(readDVD);

        try {
        DVD deletedDVD = dvdDao.read(addedDVD.getDvdId());
        Assert.assertNull(readDVD); // I want to put something here that is NOT Null
                                    // (if it reaches this point, then the test should fail)
        
        } catch(EmptyResultDataAccessException ex) {
            
        }
        
    }

    @Test
    public void testList() {

        DVD addedDVD1 = dvdDao.create(GodsNotDead);
        DVD addedDVD2 = dvdDao.create(zootopya);

        List<DVD> dvdList = dvdDao.list();

        Assert.assertEquals(2, dvdList.size());

        dvdList.add(miracle);

        List<DVD> dvdList2 = dvdDao.list();

        Assert.assertNotSame(dvdList.size(), dvdList2.size());

    }

    @Test
    public void testSearchLastNYears5Yrs() {

        dvdDao.create(miracle);
        dvdDao.create(moreThanAGame);
        dvdDao.create(zootopya);
        dvdDao.create(GodsNotDead);

        List<DVD> last5Yrs = dvdDao.searchLastNYears(5);
        Assert.assertEquals(2, last5Yrs.size());

    }

    @Test
    public void testSearchLastNYears10Yrs() {

        dvdDao.create(miracle);
        dvdDao.create(moreThanAGame);
        dvdDao.create(zootopya);
        dvdDao.create(GodsNotDead);

        List<DVD> last10Yrs = dvdDao.searchLastNYears(10);
        Assert.assertEquals(3, last10Yrs.size());

    }

    @Test
    public void testSearchLastNYears20Yrs() {

        dvdDao.create(miracle);
        dvdDao.create(moreThanAGame);
        dvdDao.create(zootopya);
        dvdDao.create(GodsNotDead);

        List<DVD> last20Yrs = dvdDao.searchLastNYears(20);
        Assert.assertEquals(4, last20Yrs.size());

    }

    @Test
    public void testSearchByMPAARatingG() {

        dvdDao.create(miracle);
        dvdDao.create(moreThanAGame);
        dvdDao.create(zootopya);
        dvdDao.create(GodsNotDead);

        List<DVD> gRatedDVDs = dvdDao.searchByMPAARating("G");
        Assert.assertEquals(0, gRatedDVDs.size());

    }

    @Test
    public void testSearchByMPAARatingPG() {

        dvdDao.create(miracle);
        dvdDao.create(moreThanAGame);
        dvdDao.create(zootopya);
        dvdDao.create(GodsNotDead);

        List<DVD> pgRatedDVDs = dvdDao.searchByMPAARating("PG");
        Assert.assertEquals(4, pgRatedDVDs.size());

    }

    @Test
    public void testSearchByDirector() {

        dvdDao.create(miracle);
        dvdDao.create(moreThanAGame);
        dvdDao.create(zootopya);
        dvdDao.create(GodsNotDead);

        List<DVD> johnLasseterDVDs = dvdDao.searchByDirector("Harold Cronk");
        Assert.assertEquals(1, johnLasseterDVDs.size());

        List<DVD> byronHowardDVDs = dvdDao.searchByDirector("Byron Howard");
        Assert.assertEquals(1, byronHowardDVDs.size());

    }
/*
    @Test
    public void testSearchByDirectorSort() {

        // Create some DVD objects
        DVD lasseterTest = new DVD();
        lasseterTest.setDirector("John Lasseter");
        lasseterTest.setMpaaRating("PG");

        DVD toyStory = new DVD();
        toyStory.setTitle("Toy Story");
        toyStory.setDirector("John Lasseter");
        toyStory.setMpaaRating("G");

        DVD toyStory2 = new DVD();
        toyStory2.setTitle("Toy Story 2");
        toyStory2.setDirector("John Lasseter");
        toyStory2.setMpaaRating("G");

        DVD verbinskiTest = new DVD();
        verbinskiTest.setDirector("Gore Verbinski");
        verbinskiTest.setMpaaRating("G");

        DVD pirates = new DVD();
        pirates.setTitle("Pirates 1");
        pirates.setDirector("Gore Verbinski");
        pirates.setMpaaRating("PG-13");

        DVD pirates2 = new DVD();
        pirates2.setTitle("Pirates 2");
        pirates2.setDirector("Gore Verbinski");
        pirates2.setMpaaRating("PG-13");

        // Add them to the Dao's list
        dvdDao.create(lasseterTest);
        dvdDao.create(toyStory);
        dvdDao.create(toyStory2);
        dvdDao.create(verbinskiTest);
        dvdDao.create(pirates);
        dvdDao.create(pirates2);

        List<DVD> johnLasseterDVDs = dvdDao.searchByDirector("John Lasseter");
        List<DVD> goreVerbinskiDVDs = dvdDao.searchByDirector("Gore Verbinski");

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

        dvdDao.create(miracle);
        dvdDao.create(moreThanAGame);
        dvdDao.create(zootopya);
        dvdDao.create(GodsNotDead);

        List<DVD> disneyPixar = dvdDao.searchByStudio("Walt Disney Pictures");
        Assert.assertEquals(2, disneyPixar.size());

        List<DVD> disney = dvdDao.searchByStudio("Michael Scott");
        Assert.assertEquals(1, disney.size());

    }

    @Test
    public void testFindAverageAge() {

        dvdDao.create(miracle);
        dvdDao.create(moreThanAGame);
        dvdDao.create(zootopya);
        dvdDao.create(GodsNotDead);

        Double avgAge = dvdDao.averageAge();

        Assert.assertEquals(5.5, avgAge);

    }


    @Test
    public void testFindNewestDVD() {

        dvdDao.create(miracle);
        dvdDao.create(moreThanAGame);
        dvdDao.create(zootopya);
        dvdDao.create(GodsNotDead);

        List<DVD> newestDVD = dvdDao.findNewestDVD();

        Assert.assertEquals(newestDVD.get(0).getTitle(), zootopya.getTitle());

    }

    @Test
    public void testFindOldestDVD() {

        dvdDao.create(miracle);
        dvdDao.create(moreThanAGame);
        dvdDao.create(zootopya);
        dvdDao.create(GodsNotDead);

        List<DVD> oldestDVD = dvdDao.findOldestDVD();

        Assert.assertEquals(oldestDVD.get(0).getTitle(), miracle.getTitle());

    }

}
