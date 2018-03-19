package com.thesoftwareguild.dvdlibraryweb.viewmodel;

import com.thesoftwareguild.dvdlibraryweb.dto.DVD;
import com.thesoftwareguild.dvdlibraryweb.dto.Note;

import java.util.List;

public class ShowDVDViewModel {

    private DVD dvd;
    private List<Note> dvdNoteList;

    public DVD getDvd() {
        return dvd;
    }

    public void setDvd(DVD dvd) {
        this.dvd = dvd;
    }

    public List<Note> getDvdNoteList() {
        return dvdNoteList;
    }

    public void setDvdNoteList(List<Note> dvdNoteList) {
        this.dvdNoteList = dvdNoteList;
    }
}
