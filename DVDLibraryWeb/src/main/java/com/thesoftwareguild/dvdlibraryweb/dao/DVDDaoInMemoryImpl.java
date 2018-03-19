package com.thesoftwareguild.dvdlibraryweb.dao;

import com.thesoftwareguild.dvdlibraryweb.dto.DVD;
import java.util.*;

public class DVDDaoInMemoryImpl implements DVDDao {

    private List<DVD> dvdLibrary = new ArrayList<>();
    private int currentYear = Calendar.getInstance().get(Calendar.YEAR);
    private long nextId = 1;

    public DVDDaoInMemoryImpl() {

    }

    @Override
    public DVD create(DVD dvd) {

        dvd.setDvdId(nextId);

        dvdLibrary.add(dvd);

        nextId++;

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

    }

    @Override
    public void delete(DVD dvd) {

        for (DVD d : dvdLibrary) {
            if (d.getDvdId() == dvd.getDvdId()) {
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
    public List<DVD> searchLastNYears(int years) {

        Integer targetYear = currentYear - years;

        List<DVD> result = new ArrayList();

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

        List<DVD> result = new ArrayList();

        try {

            for (DVD d : dvdLibrary) {
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
        
        List<DVD> result = new ArrayList();

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
