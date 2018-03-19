/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dvdlibrary.dao;

import com.mycompany.dvdlibrary.dto.DVD;
import java.util.List;

/**
 *
 * @author paulharding
 */
public interface DVDDao {
    
    public DVD create(DVD dvd);
    
    public DVD read(Integer id);
    
    public void update(DVD dvd);
    
    public void delete(DVD dvd);
    
    public List<DVD> list();
    
    public List<DVD> searchLastNYears(Integer years);
    
    public List<DVD> searchByMPAARating(String rating);
    
    public List<DVD> searchByDirector(String directorName);
    
    public List<DVD> searchByStudio(String studioName);
    
    public Double averageAge();
    
    public List<DVD> findNewestDVD();
    
    public List<DVD> findOldestDVD();
    
    public List<DVD> searchByName(String name);
    
}
