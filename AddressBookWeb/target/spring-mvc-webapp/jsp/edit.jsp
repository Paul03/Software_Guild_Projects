<%-- 
    Document   : edit
    Created on : Sep 15, 2016, 11:42:48 AM
    Author     : paulharding
--%>

<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <head>

        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">

        <title>Edit Contact</title>

    </head>

    <body>

        <div class="container">

            <jsp:include page="header.jsp" />
            <jsp:include page="navbar.jsp" />

            <h2>Edit Contact</h2>

            <div class="row">

                <div class="col-sm-4">

                    <sf:form action="${pageContext.request.contextPath}/address/edit" commandName="address" method="POST">

                        <input type="hidden" name="id" value="${address.id}" />

                        <table class="table">

                            <tr>
                                <td>First Name</td>
                                <td><sf:input type="text" path="firstName" value="${address.firstName}" /> <sf:errors path="firstName" /></td>
                            </tr>

                            <tr>
                                <td>Last Name</td>
                                <td><sf:input type="text" path="lastName" value="${address.lastName}" /> <sf:errors path="lastName" /></td>
                            </tr>

                            <tr>
                                <td>Street Address</td>
                                <td><sf:input type="text" path="streetAddress" value="${address.streetAddress}" /> <sf:errors path="streetAddress" /></td>
                            </tr>

                            <tr>
                                <td>City</td>
                                <td><sf:input type="text" path="city" value="${address.city}" /> <sf:errors path="city" /></td>
                            </tr>

                            <tr>
                                <td>State</td>
                                <td><sf:input type="text" path="state" value="${address.state}" /> <sf:errors path="state" /></td>
                            </tr>

                            <tr>
                                <td>Zip Code</td>
                                <td><sf:input type="text" path="zipCode" value="${address.zipCode}" /> <sf:errors path="zipCode" /></td>
                            </tr>

                            <tr>
                                <td></td>
                                <td><input type="submit" value="Update Contact Information" /></td>
                            </tr>

                        </table>

                    </sf:form>

                </div>

            </div>

        </div>

    </body>

</html>
