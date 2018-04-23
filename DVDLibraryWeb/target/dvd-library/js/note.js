$(document).ready(function() {

    // Create a Note
    $("#createNoteButton").on("click", function(e) {

        e.preventDefault();

        var dvd = {
            dvdId: $("#editId").val()
        };

        var note = {
            noteText: $("#createNoteText").val(),
            dvd: dvd
        };

        var noteData = JSON.stringify(note);

        $.ajax({
            url: contextRoot + "/Note/",
            type: "POST",
            data: noteData,
            dataType: "json",
            beforeSend: function(xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            },
            success: function(note) {

                clearAddNoteErrorMessages();

                var tableRow = buildEditNoteRow(note.noteText, note.noteId);

                $("#editNoteTable").append( $(tableRow) );

                $("#createNoteModal").modal("toggle");

            },
            error: function(data, status) {

                clearAddNoteErrorMessages();

                var errors = data.responseJSON.errorList;
                $.each(errors, function(index, error) {
                    $("#add-" + error.fieldName + "-validation-error").append(error.message);
                });

            }
        });

    });

    // Edit Note
    $("#editNoteModal").on("shown.bs.modal", function(e) {

        var link = $(e.relatedTarget);
        var noteId = link.data("note-id");

        $.ajax({
            url: contextRoot +  "/Note/" + noteId ,
            type: "GET" ,
            dataType: "json" ,
            beforeSend: function(xhr) {
                xhr.setRequestHeader("Accept", "application/json");
            },
            success: function(data, status) {

                $("#editNoteId").val(data.noteId);
                $("#editNoteText").val(data.noteText);
                $("#editNoteDvdId").val(data.dvd.dvdId);

            },
            error: function(data, status) {
                console.log("Error reading Note");
            }
        });

    });

    // Functionality - Edit Note "Submit Changes" Button
    $("#editNoteButton").on("click", function(e) {

        e.preventDefault();

        var dvd = {
            dvdId: $("#editNoteDvdId").val()
        };

        var note = {
            noteId: $("#editNoteId").val() ,
            noteText: $("#editNoteText").val() ,
            dvd: dvd
        };

        var noteData = JSON.stringify(note);

        $.ajax({
            url: contextRoot + "/Note/" + note.noteId ,
            type: "PUT" ,
            data: noteData ,
            dataType: "json" ,
            beforeSend: function(xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            },
            success: function(data, status) { // A Note object is the response (data)

                clearEditNoteErrorMessages();

                var tableRow = buildEditNoteRow(data.noteText, data.noteId);

                $("#note-row-" + data.noteId).replaceWith( $(tableRow) );

                $("#editNoteModal").modal("hide");

            },
            error: function(data, status) {

                clearEditNoteErrorMessages();

                var errors = data.responseJSON.errorList;

                $.each(errors, function(index, error) {

                    if(error.fieldName == "noteText") {
                        $("#edit-noteText-validation-error").append(error.message);
                    }

                });

            }
        });

    });

    // Delete Note
    $("#deleteNoteModal").on("shown.bs.modal", function(e) {

        link = $(e.relatedTarget);
        noteId = link.data("note-id");

        $.ajax({
            url: contextRoot + "/Note/" +  noteId ,
            type: "GET" ,
            dataType: "json" ,
            beforeSend: function(xhr) {
                xhr.setRequestHeader("Accept", "application/json");
            },
            success: function(data, status) {
                $("#deleteNoteId").val(data.noteId);
                $("#deleteNoteText").text(data.noteText);
            },
            error: function(data, status) {
                console.log("Error with something");
            }
        });

    });

    // Functionality - Delete Note Button
    $("#deleteNoteButton").on("click", function(e) {

        var note = {
            noteId: $("#deleteNoteId").val()
        };

        var noteData = JSON.stringify(note);

        $.ajax({
            url: contextRoot + "/Note/" + noteId ,
            type: "DELETE" ,
            data: noteData ,
            dataType: "json" ,
            beforeSend: function(xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            },
            success: function(data, status) {
                $("#note-row-" + data.noteId).remove();
            },
            error: function(data, status) {
                console.log("Error in the Delete Note Button method");
            }
        });

    });

});