<%-- 
    Document   : delete
    Created on : Sep 15, 2016, 12:53:59 PM
    Author     : paulharding
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <head>

        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">

        <title>Delete Contact</title>

    </head>

    <body>

        <div class="container">

            <jsp:include page="header.jsp" />
            <jsp:include page="navbar.jsp" />

            <h2>Delete Contact</h2>

            <div class="row">

                <div class="col-sm-4">

                    <form action="${pageContext.request.contextPath}/address/delete" method="POST">

                        <input type="hidden" name="id" value="${address.id}" />

                        <table class="table">

                            <tr>
                                <td>First Name</td>
                                <td>${address.firstName}</td>
                            </tr>

                            <tr>
                                <td>Last Name</td>
                                <td>${address.lastName}</td>
                            </tr>

                            <tr>
                                <td>Street Address</td>
                                <td>${address.streetAddress}</td>
                            </tr>

                            <tr>
                                <td>City</td>
                                <td>${address.city}</td>
                            </tr>

                            <tr>
                                <td>State</td>
                                <td>${address.state}</td>
                            </tr>

                            <tr>
                                <td>Zip Code</td>
                                <td>${address.zipCode}</td>
                            </tr>

                            <tr>
                                <td></td>
                                <td><input type="submit" value="Delete Contact" /></td>
                            </tr>

                        </table>

                    </form>

                </div>

            </div>

        </div>

    </body>

</html>
