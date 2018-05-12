package com.thesoftwareguild.dvdlibraryweb.dao;

import com.thesoftwareguild.dvdlibraryweb.dto.Dvd;

import java.util.*;

public class DvdDaoInMemoryImpl implements DvdDao {

    private List<Dvd> dvdLibrary = new ArrayList<>();
    private int currentYear = Calendar.getInstance().get(Calendar.YEAR);
    private long nextId = 1;

    public DvdDaoInMemoryImpl() {

    }

    @Override
    public Dvd create(Dvd dvd) {

        dvd.setDvdId(nextId);

        dvdLibrary.add(dvd);

        nextId++;

        return new Dvd(dvd);
    }

    @Override
    public Dvd read(long id) {

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

    }

    @Override
    public void delete(Dvd dvd) {

        for (Dvd d : dvdLibrary) {
            if (d.getDvdId() == dvd.getDvdId()) {
                dvdLibrary.remove(d);
                break;
            }
        }

    }

    @Override
    public List<Dvd> list() {

        Collections.sort(dvdLibrary);

        return new ArrayList( dvdLibrary );

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

        List<Dvd> result = new ArrayList();

        try {

            for (Dvd d : dvdLibrary) {
                if (d.getStudio().toLowerCase().equals(studioName.toLowerCase())) {
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

        // Get a list of all the DVDs from newestYear
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

        // Populate the result list with Dvd objects from the oldestYear
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

        List<Dvd> result = new ArrayList();

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
