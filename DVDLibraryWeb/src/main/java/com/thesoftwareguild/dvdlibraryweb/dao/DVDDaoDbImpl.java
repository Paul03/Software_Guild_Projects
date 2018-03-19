package com.thesoftwareguild.dvdlibraryweb.dao;

import com.thesoftwareguild.dvdlibraryweb.dto.DVD;
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
    public DVD create(DVD dvd) {

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
    public DVD read(long id) {
        
        DVD dvd = jdbcTemplate.queryForObject(SQL_SELECT_DVD, new DVDMapper(), id);
        return dvd;
        
    }

    @Override
    public void update(DVD dvd) {
        
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
    public void delete(DVD dvd) {

        jdbcTemplate.update("DELETE FROM note WHERE dvd_id=?", dvd.getDvdId());
        
        jdbcTemplate.update(SQL_DELETE_DVD, dvd.getDvdId());

    }

    @Override
    public List<DVD> list() {
        
        List<DVD> dvdList = jdbcTemplate.query(SQL_SELECT_ALL_DVD, new DVDMapper());
        
        return dvdList;

    }

    @Override
    public List<DVD> searchLastNYears(int years) {

        List<DVD> searchResultList = jdbcTemplate.query(SQL_SELECT_LAST_N_YEARS, new DVDMapper(), years);

        return searchResultList;

    }

    @Override
    public List<DVD> searchByMPAARating(String rating) {

        List<DVD> searchResultsList = jdbcTemplate.query(SQL_SELECT_MPAA_RATING, new DVDMapper(), rating);

        return searchResultsList;

    }

    @Override
    public List<DVD> searchByDirector(String directorName) {

        List<DVD> searchResultsList = jdbcTemplate.query(SQL_SELECT_DIRECTOR, new DVDMapper(), directorName);

        return searchResultsList;

    }

    @Override
    public List<DVD> searchByStudio(String studioName) {

        List<DVD> searchResultsList = jdbcTemplate.query(SQL_SELECT_STUDIO, new DVDMapper(), studioName);

        return searchResultsList;

    }

    @Override
    public Double averageAge() {

        Double result = jdbcTemplate.queryForObject(SQL_AVERAGE_AGE, Double.class);

        return result;
    }

    @Override
    public List<DVD> findNewestDVD() {

        List<DVD> sortedDVDList = jdbcTemplate.query(SQL_SELECT_NEWEST_DVD, new DVDMapper());

        return sortedDVDList;
    }

    @Override
    public List<DVD> findOldestDVD() {

        List<DVD> sortedDVDList = jdbcTemplate.query(SQL_SELECT_OLDEST_DVD, new DVDMapper());

        return sortedDVDList;

    }

    @Override
    public List<DVD> searchByTitle(String title) {

        List<DVD> searchResultsList = jdbcTemplate.query(SQL_SELECT_TITLE, new DVDMapper(), title);

        return searchResultsList;
    }
    
    private static final class DVDMapper implements RowMapper<DVD> {

        @Override
        public DVD mapRow(ResultSet resultSet, int i) throws SQLException {

            DVD dvd = new DVD();

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
