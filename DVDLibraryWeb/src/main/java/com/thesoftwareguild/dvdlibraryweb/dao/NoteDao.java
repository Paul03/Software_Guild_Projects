package com.thesoftwareguild.dvdlibraryweb.dao;

import com.thesoftwareguild.dvdlibraryweb.dto.DVD;
import com.thesoftwareguild.dvdlibraryweb.dto.Note;
import java.util.List;

public interface NoteDao {
    
    public Note create(Note note);
    
    public Note read(long id);
    
    public void update(Note note);
    
    public void delete(Note note);
    
    public List<Note> findByDVD(DVD dvd);
    
    public double getAverageNumberOfNotes();
    
}
