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

                            <form>

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

                            </form>

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