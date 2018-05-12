/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thesoftwareguild.dvdlibraryweb.tests.dao;

import com.thesoftwareguild.dvdlibraryweb.dao.DvdDao;
import com.thesoftwareguild.dvdlibraryweb.dao.NoteDao;
import com.thesoftwareguild.dvdlibraryweb.dto.Dvd;
import com.thesoftwareguild.dvdlibraryweb.dto.Note;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author paulharding
 */
public class NoteDaoJUnitTest {

    private NoteDao noteDao;
    private DvdDao dvdDao;

    private Dvd miracle;
    private Dvd zootopia;

    private Note miracleNote1;
    private Note miracleNote2;
    private Note zootopiaNote1;
    private Note zootopiaNote2;

    public NoteDaoJUnitTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-persistence.xml");
        noteDao = ctx.getBean("noteDao", NoteDao.class);
        dvdDao = ctx.getBean("dvdDao", DvdDao.class);

    }

    @Before
    public void setUp() {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-persistence.xml");

        JdbcTemplate cleaner = (JdbcTemplate) ctx.getBean("jdbcTemplate");
        cleaner.execute("DELETE FROM dvd");
        cleaner.execute("DELETE FROM note");

        miracle = new Dvd();
        miracle.setTitle("Miracle");
        miracle.setReleaseDate(new Date((2004 - 1900), (02 - 1), 6));
        miracle.setMpaaRating("PG");
        miracle.setDirector("Gavin O'Connor");
        miracle.setStudio("Walt Disney Pictures");
        miracle.setUserRating(8);

        zootopia = new Dvd();
        zootopia.setTitle("Zootopya"); // spelled wrong on purpose
        zootopia.setReleaseDate(new Date((2016 - 1900), (3 - 1), 4));
        zootopia.setMpaaRating("PG");
        zootopia.setDirector("Byron Howard");
        zootopia.setStudio("Walt Disney Pictures");
        zootopia.setUserRating(8);

        miracleNote1 = new Note();
        miracleNote1.setNoteText("Awesome movie");
        miracleNote1.setDvd(miracle);

        miracleNote2 = new Note();
        miracleNote2.setNoteText("Do you believe in miracles?");
        miracleNote2.setDvd(miracle);

        zootopiaNote1 = new Note();
        zootopiaNote1.setNoteText("Good movie");
        zootopiaNote1.setDvd(zootopia);

        zootopiaNote2 = new Note();
        zootopiaNote2.setNoteText("Yay! movie");
        zootopiaNote2.setDvd(zootopia);

    }

    @After
    public void tearDown() {
    }

    @Test
    public void testCreate() {

        Dvd addedDvd = dvdDao.insert(miracle);
        Note addedNote = noteDao.insert(miracleNote1);

        Assert.assertNotNull(addedNote);
        Assert.assertNotNull(addedNote.getNoteId());

        Assert.assertEquals("Awesome movie", addedNote.getNoteText());


    }

    @Test
    public void testRead() {

        Dvd addedDvd = dvdDao.insert(miracle);
        Note addedNote = noteDao.insert(miracleNote2);

        Note readNote = noteDao.retrieve(addedNote.getNoteId());

        Assert.assertNotNull(readNote);
        Assert.assertNotNull(readNote.getNoteId());

        Assert.assertEquals("Do you believe in miracles?", readNote.getNoteText());

    }

    @Test
    public void testUpdate() {

        Dvd addedDvd = dvdDao.insert(miracle);
        Note addedNote = noteDao.insert(miracleNote2);

        Note readNote = noteDao.retrieve(addedNote.getNoteId());

        readNote.setNoteText("17");
        noteDao.update(readNote);

        Note updatedNote = noteDao.retrieve(readNote.getNoteId());

        Assert.assertNotNull(updatedNote);

        Assert.assertEquals("17", updatedNote.getNoteText());

    }

    @Test
    public void testDelete() {

        Dvd addedDvd = dvdDao.insert(miracle);
        Note addedNote = noteDao.insert(miracleNote2);

        Note readNote = noteDao.retrieve(addedNote.getNoteId());

        noteDao.delete(readNote);

        Note deletedNote = noteDao.retrieve(readNote.getNoteId());

        Assert.assertNull(deletedNote);

    }

    @Test
    public void testFindByDVD() {

        Dvd addedMiracle = dvdDao.insert(miracle);

        Note addedMiracleNote1 = noteDao.insert(miracleNote1);
        Note addedMiracleNote2 = noteDao.insert(miracleNote2);

        List<Note> addedDVD1NoteList = noteDao.findByDvd(addedMiracle);

        Assert.assertNotNull(addedDVD1NoteList);
        Assert.assertEquals(2, addedDVD1NoteList.size());

        Assert.assertEquals(addedMiracleNote1.getNoteText(), addedDVD1NoteList.get(0).getNoteText());
        Assert.assertEquals(addedMiracleNote2.getNoteText(), addedDVD1NoteList.get(1).getNoteText());

    }
    /*
    @Test
    public void testGetAverageNumberOfNotes() {

        Dvd testDVD1 = new Dvd();
        testDVD1.setTitle("Test Dvd 1");
        Dvd testDVD2 = new Dvd();
        testDVD2.setTitle("Test Dvd 2");
        Dvd testDVD3 = new Dvd();
        testDVD3.setTitle("Test Dvd 3");

        dvdDao.insert(testDVD1);
        dvdDao.insert(testDVD2);
        dvdDao.insert(testDVD3);

        Note testNote = new Note();

        noteDao.insert(testNote);

        double averageNumberOfNotes = noteDao.getAverageNumberOfNotes();

        Assert.assertEquals(0.33, averageNumberOfNotes, 2);

    }
    */

}
