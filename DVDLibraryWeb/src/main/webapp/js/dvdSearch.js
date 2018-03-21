$(document).ready(function() {

    $('#searchButton').on('click', function(e) {

        e.preventDefault();

        $('.searchResultsRow').remove(); // clear out previous results

        var searchDVDCommand = {
            fieldToSearch: $('#categoryToSearchBy').val() ,
            valueToSearchFor: $('#valueToSearchFor').val()
        };

        var searchDVDCommandJSON = JSON.stringify(searchDVDCommand);

        $.ajax({
            url: contextRoot + "/DVD/Search/" ,
            type: "POST" ,
            data: searchDVDCommandJSON ,
            dataType: "json" ,
            beforeSend: function(xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            } ,
            success: function(searchResultList, status) {

                $.each(searchResultList, function(index, dvd) {
                    var dvdTableRow = buildDVDRow(dvd);
                    $('#searchResultsTable').append( $(dvdTableRow) );
                })

            }
        });

    });

    function buildDVDRow(dvd) {

        var date = new Date(dvd.releaseDate);
        var releaseYear = date.getFullYear();

        return '\
                <tr class="searchResultsRow" id="dvd-row-' + dvd.dvdId + '"> \n\
                    <td><a data-dvd-id="' + dvd.dvdId + '" data-toggle="modal" data-target="#showDVDModal" data-backdrop="false">' + dvd.title + '</a></td> \n\
                    <td>' + releaseYear + '</td> \n\
                    <td>' + dvd.mpaaRating + '</td> \n\
                    <td><a data-dvd-id="' + dvd.dvdId + '" data-toggle="modal" data-target="#editDVDModal" data-backdrop="false">Edit</a></td> \n\
                    <td><a data-dvd-id="' + dvd.dvdId + '" data-toggle="modal" data-target="#deleteDVDModal" data-backdrop="false">Delete</a></td> \n\
                </tr>';

    };

});