/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dvdlibrary.controller;

import com.mycompany.dvdlibrary.dao.DVDDao;
import com.mycompany.dvdlibrary.dao.NoteDao;
import com.mycompany.dvdlibrary.dto.DVD;
import com.mycompany.dvdlibrary.dto.Note;
import com.mycompany.ui.ConsoleIO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulharding
 */
public class DVDController {

    private ConsoleIO io = new ConsoleIO();
    private DVDDao dvdDao;
    private NoteDao noteDao;

    public DVDController(DVDDao dvdDao, NoteDao noteDao) {
        this.dvdDao = dvdDao;
        this.noteDao = noteDao;
    }

    public void run() {

        boolean playAgain = true;

        while (playAgain) {

            io.println("");
            io.println("                                  =========");
            io.println("                                  MAIN MENU");
            io.println("                                  =========");
            io.println("");
            io.println("\"1\" Add DVD to your DVD Library           \"4\" Search your DVD Library");
            io.println("");
            io.println("\"2\" Remove DVD from DVD Library           \"5\" Edit a DVD in your DVD Library");
            io.println("");
            io.println("\"3\" List DVDs in your DVD Library         \"6\" Exit DVD Library");
            io.println("");
            io.println("");

            int userChoice = io.getUserInt("Please input the number that corresponds to what you want to do. ", 1, 6);

            switch (userChoice) {

                case 1:
                    addDVD();
                    break;

                case 2:
                    removeDvd();
                    break;

                case 3:
                    listDvds();
                    break;

                case 4:
                    searchDvds();
                    break;

                case 5:
                    editDvd();
                    break;

                case 6:
                    playAgain = false;

            }

        }

    }

    private void addDVD() {

        boolean addAgain = true;

        io.println("");
        io.println("=======");
        io.println("ADD DVD");
        io.println("=======");
        io.println("");

        while (addAgain) {

            String name = io.getNonEmptyUserString("What is the name of the DVD? ");
            Integer releaseYear = io.getUserInt("What year was " + name + " released? ");

            DVD newDVD = addDVD(name, releaseYear);

            io.println("");
            io.println("");
            io.println(name + " has been added.");
            io.println("To add more information about " + newDVD.getName() + ",\nuse the \"Edit a DVD in your library\" option from the Main Menu");
            io.println("");

            io.println("");
            int keepAdding = io.getUserInt("Input \"0\" to return to the Main Menu, or\nInput \"1\" to add another DVD. ");

            if (keepAdding == 0) {
                addAgain = false;
            } else {
                io.println("");
            }

        }

    }

    public DVD addDVD(String name, Integer releaseYear) {

        DVD newDVD = new DVD();

        newDVD.setName(name);
        newDVD.setReleaseYear(releaseYear);
        newDVD.setMpaaRating("(Rating not input)");
        newDVD.setDirectorName("(Director not input)");
        newDVD.setStudioName("(Studio not input)");

        dvdDao.create(newDVD);

        return newDVD;
    }

    private void removeDvd() {

        io.println("");
        io.println("==========");
        io.println("REMOVE DVD");
        io.println("==========");
        io.println("");

        boolean removeAgain = true;

        while (removeAgain) {

            int idKnowledge = io.getUserInt("Do you know the id of the DVD that you want to delete?\n(Input \"1\" for yes, or \"2\" for no) ", 1, 2);

            if (idKnowledge == 2) {
                getDVDId("remove");
            }

            int dvdId = io.getUserInt("What is the id of the DVD that you want to remove? ");

            DVD deleteDVD = dvdDao.read(dvdId);

            try {
                io.println("");
                io.println(deleteDVD.getId() + " | " + deleteDVD.getName() + " | " + deleteDVD.getReleaseYear());
                io.println("");

                int reallyDelete = io.getUserInt("Are you sure that you want to delete " + deleteDVD.getName() + "?\nInput \"1\" for yes, or \"2\" for no ", 1, 2);

                if (reallyDelete == 1) {

                    removeDVD(deleteDVD);

                    io.println("");
                    io.println(deleteDVD.getName() + " has been deleted.");
                    io.println("");

                } else {
                    io.println("");
                    io.println("OK, " + deleteDVD.getName() + " has not been deleted.");
                }
            } catch (NullPointerException ex) {
                io.println("");
                io.println("There does not appear to be a DVD in your library with that id.");
                io.println("");
            }

            int keepRemoving = io.getUserInt("Input \"0\" to return to the Main Menu, or\nInput \"1\" to remove another DVD. ");

            if (keepRemoving == 0) {
                removeAgain = false;
            } else {
                io.println("");
            }

        }

    }

    public void removeDVD(DVD deleteDVD) {

        List<Note> dvdNotes = noteDao.findByDVD(deleteDVD);

        for (Note n : dvdNotes) {
            noteDao.delete(n);
        }

        dvdDao.delete(deleteDVD);

    }

    private void listDvds() {

        io.println("");
        io.println("=============");
        io.println("VIEW DVD LIST");
        io.println("=============");
        io.println("");

        List<DVD> tempDVDList = dvdDao.list();

        for (DVD d : tempDVDList) {
            io.println(d.getId() + " | " + d.getName());
        }

        io.println("");
        int returnToMenu = io.getUserInt("Input \"0\" when you are ready to return to the Main Menu. ", 0, 0);

    }

    private void searchDvds() {

        io.println("");
        io.println("                        =======================");
        io.println("                        SEARCH YOUR DVD LIBRARY");
        io.println("                        =======================");
        io.println("");

        boolean searchAgain = true;

        while (searchAgain) {

            io.println("You can search the entries in DVD Library by several different attributes.");
            io.println("");
            io.println("\"1\" Search for Movies Released       \"4\" View the Average Age of Movies");
            io.println("    in the last 'n' years                in your DVD Library");
            io.println("");
            io.println("\"2\" Search for Movies with a         \"5\" Find the Newest Movie in your");
            io.println("    given MPAA Rating                    DVD Library");
            io.println("");
            io.println("\"3\" Search for Movies Released       \"6\" Find the Oldest Movie in your");
            io.println("    by a Given Studio                    DVD Library");
            io.println("");
            io.println("\"7\" Search for Movie by Title        \"8\" View the Average Number of notes");
            io.println("                                         associated with a DVD");
            io.println("");
            int searchBy = io.getUserInt("Input the number that corresponds to your choice ", 0, 8);
            io.println("");

            List<DVD> searchResults = new ArrayList();

            switch (searchBy) {

                case 1:
                    int years = io.getUserInt("How many years back would you like to search? ");
                    searchResults = dvdDao.searchLastNYears(years);

                    io.println("");
                    io.println("Search Results:");
                    printListOfDVDInfo(searchResults);
                    break;

                case 2:
                    String rating = io.getUserString("What MPAA rating would you like to search for? ");
                    searchResults = dvdDao.searchByMPAARating(rating);

                    io.println("");
                    io.println("Search Results:");
                    printListOfDVDInfo(searchResults);
                    break;

                case 3:
                    String studio = io.getUserString("What studio would you like to search for? ");
                    searchResults = dvdDao.searchByStudio(studio);

                    io.println("");
                    io.println("Search Results:");
                    printListOfDVDInfo(searchResults);
                    break;

                case 4:
                    Double age = dvdDao.averageAge();
                    Double printAge = Math.floor(age * 100) / 100;

                    io.println("");
                    io.println("The average age of the movies (that have a release year saved) in your DVD Library is " + printAge + " years old.");
                    break;

                case 5:
                    searchResults = dvdDao.findNewestDVD();

                    io.println("");
                    io.println("Newest DVD:");
                    printListOfDVDInfo(searchResults);
                    break;

                case 6:
                    searchResults = dvdDao.findOldestDVD();

                    io.println("");
                    io.println("Oldest DVD:");
                    printListOfDVDInfo(searchResults);
                    break;
                    
                case 7:
                    
                    String name = io.getNonEmptyUserString("What is the (exact) name of the DVD you want to search for? ");
                    
                    searchResults = dvdDao.searchByName(name);
                    
                    io.println("");
                    io.println("Search Results");
                    printListOfDVDInfo(searchResults);
                    break;
                    
                case 8:
                    
                    double average = noteDao.getAverageNumberOfNotes();
                    
                    io.println("");
                    io.println("The average number of notes associated with a DVD is " + average + " notes.");
                    io.println("");
                    
                    break;
                    
                default:
                    searchAgain = false;

            }

            io.println("");
            int keepSearching = io.getUserInt("Input \"0\" to return to the Main Menu, or\nInput \"1\" to search for another DVD. ");

            if (keepSearching == 0) {
                searchAgain = false;

            } else {
                io.println("");
                searchResults.clear(); // reset the search results
            }

        }

    }

    private void editDvd() {

        boolean editSameDVD = true;
        boolean editAgain = true;

        io.println("");
        io.println("========");
        io.println("EDIT DVD");
        io.println("========");
        io.println("");

        while (editAgain) {

            int idKnowledge = io.getUserInt("Do you know the id of the DVD you would like to edit?\n(Input \"1\" for yes, \"2\" for no) ", 1, 2);

            if (idKnowledge == 2) {
                getDVDId("edit");
            }

            int id = io.getUserInt("What is the id of the movie that you want to edit? ");

            while (editSameDVD) {

                DVD dvdToEdit = dvdDao.read(id);

                if (dvdToEdit != null) {

                    io.println("");
                    io.println("");
                    io.println("    Each DVD has many attributes that can be edited.");
                    io.println("Select the attribute that you want to edit for " + dvdToEdit.getName() + ".");
                    io.println("");
                    io.println("\"1\" Edit the name                  \"5\" Edit the studio");
                    io.println("");
                    io.println("\"2\" Edit the release year          \"6\" Edit your rating");
                    io.println("");
                    io.println("\"3\" Edit the MPAA rating           \"7\" Edit your notes");
                    io.println("");
                    io.println("\"4\" Edit the Director");
                    io.println("");
                    io.println("                Input \"0\" if you do not wish to edit anything about " + dvdToEdit.getName());
                    io.println("");

                    int userChoice = io.getUserInt("What would you like to edit? ", 0, 7);
                    io.println("");

                    switch (userChoice) {

                        case 0:
                            editSameDVD = false;
                            break;

                        case 1:

                            String newName = io.getUserString("What would you like to change the name of " + dvdToEdit.getName() + " to? ");
                            dvdToEdit.setName(newName);

                            dvdDao.update(dvdToEdit);

                            io.println("");
                            io.println("The name has set as " + dvdToEdit.getName() + ".");

                            break;

                        case 2:

                            int newReleaseYear = io.getUserInt("What would you like to set the release year of " + dvdToEdit.getName() + " to? ");
                            dvdToEdit.setReleaseYear(newReleaseYear);

                            dvdDao.update(dvdToEdit);

                            io.println("");
                            io.println("The release year of " + dvdToEdit.getName() + " has been set to " + dvdToEdit.getReleaseYear() + ".");

                            break;

                        case 3:

                            String newMpaaRating = io.getUserString("What would you like to set the MPAA Rating of " + dvdToEdit.getName() + " to? ");
                            dvdToEdit.setMpaaRating(newMpaaRating);

                            dvdDao.update(dvdToEdit);

                            io.println("");
                            io.println("The MPAA rating of " + dvdToEdit.getName() + " has been set to " + dvdToEdit.getMpaaRating() + ".");

                            break;

                        case 4:

                            String newDirectorName = io.getUserString("Who would you like to set the director of " + dvdToEdit.getName() + " to? ");
                            dvdToEdit.setDirectorName(newDirectorName);

                            dvdDao.update(dvdToEdit);

                            io.println("");
                            io.println("The director of " + dvdToEdit.getName() + " has been set to " + dvdToEdit.getDirectorName() + ".");

                            break;

                        case 5:

                            String newStudioName = io.getUserString("What would you like to set the studio of " + dvdToEdit.getName() + " to? ");
                            dvdToEdit.setStudioName(newStudioName);

                            dvdDao.update(dvdToEdit);

                            io.println("");
                            io.println("The studio of " + dvdToEdit.getName() + " has been set to " + dvdToEdit.getStudioName() + ".");

                            break;

                        case 6:

                            int newUserRating = io.getUserInt("What would you like to set your rating of " + dvdToEdit.getName() + " to? ");
                            dvdToEdit.setUserRating(newUserRating);

                            dvdDao.update(dvdToEdit);

                            io.println("");
                            io.println("Your rating of " + dvdToEdit.getName() + " has been set to " + dvdToEdit.getUserRating() + ".");

                            break;

                        case 7:
                            editDVDNote(dvdToEdit);
                            break;
                    }

                    if (editSameDVD) {
                        io.println("");
                        int editSameDVDInt = io.getUserInt("Would you like to edit any other information about " + dvdToEdit.getName() + " (\"1\" for yes, \"2\", for no)? ", 1, 2);

                        if (editSameDVDInt == 2) {
                            editSameDVD = false;
                        } else {

                        }

                    }

                } else {

                    io.println("");
                    io.println("There does not appear to be a movie with that ID number in your library.");
                    editSameDVD = false;

                }

            }

            io.println("");
            int editAgainInt = io.getUserInt("Input \"0\" to return to the Main Menu, or\nInput \"1\" to edit another DVD ", 0, 1);

            if (editAgainInt == 0) {
                editAgain = false;
            } else {
                io.println("");
            }

        }

    }

    private void editDVDNote(DVD dvdToEdit) {

        io.println("");
        io.println("                    ==========");
        io.println("                    EDIT NOTES");
        io.println("                    ==========");
        io.println("");

        io.println("            What would you like to do? ");
        io.println("");
        io.println("\"1\" Edit existing note        \"2\" Delete existing note");
        io.println("");
        io.println("                \"3\" Add a new note");
        io.println("");

        int userChoice = io.getUserInt("Input the number that corresponds to your choice ", 0, 3);

        switch (userChoice) {

            case 1:

                int idKnowledge = io.getUserInt("Do you know the id of the note you want to edit? ");
                if (idKnowledge == 2) {
                    getNoteId(dvdToEdit);
                }

                int noteId = io.getUserInt("What is the id of the note? ");

                Note note = noteDao.read(noteId);

                if (note != null) {

                    io.println("");
                    io.println("That note for " + note.getDvd().getName() + " currently says: " + note.getNote());
                    io.println("");

                    String newNote = io.getUserString("What would you like to change the note to say? ");

                    note.setNote(newNote);

                    noteDao.update(note);

                } else {
                    io.println("");
                    io.println("There does not appear to be a note with that id.");
                    io.println("");
                }

                break;

            case 2:

                idKnowledge = io.getUserInt("Do you know the id of the note you want to delete? ");
                if (idKnowledge == 2) {
                    getNoteId(dvdToEdit);
                }

                noteId = io.getUserInt("What is the id of the note? ");

                note = noteDao.read(noteId);

                if (note != null) {

                    io.println("");
                    io.println("That note for " + note.getDvd().getName() + " currently says: " + note.getNote());
                    io.println("");

                    int reallyDelete = io.getUserInt("Are you sure that you want to delete this note?\n(Input \"1\" for yes, or \"2\" for no) ", 1, 2);

                    if (reallyDelete == 1) {

                        noteDao.delete(note);

                        io.println("");
                        io.println("Note deleted successfully!");
                        io.println("");

                    } else {
                        io.println("");
                        io.println("OK, the note was not deleted.");
                        io.println("");
                    }

                } else {
                    io.println("");
                    io.println("There does not appear to be a note with that id.");
                    io.println("");
                }

                break;

            case 3:

                String newUserNote = io.getUserString("What is the new note for " + dvdToEdit.getName() + "?");

                Note newNote = new Note();

                newNote.setNote(newUserNote);
                newNote.setDvd(dvdToEdit);

                noteDao.create(newNote);

                io.println("");
                io.println("New note saved for " + dvdToEdit.getName() + "!");

                break;

            default:

        }

    }

    private void printListOfDVDs(List<DVD> dvdList) {

        for (DVD d : dvdList) {
            io.println("    " + d.getId() + " | " + d.getName() + " | " + d.getReleaseYear());
        }

        io.println("");
    }

    private void printListOfDVDInfo(List<DVD> searchResults) {

        /*
         This method takes a list of DVDs
         and it prints info about each DVD 
         in the list
         */
        for (DVD d : searchResults) {

            io.println("-------------------------------------");
            io.println("ID # " + d.getId());
            io.println("Name: " + d.getName() + " | " + d.getReleaseYear());
            io.println("MPAA Rating: " + d.getMpaaRating());
            io.println("Director: " + d.getDirectorName());
            io.println("Studio: " + d.getStudioName());
            io.println("Your Rating: " + d.getUserRating());

            io.println("Notes:");

            List<Note> dvdNotes = noteDao.findByDVD(d);

            for (int i = 0; i < dvdNotes.size(); i++) {
                io.println("    (" + (i + 1) + ") " + dvdNotes.get(i).getNote());
            }

        }

        io.println("-------------------------------------");
    }

    private void getDVDId(String operation) {

        io.println("");
        String name = io.getUserString("What is the (exact) name of the DVD that you want to " + operation + "? ");
        io.println("");

        List<DVD> dvdNameList = dvdDao.searchByName(name);

        printListOfDVDs(dvdNameList);

        io.println("");

    }

    private void getNoteId(DVD dvd) {

        io.println("");

        List<Note> noteList = noteDao.findByDVD(dvd);

        for (Note n : noteList) {
            io.println(n.getId() + " | " + n.getNote());
        }

        io.println("");

    }

}
