package com.thesoftwareguild.dvdlibraryweb.dao;

import com.thesoftwareguild.dvdlibraryweb.dto.DVD;
import com.thesoftwareguild.dvdlibraryweb.dto.Note;
import java.util.ArrayList;
import java.util.List;

public class NoteDaoInMemoryImpl implements NoteDao {

    private final String FILENAME = "notes.txt";
    private final String TOKEN = "::";

    private List<Note> noteLibrary = new ArrayList<>();
    private Integer nextId = 1;

    private DVDDao dvdDao;

    public NoteDaoInMemoryImpl(DVDDao dao) {
        this.dvdDao = dao;

    }

    @Override
    public Note create(Note note) {

        note.setNoteId(nextId);

        nextId++;

        noteLibrary.add(note);

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

    }

    @Override
    public void delete(Note note) {

        for (Note n : noteLibrary) {
            if (n.getNoteId() == note.getNoteId()) {
                noteLibrary.remove(n);
                break;
            }
        }

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
