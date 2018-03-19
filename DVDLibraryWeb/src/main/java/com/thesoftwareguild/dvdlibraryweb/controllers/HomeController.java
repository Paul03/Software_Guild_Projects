package com.thesoftwareguild.dvdlibraryweb.controllers;

import com.thesoftwareguild.dvdlibraryweb.dao.DVDDao;
import com.thesoftwareguild.dvdlibraryweb.dto.AddDVDCommand;
import com.thesoftwareguild.dvdlibraryweb.dto.DVD;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
    
    private DVDDao dvdDao;

    @Inject
    public HomeController(DVDDao dvdDao) {
        this.dvdDao = dvdDao;
    }
    
    @RequestMapping(value="/", method= RequestMethod.GET)
    public String home(Map model) {
        
        List<DVD> dvdList = dvdDao.list();
        model.put("dvdList", dvdList);
        model.put("addDVDCommand", new AddDVDCommand());
        
        return "home";
    }
}
