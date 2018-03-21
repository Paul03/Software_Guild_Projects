package com.thesoftwareguild.dvdlibraryweb.viewmodel;

import com.thesoftwareguild.dvdlibraryweb.dto.Dvd;
import com.thesoftwareguild.dvdlibraryweb.dto.Note;

import java.util.List;

public class ShowDvdViewModel {

    private Dvd dvd;
    private List<Note> dvdNoteList;

    public Dvd getDvd() {
        return dvd;
    }

    public void setDvd(Dvd dvd) {
        this.dvd = dvd;
    }

    public List<Note> getDvdNoteList() {
        return dvdNoteList;
    }

    public void setDvdNoteList(List<Note> dvdNoteList) {
        this.dvdNoteList = dvdNoteList;
    }
}
