package com.thesoftwareguild.dvdlibraryweb.dto;

import org.hibernate.validator.constraints.NotEmpty;

public class Note {
    
    private long noteId;

    @NotEmpty(message="Note text may not be empty")
    private String noteText;

    private DVD dvd;

    public Note() {
        
    }
    
    public Note(Note note) {
        this.noteId = note.getNoteId();
        this.noteText = note.getNoteText();
        this.dvd = note.getDvd();
    }

    public long getNoteId() {
        return noteId;
    }

    public void setNoteId(long noteId) {
        this.noteId = noteId;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public DVD getDvd() {
        return dvd;
    }

    public void setDvd(DVD dvd) {
        this.dvd = dvd;
    }
}
