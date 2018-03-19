package com.thesoftwareguild.dvdlibraryweb.controllers;

import com.thesoftwareguild.dvdlibraryweb.dao.DVDDao;
import com.thesoftwareguild.dvdlibraryweb.dao.NoteDao;
import com.thesoftwareguild.dvdlibraryweb.dto.AddDVDCommand;
import com.thesoftwareguild.dvdlibraryweb.dto.DVD;
import com.thesoftwareguild.dvdlibraryweb.dto.Note;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.validation.Valid;

import com.thesoftwareguild.dvdlibraryweb.dto.SearchDVDCommand;
import com.thesoftwareguild.dvdlibraryweb.viewmodel.ShowDVDViewModel;
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
    public ShowDVDViewModel show(@PathVariable("id") Integer dvdId) {

        DVD d = dvdDao.read(dvdId);

        List<Note> dvdNotes = noteDao.findByDVD(d);

        ShowDVDViewModel showDVDViewModel = new ShowDVDViewModel();

        showDVDViewModel.setDvd(d);
        showDVDViewModel.setDvdNoteList(dvdNotes);

        return showDVDViewModel;

    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public DVD create(@Valid @RequestBody AddDVDCommand commandObject) {

        DVD dvd = getDVDfromCommandObject(commandObject);
        DVD d = dvdDao.create(dvd);

        if (!commandObject.getNoteText().trim().equals("")){

            Note note = getNoteFromCommandObject(commandObject);
            note.setDvd(d);
            noteDao.create(note);
        }

        return d;

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public DVD edit(@Valid @RequestBody DVD dvd) {

        dvdDao.update(dvd);

        return dvd;

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @Transactional(propagation = Propagation.REQUIRED)
    @ResponseBody
    public DVD delete(@PathVariable("id") Integer id) {
        
        DVD dvd = dvdDao.read(id);

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
    public List<DVD> search(@RequestBody SearchDVDCommand searchDVDCommand) {

        List<DVD> searchResultList = new ArrayList<>();

        if (searchDVDCommand.getFieldToSearch().equals("title")) {

            String titleToSearchFor = searchDVDCommand.getValueToSearchFor();

            searchResultList = dvdDao.searchByTitle( titleToSearchFor );

        }

        if (searchDVDCommand.getFieldToSearch().equals("studio")) {

            String studioToSearchFor = searchDVDCommand.getValueToSearchFor();

            searchResultList = dvdDao.searchByStudio( studioToSearchFor );

        }

        if (searchDVDCommand.getFieldToSearch().equals("director")) {

            String directorToSearchFor = searchDVDCommand.getValueToSearchFor();

            searchResultList = dvdDao.searchByDirector( directorToSearchFor );

        }

        if (searchDVDCommand.getFieldToSearch().equals("mpaaRating")) {

            String mpaaRatingToSearch = searchDVDCommand.getValueToSearchFor();

            searchResultList = dvdDao.searchByMPAARating( mpaaRatingToSearch );

        }

        return searchResultList;
    }
    
    private DVD getDVDfromCommandObject(AddDVDCommand commandObject) {

        DVD dvd = new DVD();

        dvd.setTitle(commandObject.getTitle());
        dvd.setReleaseDate(commandObject.getReleaseDate());
        dvd.setMpaaRating(commandObject.getMpaaRating());
        dvd.setDirector(commandObject.getDirector());
        dvd.setStudio(commandObject.getStudio());
        dvd.setUserRating(commandObject.getUserRating());

        return dvd;

    }

    private Note getNoteFromCommandObject(AddDVDCommand commandObject) {

        Note note = new Note();
        note.setNoteText(commandObject.getNoteText());

        return note;

    }
}
