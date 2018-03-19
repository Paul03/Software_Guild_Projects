/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {


    // Create an address
    $('#addAddressButton').on('click', function (e) { // this code is triggered by the 'Create Contact' button being clicked on the home page

        e.preventDefault();

        var address = {
            firstName: $('#firstNameCreate').val(),
            lastName: $('#lastNameCreate').val(),
            streetAddress: $('#streetAddressCreate').val(),
            city: $('#cityCreate').val(),
            state: $('#stateCreate').val(),
            zipCode: $('#zipCodeCreate').val()
        };

        var addressData = JSON.stringify(address);

        $.ajax({
            url: contextRoot + "/address",
            type: "POST",
            data: addressData,
            dataType: "json",
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-type", "application/json");
            },
            success: function (data, status) {

                clearCreateAddressErrorMessages();

                var tableRow = buildAddressRow(data);

                $('#addressTable').append($(tableRow));

                $('#firstNameCreate').val('');
                $('#lastNameCreate').val('');
                $('#streetAddressCreate').val('');
                $('#cityCreate').val('');
                $('#stateCreate').val('');
                $('#zipCodeCreate').val('');

            },
            error: function (data, status) {

                clearCreateAddressErrorMessages();
                
                var errors = data.responseJSON.errorList;

                $.each(errors, function(index, error) {

                    if(error.fieldName == "firstName") {
                        $('#add-firstName-validation-error').append(error.message);
                    }

                    if(error.fieldName == "lastName") {
                        $('#add-lastName-validation-error').append(error.message);
                    }

                    if(error.fieldName == "streetAddress") {
                        $('#add-streetAddress-validation-error').append(error.message);
                    }

                    if(error.fieldName == "city") {
                        $('#add-city-validation-error').append(error.message);
                    }

                    if(error.fieldName == "state") {
                        $('#add-state-validation-error').append(error.message);
                    }

                    if(error.fieldName == "zipCode") {
                        $('#add-zipCode-validation-error').append(error.message);
                    }

                });
                
            }
        });

    });


    // Read an address
    $('#viewAddressModal').on('shown.bs.modal', function (e) {

        var link = $(e.relatedTarget);
        var addressId = link.data('address-id');
        
        $.ajax({
            url: contextRoot + "/address/" + addressId ,
            type: "GET" ,
            dataType: "json" ,
            beforeSend: function(xhr) {
                xhr.setRequestHeader("Accept", "application/json");
            },
            success: function(data, status) {
                $('#showFirstName').text(data.firstName);
                $('#showLastName').text(data.lastName);
                $('#showStreetAddress').text(data.streetAddress);
                $('#showCity').text(data.city);
                $('#showState').text(data.state);
                $('#showZipCode').text(data.zipCode);
            },
            error: function(data, status) {
                console.log("Error reading address");
            }
        });


    });
    
    
    // Edit an address
    $('#editAddressModal').on('shown.bs.modal', function (e) {
        
        var link = $(e.relatedTarget);
        var addressId = link.data('address-id');
        
        $.ajax({
            url: contextRoot + "/address/" + addressId ,
            type: "GET" ,
            dataType: "json" ,
            beforeSend: function(xhr) {
                xhr.setRequestHeader("Accept", "application/json");
            },
            success: function(data, status) {
                $('#editId').val(data.id);
                $('#editFirstName').val(data.firstName);
                $('#editLastName').val(data.lastName);
                $('#editStreetAddress').val(data.streetAddress);
                $('#editCity').val(data.city);
                $('#editState').val(data.state);
                $('#editZipCode').val(data.zipCode);
            },
            error: function(data, status) {
                alert("Address not found");
            }
        });
        
        
    });
    
    
    // Edit - functionality for Submit button
    $('#editAddressButton').on('click', function (e) {
        
        e.preventDefault();
        
        var address = {
          id: $('#editId').val() ,
          firstName: $('#editFirstName').val() ,
          lastName: $('#editLastName').val() ,
          streetAddress: $('#editStreetAddress').val() ,
          city: $('#editCity').val() ,
          state: $('#editState').val() ,
          zipCode: $('#editZipCode').val()
        };
        
        var addressData = JSON.stringify(address);
        
        $.ajax({
           url: contextRoot + "/address/" + address.id ,
           type: "PUT" ,
           data: addressData ,
           dataType: "json" ,
           beforeSend: function(xhr) {
               xhr.setRequestHeader("Accept", "application/json");
               xhr.setRequestHeader("Content-Type", "application/json");
           },
           success: function(data, status) {

               clearEditAddressErrorMessages();
               
               var tableRow = buildAddressRow(data);
               
               $('#address-row-' + data.id).replaceWith( $(tableRow) );

               $('#editAddressModal').modal('hide');
               
           },
           error: function(data, status) {

               clearEditAddressErrorMessages();

               var errors = data.responseJSON.errorList;

               $.each(errors, function(index, error) {

                   if(error.fieldName == "firstName") {
                       $('#edit-firstName-validation-error').append(error.message);
                   }

                   if(error.fieldName == "lastName") {
                       $('#edit-lastName-validation-error').append(error.message);
                   }

                   if(error.fieldName == "streetAddress") {
                       $('#edit-streetAddress-validation-error').append(error.message);
                   }

                   if(error.fieldName == "city") {
                       $('#edit-city-validation-error').append(error.message);
                   }

                   if(error.fieldName == "state") {
                       $('#edit-state-validation-error').append(error.message);
                   }

                   if(error.fieldName == "zipCode") {
                       $('#edit-zipCode-validation-error').append(error.message);
                   }
               })
           }
        });
        
    });
    
    
    // Delete Address
    $('#deleteAddressModal').on('shown.bs.modal', function(e) {
        
        var link = $(e.relatedTarget);
        var addressId = link.data('address-id');
        
        $.ajax({
           url: contextRoot + "/address/" + addressId ,
           type: "GET" ,
           dataType: "json" ,
           beforeSend: function(xhr) {
               xhr.setRequestHeader("Accept", "application/json");
           },
           success: function(data, status) {
               $('#deleteId').val(data.id);
               $('#deleteFirstName').text(data.firstName);
               $('#deleteLastName').text(data.lastName);
               $('#deleteStreetAddress').text(data.streetAddress);
               $('#deleteCity').text(data.city);
               $('#deleteState').text(data.state);
               $('#deleteZipCode').text(data.zipCode);
           },
           error: function(data, status) {
               console.log("Error reading address");
           }
        });
        
    });


    // Functionality - Delete Contact Button
    $('#deleteAddressButton').on('click', function(e) {
        
        e.preventDefault();
        
        var address = {
          id: $('#deleteId').val() ,
          firstName: $('#deleteFirstName').val() ,
          lastName: $('#deleteLastName').val() ,
          streetAddress: $('#deleteStreetAddress').val() ,
          city: $('#deleteCity').val() ,
          state: $('#deleteState').val() ,
          zipCode: $('#deleteZipCode').val()
        };
        
        var addressData = JSON.stringify(address);
        
        $.ajax({
           url: contextRoot + "/address/" + address.id ,
           type: "DELETE" ,
           data: addressData ,
           dataType: "json" ,
           beforeSend: function(xhr) {
               xhr.setRequestHeader("Accept", "application/json");
               xhr.setRequestHeader("Content-Type", "application/json");
           },
           success: function(data, status) {
               $('#address-row-' + data.id).remove();
           },
           error: function(data, status) {
               console.log("Address not found");
           }
        });
        
    });


    $('#editAddressModal').on('hidden.bs.modal', function() {

        clearEditAddressErrorMessages();

        $('#editFirstName').val('');
        $('#editLastName').val('');
        $('#editStreetAddress').val('');
        $('#editCity').val('');
        $('#editState').val('');
        $('#editZipCode').val('');

    });







    function buildAddressRow(data) {

        var tableRow = '\
                    <tr id="address-row-' + data.id + '"> \n\
                        <td><a data-address-id="' + data.id + '" data-toggle="modal" data-target="#viewAddressModal">' + data.firstName + ' ' + data.lastName + '</a></td> \n\
                        <td>' + data.streetAddress + '</td> \n\
                        <td>' + data.city + ', ' + data.state + '</td> \n\
                        <td><a data-address-id="' + data.id + '" data-toggle="modal" data-target="#editAddressModal">Edit</a></td> \n\
                        <td><a data-address-id="' + data.id + '" data-toggle="modal" data-target="#deleteAddressModal">Delete</a></td> \n\
                    </tr>';

        return tableRow;

    }

    function clearCreateAddressErrorMessages() {

        $('#add-firstName-validation-error').text('');
        $('#add-lastName-validation-error').text('');
        $('#add-streetAddress-validation-error').text('');
        $('#add-city-validation-error').text('');
        $('#add-state-validation-error').text('');
        $('#add-zipCode-validation-error').text('');

    }

    function clearEditAddressErrorMessages() {

        $('#edit-firstName-validation-error').text('');
        $('#edit-lastName-validation-error').text('');
        $('#edit-streetAddress-validation-error').text('');
        $('#edit-city-validation-error').text('');
        $('#edit-state-validation-error').text('');
        $('#edit-zipCode-validation-error').text('');

    }


});
