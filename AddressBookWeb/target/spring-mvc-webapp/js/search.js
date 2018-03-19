/**
 * Created by paulharding on 10/1/16.
 */

$(document).ready(function () {

    // Search Button functionality
    $('#searchButton').on('click', function(e) {

        e.preventDefault();
        $('.searchResultsRows').remove();

        var searchCommand = {
            fieldToSearch: $('#searchCriteria').val() ,
            valueToSearchFor: $('#searchValue').val()
        };

        var searchCommandData = JSON.stringify(searchCommand);

        $.ajax({
            url: contextRoot +  "/address/search" ,
            type: "POST" ,
            data: searchCommandData ,
            dataType: "json" ,
            beforeSend: function(xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            } ,
            success: function (searchResultList, status) {

                $.each(searchResultList, function(index, address) {

                    var addressRow = buildAddressRow(address);

                    $('#searchResultsTable').append( $(addressRow) );

                })

            },
            error: function(data, status) {

            }
        });

    });

    function buildAddressRow(address) {

        var tableRow = '\
                    <tr class="searchResultsRows" id="address-row-' + address.id + '"> \n\
                        <td><a data-address-id="' + address.id + '" data-toggle="modal" data-target="#viewAddressModal">' + address.firstName + ' ' + address.lastName + '</a></td> \n\
                        <td>' + address.streetAddress + '</td> \n\
                        <td>' + address.city + ', ' + address.state + '</td> \n\
                        <td><a data-address-id="' + address.id + '" data-toggle="modal" data-target="#editAddressModal">Edit</a></td> \n\
                        <td><a data-address-id="' + address.id + '" data-toggle="modal" data-target="#deleteAddressModal">Delete</a></td> \n\
                    </tr>';

        return tableRow;

    }

});