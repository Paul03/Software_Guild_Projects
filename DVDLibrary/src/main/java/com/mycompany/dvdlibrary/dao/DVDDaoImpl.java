/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dvdlibrary.dao;

import com.mycompany.dvdlibrary.dto.DVD;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 *
 * @author paulharding
 */
public class DVDDaoImpl implements DVDDao {

    private static final String FILENAME = "dvds.txt";
    private static final String TOKEN = "::";
    Integer currentYear = Calendar.getInstance().get(Calendar.YEAR);
    List<DVD> dvdLibrary = null;
    Integer nextId = 1;

    public DVDDaoImpl() {

        dvdLibrary = decode();

        for (DVD d : dvdLibrary) {

            if (d.getId() >= nextId) {

                nextId = d.getId() + 1;

            }

        }

    }

    @Override
    public DVD create(DVD dvd) {

        dvd.setId(nextId);

        dvdLibrary.add(dvd);

        encode();

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

        encode();

    }

    @Override
    public void delete(DVD dvd) {

        for (DVD d : dvdLibrary) {

            if (d.getId() == dvd.getId()) {

                dvdLibrary.remove(d);
                break;

            }

        }

        encode();

    }

    @Override
    public List<DVD> list() {

        Collections.sort(dvdLibrary);

        return new ArrayList( dvdLibrary );

    }

    private void encode() {

        PrintWriter out = null;

        try {

            out = new PrintWriter(new FileWriter(FILENAME));

            for (DVD d : dvdLibrary) {

                out.print(d.getId());
                out.print(TOKEN);

                out.print(d.getName());
                out.print(TOKEN);

                out.print(d.getReleaseYear());
                out.print(TOKEN);

                out.print(d.getMpaaRating());
                out.print(TOKEN);

                out.print(d.getDirectorName());
                out.print(TOKEN);

                out.print(d.getStudioName());
                out.print(TOKEN);

                out.print(d.getUserRating());
                out.print("\n");

            }

        } catch (IOException ex) {

        } finally {

            out.close();

        }

    }

    private List<DVD> decode() {

        List<DVD> tempDVDList = new ArrayList();

        try {

            Scanner sc = new Scanner(new BufferedReader(new FileReader(FILENAME)));

            while (sc.hasNextLine()) {

                String currentLine = sc.nextLine();

                String[] stringParts = currentLine.split(TOKEN);

                DVD myDVD = new DVD();

                int dvdId = Integer.parseInt(stringParts[0]);

                Integer dvdReleaseYear;
                try {

                    dvdReleaseYear = Integer.parseInt(stringParts[2]);

                } catch (NumberFormatException ex) {

                    dvdReleaseYear = null;

                }

                Integer dvdUserRating;
                try {

                    dvdUserRating = Integer.parseInt(stringParts[6]);

                } catch (NumberFormatException ex) {

                    dvdUserRating = null;

                }

                myDVD.setId(dvdId);
                myDVD.setName(stringParts[1]);
                myDVD.setReleaseYear(dvdReleaseYear);
                myDVD.setMpaaRating(stringParts[3]);
                myDVD.setDirectorName(stringParts[4]);
                myDVD.setStudioName(stringParts[5]);
                myDVD.setUserRating(dvdUserRating);

                tempDVDList.add(myDVD);

            }

        } catch (FileNotFoundException ex) {

        }

        return tempDVDList;

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
        
        Collections.sort(result);

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
