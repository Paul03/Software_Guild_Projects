package com.thesoftwareguild.dvdlibraryweb.dao;

import com.thesoftwareguild.dvdlibraryweb.dto.Dvd;
import com.thesoftwareguild.dvdlibraryweb.utility.DateUtility;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class DvdDaoImpl implements DvdDao {

    private static final String FILENAME = "dvds.txt";
    private static final String TOKEN = "::";

    private int currentYear = Calendar.getInstance().get(Calendar.YEAR);
    private List<Dvd> dvdLibrary = null;
    private long nextId = 1;

    public DvdDaoImpl() {

        dvdLibrary = decode();

        for (Dvd dvd : dvdLibrary) {
            if (dvd.getDvdId() >= nextId) {
                nextId = dvd.getDvdId() + 1;
            }
        }

    }

    @Override
    public Dvd insert(Dvd dvd) {

        dvd.setDvdId(nextId);
        dvdLibrary.add(dvd);

        nextId++;
        encode();

        return new Dvd(dvd);
    }

    @Override
    public Dvd retrieve(long id) {

        for (Dvd d : dvdLibrary) {
            if (d.getDvdId() == id) {
                return new Dvd(d);
            }
        }

        return null;
    }

    @Override
    public void update(Dvd dvd) {

        for (int i = 0; i < dvdLibrary.size(); i++) {
            if (dvdLibrary.get(i).getDvdId() == dvd.getDvdId()) {
                dvdLibrary.set(i, dvd);
                break;
            }
        }

        encode();
    }

    @Override
    public void delete(Dvd dvd) {

        for (Dvd d : dvdLibrary) {
            if (d.getDvdId() == dvd.getDvdId()) {
                dvdLibrary.remove(d);
                break;
            }
        }

        encode();
    }

    @Override
    public List<Dvd> all() {

        return new ArrayList<>(dvdLibrary);

    }

    private void encode() {

        PrintWriter out = null;

        try {

            out = new PrintWriter(new FileWriter(FILENAME));

            for (Dvd d : dvdLibrary) {

                out.print(d.getDvdId());
                out.print(TOKEN);

                out.print(d.getTitle());
                out.print(TOKEN);

                String releaseDate = DateUtility.formatDateForEncode(d.getReleaseDate());
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

    private List<Dvd> decode() {

        List<Dvd> tempDvdList = new ArrayList<>();

        try {

            Scanner sc = new Scanner(new BufferedReader(new FileReader(FILENAME)));

            while (sc.hasNextLine()) {

                String currentLine = sc.nextLine();

                String[] stringParts = currentLine.split(TOKEN);

                Dvd myDvd = new Dvd();

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

                myDvd.setDvdId(dvdId);
                myDvd.setTitle(stringParts[1]);
                myDvd.setReleaseDate(dvdReleaseDate);
                myDvd.setMpaaRating(stringParts[3]);
                myDvd.setDirector(stringParts[4]);
                myDvd.setStudio(stringParts[5]);
                myDvd.setUserRating(dvdUserRating);

                tempDvdList.add(myDvd);

            }

        } catch (FileNotFoundException ex) {

        }

        return tempDvdList;

    }

    @Override
    public List<Dvd> searchLastNYears(int years) {

        Integer targetYear = currentYear - years;

        List<Dvd> result = new ArrayList<>();

        try {

            for (Dvd d : dvdLibrary) {
                if (d.getReleaseDate().getYear() >= targetYear) {
                    result.add(d);
                }
            }

        } catch (NullPointerException ex) {

        }

        return result;

    }

    @Override
    public List<Dvd> searchByMPAARating(String rating) {

        List<Dvd> result = new ArrayList<>();

        try {

            for (Dvd d : dvdLibrary) {
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
    public List<Dvd> searchByDirector(String directorName) {

        List<Dvd> result = new ArrayList<>();

        try {

            for (Dvd d : dvdLibrary) {
                if (d.getDirector().toLowerCase().equals(directorName.toLowerCase())) {
                    result.add(d);
                }
            }

        } catch (NullPointerException ex) {

        }

        result.stream()
                .sorted(Dvd::compareTo)
                .close();

        return result;
    }

    @Override
    public List<Dvd> searchByStudio(String studioName) {

        List<Dvd> result = new ArrayList<>();

        try {

            for (Dvd d : dvdLibrary) {
                if (d.getStudio().toLowerCase().equals(studioName.toLowerCase())) {
                    result.add(d);
                }
            }

        } catch (NullPointerException ex) {

        }

        result.stream()
                .sorted(Dvd::compareTo)
                .close();

        return result;

    }

    @Override
    public Double averageAge() {

        Double result = 0.0;
        Double totalAge = 0.0;
        Double movieCount = 0.0;

        for (Dvd d : dvdLibrary) {
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
    public List<Dvd> findNewestDVD() {

        List<Dvd> result = new ArrayList();
        Integer newestYear = 0;

        // Determine what the newestYear is
        for (Dvd d : dvdLibrary) {
            try {

                if (d.getReleaseDate().getYear() > newestYear) {
                    newestYear = d.getReleaseDate().getYear();
                }
            } catch (NullPointerException ex) {

            }

        }

        // Get a all of all the DVDs from newestYear
        for (Dvd d : dvdLibrary) {
            if (Objects.equals(d.getReleaseDate().getYear(), newestYear)) {
                result.add(d);
            }
        }

        Collections.sort(result);

        return result;

    }

    @Override
    public List<Dvd> findOldestDVD() {

        List<Dvd> result = new ArrayList();
        Integer oldestYear = 9999999;

        // Determine what the oldestYear is
        for (Dvd d : dvdLibrary) {

            try {
                if (d.getReleaseDate().getYear() < oldestYear) {
                    oldestYear = d.getReleaseDate().getYear();
                }
            } catch (NullPointerException ex) {

            }
        }

        // Populate the result all with Dvd objects from the oldestYear
        for (Dvd d : dvdLibrary) {
            if (Objects.equals(d.getReleaseDate().getYear(), oldestYear)) {
                result.add(d);
            }
        }

        Collections.sort(result);

        return result;

    }

    @Override
    public List<Dvd> searchByTitle(String title) {

        List<Dvd> result = new ArrayList<>();

        try {

            for (Dvd d : dvdLibrary) {
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
