<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css" />
        <title>DVD Library | Search</title>
    </head>

    <body>

        <div class="container">

            <jsp:include page="navbar.jsp"/>
            <div class="space-50"></div>

            <div class="row">

                <div class="col-sm-6">

                    <h2>Search Results</h2>

                    <table class="table" id="searchResultsTable">

                        <tr>
                            <th>Title</th>
                            <th>Release Year</th>
                            <th>Rating</th>
                        </tr>

                    </table>

                </div>

                <div class="col-sm-4 col-sm-offset-2">

                    <form action="" method="POST">

                        <h2>Search</h2>

                        <table class="table">
                            <tr>
                                <td><input type="text" id="valueToSearchFor" /></td>
                                <td>
                                    <select type="select" id="categoryToSearchBy">
                                        <option>-Criteria-</option>
                                        <option value="title">Title</option>
                                        <option value="studio">Studio</option>
                                        <option value="director">Director</option>
                                        <option value="mpaaRating">MPAA Rating</option>
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

        <script src="${pageContext.request.contextPath}/js/jquery-3.1.0.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/dvdSearch.js"></script>

    </body>

</html>
