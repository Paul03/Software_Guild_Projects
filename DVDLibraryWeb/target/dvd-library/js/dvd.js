$(document).ready(function() {

    /* Read a DVD */
    $("#showDVDModal").on("shown.bs.modal", function (e) {

        var link = $(e.relatedTarget);
        var dvdId = link.data("dvd-id");

        $.ajax({
            url: contextRoot + "/DVD/" + dvdId ,
            type: "GET" ,
            dataType: "json" ,
            beforeSend: function(xhr) {
                xhr.setRequestHeader("Content-Type", "application/json");
            },
            success: function(dvd) {

                $("#showDVDModalHeader").text(dvd.dvd.title);

                $("#showTitle").text(dvd.dvd.title);
                $("#showReleaseDate").text(dvd.dvd.releaseDate);
                $("#showMpaaRating").text(dvd.dvd.mpaaRating);
                $("#showDirector").text(dvd.dvd.director);
                $("#showStudio").text(dvd.dvd.studio);
                $("#showUserRating").text(dvd.dvd.userRating);

                for (var i = 0; i < dvd.dvdNoteList.length; i++) {
                    var tableRow = buildShowNoteRow(dvd.dvdNoteList[i].noteText);
                    $("#showNoteTable").append( $(tableRow) );
                }

            },
            error: function() {
                console.log("DVD not found");
            }
        });
    });

    // Edit a DVD
    $("#editDVDModal").on("shown.bs.modal", function (e) {

        var link = $(e.relatedTarget);
        var dvdId = link.data("dvd-id");

        $.ajax({
            url: contextRoot +  "/DVD/" + dvdId,
            type: "GET",
            dataType: "json",
            beforeSend: function(xhr) {
                xhr.setRequestHeader("Accept", "application/json");
            },
            success: function(dvd) {

                $("#editDVDModalHeader").text(dvd.dvd.title);

                $("#editId").val(dvd.dvd.dvdId);
                $("#editTitle").val(dvd.dvd.title);
                $("#editReleaseDate").val(dvd.dvd.releaseDate);
                $("#editMpaaRating").val(dvd.dvd.mpaaRating);
                $("#editDirector").val(dvd.dvd.director);
                $("#editStudio").val(dvd.dvd.studio);
                $("#editUserRating").val(dvd.dvd.userRating);

                for (var i = 0; i < dvd.dvdNoteList.length; i++) {
                    var tableRow = buildEditNoteRow(dvd.dvdNoteList[i].noteText, dvd.dvdNoteList[i].noteId);
                    $("#editNoteTable").append( $(tableRow) );
                }

            },
            error: function() {
                console.log("Error reading DVD");
            }
        });
    });

    // Functionality - Edit Submit Button
    $("#editDVDButton").on("click", function(e) {

        e.preventDefault();

        var dvd = {
            dvdId: $("#editId").val() ,
            title: $("#editTitle").val() ,
            releaseDate: $("#editReleaseDate").val() ,
            mpaaRating: $("#editMpaaRating").val() ,
            director: $("#editDirector").val() ,
            studio: $("#editStudio").val() ,
            userRating: $("#editUserRating").val()
        };

        var dvdData = JSON.stringify(dvd);

        $.ajax({
            url: contextRoot + "/DVD/" + dvd.dvdId ,
            type: "PUT" ,
            data: dvdData ,
            dataType: "json" ,
            beforeSend: function(xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            },
            success: function(dvd) {

                clearEditDVDErrorMessages();

                var tableRow = buildDVDRow(dvd);
                $("#dvd-row-" + dvd.dvdId).replaceWith( $(tableRow) );

                $("#editDVDModal").modal("hide");

            },
            error: function(data) {

                clearEditDVDErrorMessages();

                var errors = data.responseJSON.errorList;

                $.each(errors, function(index, error) {
                    $("#edit-" + error.fieldName + "-validation-error").append(error.message);
                });
            }
        });

    });

});

function buildDVDRow(data) {

    var date = new Date(data.releaseDate);
    var releaseYear = date.getFullYear();

    return "\
                <tr id=\"dvd-row-" + data.dvdId + "\"> \n\
                    <td><a class=\"glyphicon glyphicon-expand\" data-dvd-id=" + data.dvdId + " data-toggle=\"modal\" data-target=\"#showDVDModal\" data-backdrop=\"false\"></a>" + data.title + "</td> \n\
                    <td>" + releaseYear + "</td> \n\
                    <td>" + data.mpaaRating + "</td> \n\
                    <td><a class=\"glyphicon glyphicon-edit\" data-dvd-id=" + data.dvdId + " data-toggle=\"modal\" data-target=\"#editDVDModal\" data-backdrop=\"false\" style=\"color:green;\"></a> Edit</td> \n\
                    <td><a class=\"glyphicon glyphicon-remove-circle\" data-dvd-id=" + data.dvdId + " data-toggle=\"modal\" data-target=\"#deleteDVDModal\" data-backdrop=\"false\" style=\"color:red;\"></a> Delete \n\
                    </td> \n\
                </tr>";

}