$(document).ready(function() {

    $('#searchButton').on('click', function(e) {

        e.preventDefault();

        $('.searchResultsRow').remove(); // clear out previous results

        var searchDVDCommand = {
            fieldToSearch: $('#categoryToSearchBy').val() ,
            valueToSearchFor: $('#valueToSearchFor').val()
        };

        var searchDvdCommandJSON = JSON.stringify(searchDVDCommand);

        $.ajax({
            url: contextRoot + "/DVD/Search/",
            type: "POST",
            data: searchDvdCommandJSON,
            dataType: "json",
            beforeSend: function(xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            },
            success: function(searchResultList, status) {

                $.each(searchResultList, function(index, dvd) {
                    var dvdTableRow = buildDVDRow(dvd);
                    $('#searchResultsTable').append( $(dvdTableRow) );
                })

            }
        });

    });

});