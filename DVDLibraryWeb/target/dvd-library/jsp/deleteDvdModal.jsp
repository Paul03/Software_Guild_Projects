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