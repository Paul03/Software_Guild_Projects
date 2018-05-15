package com.thesoftwareguild.dvdlibraryweb.dao;

import com.thesoftwareguild.dvdlibraryweb.dto.User;

public interface UserDao {

    User insert(User userToInsert);
    User retrieve(long userId);
    User retrieve(String userName);
    void update(User userToUpdate);
    void delete(long userId);

}
