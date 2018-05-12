$(document).ready(function () {

    /* Create DVD */
    $("#addDVDButton").on("click", function (e) {

        e.preventDefault();

        clearAddDVDErrorMessages();
        var addDVDFormIsValid = checkRequiredFieldValidation("addDVDForm");

        if (addDVDFormIsValid) {
            makeAjaxCallToCreateDvd();
        }
    });

    // Delete DVD
    $("#deleteDVDModal").on("shown.bs.modal", function(e) {

        var link = $(e.relatedTarget);
        var dvdId = link.data("dvd-id");

        $.ajax({
            url: contextRoot + "/DVD/" + dvdId,
            type: "GET",
            dataType: "json",
            beforeSend: function(xhr) {
                xhr.setRequestHeader("Accept", "application/json");
            },
            success: function(data) {

                $("#deleteDVDModalHeader").text("Really Delete " + data.dvd.title + "?");

                $("#deleteId").val(data.dvd.dvdId);
                $("#deleteTitle").text(data.dvd.title);
                $("#deleteReleaseDate").text(data.dvd.releaseDate);
                $("#deleteMpaaRating").text(data.dvd.mpaaRating);
                $("#deleteDirector").text(data.dvd.director);
                $("#deleteStudio").text(data.dvd.studio);
                $("#deleteUserRating").text(data.dvd.userRating);

                $("#deleteUserNotes").val(data.userNotes);

                for (var i = 0; i < data.dvdNoteList.length; i++) {
                   var tableRow = buildShowNoteRow(data.dvdNoteList[i].noteText);
                   $("#deleteNoteTable").append( $(tableRow) );
               }
            },
            error: function(data, status) {
                console.log("DVD not found");
            }
        });
    });

    // Functionality - Delete DVD Button
    $("#deleteDVDButton").on("click", function(e) {

        e.preventDefault();

        var dvd = {
            dvdId: $("#deleteId").val() ,
            title: $("#deleteTitle").val() ,
            releaseDate: $("#deleteReleaseDate").val() ,
            mpaaRating: $("#deleteMpaaRating").val() ,
            director: $("#deleteDirector").val() ,
            studio: $("#deleteStudio").val() ,
            userRating: $("#deleteUserRating").val() ,

            userNotes: $("#deleteUserNotes").val()
        };

        var dvdData = JSON.stringify(dvd);

        $.ajax({
            url: contextRoot + "/DVD/" + dvd.dvdId ,
            type: "DELETE" ,
            data: dvdData ,
            dataType: "json" ,
            beforeSend: function(xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            },
            success: function(dvd) {
                $("#dvd-row-" + dvd.dvdId).remove();
            },
            error: function() {
                console.log("DVD not found");
            }
        });

    });











    $(".addDVDFormElements").on("input", function() {
        checkRequiredFieldValidation();
    });

    function makeAjaxCallToCreateDvd() {

        var addDVDCommand = {
            title: $("#add-title").val(),
            releaseDate: $("#add-releaseDate").val(),
            mpaaRating: $("#add-mpaaRating").val(),
            director: $("#add-director").val(),
            studio: $("#add-studio").val(),
            userRating: $("#add-userRating").val(),
            noteText: $("#noteTextCreate").val()
        };

        var addDVDCommandData = JSON.stringify(addDVDCommand);

        $.ajax({
            url: contextRoot + "/DVD/",
            type: "POST",
            data: addDVDCommandData,
            dataType: "json",
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            },
            success: function (data, status) {

                var tableRow = buildDVDRow(data);
                $("#DVDTable").append( $(tableRow) );

                $(".addDVDFormElements").val("");
                $("#noteTextCreate").val("");

            },
            error: function (data, status) {

                var errors = data.responseJSON.errorList;

                $.each(errors, function(index, error) {

                    $("#add-" + error.fieldName + "-validation-error").append(error.message);
                    $("#add-" + error.fieldName).addClass("validationError");

                });

            }
        });

    }

    /**
     * Methods for clearing data in modals
     * when they are closed
     */

    // showDVDModal
    $("#showDVDModal").on("hidden.bs.modal", function () {

        $("#showDVDModalHeader").text("");

        $("#showTitle").text("");
        $("#showReleaseDate").text("");
        $("#showMpaaRating").text("");
        $("#showDirector").text("");
        $("#showStudio").text("");
        $("#showUserRating").text("");

        $("#showNotesTableHeader").text("");

        $(".noteRows").text("");

    });

    // editDVDModal
    $("#editDVDModal").on("hidden.bs.modal", function () {

        $("#editTitle").val("");
        $("#editReleaseDate").val("");
        $("#editDirector").val("");
        $("#editStudio").val("");

        $(".noteRows").text("");

    });

    // deleteDVDModal
    $("#deleteDVDModal").on("hidden.bs.modal", function () {

        $("#deleteTitle").text("");
        $("#deleteReleaseDate").text("");
        $("#deleteMpaaRating").text("");
        $("#deleteDirector").text("");
        $("#deleteStudio").text("");

        $(".noteRows").text("");

    });

    // createNoteModal
    $("#createNoteModal").on("hidden.bs.modal", function() {

        $("#createNoteText").val("");

        $("#add-noteText-validation-error").text("");

    });

    // editNoteModal
    $("#editNoteModal").on("hidden.bs.modal", function() {

        $("#editNoteText").val("");

        $("#edit-noteText-validation-error").text("");

    });

});

function buildShowNoteRow(text) {

    return "\
                <tr class=\"noteRows\"> \n\
                    <td>" + text + "</td> \n\
                </tr>";

}

function buildEditNoteRow(text, noteId) {

    return "\
                <tr id=\"note-row-\"" + noteId + " class=\"noteRows\"> \n\
                    <td>" + text + "</td> \n\
                    <td><a data-note-id=" + noteId + " data-toggle=\"modal\" data-target=\"#editNoteModal\" data-backdrop=\"false\">Edit</a></td>\n\
                    <td><a data-note-id=" + noteId + " data-toggle=\"modal\" data-target=\"#deleteNoteModal\" data-backdrop=\"false\">Delete</a></td>\n\
                </tr>";

}

function clearEditDVDErrorMessages() {

    $("#edit-title-validation-error").text("");
    $("#edit-releaseDate-validation-error").text("");
    $("#edit-mpaaRating-validation-error").text("");
    $("#edit-director-validation-error").text("");
    $("#edit-studio-validation-error").text("");
    $("#edit-userRating-validation-error").text("");

}

function clearAddDVDErrorMessages() {

    $("#add-title-validation-error").text("");
    $("#add-releaseDate-validation-error").text("");
    $("#add-mpaaRating-validation-error").text("");
    $("#add-director-validation-error").text("");
    $("#add-studio-validation-error").text("");
    $("#add-userRating-validation-error").text("");

    $("#add-title").removeClass("validationError");
    $("#add-releaseDate").removeClass("validationError");
    $("#add-mpaaRating").removeClass("validationError");
    $("#add-director").removeClass("validationError");
    $("#add-studio").removeClass("validationError");
    $("#add-userRating").removeClass("validationError");

}

function clearAddNoteErrorMessages() {
    $("#add-noteText-validation-error").text("");
}

function clearEditNoteErrorMessages() {
    $("#edit-noteText-validation-error").text("");
}

// Summary: Checks if fields marked as required have data in them
// Parameter: The CSS id of the form to be checked
// Returns: True/False whether the form is valid or not
function checkRequiredFieldValidation(formId) {

    var elementsInDVDForm = $("#" + formId).prop("elements"); // List all elements in the form
    var formIsValid = true; // variable to track if form is valid

    for (var i = 0; i < elementsInDVDForm.length; i++) {

        var ajaxElementId = "#" + elementsInDVDForm[i].id;
        var isRequiredField = $(ajaxElementId).hasClass("required");

        if (isRequiredField) {

            var fieldIsValid = $(ajaxElementId).val() != "" && $(ajaxElementId).val() != null;

            if (fieldIsValid) {
                $(ajaxElementId).removeClass("validationError");
            } else {
                $(ajaxElementId).addClass("validationError");
                // TODO display clientside error message divs
                formIsValid = false;
            }

        }

    }

    return formIsValid;

}