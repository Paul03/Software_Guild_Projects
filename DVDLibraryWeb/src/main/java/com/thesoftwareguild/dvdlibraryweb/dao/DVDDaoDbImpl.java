package com.thesoftwareguild.dvdlibraryweb.dao;

import com.thesoftwareguild.dvdlibraryweb.dto.Dvd;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import org.springframework.jdbc.core.RowMapper;

public class DVDDaoDbImpl implements DVDDao {

    private JdbcTemplate jdbcTemplate;

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
    private static final String SQL_AVERAGE_AGE = "SELECT YEAR(CURDATE()) - AVG( YEAR(release_date) ) FROM dvd;";
    private static final String SQL_SELECT_NEWEST_DVD = "SELECT * FROM dvd ORDER BY release_date desc;";
    private static final String SQL_SELECT_OLDEST_DVD = "SELECT * FROM dvd ORDER BY release_date asc;";

    public DVDDaoDbImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Dvd create(Dvd dvd) {

        jdbcTemplate.update(SQL_INSERT_DVD,
                dvd.getTitle(),
                dvd.getReleaseDate(),
                dvd.getMpaaRating(),
                dvd.getDirector(),
                dvd.getStudio(),
                dvd.getUserRating());

        Integer newId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID();", Integer.class);
        dvd.setDvdId(newId);

        return dvd;
    }

    @Override
    public Dvd read(long id) {

        return jdbcTemplate.queryForObject(SQL_SELECT_DVD, new DVDMapper(), id);
        
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

    }

    @Override
    public void delete(Dvd dvd) {

        jdbcTemplate.update("DELETE FROM note WHERE dvd_id=?", dvd.getDvdId());
        jdbcTemplate.update(SQL_DELETE_DVD, dvd.getDvdId());

    }

    @Override
    public List<Dvd> list() {

        return jdbcTemplate.query(SQL_SELECT_ALL_DVD, new DVDMapper());

    }

    @Override
    public List<Dvd> searchLastNYears(int years) {

        return jdbcTemplate.query(SQL_SELECT_LAST_N_YEARS, new DVDMapper(), years);

    }

    @Override
    public List<Dvd> searchByMPAARating(String rating) {

        return jdbcTemplate.query(SQL_SELECT_MPAA_RATING, new DVDMapper(), rating);

    }

    @Override
    public List<Dvd> searchByDirector(String directorName) {

        return jdbcTemplate.query(SQL_SELECT_DIRECTOR, new DVDMapper(), directorName);

    }

    @Override
    public List<Dvd> searchByStudio(String studioName) {

        return jdbcTemplate.query(SQL_SELECT_STUDIO, new DVDMapper(), studioName);

    }

    @Override
    public Double averageAge() {

        return jdbcTemplate.queryForObject(SQL_AVERAGE_AGE, Double.class);
    }

    @Override
    public List<Dvd> findNewestDVD() {

        return jdbcTemplate.query(SQL_SELECT_NEWEST_DVD, new DVDMapper());
    }

    @Override
    public List<Dvd> findOldestDVD() {

        return jdbcTemplate.query(SQL_SELECT_OLDEST_DVD, new DVDMapper());

    }

    @Override
    public List<Dvd> searchByTitle(String title) {

        return jdbcTemplate.query(SQL_SELECT_TITLE, new DVDMapper(), title);
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
