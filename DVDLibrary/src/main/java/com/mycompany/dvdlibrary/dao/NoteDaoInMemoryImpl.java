/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dvdlibrary.dao;

import com.mycompany.dvdlibrary.dto.DVD;
import com.mycompany.dvdlibrary.dto.Note;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author paulharding
 */
public class NoteDaoInMemoryImpl implements NoteDao {

    private final String FILENAME = "notes.txt";
    private final String TOKEN = "::";

    private List<Note> noteLibrary = new ArrayList();
    private Integer nextId = 1;

    private DVDDao dvdDao;

    public NoteDaoInMemoryImpl(DVDDao dao) {
        this.dvdDao = dao;

    }

    @Override
    public Note create(Note note) {

        note.setId(nextId);

        nextId++;

        noteLibrary.add(note);

        return note;

    }

    @Override
    public Note read(Integer id) {

        for (Note n : noteLibrary) {
            if (n.getId() == id) {
                return n;
            }
        }

        return null;

    }

    @Override
    public void update(Note note) {

        for (Note n : noteLibrary) {
            if (n.getId() == note.getId()) {
                n = note;
                break;
            }
        }

    }

    @Override
    public void delete(Note note) {

        for (Note n : noteLibrary) {
            if (n.getId() == note.getId()) {
                noteLibrary.remove(n);
                break;
            }
        }

    }

    @Override
    public List<Note> findByDVD(DVD dvd) {

        List<Note> dvdNotes = new ArrayList();

        for (Note n : noteLibrary) {
            if (n.getDvd().getId() == dvd.getId()) {
                dvdNotes.add(n);
            }
        }

        return dvdNotes;

    }
    
    @Override
    public double getAverageNumberOfNotes() {

        List<DVD> tempDVDList = dvdDao.list();

        double average = ((double) noteLibrary.size()) / ((double) tempDVDList.size());

        return average;

    }

}
