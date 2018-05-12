package com.thesoftwareguild.dvdlibraryweb.dao;

import com.thesoftwareguild.dvdlibraryweb.dto.Dvd;

import java.util.List;

public interface DvdDao {

    Dvd insert(Dvd dvd);

    Dvd retrieve(long id);

    void update(Dvd dvd);

    void delete(Dvd dvd);

    List<Dvd> all();

    List<Dvd> searchLastNYears(int years);

    List<Dvd> searchByMPAARating(String rating);

    List<Dvd> searchByDirector(String directorName);

    List<Dvd> searchByStudio(String studioName);

    Double averageAge();

    List<Dvd> findNewestDVD();

    List<Dvd> findOldestDVD();

    List<Dvd> searchByTitle(String title);

}
