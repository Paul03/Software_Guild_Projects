<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

    <head>

        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

        <title>Delete Note</title>

    </head>

    <body>

        <div class="container">

            <jsp:include page="navbar.jsp" />

            <div class="row">
                
                <div class="col-sm-6 col-sm-offset-4">
                    
                    <h2>Delete Note for "${note.dvd.title}"</h2>
                </div>
            </div>

            <br />

            <div class="row">

                <div class="col-sm-4 col-sm-offset-4">

                    <form action="${pageContext.request.contextPath}/Note/delete" method="POST">

                        <input type="hidden" name="noteId" value="${note.noteId}" >
                        <input type="hidden" name="dvdId" value="${note.dvd.dvdId}" >

                        <table class="table table-bordered">

                            <tr>
                                <td>Note</td>
                                <td>${note.noteText}</td>
                            </tr>


                            <tr>
                                <td></td>
                                <td><input type="submit" value="Delete Note" ></td>
                            </tr>

                        </table>

                    </form>

                </div>

            </div>

            <div class="row">
                <div class="col-sm-4 col-sm-offset-5">
                    <a href="${pageContext.request.contextPath}/DVD/edit/${note.dvd.dvdId}" >Back to Edit ${note.dvd.title}</a>
                </div>
            </div>

        </div>

    </body>

</html>
