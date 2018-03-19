/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dvdlibrary.dto;

import java.util.Comparator;
import java.util.List;

/**
 *
 * @author paulharding
 */
public class DVD implements Comparator, Comparable {

    private Integer id;
    private String name;
    private Integer releaseYear;
    private String mpaaRating;
    private String directorName;
    private String studioName;
    private Integer userRating;
    private List<Note> userNotes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getMpaaRating() {
        return mpaaRating;
    }

    public void setMpaaRating(String mpaaRating) {
        this.mpaaRating = mpaaRating;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public String getStudioName() {
        return studioName;
    }

    public void setStudioName(String studioName) {
        this.studioName = studioName;
    }

    public Integer getUserRating() {
        return userRating;
    }

    public void setUserRating(Integer userRating) {
        this.userRating = userRating;
    }

    public List<Note> getUserNotes() {
        return userNotes;
    }

    public void setUserNotes(List<Note> userNotes) {
        this.userNotes = userNotes;
    }

    @Override
    public int compare(Object o1, Object o2) {

        DVD dvd1 = (DVD) o1;
        DVD dvd2 = (DVD) o2;

        String dvd1Rating = dvd1.getMpaaRating();
        String dvd2Rating = dvd2.getMpaaRating();

        if (dvd1Rating == null && dvd2Rating == null) {
            return 0;
        } else if (dvd1Rating == null) {
            return 1;
        } else if (dvd2Rating == null) {
            return -1;
        } else {
            return dvd1.mpaaRating.compareToIgnoreCase(dvd2.mpaaRating);
        }

    }

    @Override
    public int compareTo(Object o) { // if the DVD does not get a name, then this throws a NullPointerException
                                     // every DVD can be forced to have a name in the controller
        
        return this.name.compareToIgnoreCase( ( (DVD) o ).getName() );
        
    }

}
