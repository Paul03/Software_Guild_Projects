/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dvdlibrary.tests;

import com.mycompany.dvdlibrary.dao.DVDDao;
import com.mycompany.dvdlibrary.dao.NoteDao;
import com.mycompany.dvdlibrary.dto.DVD;
import com.mycompany.dvdlibrary.dto.Note;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author paulharding
 */
public class NoteDaoJUnitTest {
    
    NoteDao noteDao;
    DVDDao dvdDao;
    
    public NoteDaoJUnitTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        noteDao = ctx.getBean("noteDao", NoteDao.class);
        dvdDao = ctx.getBean("dvdDao", DVDDao.class);
        
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testCreate() {
        
        Note testNote = new Note();
        testNote.setNote("Awesome DVD");
        
        Note addedNote = noteDao.create(testNote);
        
        Assert.assertNotNull(addedNote);
        Assert.assertNotNull(addedNote.getId());
        
        Assert.assertEquals("Awesome DVD", addedNote.getNote());
        
        
    }
    
    @Test
    public void testRead() {
        
        Note testNote = new Note();
        testNote.setNote("Mediocre movie");
        
        Note addedNote = noteDao.create(testNote);
        
        Note readNote = noteDao.read(addedNote.getId());
        
        Assert.assertNotNull(readNote);
        Assert.assertNotNull(readNote.getId());
        
        Assert.assertEquals("Mediocre movie", readNote.getNote());
        
    }
    
    @Test
    public void testUpdate() {
        
        Note testNote = new Note();
        testNote.setNote("Sweetness");
        
        Note addedNote = noteDao.create(testNote);
        
        Note noteToUpdate = noteDao.read(addedNote.getId());
        
        noteToUpdate.setNote("17");
        noteDao.update(noteToUpdate);
        
        Note updatedNote = noteDao.read(noteToUpdate.getId());
        
        Assert.assertNotNull(updatedNote);
        
        Assert.assertEquals("17", updatedNote.getNote());
        
    }
    
    @Test
    public void testDelete() {
        
        Note testNote = new Note();
        testNote.setNote("Good movie");
        
        Note addedNote = noteDao.create(testNote);
        
        Note noteToDelete = noteDao.read(addedNote.getId());
        
        Assert.assertNotNull(noteToDelete);
        
        noteDao.delete(noteToDelete);
        
        Note deletedNote = noteDao.read(noteToDelete.getId());
        
        Assert.assertNull(deletedNote);
        
    }
    
    @Test
    public void testFindByDVD() {
        
        DVD testDVD = new DVD();
        testDVD.setName("Test DVD");
        
        dvdDao.create(testDVD);
        
        Note testNote1 = new Note();
        testNote1.setDvd(testDVD);
        
        Note testNote2 = new Note();
        testNote2.setDvd(testDVD);
        
        Note testNote3 = new Note();
        testNote3.setDvd(testDVD);
        
        noteDao.create(testNote1);
        noteDao.create(testNote2);
        noteDao.create(testNote3);
        
        List<Note> testDVDNotes = noteDao.findByDVD(testDVD);
        
        Assert.assertNotNull(testDVDNotes);
        Assert.assertEquals(3, testDVDNotes.size());
        
        Assert.assertEquals(testNote1, testDVDNotes.get(0));
        Assert.assertEquals(testNote2, testDVDNotes.get(1));
        Assert.assertEquals(testNote3, testDVDNotes.get(2));
        
    }
    
    @Test
    public void testGetAverageNumberOfNotes() {
        
        DVD testDVD1 = new DVD();
        testDVD1.setName("Test DVD 1");
        DVD testDVD2 = new DVD();
        testDVD2.setName("Test DVD 2");
        DVD testDVD3 = new DVD();
        testDVD3.setName("Test DVD 3");
        
        dvdDao.create(testDVD1);
        dvdDao.create(testDVD2);
        dvdDao.create(testDVD3);
        
        Note testNote = new Note();
        
        noteDao.create(testNote);
        
        double averageNumberOfNotes = noteDao.getAverageNumberOfNotes();
        
        Assert.assertEquals(0.33, averageNumberOfNotes, 2);
        
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
