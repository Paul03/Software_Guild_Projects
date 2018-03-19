<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
    Document   : navbar
    Created on : Sep 15, 2016, 11:09:46 AM
    Author     : paulharding
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <head>

        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">

        <title>Address Book Navigation Bar</title>

        <style>

            ul {
                list-style: none;
                margin: 0;
                padding: 0;
            }

        </style>

    </head>

    <body>

        <div class="container">

            <ul class="nav-pills">

                <li>
                    <a href="${pageContext.request.contextPath}" >
                        <button type="button btn-default btn-large"><span class="glyphicon glyphicon-home" /></button>
                    </a>
                </li>

                <li>
                    <a href="${pageContext.request.contextPath}/address/search">Search</a>
                </li>

            </ul>

        </div>

        <br />
        <br />

        <script src="${pageContext.request.contextPath}/js/jquery-3.1.0.min.js" ></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>

</html>
