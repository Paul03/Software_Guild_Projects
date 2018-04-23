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