package com.thesoftwareguild.dvdlibraryweb.controllers;

import com.thesoftwareguild.dvdlibraryweb.dao.DvdDao;
import com.thesoftwareguild.dvdlibraryweb.dto.AddDvdCommand;
import com.thesoftwareguild.dvdlibraryweb.dto.Dvd;
import com.thesoftwareguild.dvdlibraryweb.ui.ViewSelector;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    private ViewSelector viewSelector;
    private DvdDao dvdDao;

    @Inject
    public HomeController(ViewSelector viewSelector, DvdDao dvdDao) {
        this.viewSelector = viewSelector;
        this.dvdDao = dvdDao;
    }

    @RequestMapping(value="/", method= RequestMethod.GET)
    public String home(Map model) {

        List<Dvd> dvdList = dvdDao.list();

        model.put("dvdList", dvdList);
        model.put("addDVDCommand", new AddDvdCommand());

        return viewSelector.home();
    }
}
