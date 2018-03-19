<%-- 
    Document   : create
    Created on : Sep 15, 2016, 11:26:12 AM
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

        
        <title>New Contact</title>

    </head>

    <body>

        <div class="container">

            <jsp:include page="header.jsp" />
            <jsp:include page="navbar.jsp" />

            <h2>New Contact</h2>

            <div class="row">

                <div class="col-sm-4">

                    <sf:form action="${pageContext.request.contextPath}/address/create" commandName="address" method="POST">

                        <table class="table">

                            <tr>
                                <td>First Name</td>
                                <td><sf:input type="text" path="firstName" /> <sf:errors path="firstName" /></td>
                            </tr>

                            <tr>
                                <td>Last Name</td>
                                <td><sf:input type="text" path="lastName" /> <sf:errors path="lastName" /></td>
                            </tr>

                            <tr>
                                <td>Street Address</td>
                                <td><sf:input type="text" path="streetAddress" /> <sf:errors path="streetAddress" /></td>
                            </tr>

                            <tr>
                                <td>City</td>
                                <td><sf:input type="text" path="city" /> <sf:errors path="city" /></td>
                            </tr>

                            <tr>
                                <td>State</td>
                                <td><sf:input type="text" path="state" /> <sf:errors path="state" /></td>
                            </tr>

                            <tr>
                                <td>Zip Code</td>
                                <td><sf:input type="text" path="zipCode" /> <sf:errors path="zipCode" /></td>
                            </tr>

                            <tr>
                                <td></td>
                                <td><input type="submit" value="Create Contact" /></td>
                            </tr>

                        </table>

                    </sf:form>

                </div>

            </div>

        </div>

    </body>

</html>
