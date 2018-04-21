$(document).ready(function() {




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