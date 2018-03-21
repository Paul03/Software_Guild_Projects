package com.thesoftwareguild.dvdlibraryweb.dao;

import com.thesoftwareguild.dvdlibraryweb.dto.Dvd;
import com.thesoftwareguild.dvdlibraryweb.dto.Note;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class NoteDaoDbImpl implements NoteDao {

    private DVDDao dvdDao;
    private JdbcTemplate jdbcTemplate;

    private static final String SQL_INSERT_NOTE = "INSERT INTO note (note_text, dvd_id) VALUES(?, ?);";
    private static final String SQL_SELECT_NOTE = "SELECT * FROM note WHERE id=?;";
    private static final String SQL_UPDATE_NOTE = "UPDATE note SET note_text=?, dvd_id=? WHERE id=?;";
    private static final String SQL_DELETE_NOTE = "DELETE FROM note WHERE id=?;";
    private static final String SQL_SELECT_BY_DVD = "SELECT * FROM note WHERE dvd_id=?;";

    public NoteDaoDbImpl(DVDDao dvdDao, JdbcTemplate jdbcTemplate) {
        this.dvdDao = dvdDao;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Note create(Note note) {

        jdbcTemplate.update(SQL_INSERT_NOTE,
                note.getNoteText(),
                note.getDvd().getDvdId());

        Integer noteId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID();", Integer.class);

        note.setNoteId(noteId);

        return note;

    }

    @Override
    public Note read(long id) {

        return jdbcTemplate.queryForObject(SQL_SELECT_NOTE, new NoteMapper(), id);

    }

    @Override
    public void update(Note note) {

        jdbcTemplate.update(SQL_UPDATE_NOTE,
                note.getNoteText(),
                note.getDvd().getDvdId(),
                note.getNoteId());

    }

    @Override
    public void delete(Note note) {

        jdbcTemplate.update(SQL_DELETE_NOTE, note.getNoteId());

    }

    @Override
    public List<Note> findByDVD(Dvd dvd) {

        return jdbcTemplate.query(SQL_SELECT_BY_DVD, new NoteMapper(), dvd.getDvdId());

    }

    @Override
    public double getAverageNumberOfNotes() {
        return 0;
    }

    private final class NoteMapper implements RowMapper<Note> {

        @Override
        public Note mapRow(ResultSet resultSet, int i) throws SQLException {

            Integer dvdId = resultSet.getInt("dvd_id");

            Dvd dvd = dvdDao.read(dvdId);

            Note note = new Note();

            note.setNoteId(resultSet.getInt("id"));
            note.setNoteText(resultSet.getString("note_text"));
            note.setDvd(dvd);

            return note;

        }
    }

}
