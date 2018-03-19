/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thesoftwareguild.dvdlibraryweb.tests.dao;

import com.thesoftwareguild.dvdlibraryweb.dao.DVDDao;
import com.thesoftwareguild.dvdlibraryweb.dao.NoteDao;
import com.thesoftwareguild.dvdlibraryweb.dto.DVD;
import com.thesoftwareguild.dvdlibraryweb.dto.Note;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author paulharding
 */
public class NoteDaoJUnitTest {

    private NoteDao noteDao;
    private DVDDao dvdDao;

    private DVD miracle;
    private DVD zootopia;

    private Note miracleNote1;
    private Note miracleNote2;
    private Note zootopiaNote1;
    private Note zootopiaNote2;

    public NoteDaoJUnitTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-persistence.xml");
        noteDao = ctx.getBean("noteDao", NoteDao.class);
        dvdDao = ctx.getBean("dvdDao", DVDDao.class);

    }

    @Before
    public void setUp() {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-persistence.xml");

        JdbcTemplate cleaner = (JdbcTemplate) ctx.getBean("jdbcTemplate");
        cleaner.execute("DELETE FROM dvd");
        cleaner.execute("DELETE FROM note");

        miracle = new DVD();
        miracle.setTitle("Miracle");
        miracle.setReleaseDate(new Date((2004 - 1900), (02 - 1), 6));
        miracle.setMpaaRating("PG");
        miracle.setDirector("Gavin O'Connor");
        miracle.setStudio("Walt Disney Pictures");
        miracle.setUserRating(8);

        zootopia = new DVD();
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

        DVD addedDVD = dvdDao.create(miracle);
        Note addedNote = noteDao.create(miracleNote1);

        Assert.assertNotNull(addedNote);
        Assert.assertNotNull(addedNote.getNoteId());

        Assert.assertEquals("Awesome movie", addedNote.getNoteText());


    }

    @Test
    public void testRead() {

        DVD addedDVD = dvdDao.create(miracle);
        Note addedNote = noteDao.create(miracleNote2);

        Note readNote = noteDao.read(addedNote.getNoteId());

        Assert.assertNotNull(readNote);
        Assert.assertNotNull(readNote.getNoteId());

        Assert.assertEquals("Do you believe in miracles?", readNote.getNoteText());

    }

    @Test
    public void testUpdate() {

        DVD addedDVD = dvdDao.create(miracle);
        Note addedNote = noteDao.create(miracleNote2);

        Note readNote = noteDao.read(addedNote.getNoteId());

        readNote.setNoteText("17");
        noteDao.update(readNote);

        Note updatedNote = noteDao.read(readNote.getNoteId());

        Assert.assertNotNull(updatedNote);

        Assert.assertEquals("17", updatedNote.getNoteText());

    }

    @Test
    public void testDelete() {

        DVD addedDVD = dvdDao.create(miracle);
        Note addedNote = noteDao.create(miracleNote2);

        Note readNote = noteDao.read(addedNote.getNoteId());

        noteDao.delete(readNote);

        Note deletedNote = noteDao.read(readNote.getNoteId());

        Assert.assertNull(deletedNote);

    }

    @Test
    public void testFindByDVD() {

        DVD addedMiracle = dvdDao.create(miracle);

        Note addedMiracleNote1 = noteDao.create(miracleNote1);
        Note addedMiracleNote2 = noteDao.create(miracleNote2);

        List<Note> addedDVD1NoteList = noteDao.findByDVD(addedMiracle);

        Assert.assertNotNull(addedDVD1NoteList);
        Assert.assertEquals(2, addedDVD1NoteList.size());

        Assert.assertEquals(addedMiracleNote1.getNoteText(), addedDVD1NoteList.get(0).getNoteText());
        Assert.assertEquals(addedMiracleNote2.getNoteText(), addedDVD1NoteList.get(1).getNoteText());

    }
    /*
    @Test
    public void testGetAverageNumberOfNotes() {
        
        DVD testDVD1 = new DVD();
        testDVD1.setTitle("Test DVD 1");
        DVD testDVD2 = new DVD();
        testDVD2.setTitle("Test DVD 2");
        DVD testDVD3 = new DVD();
        testDVD3.setTitle("Test DVD 3");
        
        dvdDao.create(testDVD1);
        dvdDao.create(testDVD2);
        dvdDao.create(testDVD3);
        
        Note testNote = new Note();
        
        noteDao.create(testNote);
        
        double averageNumberOfNotes = noteDao.getAverageNumberOfNotes();
        
        Assert.assertEquals(0.33, averageNumberOfNotes, 2);
        
    }
    */

}
