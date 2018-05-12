<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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

                                            <option value="G">G</option>
                                            <option value="PG">PG</option>
                                            <option value="PG-13">PG-13</option>
                                            <option value="R">R</option>
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
                                                <option value="${i}">${i}</option>
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