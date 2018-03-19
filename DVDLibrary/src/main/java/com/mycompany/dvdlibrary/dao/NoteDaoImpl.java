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
public class NoteDaoImpl implements NoteDao {

    private final String FILENAME = "notes.txt";
    private final String TOKEN = "::";

    private List<Note> noteLibrary = null;
    private Integer nextId = 1;

    private DVDDao dvdDao;

    public NoteDaoImpl(DVDDao dao) {
        this.dvdDao = dao;
        
        noteLibrary = decode();
        
        for (Note n : noteLibrary) {
            if (n.getId() >= nextId) {
                nextId = n.getId() + 1;
            }
        }
        
    }

    @Override
    public Note create(Note note) {

        note.setId(nextId);

        nextId++;

        noteLibrary.add(note);

        encode();

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
        
        encode();
    }

    @Override
    public void delete(Note note) {

        for (Note n : noteLibrary) {
            if (n.getId() == note.getId()) {
                noteLibrary.remove(n);
                break;
            }
        }
        
        encode();

    }

    private void encode() {

        PrintWriter out = null;

        try {

            out = new PrintWriter(new FileWriter(FILENAME));

            for (Note n : noteLibrary) {

                out.print(n.getId());
                out.print(TOKEN);

                out.print(n.getNote());
                out.print(TOKEN);

                out.print(n.getDvd().getId());
                out.print("\n");

            }

        } catch (IOException ex) {

        } finally {

            out.close();

        }

    }

    private List<Note> decode() {

        List<Note> tempNoteList = new ArrayList();

        try {

            Scanner sc = new Scanner(new BufferedReader(new FileReader(FILENAME)));

            while (sc.hasNextLine()) {

                String currentLine = sc.nextLine();

                String[] stringParts = currentLine.split(TOKEN);

                Note note = new Note();

                Integer noteId = Integer.parseInt(stringParts[0]);
                Integer dvdId = Integer.parseInt(stringParts[2]);

                note.setId(noteId);
                note.setNote(stringParts[1]);
                note.setDvd(dvdDao.read(dvdId));

                tempNoteList.add(note);

            }

        } catch (FileNotFoundException ex) {

        }

        return tempNoteList;

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
