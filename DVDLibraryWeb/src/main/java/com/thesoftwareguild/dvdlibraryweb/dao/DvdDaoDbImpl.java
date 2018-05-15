package com.thesoftwareguild.dvdlibraryweb.dao;

import com.thesoftwareguild.dvdlibraryweb.dto.Dvd;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.thesoftwareguild.dvdlibraryweb.dto.Note;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import org.springframework.jdbc.core.RowMapper;

public class DvdDaoDbImpl implements DvdDao {

    private JdbcTemplate jdbcTemplate;
    private NoteDao noteDao;

    private static final String SQL_INSERT_DVD = "INSERT INTO dvd (title, release_date, mpaa_rating, director, studio, user_rating) VALUES(?, ?, ?, ?, ?, ?);";
    private static final String SQL_SELECT_DVD = "SELECT * FROM dvd WHERE id=?;";
    private static final String SQL_UPDATE_DVD = "UPDATE dvd SET title=?, release_date=?, mpaa_rating=?, director=?, studio=?, user_rating=? WHERE id=?;";
    private static final String SQL_DELETE_DVD = "DELETE FROM dvd WHERE id=?;";
    private static final String SQL_SELECT_ALL_DVD = "SELECT * FROM dvd ORDER BY title;";
    private static final String SQL_SELECT_LAST_N_YEARS = "SELECT * FROM dvd WHERE ( YEAR(release_Date) > YEAR(CURDATE()) - ? );";
    private static final String SQL_SELECT_MPAA_RATING = "SELECT * FROM dvd WHERE mpaa_rating=?;";
    private static final String SQL_SELECT_DIRECTOR = "SELECT * FROM dvd WHERE director=?;";
    private static final String SQL_SELECT_STUDIO = "SELECT * FROM dvd WHERE studio=?;";
    private static final String SQL_SELECT_TITLE = "SELECT * FROM dvd WHERE title=?;";
    private static final String SQL_SELECT_NEWEST_DVD = "SELECT * FROM dvd ORDER BY release_date desc;";
    private static final String SQL_SELECT_OLDEST_DVD = "SELECT * FROM dvd ORDER BY release_date asc;";

    public DvdDaoDbImpl(JdbcTemplate jdbcTemplate, NoteDao noteDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.noteDao = noteDao;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Dvd insert(Dvd dvd) {

        jdbcTemplate.update(SQL_INSERT_DVD,
                dvd.getTitle(),
                dvd.getReleaseDate(),
                dvd.getMpaaRating(),
                dvd.getDirector(),
                dvd.getStudio(),
                dvd.getUserRating());

        Integer newId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID();", Integer.class);
        dvd.setDvdId(newId);

        // Insert Note(s)
        dvd.getUserNotes().forEach(note -> noteDao.insert(note));

        return dvd;
    }

    @Override
    public Dvd retrieve(long id) {

        Dvd dvd = jdbcTemplate.queryForObject(SQL_SELECT_DVD, new DVDMapper(), id);
        List<Note> userNotes = noteDao.findByDvd(dvd);
        dvd.setUserNotes(userNotes);

        return dvd;

    }

    @Override
    public void update(Dvd dvd) {

        jdbcTemplate.update(SQL_UPDATE_DVD,
                dvd.getTitle(),
                dvd.getReleaseDate(),
                dvd.getMpaaRating(),
                dvd.getDirector(),
                dvd.getStudio(),
                dvd.getUserRating(),
                dvd.getDvdId());

        dvd.getUserNotes().forEach(note -> noteDao.update(note));

    }

    @Override
    public void delete(Dvd dvd) {

        jdbcTemplate.update("DELETE FROM note WHERE dvd_id=?", dvd.getDvdId());
        jdbcTemplate.update(SQL_DELETE_DVD, dvd.getDvdId());

    }

    @Override
    public List<Dvd> all() {

        List<Dvd> dvds = jdbcTemplate.query(SQL_SELECT_ALL_DVD, new DVDMapper());
        setUserNotes(dvds);

        return dvds;

    }

    @Override
    public List<Dvd> searchLastNYears(int years) {

        List<Dvd> dvds = jdbcTemplate.query(SQL_SELECT_LAST_N_YEARS, new DVDMapper(), years);
        setUserNotes(dvds);

        return dvds;

    }

    @Override
    public List<Dvd> searchByMPAARating(String rating) {

        List<Dvd> dvds = jdbcTemplate.query(SQL_SELECT_MPAA_RATING, new DVDMapper(), rating);
        setUserNotes(dvds);

        return dvds;

    }

    @Override
    public List<Dvd> searchByDirector(String directorName) {

        List<Dvd> dvds = jdbcTemplate.query(SQL_SELECT_DIRECTOR, new DVDMapper(), directorName);
        setUserNotes(dvds);

        return dvds;

    }

    @Override
    public List<Dvd> searchByStudio(String studioName) {

        List<Dvd> dvds = jdbcTemplate.query(SQL_SELECT_STUDIO, new DVDMapper(), studioName);
        setUserNotes(dvds);

        return dvds;

    }

    @Override
    public List<Dvd> findNewestDvd() {

        List<Dvd> dvds = jdbcTemplate.query(SQL_SELECT_NEWEST_DVD, new DVDMapper());
        setUserNotes(dvds);

        return dvds;
    }

    @Override
    public List<Dvd> findOldestDvd() {

        List<Dvd> dvds = jdbcTemplate.query(SQL_SELECT_OLDEST_DVD, new DVDMapper());
        setUserNotes(dvds);

        return dvds;

    }

    @Override
    public List<Dvd> searchByTitle(String title) {

        List<Dvd> dvds = jdbcTemplate.query(SQL_SELECT_TITLE, new DVDMapper(), title);
        setUserNotes(dvds);

        return dvds;

    }

    private void setUserNotes(List<Dvd> dvds) {

        dvds.forEach(dvd -> {
            List<Note> dvdNotes = noteDao.findByDvd(dvd);
            dvd.setUserNotes(dvdNotes);
        });

    }

    private static final class DVDMapper implements RowMapper<Dvd> {

        @Override
        public Dvd mapRow(ResultSet resultSet, int i) throws SQLException {

            Dvd dvd = new Dvd();

            dvd.setDvdId(resultSet.getInt("id"));
            dvd.setTitle(resultSet.getString("title"));
            dvd.setReleaseDate(resultSet.getDate("release_date"));
            dvd.setMpaaRating(resultSet.getString("mpaa_rating"));
            dvd.setDirector(resultSet.getString("director"));
            dvd.setStudio(resultSet.getString("studio"));
            dvd.setUserRating(resultSet.getInt("user_rating"));

            return dvd;

        }
    }

}
