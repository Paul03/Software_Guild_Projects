package com.thesoftwareguild.dvdlibraryweb.dao;

import com.thesoftwareguild.dvdlibraryweb.dto.DVD;
import com.thesoftwareguild.dvdlibraryweb.dto.Note;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NoteDaoImpl implements NoteDao {

    private final String FILENAME = "notes.txt";
    private final String TOKEN = "::";

    private List<Note> noteLibrary = null;
    private long nextId = 1;

    private DVDDao dvdDao;

    public NoteDaoImpl(DVDDao dao) {
        this.dvdDao = dao;
        
        noteLibrary = decode();
        
        for (Note n : noteLibrary) {
            if (n.getNoteId() >= nextId) {
                nextId = n.getNoteId() + 1;
            }
        }
        
    }

    @Override
    public Note create(Note note) {

        note.setNoteId(nextId);

        nextId++;

        noteLibrary.add(note);
        
        encode();

        return new Note(note);

    }

    @Override
    public Note read(long id) {

        for (Note n : noteLibrary) {
            if (n.getNoteId() == id) {
                return new Note(n);
            }
        }

        return null;

    }

    @Override
    public void update(Note note) {

        for (int i = 0; i < noteLibrary.size(); i++) {
            if (noteLibrary.get(i).getNoteId() == note.getNoteId()) {
                noteLibrary.set(i, note);
                break;
            }
        }
        
        encode();

    }

    @Override
    public void delete(Note note) {

        for (Note n : noteLibrary) {
            if (n.getNoteId() == note.getNoteId()) {
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

                out.print(n.getNoteId());
                out.print(TOKEN);

                out.print(n.getNoteText());
                out.print(TOKEN);

                out.print(n.getDvd().getDvdId());
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

                note.setNoteId(noteId);
                note.setNoteText(stringParts[1]);
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
            if (n.getDvd().getDvdId() == dvd.getDvdId()) {
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
