package com.thesoftwareguild.dvdlibraryweb.dao;

import com.thesoftwareguild.dvdlibraryweb.dto.DVD;
import com.thesoftwareguild.dvdlibraryweb.utility.DVDUtility;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class DVDDaoImpl implements DVDDao {

    private static final String FILENAME = "dvds.txt";
    private static final String TOKEN = "::";

    private int currentYear = Calendar.getInstance().get(Calendar.YEAR);
    private List<DVD> dvdLibrary = null;
    private long nextId = 1;

    public DVDDaoImpl() {

        dvdLibrary = decode();

        for (DVD dvd : dvdLibrary) {
            if (dvd.getDvdId() >= nextId) {
                nextId = dvd.getDvdId() + 1;
            }
        }

    }

    @Override
    public DVD create(DVD dvd) {

        dvd.setDvdId(nextId);
        dvdLibrary.add(dvd);

        nextId++;
        encode();

        return new DVD(dvd);
    }

    @Override
    public DVD read(long id) {

        for (DVD d : dvdLibrary) {
            if (d.getDvdId() == id) {
                return new DVD(d);
            }
        }

        return null;
    }

    @Override
    public void update(DVD dvd) {

        for (int i = 0; i < dvdLibrary.size(); i++) {
            if (dvdLibrary.get(i).getDvdId() == dvd.getDvdId()) {
                dvdLibrary.set(i, dvd);
                break;
            }
        }

        encode();
    }

    @Override
    public void delete(DVD dvd) {

        for (DVD d : dvdLibrary) {
            if (d.getDvdId() == dvd.getDvdId()) {
                dvdLibrary.remove(d);
                break;
            }
        }

        encode();
    }

    @Override
    public List<DVD> list() {

        return new ArrayList<>(dvdLibrary);

    }

    private void encode() {

        PrintWriter out = null;

        try {

            out = new PrintWriter(new FileWriter(FILENAME));

            for (DVD d : dvdLibrary) {

                out.print(d.getDvdId());
                out.print(TOKEN);

                out.print(d.getTitle());
                out.print(TOKEN);

                String releaseDate = DVDUtility.formatDateForEncode(d.getReleaseDate());
                out.print(releaseDate);
                out.print(TOKEN);

                out.print(d.getMpaaRating());
                out.print(TOKEN);

                out.print(d.getDirector());
                out.print(TOKEN);

                out.print(d.getStudio());
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

        List<DVD> tempDVDList = new ArrayList<>();

        try {

            Scanner sc = new Scanner(new BufferedReader(new FileReader(FILENAME)));

            while (sc.hasNextLine()) {

                String currentLine = sc.nextLine();

                String[] stringParts = currentLine.split(TOKEN);

                DVD myDVD = new DVD();

                int dvdId = Integer.parseInt(stringParts[0]);

                String monthString;
                if (stringParts[2].length() >= 2) {
                    monthString = stringParts[2].substring(0, 2);
                } else {
                    monthString = "MM";
                }

                String dayString;
                if (stringParts[2].length() >= 5) {
                    dayString = stringParts[2].substring(3, 5);
                } else {
                    dayString = "DD";
                }

                String yearString;
                if (stringParts[2].length() >= 10) {
                    yearString = stringParts[2].substring(6, 10);
                } else {
                    yearString = "YYYY";
                }
                
                Date dvdReleaseDate = new Date();

                Integer month;
                try {

                    month = Integer.parseInt(monthString);
                    dvdReleaseDate.setMonth(month - 1);
                } catch (NumberFormatException ex) {
                    month = 00;
                }

                Integer day;
                try {

                    day = Integer.parseInt(dayString);
                    dvdReleaseDate.setDate(day);
                } catch (NumberFormatException ex) {
                    day = 00;
                }

                Integer year;
                try {

                    year = Integer.parseInt(yearString);
                    dvdReleaseDate.setYear(year - 1900);
                } catch (NumberFormatException ex) {
                    year = 0000;
                }

                Integer dvdUserRating;
                try {

                    dvdUserRating = Integer.parseInt(stringParts[6]);

                } catch (NumberFormatException ex) {

                    dvdUserRating = null;

                }

                myDVD.setDvdId(dvdId);
                myDVD.setTitle(stringParts[1]);
                myDVD.setReleaseDate(dvdReleaseDate);
                myDVD.setMpaaRating(stringParts[3]);
                myDVD.setDirector(stringParts[4]);
                myDVD.setStudio(stringParts[5]);
                myDVD.setUserRating(dvdUserRating);

                tempDVDList.add(myDVD);

            }

        } catch (FileNotFoundException ex) {

        }

        return tempDVDList;

    }

    @Override
    public List<DVD> searchLastNYears(int years) {

        Integer targetYear = currentYear - years;

        List<DVD> result = new ArrayList<>();

        try {

            for (DVD d : dvdLibrary) {
                if (d.getReleaseDate().getYear() >= targetYear) {
                    result.add(d);
                }
            }

        } catch (NullPointerException ex) {

        }

        return result;

    }

    @Override
    public List<DVD> searchByMPAARating(String rating) {

        List<DVD> result = new ArrayList<>();

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

        List<DVD> result = new ArrayList<>();

        try {

            for (DVD d : dvdLibrary) {
                if (d.getDirector().toLowerCase().equals(directorName.toLowerCase())) {
                    result.add(d);
                }
            }

        } catch (NullPointerException ex) {

        }

        result.stream()
                .sorted(DVD::compareTo)
                .close();

        return result;
    }

    @Override
    public List<DVD> searchByStudio(String studioName) {

        List<DVD> result = new ArrayList<>();

        try {

            for (DVD d : dvdLibrary) {
                if (d.getStudio().toLowerCase().equals(studioName.toLowerCase())) {
                    result.add(d);
                }
            }

        } catch (NullPointerException ex) {

        }

        result.stream()
                .sorted(DVD::compareTo)
                .close();

        return result;

    }

    @Override
    public Double averageAge() {

        Double result = 0.0;
        Double totalAge = 0.0;
        Double movieCount = 0.0;

        for (DVD d : dvdLibrary) {
            try {

                double age = currentYear - d.getReleaseDate().getYear();
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

                if (d.getReleaseDate().getYear() > newestYear) {
                    newestYear = d.getReleaseDate().getYear();
                }
            } catch (NullPointerException ex) {

            }

        }

        // Get a list of all the DVDs from newestYear
        for (DVD d : dvdLibrary) {
            if (Objects.equals(d.getReleaseDate().getYear(), newestYear)) {
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
                if (d.getReleaseDate().getYear() < oldestYear) {
                    oldestYear = d.getReleaseDate().getYear();
                }
            } catch (NullPointerException ex) {

            }
        }

        // Populate the result list with DVD objects from the oldestYear
        for (DVD d : dvdLibrary) {
            if (Objects.equals(d.getReleaseDate().getYear(), oldestYear)) {
                result.add(d);
            }
        }

        Collections.sort(result);

        return result;

    }

    @Override
    public List<DVD> searchByTitle(String title) {

        List<DVD> result = new ArrayList<>();

        try {

            for (DVD d : dvdLibrary) {
                if (d.getTitle().toLowerCase().equals(title.toLowerCase())) {
                    result.add(d);
                }
            }

        } catch (NullPointerException ex) {

        }

        Collections.sort(result);

        return result;


    }

}
