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