package com.thesoftwareguild.dvdlibraryweb.controllers;

import com.thesoftwareguild.dvdlibraryweb.dao.UserDao;
import com.thesoftwareguild.dvdlibraryweb.dto.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/")
public class UserController {

    private UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public User insert(@RequestBody User newUser) {
        return userDao.insert(newUser); // TODO throw this into a try/catch ?
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public void update(@RequestBody User user) {
        userDao.update(user); // TODO throw this into a try/catch ?
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("userId") long userId) {
        userDao.delete(userId); // TODO throw this into a try/catch ?
    }

}
