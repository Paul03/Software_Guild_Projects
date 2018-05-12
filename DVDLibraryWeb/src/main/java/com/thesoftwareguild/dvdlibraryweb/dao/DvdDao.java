package com.thesoftwareguild.dvdlibraryweb.dao;

import com.thesoftwareguild.dvdlibraryweb.dto.Dvd;

import java.util.List;

public interface DvdDao {

    Dvd create(Dvd dvd);

    Dvd read(long id);

    void update(Dvd dvd);

    void delete(Dvd dvd);

    List<Dvd> list();

    List<Dvd> searchLastNYears(int years);

    List<Dvd> searchByMPAARating(String rating);

    List<Dvd> searchByDirector(String directorName);

    List<Dvd> searchByStudio(String studioName);

    Double averageAge();

    List<Dvd> findNewestDVD();

    List<Dvd> findOldestDVD();

    List<Dvd> searchByTitle(String title);

}
