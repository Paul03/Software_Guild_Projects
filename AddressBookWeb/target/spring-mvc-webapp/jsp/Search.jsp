<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

    <head>

        <title>Search</title>

    </head>

    <body>

        <div class="container">

            <header>
                <jsp:include page="header.jsp" />
                <jsp:include page="navbar.jsp" />
            </header>

            <div class="row">

                <div class="col-sm-6">

                    <h2>Search Results</h2>

                    <table id="searchResultsTable" class="table">

                        <tr>
                            <th>Name</th>
                            <th>Address</th>
                            <th>City</th>
                        </tr>

                    </table>

                </div>

                <div class="col-sm-4 col-sm-offset-2">

                    <h2>Search</h2>

                    <form action="" method="POST">

                        <table class="table">
                            <tr>
                                <td><input type="text" id="searchValue" /></td>
                                <td>
                                    <select type="select" id="searchCriteria">
                                        <option>-Criteria-</option>
                                        <option value="lastName">Last Name</option>
                                        <option value="city">City</option>
                                        <option value="state">State</option>
                                        <option value="zipCode">Zip Code</option>
                                    </select>
                                </td>
                            </tr>

                            <tr>
                                <td><input type="submit" value="Search" id="searchButton" /></td>
                            </tr>

                        </table>

                    </form>

                </div>

            </div>

        </div>

        <script>
            var contextRoot = "${pageContext.request.contextPath}";
        </script>

        <script src="${pageContext.request.contextPath}/js/jquery-3.1.0.min.js" ></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/search.js"></script>

    </body>

</html>
