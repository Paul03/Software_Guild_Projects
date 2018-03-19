/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dvdlibrary.dao;

import com.mycompany.dvdlibrary.dto.DVD;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author paulharding
 */
public class DVDDaoInMemoryImpl implements DVDDao {

    List<DVD> dvdLibrary = new ArrayList();
    Integer currentYear = Calendar.getInstance().get(Calendar.YEAR);
    Integer nextId = 1;

    public DVDDaoInMemoryImpl() {

    }

    @Override
    public DVD create(DVD dvd) {

        dvd.setId(nextId);

        dvdLibrary.add(dvd);

        nextId++;

        return dvd;
    }

    @Override
    public DVD read(Integer id) {

        for (DVD d : dvdLibrary) {

            if (d.getId() == id) {

                return d;

            }

        }

        return null;
    }

    @Override
    public void update(DVD newDvd) {

        for (DVD d : dvdLibrary) {

            if (d.getId() == newDvd.getId()) {

                d = newDvd;
                break;

            }

        }

    }

    @Override
    public void delete(DVD dvd) {

        for (DVD d : dvdLibrary) {

            if (d.getId() == dvd.getId()) {

                dvdLibrary.remove(d);
                break;

            }

        }

    }

    @Override
    public List<DVD> list() {

        Collections.sort(dvdLibrary);

        return new ArrayList( dvdLibrary );

    }

    @Override
    public List<DVD> searchLastNYears(Integer years) {

        Integer targetYear = currentYear - years;

        List<DVD> result = new ArrayList();

        try {

            for (DVD d : dvdLibrary) {
                if (d.getReleaseYear() >= targetYear) {
                    result.add(d);
                }
            }

        } catch (NullPointerException ex) {

        }

        return result;

    }

    @Override
    public List<DVD> searchByMPAARating(String rating) {

        List<DVD> result = new ArrayList();

        try {

            for (DVD d : dvdLibrary) {
                if (d.getMpaaRating().equals(rating)) {
                    result.add(d);
                }

            }

        } catch (NullPointerException ex) {

        }

        Collections.sort(result);

        return result;

    }

    @Override
    public List<DVD> searchByDirector(String directorName) {

        List<DVD> result = new ArrayList();

        try {

            for (DVD d : dvdLibrary) {
                if (d.getDirectorName().toLowerCase().equals(directorName.toLowerCase())) {
                    result.add(d);
                }
            }

        } catch (NullPointerException ex) {

        }

        Collections.sort(result, new DVD()); // If the movie does not have an MPAA rating stored, then we get a NullPointerException

        return result;

    }

    @Override
    public List<DVD> searchByStudio(String studioName) {

        List<DVD> result = new ArrayList();

        try {

            for (DVD d : dvdLibrary) {
                if (d.getStudioName().toLowerCase().equals(studioName.toLowerCase())) {
                    result.add(d);
                }
            }

        } catch (NullPointerException ex) {

        }

        Collections.sort(result);

        return result;

    }

    @Override
    public Double averageAge() {

        Double result = 0.0;
        Double totalAge = 0.0;
        Double movieCount = 0.0;

        for (DVD d : dvdLibrary) {
            try {

                double age = currentYear - d.getReleaseYear();
                totalAge += age;
                movieCount++;

            } catch (NullPointerException ex) {

            }
        }

        result = totalAge / movieCount;

        return result;

    }

    @Override
    public List<DVD> findNewestDVD() {

        List<DVD> result = new ArrayList();
        Integer newestYear = 0;

        // Determine what the newestYear is
        for (DVD d : dvdLibrary) {
            try {

                if (d.getReleaseYear() > newestYear) {
                    newestYear = d.getReleaseYear();
                }
            } catch (NullPointerException ex) {

            }

        }

        // Get a list of all the DVDs from newestYear
        for (DVD d : dvdLibrary) {
            if (Objects.equals(d.getReleaseYear(), newestYear)) {
                result.add(d);
            }
        }

        Collections.sort(result);

        return result;

    }

    @Override
    public List<DVD> findOldestDVD() {

        List<DVD> result = new ArrayList();
        Integer oldestYear = 9999999;

        // Determine what the oldestYear is
        for (DVD d : dvdLibrary) {

            try {
                if (d.getReleaseYear() < oldestYear) {
                    oldestYear = d.getReleaseYear();
                }
            } catch (NullPointerException ex) {

            }
        }

        // Populate the result list with DVD objects from the oldestYear
        for (DVD d : dvdLibrary) {
            if (Objects.equals(d.getReleaseYear(), oldestYear)) {
                result.add(d);
            }
        }
        
        Collections.sort(result);

        return result;

    }

    @Override
    public List<DVD> searchByName(String name) {
        
        List<DVD> result = new ArrayList();

        try {

            for (DVD d : dvdLibrary) {
                if (d.getName().toLowerCase().equals(name.toLowerCase())) {
                    result.add(d);
                }
            }

        } catch (NullPointerException ex) {

        }

        Collections.sort(result);

        return result;
        
        
    }

}
