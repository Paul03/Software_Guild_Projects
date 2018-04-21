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