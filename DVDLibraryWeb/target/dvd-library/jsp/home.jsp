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

        <!-- Show Dvd Modal -->
        <div class="modal fade" id="showDVDModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                aria-hidden="true">&times;</span></button>
                        <h2 class="modal-title" id="showDVDModalHeader"></h2>
                    </div>
                    <div class="modal-body">

                        <div class="container">

                            <div class="row">

                                <div class="col-sm-3">

                                    <table class="table table-bordered">

                                        <tr>
                                            <td class="tableLeftColumn">Title:</td>
                                            <td id="showTitle"></td>
                                        </tr>

                                        <tr>
                                            <td class="tableLeftColumn">Release Date:</td>
                                            <td id="showReleaseDate"></td>
                                        </tr>

                                        <tr>
                                            <td class="tableLeftColumn">MPAA Rating:</td>
                                            <td id="showMpaaRating"></td>
                                        </tr>

                                        <tr>
                                            <td class="tableLeftColumn">Director</td>
                                            <td id="showDirector"></td>
                                        </tr>

                                        <tr>
                                            <td class="tableLeftColumn">Studio</td>
                                            <td id="showStudio"></td>
                                        </tr>

                                        <tr>
                                            <td class="tableLeftColumn">Your Rating:</td>
                                            <td id="showUserRating"></td>
                                        </tr>

                                    </table>

                                </div>

                                <div class="col-sm-3 col-sm-offset-0">

                                    <table id="showNoteTable" class="table table-bordered">

                                        <tr>
                                            <th id="showNotesTableHeader">Notes</th>
                                        </tr>

                                    </table>

                                </div>

                            </div>

                        </div>


                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Edit Dvd Modal -->
        <div class="modal" id="editDVDModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                aria-hidden="true">&times;</span></button>
                        <h2 id="editDVDModalHeader" class="modal-title"></h2>
                    </div>
                    <div class="modal-body">

                        <div class="container">

                            <div class="row">

                                <div class="col-sm-3">

                                    <table class="table table-bordered">

                                        <tr>
                                            <td>
                                                <input type="hidden" id="editId"/>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td>
                                                <label for="editTitle">Title</label>
                                            </td>
                                            <td>
                                                <input type="text" id="editTitle"/> <br/>
                                                <div class="errorMessages" id="edit-title-validation-error"></div>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td>
                                                <label for="editReleaseDate">Release Date</label>
                                            </td>
                                            <td>
                                                <input type="date" id="editReleaseDate"/><br/>
                                                <div class="errorMessages" id="edit-releaseDate-validation-error"></div>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td>
                                                <label for="editMpaaRating">MPAA Rating</label>
                                            </td>
                                            <td>
                                                <select id="editMpaaRating">
                                                    <option value="">-MPAA Rating-</option>

                                                    <option ${dvd.mpaaRating=="G" ? "selected" : ""} value="G">G</option>
                                                    <option ${dvd.mpaaRating=="PG" ? "selected" : ""} value="PG">PG</option>
                                                    <option ${dvd.mpaaRating=="PG-13" ? "selected" : ""} value="PG-13">PG-13
                                                    </option>
                                                    <option ${dvd.mpaaRating=="R" ? "selected" : ""} value="R">R</option>
                                                </select>

                                                <br/>

                                                <div class="errorMessages" id="edit-mpaaRating-validation-error"></div>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td>
                                                <label for="editDirector">Director</label>
                                            </td>
                                            <td>
                                                <input type="text" id="editDirector"/> <br/>
                                                <div class="errorMessages" id="edit-director-validation-error"></div>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td>
                                                <label for="editStudio">Studio</label>
                                            </td>
                                            <td>
                                                <input type="text" id="editStudio"/> <br/>
                                                <div class="errorMessages" id="edit-studio-validation-error"></div>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td>
                                                <label for="editUserRating">Your Rating</label>
                                            </td>
                                            <td>
                                                <select id="editUserRating">

                                                    <option value="">-Your Rating-</option>

                                                    <c:forEach var="i" begin="1" end="10">
                                                        <option ${dvd.userRating==i ? "selected" : ""}
                                                                value="${i}">${i}</option>
                                                    </c:forEach>

                                                </select>

                                                <br/>

                                                <div class="errorMessages" id="edit-userRating-validation-error"></div>
                                            </td>
                                        </tr>

                                    </table>

                                </div>

                                <div class="col-sm-3 col-sm-offset-0">

                                    <table id="editNoteTable" class="table table-bordered">

                                        <tr>
                                            <th id="editNotesTableHeader">Notes</th>
                                            <td colspan="2"><a data-toggle="modal" data-target="#createNoteModal"
                                                               data-backdrop="false">Add Note</a>
                                            </td>
                                        </tr>

                                    </table>

                                </div>

                            </div>

                        </div>


                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary" id="editDVDButton">Save changes</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Delete Dvd Modal -->
        <div class="modal fade" id="deleteDVDModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="deleteDVDModalHeader">Really Delete?</h4>
                    </div>
                    <div class="modal-body">

                        <div class="container">

                            <div class="row">

                                <div class="col-sm-3">

                                    <table class="table table-bordered">

                                        <tr>
                                            <td>
                                                <input type="hidden" id="deleteId"/>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td>Title:</td>
                                            <td id="deleteTitle"></td>
                                        </tr>

                                        <tr>
                                            <td>Release Date:</td>
                                            <td id="deleteReleaseDate"></td>
                                        </tr>

                                        <tr>
                                            <td>MPAA Rating:</td>
                                            <td id="deleteMpaaRating"></td>
                                        </tr>

                                        <tr>
                                            <td>Director</td>
                                            <td id="deleteDirector"></td>
                                        </tr>

                                        <tr>
                                            <td>Studio</td>
                                            <td id="deleteStudio"></td>
                                        </tr>

                                        <tr>
                                            <td>Your Rating:</td>
                                            <td id="deleteUserRating"></td>
                                        </tr>

                                        <tr>
                                            <td>
                                                <input type="hidden" id="deleteUserNotes"/>
                                            </td>
                                        </tr>

                                    </table>

                                </div>

                                <div class="col-sm-3 col-sm-offset-0">

                                    <table id="deleteNoteTable" class="table table-bordered">

                                        <tr>
                                            <th id="deleteNotesTableHeader">Notes</th>
                                        </tr>

                                    </table>

                                </div>

                            </div>

                        </div>


                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary" id="deleteDVDButton" data-dismiss="modal">Confirm</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Create Note Modal -->
        <div class="modal" id="createNoteModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="createNoteModalHeader">New Note</h4>
                    </div>
                    <div class="modal-body">

                        <div class="container">

                            <div class="row">

                                <div class="col-sm-2 col-sm-offset-2">

                                    <table class="table">

                                        <tr>
                                            <td style="text-align: center"><h4>Text</h4></td>
                                        </tr>

                                        <tr>
                                            <td style="text-align: center">
                                                <input type="text" id="createNoteText"/> <br/>
                                                <div class="errorMessages" id="add-noteText-validation-error"></div>
                                            </td>
                                        </tr>

                                    </table>

                                </div>

                            </div>

                        </div>


                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary" id="createNoteButton">Create Note</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Edit Note Modal -->
        <div class="modal" id="editNoteModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="editNoteModalHeader">Edit Existing Note</h4>
                    </div>
                    <div class="modal-body">


                        <table>

                            <tr>
                                <td>
                                    <input type="hidden" id="editNoteId"/>
                                </td>
                                <td>
                                    <input type="hidden" id="editNoteDvdId"/>
                                </td>
                            </tr>

                            <tr>
                                <td>
                                    <label for="editNoteText">Note Text</label>
                                </td>
                                <td>
                                    <input type="text" id="editNoteText"/> <br/>
                                    <div class="errorMessages" id="edit-noteText-validation-error"></div>
                                </td>
                            </tr>

                        </table>


                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary" id="editNoteButton">Submit Changes
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Delete Note Modal -->
        <div class="modal" id="deleteNoteModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="deleteNoteModalHeader">Really Delete?</h4>
                    </div>
                    <div class="modal-body">

                        <div class="container">

                            <div class="row">

                                <div class="col-sm-2 col-sm-offset-2">

                                    <table class="table">

                                        <tr>
                                            <td>
                                                <input type="hidden" id="deleteNoteId"/>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td>Note Text:</td>
                                            <td id="deleteNoteText"></td>
                                        </tr>

                                    </table>

                                </div>

                            </div>

                        </div>


                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary" id="deleteNoteButton" data-dismiss="modal">Confirm
                        </button>
                    </div>
                </div>
            </div>
        </div>


        <script>
            var contextRoot = "${pageContext.request.contextPath}";
        </script>

        <script src="${pageContext.request.contextPath}/js/jquery-3.1.0.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/app.js"></script>

    </body>

</html>
