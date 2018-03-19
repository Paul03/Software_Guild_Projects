/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dvdlibrary.tests;

import com.mycompany.dvdlibrary.dao.DVDDao;
import com.mycompany.dvdlibrary.dto.DVD;
import java.util.List;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author paulharding
 */
public class DVDDaoJUnitTest {

    DVDDao dvdDao;

    public DVDDaoJUnitTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        dvdDao = ctx.getBean("dvdDaoLambda", DVDDao.class);
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAddDvd() {

        DVD myDvd = new DVD(); // Create new DVD object

        myDvd.setName("Miracle"); // Fill in some attributes of myDvd
        myDvd.setReleaseYear(2004);

        DVD addedDvd = dvdDao.create(myDvd);

        Integer expectedYear = 2004;

        Assert.assertNotNull(addedDvd.getName());
        Assert.assertEquals(expectedYear, addedDvd.getReleaseYear());

        Assert.assertNotNull(addedDvd.getId());

    }

    @Test
    public void testReadDvd() {

        DVD myDvd = new DVD(); // Create new DVD object
        myDvd.setName("More Than A Game");
        myDvd.setReleaseYear(2008);

        DVD addedDVD = dvdDao.create(myDvd); // Pass myDvd to the dao to give it an id

        Assert.assertNotNull(myDvd);
        Assert.assertNotNull(addedDVD.getId());
        Assert.assertEquals("More Than A Game", addedDVD.getName());

        DVD readDVD = dvdDao.read(addedDVD.getId()); // Pass the id into the read method

        Assert.assertEquals("More Than A Game", readDVD.getName());
        Assert.assertNotNull(readDVD.getId());

    }

    @Test
    public void testUpdateDvd() {

        DVD myDvd = new DVD(); // Create a new DVD object
        myDvd.setName("Zootopya");
        myDvd.setReleaseYear(2016);

        DVD addedDvd = dvdDao.create(myDvd); // Pass myDvd to dao - put it in the List, give it an id

        DVD readDvd = dvdDao.read(addedDvd.getId());
        readDvd.setName("Zootopia");

        dvdDao.update(readDvd); // Pass readDvd to update

        DVD resultDvd = dvdDao.read(addedDvd.getId()); // Read the DVD in the library with the id we are working with
        String resultName = resultDvd.getName(); // Get the name of the DVD with the key of the DVD we updated

        Assert.assertEquals("Zootopia", resultName);

    }

    @Test
    public void testDeleteDvd() {

        DVD myDvd = new DVD(); // Create a new DVD object
        myDvd.setName("God's Not Dead");
        myDvd.setReleaseYear(2014);

        DVD addedDvd = dvdDao.create(myDvd); // Pass myDvd to dao - put it in the HashMap, give it an id

        dvdDao.delete(addedDvd);

        DVD readDvd = dvdDao.read(addedDvd.getId()); // Attempt to read DVD with the id where addedDvd was (hopefully it returns null)

        Assert.assertNull(readDvd);

    }

    @Test
    public void testList() {

        DVD myDvd = new DVD(); // Create a new DVD object
        myDvd.setName("God's Not Dead");
        myDvd.setReleaseYear(2014);

        DVD myDvd2 = new DVD(); // Create a second new DVD object
        myDvd2.setName("Zootopya");
        myDvd2.setReleaseYear(2016);

        DVD addedDvd1 = dvdDao.create(myDvd); // Add both DVDs to the List
        DVD addedDvd2 = dvdDao.create(myDvd2);

        List<DVD> testList = dvdDao.list(); // Get a copy of the List

        DVD replaceDvd = dvdDao.read(addedDvd1.getId()); // Create a new DVD to replace id 1 of testList
        replaceDvd.setName("More Than A Game");
        replaceDvd.setReleaseYear(2008);

        dvdDao.update(replaceDvd);

        DVD readDvd = dvdDao.read(addedDvd1.getId());

        Assert.assertFalse(readDvd.getName().equals(testList.get(1))); // this shows that the list method returns a list that does not point
        // to the same place in memory as the DAO's list

    }

    @Test
    public void testSearchLastNYears5Yrs() {

        // Create some DVD objects
        DVD toyStory = new DVD();
        toyStory.setName("Toy Story");
        toyStory.setReleaseYear(1995);

        DVD toyStory2 = new DVD();
        toyStory2.setName("Toy Story 2");
        toyStory2.setReleaseYear(1999);

        DVD pirates = new DVD();
        pirates.setName("Pirates 1");
        pirates.setReleaseYear(2003);

        DVD toyStory3 = new DVD();
        toyStory3.setName("Toy Story 3");
        toyStory3.setReleaseYear(2010);

        DVD zootopia = new DVD();
        zootopia.setName("Zootopia");
        zootopia.setReleaseYear(2016);

        // Add them to the Dao's list
        dvdDao.create(toyStory);
        dvdDao.create(toyStory2);
        dvdDao.create(pirates);
        dvdDao.create(toyStory3);
        dvdDao.create(zootopia);

        List<DVD> last5Yrs = dvdDao.searchLastNYears(5);
        Assert.assertEquals(1, last5Yrs.size());

    }

    @Test
    public void testSearchLastNYears10Yrs() {

        // Create some DVD objects
        DVD toyStory = new DVD();
        toyStory.setName("Toy Story");
        toyStory.setReleaseYear(1995);

        DVD toyStory2 = new DVD();
        toyStory2.setName("Toy Story 2");
        toyStory2.setReleaseYear(1999);

        DVD pirates = new DVD();
        pirates.setName("Pirates 1");
        pirates.setReleaseYear(2003);

        DVD toyStory3 = new DVD();
        toyStory3.setName("Toy Story 3");
        toyStory3.setReleaseYear(2010);

        DVD zootopia = new DVD();
        zootopia.setName("Zootopia");
        zootopia.setReleaseYear(2016);

        // Add them to the Dao's list
        dvdDao.create(toyStory);
        dvdDao.create(toyStory2);
        dvdDao.create(pirates);
        dvdDao.create(toyStory3);
        dvdDao.create(zootopia);

        List<DVD> last10Yrs = dvdDao.searchLastNYears(10);
        Assert.assertEquals(2, last10Yrs.size());

    }

    @Test
    public void testSearchLastNYears20Yrs() {

        // Create some DVD objects
        DVD toyStory = new DVD();
        toyStory.setName("Toy Story");
        toyStory.setReleaseYear(1995);

        DVD toyStory2 = new DVD();
        toyStory2.setName("Toy Story 2");
        toyStory2.setReleaseYear(1999);

        DVD pirates = new DVD();
        pirates.setName("Pirates 1");
        pirates.setReleaseYear(2003);

        DVD toyStory3 = new DVD();
        toyStory3.setName("Toy Story 3");
        toyStory3.setReleaseYear(2010);

        DVD zootopia = new DVD();
        zootopia.setName("Zootopia");
        zootopia.setReleaseYear(2016);

        // Add them to the Dao's list
        dvdDao.create(toyStory);
        dvdDao.create(toyStory2);
        dvdDao.create(pirates);
        dvdDao.create(toyStory3);
        dvdDao.create(zootopia);

        List<DVD> last20Yrs = dvdDao.searchLastNYears(20);
        Assert.assertEquals(4, last20Yrs.size());

    }

    @Test
    public void testSearchLastNYearsNull() {

        // Create some DVD objects
        DVD toyStory = new DVD();
        toyStory.setName("Toy Story");

        DVD toyStory2 = new DVD();
        toyStory2.setName("Toy Story 2");

        // Add them to the Dao's list
        dvdDao.create(toyStory);
        dvdDao.create(toyStory2);

        List<DVD> last20Yrs = dvdDao.searchLastNYears(20);
        Assert.assertEquals(0, last20Yrs.size());

    }

    @Test
    public void testSearchByMPAARatingG() {

        // Create some DVD objects
        DVD toyStory = new DVD();
        toyStory.setName("Toy Story");
        toyStory.setMpaaRating("G");

        DVD toyStory2 = new DVD();
        toyStory2.setName("Toy Story 2");
        toyStory2.setMpaaRating("G");

        DVD pirates = new DVD();
        pirates.setName("Pirates 1");
        pirates.setMpaaRating("PG-13");

        DVD toyStory3 = new DVD();
        toyStory3.setName("Toy Story 3");
        toyStory3.setMpaaRating("G");

        DVD zootopia = new DVD();
        zootopia.setName("Zootopia");
        zootopia.setMpaaRating("PG");

        // Add them to the Dao's list
        dvdDao.create(toyStory);
        dvdDao.create(toyStory2);
        dvdDao.create(pirates);
        dvdDao.create(toyStory3);
        dvdDao.create(zootopia);

        List<DVD> gRatedDVDs = dvdDao.searchByMPAARating("G");
        Assert.assertEquals(3, gRatedDVDs.size());

    }

    @Test
    public void testSearchByMPAARatingPG() {

        // Create some DVD objects
        DVD toyStory = new DVD();
        toyStory.setName("Toy Story");
        toyStory.setMpaaRating("G");

        DVD toyStory2 = new DVD();
        toyStory2.setName("Toy Story 2");
        toyStory2.setMpaaRating("G");

        DVD pirates = new DVD();
        pirates.setName("Pirates 1");
        pirates.setMpaaRating("PG-13");

        DVD toyStory3 = new DVD();
        toyStory3.setName("Toy Story 3");
        toyStory3.setMpaaRating("G");

        DVD zootopia = new DVD();
        zootopia.setName("Zootopia");
        zootopia.setMpaaRating("PG");

        // Add them to the Dao's list
        dvdDao.create(toyStory);
        dvdDao.create(toyStory2);
        dvdDao.create(pirates);
        dvdDao.create(toyStory3);
        dvdDao.create(zootopia);

        List<DVD> pgRatedDVDs = dvdDao.searchByMPAARating("PG");
        Assert.assertEquals(1, pgRatedDVDs.size());

    }

    @Test
    public void testSearchByMPAARatingNull() {

        // Create some DVD objects
        DVD toyStory = new DVD();
        toyStory.setName("Toy Story");

        DVD toyStory2 = new DVD();
        toyStory2.setName("Toy Story 2");

        // Add them to the Dao's list
        dvdDao.create(toyStory);
        dvdDao.create(toyStory2);

        List<DVD> gRatedDVDs = dvdDao.searchByMPAARating("G");
        Assert.assertEquals(0, gRatedDVDs.size());

    }

    @Test
    public void testSearchByDirector() {

        // Create some DVD objects
        DVD toyStory = new DVD();
        toyStory.setName("Toy Story");
        toyStory.setDirectorName("John Lasseter");

        DVD toyStory2 = new DVD();
        toyStory2.setName("Toy Story 2");
        toyStory2.setDirectorName("John Lasseter");

        DVD pirates = new DVD();
        pirates.setName("Pirates 1");
        pirates.setDirectorName("Gore Verbinski");

        DVD toyStory3 = new DVD();
        toyStory3.setName("Toy Story 3");
        toyStory3.setDirectorName("Lee Unkrich");

        DVD zootopia = new DVD();
        zootopia.setName("Zootopia");
        zootopia.setDirectorName("Byron Howard");

        // Add them to the Dao's list
        dvdDao.create(toyStory);
        dvdDao.create(toyStory2);
        dvdDao.create(pirates);
        dvdDao.create(toyStory3);
        dvdDao.create(zootopia);

        List<DVD> johnLasseterDVDs = dvdDao.searchByDirector("John Lasseter");
        Assert.assertEquals(2, johnLasseterDVDs.size());

        List<DVD> byronHowardDVDs = dvdDao.searchByDirector("Byron Howard");
        Assert.assertEquals(1, byronHowardDVDs.size());

    }

    @Test
    public void testSearchByDirectorNull() {

        // Create some DVD objects
        DVD toyStory = new DVD();
        toyStory.setName("Toy Story");

        DVD toyStory2 = new DVD();
        toyStory2.setName("Toy Story 2");

        // Add them to the Dao's list
        dvdDao.create(toyStory);
        dvdDao.create(toyStory2);

        List<DVD> johnLasseterDVDs = dvdDao.searchByDirector("John Lasseter");
        Assert.assertNotNull(johnLasseterDVDs);
        Assert.assertEquals(0, johnLasseterDVDs.size());

    }

    @Test
    public void testSearchByDirectorSort() {

        // Create some DVD objects
        DVD lasseterTest = new DVD();
        lasseterTest.setDirectorName("John Lasseter");
        lasseterTest.setMpaaRating("PG");

        DVD toyStory = new DVD();
        toyStory.setName("Toy Story");
        toyStory.setDirectorName("John Lasseter");
        toyStory.setMpaaRating("G");

        DVD toyStory2 = new DVD();
        toyStory2.setName("Toy Story 2");
        toyStory2.setDirectorName("John Lasseter");
        toyStory2.setMpaaRating("G");

        DVD verbinskiTest = new DVD();
        verbinskiTest.setDirectorName("Gore Verbinski");
        verbinskiTest.setMpaaRating("G");

        DVD pirates = new DVD();
        pirates.setName("Pirates 1");
        pirates.setDirectorName("Gore Verbinski");
        pirates.setMpaaRating("PG-13");

        DVD pirates2 = new DVD();
        pirates2.setName("Pirates 2");
        pirates2.setDirectorName("Gore Verbinski");
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

    @Test
    public void testSearchByDirectorSortNull() {

        // Create some DVD objects
        DVD lasseterTest = new DVD();
        lasseterTest.setDirectorName("John Lasseter");
        lasseterTest.setMpaaRating("PG");

        DVD toyStory = new DVD();
        toyStory.setName("Toy Story");
        toyStory.setDirectorName("John Lasseter");

        DVD toyStory2 = new DVD();
        toyStory2.setName("Toy Story 2");
        toyStory2.setDirectorName("John Lasseter");
        toyStory2.setMpaaRating("G");

        DVD verbinskiTest = new DVD();
        verbinskiTest.setDirectorName("Gore Verbinski");
        verbinskiTest.setMpaaRating("G");

        DVD pirates = new DVD();
        pirates.setName("Pirates 1");
        pirates.setDirectorName("Gore Verbinski");

        DVD pirates2 = new DVD();
        pirates2.setName("Pirates 2");
        pirates2.setDirectorName("Gore Verbinski");
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
        Assert.assertEquals("PG", johnLasseterDVDs.get(1).getMpaaRating());
        Assert.assertNull(johnLasseterDVDs.get(2).getMpaaRating());

        Assert.assertEquals("G", goreVerbinskiDVDs.get(0).getMpaaRating());
        Assert.assertEquals("PG-13", goreVerbinskiDVDs.get(1).getMpaaRating());
        Assert.assertNull(goreVerbinskiDVDs.get(2).getMpaaRating());

    }

    @Test
    public void testSearchByStudio() {

        // Create some DVD objects
        DVD toyStory = new DVD();
        toyStory.setName("Toy Story");
        toyStory.setStudioName("Disney Pixar");

        DVD toyStory2 = new DVD();
        toyStory2.setName("Toy Story 2");
        toyStory2.setStudioName("Disney Pixar");

        DVD pirates = new DVD();
        pirates.setName("Pirates 1");
        pirates.setStudioName("Disney");

        DVD toyStory3 = new DVD();
        toyStory3.setName("Toy Story 3");
        toyStory3.setStudioName("Disney Pixar");

        DVD zootopia = new DVD();
        zootopia.setName("Zootopia");
        zootopia.setStudioName("Disney");

        // Add them to the Dao's list
        dvdDao.create(toyStory);
        dvdDao.create(toyStory2);
        dvdDao.create(pirates);
        dvdDao.create(toyStory3);
        dvdDao.create(zootopia);

        List<DVD> disneyPixar = dvdDao.searchByStudio("Disney Pixar");
        Assert.assertEquals(3, disneyPixar.size());

        List<DVD> disney = dvdDao.searchByStudio("Disney");
        Assert.assertEquals(2, disney.size());

    }

    @Test
    public void testSearchByStudioNull() {

        // Create some DVD objects
        DVD toyStory = new DVD();
        toyStory.setName("Toy Story");

        DVD toyStory2 = new DVD();
        toyStory2.setName("Toy Story 2");

        // Add them to the Dao's list
        dvdDao.create(toyStory);
        dvdDao.create(toyStory2);

        List<DVD> disneyPixar = dvdDao.searchByStudio("Disney Pixar");
        Assert.assertEquals(0, disneyPixar.size());

    }

    @Test
    public void testFindAverageAge() {

        DVD toyStory = new DVD();
        toyStory.setReleaseYear(1995);

        DVD toyStory2 = new DVD();
        toyStory2.setReleaseYear(1999);

        dvdDao.create(toyStory);
        dvdDao.create(toyStory2);

        Double avgAge = dvdDao.averageAge();

        Assert.assertEquals(19.0, avgAge);

    }

    @Test
    public void testFindAverageAgeNull() {

        DVD toyStory = new DVD();
        toyStory.setReleaseYear(1995); // age is 21

        DVD toyStory2 = new DVD();
        toyStory2.setReleaseYear(1999); // age is 17

        DVD zootopia = new DVD();
        zootopia.setReleaseYear(2016); // age is 0

        DVD pirates = new DVD(); // this has no release year associated with it, should not be counted in average age

        DVD toyStory3 = new DVD();
        toyStory3.setReleaseYear(2010); // age is 6

        dvdDao.create(toyStory);
        dvdDao.create(toyStory2);
        dvdDao.create(zootopia);
        dvdDao.create(pirates);
        dvdDao.create(toyStory3);

        Double avgAge = dvdDao.averageAge();

        Assert.assertEquals(11.0, avgAge);

    }

    @Test
    public void testFindNewestDVD() {

        // Create some DVD objects
        DVD toyStory = new DVD();
        toyStory.setName("Toy Story");
        toyStory.setReleaseYear(1995);

        DVD toyStory2 = new DVD();
        toyStory2.setName("Toy Story 2");
        toyStory2.setReleaseYear(1999);

        DVD pirates = new DVD();
        pirates.setName("Pirates 1");
        pirates.setReleaseYear(2003);

        DVD toyStory3 = new DVD();
        toyStory3.setName("Toy Story 3");
        toyStory3.setReleaseYear(2010);

        DVD zootopia = new DVD();
        zootopia.setName("Zootopia");
        zootopia.setReleaseYear(2016);

        DVD awesome = new DVD();
        awesome.setName("Awesome Test Movie");
        awesome.setReleaseYear(2016);

        // Add them to the Dao's list
        dvdDao.create(toyStory);
        dvdDao.create(toyStory2);
        dvdDao.create(pirates);
        dvdDao.create(toyStory3);
        dvdDao.create(zootopia);
        dvdDao.create(awesome);

        List<DVD> newestDVD = dvdDao.findNewestDVD();

        Assert.assertEquals(2, newestDVD.size());

    }

    @Test
    public void testFindNewestNull() {

        DVD toyStory = new DVD();
        toyStory.setName("Toy Story");

        DVD toyStory2 = new DVD();
        toyStory2.setName("Toy Story 2");

        dvdDao.create(toyStory);
        dvdDao.create(toyStory2);

        List<DVD> newestDVD = dvdDao.findNewestDVD();

        Assert.assertNotNull(newestDVD);
        Assert.assertEquals(0, newestDVD.size());

    }

    @Test
    public void testFindOldestDVD() {

        // Create some DVD objects
        DVD toyStory = new DVD();
        toyStory.setName("Toy Story");
        toyStory.setReleaseYear(1995);

        DVD awesome = new DVD();
        awesome.setName("Awesome Test Movie");
        awesome.setReleaseYear(1995);

        DVD toyStory2 = new DVD();
        toyStory2.setName("Toy Story 2");
        toyStory2.setReleaseYear(1999);

        DVD pirates = new DVD();
        pirates.setName("Pirates 1");
        pirates.setReleaseYear(2003);

        DVD toyStory3 = new DVD();
        toyStory3.setName("Toy Story 3");
        toyStory3.setReleaseYear(2010);

        DVD zootopia = new DVD();
        zootopia.setName("Zootopia");
        zootopia.setReleaseYear(2016);

        // Add them to the Dao's list
        dvdDao.create(toyStory);
        dvdDao.create(awesome);
        dvdDao.create(toyStory2);
        dvdDao.create(pirates);
        dvdDao.create(toyStory3);
        dvdDao.create(zootopia);

        List<DVD> oldestDVD = dvdDao.findOldestDVD();

        Assert.assertEquals(2, oldestDVD.size());

    }

    @Test
    public void testFindOldestNull() {

        DVD toyStory = new DVD();
        toyStory.setName("Toy Story");

        DVD toyStory2 = new DVD();
        toyStory2.setName("Toy Story 2");

        dvdDao.create(toyStory);
        dvdDao.create(toyStory2);

        List<DVD> oldestDVD = dvdDao.findOldestDVD();

        Assert.assertNotNull(oldestDVD);
        Assert.assertEquals(0, oldestDVD.size());

    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
