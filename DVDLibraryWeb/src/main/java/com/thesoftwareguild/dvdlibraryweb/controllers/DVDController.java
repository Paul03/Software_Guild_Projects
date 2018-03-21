package com.thesoftwareguild.dvdlibraryweb.controllers;

import com.thesoftwareguild.dvdlibraryweb.dao.DVDDao;
import com.thesoftwareguild.dvdlibraryweb.dao.NoteDao;
import com.thesoftwareguild.dvdlibraryweb.dto.AddDvdCommand;
import com.thesoftwareguild.dvdlibraryweb.dto.Dvd;
import com.thesoftwareguild.dvdlibraryweb.dto.Note;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.validation.Valid;

import com.thesoftwareguild.dvdlibraryweb.dto.SearchDvdCommand;
import com.thesoftwareguild.dvdlibraryweb.viewmodel.ShowDvdViewModel;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/DVD")
public class DVDController {

    private DVDDao dvdDao;
    private NoteDao noteDao;

    @Inject
    public DVDController(DVDDao dvdDao, NoteDao noteDao) {
        this.dvdDao = dvdDao;
        this.noteDao = noteDao;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ShowDvdViewModel show(@PathVariable("id") Integer dvdId) {

        Dvd d = dvdDao.read(dvdId);

        List<Note> dvdNotes = noteDao.findByDVD(d);

        ShowDvdViewModel showDvdViewModel = new ShowDvdViewModel();

        showDvdViewModel.setDvd(d);
        showDvdViewModel.setDvdNoteList(dvdNotes);

        return showDvdViewModel;

    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public Dvd create(@Valid @RequestBody AddDvdCommand commandObject) {

        Dvd dvd = getDVDfromCommandObject(commandObject);
        Dvd d = dvdDao.create(dvd);

        if (!commandObject.getNoteText().trim().equals("")){

            Note note = getNoteFromCommandObject(commandObject);
            note.setDvd(d);
            noteDao.create(note);
        }

        return d;

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Dvd edit(@Valid @RequestBody Dvd dvd) {

        dvdDao.update(dvd);

        return dvd;

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @Transactional(propagation = Propagation.REQUIRED)
    @ResponseBody
    public Dvd delete(@PathVariable("id") Integer id) {
        
        Dvd dvd = dvdDao.read(id);

        List<Note> dvdNoteList = noteDao.findByDVD(dvd);

        for (Note n : dvdNoteList) {
            noteDao.delete(n);
        }

        dvdDao.delete(dvd);

        return dvd;

    }

    @RequestMapping(value="/Search", method=RequestMethod.GET)
    public String search() {
        return "search";
    }

    @RequestMapping(value="/Search", method=RequestMethod.POST)
    @ResponseBody
    public List<Dvd> search(@RequestBody SearchDvdCommand searchDvdCommand) {

        List<Dvd> searchResultList = new ArrayList<>();

        if (searchDvdCommand.getFieldToSearch().equals("title")) {

            String titleToSearchFor = searchDvdCommand.getValueToSearchFor();

            searchResultList = dvdDao.searchByTitle( titleToSearchFor );

        }

        if (searchDvdCommand.getFieldToSearch().equals("studio")) {

            String studioToSearchFor = searchDvdCommand.getValueToSearchFor();

            searchResultList = dvdDao.searchByStudio( studioToSearchFor );

        }

        if (searchDvdCommand.getFieldToSearch().equals("director")) {

            String directorToSearchFor = searchDvdCommand.getValueToSearchFor();

            searchResultList = dvdDao.searchByDirector( directorToSearchFor );

        }

        if (searchDvdCommand.getFieldToSearch().equals("mpaaRating")) {

            String mpaaRatingToSearch = searchDvdCommand.getValueToSearchFor();

            searchResultList = dvdDao.searchByMPAARating( mpaaRatingToSearch );

        }

        return searchResultList;
    }
    
    private Dvd getDVDfromCommandObject(AddDvdCommand commandObject) {

        Dvd dvd = new Dvd();

        dvd.setTitle(commandObject.getTitle());
        dvd.setReleaseDate(commandObject.getReleaseDate());
        dvd.setMpaaRating(commandObject.getMpaaRating());
        dvd.setDirector(commandObject.getDirector());
        dvd.setStudio(commandObject.getStudio());
        dvd.setUserRating(commandObject.getUserRating());

        return dvd;

    }

    private Note getNoteFromCommandObject(AddDvdCommand commandObject) {

        Note note = new Note();
        note.setNoteText(commandObject.getNoteText());

        return note;

    }
}
