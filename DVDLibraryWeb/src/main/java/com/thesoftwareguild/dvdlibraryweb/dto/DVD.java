package com.thesoftwareguild.dvdlibraryweb.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

public class DVD implements Comparable {

    private long dvdId;

    @NotEmpty(message="Title is a required field")
    private String title;

    @NotNull(message="Release date is a required field")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="America/Phoenix")
    private Date releaseDate;

    @NotEmpty(message="MPAA Rating is a required field")
    private String mpaaRating;

    @NotEmpty(message="Director is a required field")
    private String director;

    @NotEmpty(message="Studio is a required field")
    private String studio;

    @NotNull(message="Your rating is a required field")
    private int userRating;

    private List<Note> userNotes;

    public DVD() {

    }

    public DVD(DVD dvd) {
        this.dvdId = dvd.getDvdId();
        this.title = dvd.getTitle();
        this.releaseDate = dvd.getReleaseDate();
        this.mpaaRating = dvd.getMpaaRating();
        this.director = dvd.getDirector();
        this.studio = dvd.getStudio();
        this.userRating = dvd.getUserRating();
        this.userNotes = dvd.getUserNotes();
    }

    public long getDvdId() {
        return dvdId;
    }

    public void setDvdId(long dvdId) {
        this.dvdId = dvdId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getMpaaRating() {
        return mpaaRating;
    }

    public void setMpaaRating(String mpaaRating) {
        this.mpaaRating = mpaaRating;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public int getUserRating() {
        return userRating;
    }

    public void setUserRating(int userRating) {
        this.userRating = userRating;
    }

    public List<Note> getUserNotes() {
        return userNotes;
    }

    public void setUserNotes(List<Note> userNotes) {
        this.userNotes = userNotes;
    }

    @Override
    public int compareTo(Object o) {

        DVD dvdToCompare = (DVD) o;
        String titleToCompare = dvdToCompare.getTitle();

        return this.title.compareToIgnoreCase(titleToCompare);
    }

}
