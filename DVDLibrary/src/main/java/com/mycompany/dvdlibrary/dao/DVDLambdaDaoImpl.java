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
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * @author paulharding
 */
public class DVDLambdaDaoImpl implements DVDDao {

    private static final String FILENAME = "dvds.txt";
    private static final String TOKEN = "::";
    List<DVD> dvdLibrary = null;
    Integer nextId = 1;
    Integer currentYear = 2016;

    public DVDLambdaDaoImpl() {

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

        return new ArrayList(dvdLibrary);

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

        Integer targetRange = currentYear - years;

        try {

            List<DVD> result = dvdLibrary
                    .stream()
                    .filter(d -> d.getReleaseYear() >= targetRange)
                    .collect(Collectors.toList());

            return result;

        } catch (NullPointerException ex) {
            return new ArrayList();
        }

    }

    @Override
    public List<DVD> searchByMPAARating(String rating) {

        try {

            List<DVD> result = dvdLibrary
                    .stream()
                    .filter(d -> d.getMpaaRating().toLowerCase().equals(rating.toLowerCase()))
                    .collect(Collectors.toList());

            Collections.sort(result);

            return result;

        } catch (NullPointerException ex) {
            return new ArrayList();
        }
    }

    @Override
    public List<DVD> searchByDirector(String directorName) {

        List<DVD> result = new ArrayList();

        try {

            List<DVD> directorList = dvdLibrary
                    .stream()
                    .filter(d -> d.getDirectorName().toLowerCase().equals(directorName.toLowerCase()))
                    .collect(Collectors.toList());

            for (DVD d : directorList) {
                result.add(d);

            }

        } catch (NullPointerException ex) {

        }

        Collections.sort(result, new DVD());

        return result;

    }

    @Override
    public List<DVD> searchByStudio(String studioName) {

        try {

            List<DVD> result = dvdLibrary
                    .stream()
                    .filter(d -> d.getStudioName().toLowerCase().equals(studioName.toLowerCase()))
                    .collect(Collectors.toList());

            Collections.sort(result);

            return result;

        } catch (NullPointerException ex) {
            return new ArrayList();
        }

    }

    @Override
    public Double averageAge() {

        //Double result = 0.0;
        List<Integer> releaseYears = new ArrayList();

        for (DVD d : dvdLibrary) {
            Integer year = d.getReleaseYear();
            releaseYears.add(year);
        }

        double average = dvdLibrary
                .stream()
                .filter(d -> d.getReleaseYear() != null)
                .mapToDouble(DVD::getReleaseYear)
                .average()
                .getAsDouble();

        average = currentYear - average;

        return average;

    }

    @Override
    public List<DVD> findNewestDVD() {

        Integer findNewestYear = 0;

        try {

            // Determine what the newestYear is
            for (DVD d : dvdLibrary) {
                if (d.getReleaseYear() > findNewestYear) {
                    findNewestYear = d.getReleaseYear();
                }
            }

            final Integer newestYear = findNewestYear;

            // Put the DVDs that are from newestYear into a list
            List<DVD> result = dvdLibrary
                    .stream()
                    .filter(d -> Objects.equals(d.getReleaseYear(), newestYear))
                    .collect(Collectors.toList());

            Collections.sort(result);

            return result;

        } catch (NullPointerException ex) {
            return new ArrayList();
        }

    }

    @Override
    public List<DVD> findOldestDVD() {

        Integer findOldestYear = 99999999;

        try {

            for (DVD d : dvdLibrary) {
                if (d.getReleaseYear() < findOldestYear) {
                    findOldestYear = d.getReleaseYear();
                }
            }

            final Integer oldestYear = findOldestYear;

            List<DVD> result = dvdLibrary
                    .stream()
                    .filter(d -> Objects.equals(d.getReleaseYear(), oldestYear))
                    .collect(Collectors.toList());

            Collections.sort(result);

            return result;

        } catch (NullPointerException ex) {
            return new ArrayList();
        }

    }

    @Override
    public List<DVD> searchByName(String name) {

        try {

            List<DVD> result = dvdLibrary
                    .stream()
                    .filter(d -> d.getName().toLowerCase().equals(name.toLowerCase()))
                    .collect(Collectors.toList());

            Collections.sort(result);

            return result;

        } catch (NullPointerException ex) {
            return new ArrayList();
        }

    }

}
