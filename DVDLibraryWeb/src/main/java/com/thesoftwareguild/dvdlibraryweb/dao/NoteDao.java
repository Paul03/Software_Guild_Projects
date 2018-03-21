package com.thesoftwareguild.dvdlibraryweb.dao;

import com.thesoftwareguild.dvdlibraryweb.dto.Dvd;
import com.thesoftwareguild.dvdlibraryweb.dto.Note;
import java.util.List;

public interface NoteDao {
    
    Note create(Note note);
    
    Note read(long id);
    
    void update(Note note);
    
    void delete(Note note);
    
    List<Note> findByDVD(Dvd dvd);
    
    double getAverageNumberOfNotes();
    
}
