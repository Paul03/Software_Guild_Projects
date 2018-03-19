package com.thesoftwareguild.dvdlibraryweb.dao;

import com.thesoftwareguild.dvdlibraryweb.dto.DVD;
import java.util.List;

public interface DVDDao {
    
    DVD create(DVD dvd);
    
    DVD read(long id);
    
    void update(DVD dvd);
    
    void delete(DVD dvd);
    
    List<DVD> list();
    
    List<DVD> searchLastNYears(int years);
    
    List<DVD> searchByMPAARating(String rating);
    
    List<DVD> searchByDirector(String directorName);
    
    List<DVD> searchByStudio(String studioName);
    
    Double averageAge();
    
    List<DVD> findNewestDVD();
    
    List<DVD> findOldestDVD();
    
    List<DVD> searchByTitle(String title);
    
}
