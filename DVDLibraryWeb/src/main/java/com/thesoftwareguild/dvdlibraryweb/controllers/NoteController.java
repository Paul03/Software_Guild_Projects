package com.thesoftwareguild.dvdlibraryweb.controllers;

import com.thesoftwareguild.dvdlibraryweb.dao.DvdDao;
import com.thesoftwareguild.dvdlibraryweb.dao.NoteDao;
import com.thesoftwareguild.dvdlibraryweb.dto.Dvd;
import com.thesoftwareguild.dvdlibraryweb.dto.Note;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping(value="/Note")
public class NoteController {

    private DvdDao dvdDao;
    private NoteDao noteDao;

    @Inject
    public NoteController(DvdDao dvdDao, NoteDao noteDao) {
        this.dvdDao = dvdDao;
        this.noteDao = noteDao;
    }

    @RequestMapping(value="/{id}", method= RequestMethod.GET)
    @ResponseBody
    public Note show(@PathVariable("id") Integer noteId) {

        return noteDao.retrieve(noteId);

    }

    @RequestMapping(value="add/{dvdId}", method=RequestMethod.GET)
    public String addNote(@PathVariable("dvdId") Integer dvdId, Map model) {

        Dvd dvd = dvdDao.retrieve(dvdId);
        model.put("dvd", dvd);

        return "addNote";

    }

    @RequestMapping(value="", method=RequestMethod.POST)
    @ResponseBody
    public Note addNote(@Valid @RequestBody Note note) {

        return noteDao.insert(note);

    }

    @RequestMapping(value="edit/{id}", method=RequestMethod.GET)
    public String edit(@PathVariable("id") Integer noteId, Map model) {

        Note note = noteDao.retrieve(noteId);
        model.put("note", note);

        return "editNote";

    }

    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    @ResponseBody
    public Note edit(@Valid @RequestBody Note note) {

        noteDao.update(note);
        return note;

    }

    @RequestMapping(value="delete/{id}", method=RequestMethod.GET)
    public String delete(@PathVariable("id") Integer id, Map model) {

        Note note = noteDao.retrieve(id);
        model.put("note", note);

        return "deleteNote";

    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    @ResponseBody
    public Note delete(@RequestBody Note note) {

        noteDao.delete(note);
        return note;

    }
}
