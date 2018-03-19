/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dvdlibrary.tests;

import com.mycompany.dvdlibrary.controller.DVDController;
import com.mycompany.dvdlibrary.dao.DVDDao;
import com.mycompany.dvdlibrary.dao.NoteDao;
import com.mycompany.dvdlibrary.dto.DVD;
import com.mycompany.dvdlibrary.dto.Note;
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
public class DVDControllerJUnitTest {
    
    DVDDao dvdDao;
    NoteDao noteDao;
    DVDController dc;
    
    public DVDControllerJUnitTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        dvdDao = ctx.getBean("dvdDao", DVDDao.class);
        noteDao = ctx.getBean("noteDao", NoteDao.class);
        dc = ctx.getBean("dvdController", DVDController.class);
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testAddDVD() {
        
        String name = "Miracle";
        Integer year = 2004;
        
        DVD addedDVD = dc.addDVD(name, year);
        
        Assert.assertEquals("Miracle", addedDVD.getName());
        Assert.assertEquals(year, addedDVD.getReleaseYear());
        
    }
    
    @Test
    public void testRemoveDVD() {
        
        DVD toyStory = new DVD();
        toyStory.setName("Toy Story");
        
        Note note1 = new Note();
        note1.setDvd(toyStory);
        
        Note note2 = new Note();
        note2.setDvd(toyStory);
        
        DVD addedDVD = dvdDao.create(toyStory);
        Note addedNote1 = noteDao.create(note1);
        Note addedNote2 = noteDao.create(note2);
        
        // Delete Toy Story from the controller (this should get rid of the notes as well)
        dc.removeDVD(toyStory);
        
        Assert.assertNull(dvdDao.read(addedDVD.getId()));
        Assert.assertNull(noteDao.read(addedNote1.getId()));
        Assert.assertNull(noteDao.read(addedNote2.getId()));
        
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
