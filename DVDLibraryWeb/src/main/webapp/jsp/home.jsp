<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/home.css" rel="stylesheet" />
    <title>DVD Library | Home</title>
</head>

    <body>

        <div class="container">

            <jsp:include page="navbar.jsp"/>
            <div class="space-50"></div>

            <div class="row">

                <div class="col-sm-6 ">

                    <h2>DVDs</h2>

                    <table id="DVDTable" class="table table-hover">

                        <tr>
                            <th>Title</th>
                            <th>Release Year</th>
                            <th>Rating</th>
                        </tr>

                        <c:forEach items="${dvdList}" var="dvd">
                            <tr id="dvd-row-${dvd.dvdId}">
                                <td><a class="glyphicon glyphicon-expand" data-dvd-id="${dvd.dvdId}" data-toggle="modal" data-target="#showDVDModal" data-backdrop="false"></a> ${dvd.title}</td>
                                <td>${dvd.releaseDate.year + 1900}</td>
                                <td>${dvd.mpaaRating}</td>
                                <td><a class="glyphicon glyphicon-edit" data-dvd-id="${dvd.dvdId}" data-toggle="modal" data-target="#editDVDModal" data-backdrop="false" style="color:green;"></a> Edit</td>
                                <td><a class="glyphicon glyphicon-remove-circle" data-dvd-id="${dvd.dvdId}" data-toggle="modal" data-target="#deleteDVDModal" data-backdrop="false" style="color:red;"></a> Delete
                                </td>
                            </tr>
                        </c:forEach>

                    </table>

                </div>

                <div class="col-sm-5 col-sm-offset-1">

                    <h2>Add new DVD</h2>

                    <form id="addDVDForm">

                        <div class="col-md-12" id="add-dvd-div">

                            <div class="space-20"></div>

                            <div class="row">
                                <div class="col-md-4 col-md-offset-1">
                                    <label for="add-title">Title</label> <br />
                                    <input class="addDVDFormElements required" id="add-title" type="text" /> <br/>
                                    <div class="errorMessages" id="add-title-validation-error"></div>
                                </div>
                                <div class="col-md-4 col-md-offset-1">
                                    <label for="add-releaseDate">Release Date</label> <br />
                                    <input class="addDVDFormElements required" id="add-releaseDate" type="date" placeholder="MM/DD/YYYY" /> <br/>
                                    <div class="errorMessages" id="add-releaseDate-validation-error"></div>
                                </div>
                            </div>

                            <div class="space-20"></div>

                            <div class="row">
                                <div class="col-md-4 col-md-offset-1">
                                    <label for="add-director">Director</label> <br />
                                    <input class="addDVDFormElements required" id="add-director" type="text" /> <br/>
                                    <div class="errorMessages" id="add-director-validation-error"></div>
                                </div>
                                <div class="col-md-4 col-md-offset-1">
                                    <label for="add-studio">Studio</label> <br />
                                    <input class="addDVDFormElements required" id="add-studio" type="text" /> <br/>
                                    <div class="errorMessages" id="add-studio-validation-error"></div>
                                </div>
                            </div>

                            <div class="space-20"></div>

                            <div class="row">
                                <div class="col-md-4 col-md-offset-1">
                                    <label for="add-mpaaRating">MPAA Rating</label> <br />
                                    <select class="addDVDFormElements required" id="add-mpaaRating">
                                        <option value="">-MPAA Rating-</option>
                                        <option value="G">G</option>
                                        <option value="PG">PG</option>
                                        <option value="PG-13">PG-13</option>
                                        <option value="R">R</option>
                                    </select> <br/>
                                    <div class="errorMessages" id="add-mpaaRating-validation-error"></div>
                                </div>
                                <div class="col-md-4 col-md-offset-1">
                                    <label for="add-userRating">Your Rating</label> <br />
                                    <select class="addDVDFormElements required" id="add-userRating">
                                        <option value="">-Your Rating-</option>
                                        <c:forEach var="i" begin="1" end="10">
                                            <option value="${i}">${i}</option>
                                        </c:forEach>
                                    </select> <br/>
                                    <div class="errorMessages" id="add-userRating-validation-error"></div>
                                </div>
                            </div>

                            <div class="space-20"></div>

                            <div class="row">
                                <div class="col-md-4 col-md-offset-1">
                                    <label for="noteTextCreate">Note</label> <br />
                                    <input id="noteTextCreate" type="text" />
                                </div>
                            </div>

                            <div class="space-20"></div>

                            <div class="row">
                                <div class="col-md-10 col-md-offset-1">
                                    <button id="addDVDButton" class="btn btn-primary btn-block">Add DVD</button>
                                </div>
                            </div>

                            <div class="space-20"></div>

                        </div>

                    </form>

                </div>

            </div>

        </div>

        <jsp:include page="showDvdModal.jsp" />
        <jsp:include page="editDvdModal.jsp" />
        <jsp:include page="deleteDvdModal.jsp" />
        <jsp:include page="createNoteModal.jsp" />
        <jsp:include page="editNoteModal.jsp" />
        <jsp:include page="deleteNoteModal.jsp" />

        <script>
            var contextRoot = "${pageContext.request.contextPath}";
        </script>

        <script src="${pageContext.request.contextPath}/js/jquery-3.1.0.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/app.js"></script>
        <script src="${pageContext.request.contextPath}/js/dvd.js"></script>
        <script src="${pageContext.request.contextPath}/js/note.js"></script>

    </body>

</html>