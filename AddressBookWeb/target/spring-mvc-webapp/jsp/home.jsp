<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet"/>

        <title>Address Book Home</title>

        <style>

            .errorMessages {
                color: red;
            }

            #myModalLabelDeleteAddress {
                text-align: center;
            }

        </style>

    </head>

    <body>

        <div class="container">

            <jsp:include page="header.jsp" />
            <jsp:include page="navbar.jsp" />

            <div class="row">

                <div class="col-sm-6">

                    <h2>My Addresses</h2>

                    <table id="addressTable" class="table">

                        <tr>
                            <th>Name</th>
                            <th>Address</th>
                            <th>City</th>
                        </tr>

                        <c:forEach items="${addressBookList}" var="address" >

                            <tr id="address-row-${address.id}">
                                <td><a data-address-id="${address.id}" data-toggle="modal" data-target="#viewAddressModal" data-backdrop="false">${address.firstName} ${address.lastName}</a></td>
                                <td>${address.streetAddress}</td>
                                <td>${address.city}, ${address.state}</td>
                                <td><a data-address-id="${address.id}" data-toggle="modal" data-target="#editAddressModal" data-backdrop="false">Edit</a></td>
                                <td><a data-address-id="${address.id}" data-toggle="modal" data-target="#deleteAddressModal" data-backdrop="false">Delete</a></td>
                            </tr>

                        </c:forEach>

                    </table>

                </div>

                <div class="col-sm-5 col-sm-offset-1">

                    <h2>Add New Address</h2>

                    <sf:form action="${pageContext.request.contextPath}/address/create" commandName="address" method="POST">

                        <table class="table">

                            <tr>
                                <td>First Name</td>
                                <td>
                                    <input id="firstNameCreate" placeholder="First Name" type="text" name="firstName" /> <br />
                                    <div class="errorMessages" id="add-firstName-validation-error" ></div>
                                </td>
                            </tr>

                            <tr>
                                <td>Last Name</td>
                                <td>
                                    <input id="lastNameCreate" placeholder="Last Name" type="text" name="lastName" /> <br />
                                    <div class="errorMessages" id="add-lastName-validation-error" ></div>
                                </td>
                            </tr>

                            <tr>
                                <td>Street Address</td>
                                <td>
                                    <input id="streetAddressCreate" placeholder="Street Address" type="text" path="streetAddress" /> <br />
                                    <div class="errorMessages" id="add-streetAddress-validation-error" ></div>
                                </td>
                            </tr>

                            <tr>
                                <td>City</td>
                                <td>
                                    <input id="cityCreate" placeholder="City" type="text" path="city" /> <br />
                                    <div class="errorMessages" id="add-city-validation-error" ></div>
                                </td>
                            </tr>

                            <tr>
                                <td>State</td>
                                <td>
                                    <input id="stateCreate" placeholder="State" type="text" path="state" /> <br />
                                    <div class="errorMessages" id="add-state-validation-error" ></div>
                                </td>
                            </tr>

                            <tr>
                                <td>Zip Code</td>
                                <td>
                                    <input id="zipCodeCreate" placeholder="Zip Code" type="text" path="zipCode" /> <br />
                                    <div class="errorMessages" id="add-zipCode-validation-error" ></div>
                                </td>
                            </tr>

                            <tr>
                                <td></td>
                                <td>
                                    <input id="addAddressButton" type="submit" value="Create Contact" />
                                </td>
                            </tr>

                        </table>

                    </sf:form>

                </div>

            </div>

        </div>

        <!-- View Address Modal -->
        <div class="modal fade" id="viewAddressModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabelViewAddress">View Addresss</h4>
                    </div>
                    <div class="modal-body">

                        <table class="table table-bordered">

                            <tr>
                                <td>First Name:</td>
                                <td id="showFirstName"></td>
                            </tr>

                            <tr>
                                <td>Last Name:</td>
                                <td id="showLastName"></td>
                            </tr>

                            <tr>
                                <td>Street Address:</td>
                                <td id="showStreetAddress"></td>
                            </tr>

                            <tr>
                                <td>City:</td>
                                <td id="showCity"></td>
                            </tr>

                            <tr>
                                <td>State:</td>
                                <td id="showState"></td>
                            </tr>

                            <tr>
                                <td>Zip Code:</td>
                                <td id="showZipCode"></td>
                            </tr>

                        </table>




                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary">Save changes</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Edit Address Modal -->
        <div class="modal" id="editAddressModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabelEditAddress">Edit Addresss</h4>
                    </div>
                    <div class="modal-body">

                        <table class="table table-bordered">

                            <input type="hidden" id="editId" />

                            <tr>
                                <td>First Name:</td>
                                <td>
                                    <input type="text" id="editFirstName" /> <br />
                                    <div class="errorMessages" id="edit-firstName-validation-error" ></div>
                                </td>
                            </tr>

                            <tr>
                                <td>Last Name:</td>
                                <td>
                                    <input type="text" id="editLastName" /> <br />
                                    <div class="errorMessages" id="edit-lastName-validation-error" ></div>
                                </td>
                            </tr>

                            <tr>
                                <td>Street Address:</td>
                                <td>
                                    <input type="text" id="editStreetAddress" /> <br />
                                    <div class="errorMessages" id="edit-streetAddress-validation-error" ></div>
                                </td>
                            </tr>

                            <tr>
                                <td>City:</td>
                                <td>
                                    <input type="text" id="editCity" /> <br />
                                    <div class="errorMessages" id="edit-city-validation-error" ></div>
                                </td>
                            </tr>

                            <tr>
                                <td>State:</td>
                                <td>
                                    <input type="text" id="editState" /> <br />
                                    <div class="errorMessages" id="edit-state-validation-error" ></div>
                                </td>
                            </tr>

                            <tr>
                                <td>Zip Code:</td>
                                <td>
                                    <input type="text" id="editZipCode" /> <br />
                                    <div class="errorMessages" id="edit-zipCode-validation-error" ></div>
                                </td>
                            </tr>

                        </table>




                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary" id="editAddressButton">Save changes</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Delete Address Modal -->
        <div class="modal fade" id="deleteAddressModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabelDeleteAddress">Really Delete?</h4>
                    </div>
                    <div class="modal-body">

                        <table class="table table-bordered">

                            <input type="hidden" id="deleteId" />

                            <tr>
                                <td>First Name:</td>
                                <td id="deleteFirstName"></td>
                            </tr>

                            <tr>
                                <td>Last Name:</td>
                                <td id="deleteLastName"></td>
                            </tr>

                            <tr>
                                <td>Street Address:</td>
                                <td id="deleteStreetAddress"></td>
                            </tr>

                            <tr>
                                <td>City:</td>
                                <td id="deleteCity"></td>
                            </tr>

                            <tr>
                                <td>State:</td>
                                <td id="deleteState"></td>
                            </tr>

                            <tr>
                                <td>Zip Code:</td>
                                <td id="deleteZipCode"></td>
                            </tr>

                        </table>




                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary" id="deleteAddressButton" data-dismiss="modal">Confirm</button>
                    </div>
                </div>
            </div>
        </div>

        <script>
            var contextRoot = "${pageContext.request.contextPath}";
        </script>

        <script src="${pageContext.request.contextPath}/js/jquery-3.1.0.min.js" ></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/app.js" ></script>

    </body>

</html>
