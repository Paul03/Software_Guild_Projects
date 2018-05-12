package com.thesoftwareguild.dvdlibraryweb.dao;

import com.thesoftwareguild.dvdlibraryweb.dto.Dvd;
import com.thesoftwareguild.dvdlibraryweb.dto.Note;
import java.util.List;

public interface NoteDao {

    Note insert(Note note);

    Note retrieve(long id);

    void update(Note note);

    void delete(Note note);

    List<Note> findByDvd(Dvd dvd);

    double getAverageNumberOfNotes();

}
