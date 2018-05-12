<!DOCTYPE html>

<html ng-app="dvdLibrary">

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/angular/css/index.css" rel="stylesheet" />
    <title>DVD Library | Home</title>
</head>

<body ng-controller="DvdController">

<div class="container">

    <div class="space-50"></div>

    <div class="row">

        <div class="col-sm-6 ">

            <h2>DVDs</h2>

            <table id="DVDTable" class="table table-hover list-group">

                <tr class="list-group-item">
                    <th ng-click="sort('title')" style="cursor: pointer;">Title</th>
                    <th ng-click="sort('releaseDate')" style="cursor: pointer;">Release Year</th>
                    <th ng-click="sort('mpaaRating')" style="cursor: pointer;">Rating</th>
                </tr>

                <tr ng-repeat="dvd in dvds | orderBy : sortBy : reverse:reverse">

                    <td>{{ dvd.title }}</td>
                    <td>{{ dvd.releaseDate }}</td>
                    <td>{{ dvd.mpaaRating }}</td>
                    <td>Edit</td>
                    <td>Delete</td>
                </tr>

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
                            <input class="addDVDFormElements required" id="add-title" type="text" ng-model="addDvd.title" /> <br/>
                            <div class="errorMessages" id="add-title-validation-error"></div>
                        </div>
                        <div class="col-md-4 col-md-offset-1">
                            <label for="add-releaseDate">Release Date</label> <br />
                            <input class="addDVDFormElements required" id="add-releaseDate" type="date" placeholder="MM/DD/YYYY" ng-model="addDvd.releaseDate" /> <br/>
                            <div class="errorMessages" id="add-releaseDate-validation-error"></div>
                        </div>
                    </div>

                    <div class="space-20"></div>

                    <div class="row">
                        <div class="col-md-4 col-md-offset-1">
                            <label for="add-director">Director</label> <br />
                            <input class="addDVDFormElements required" id="add-director" type="text" ng-model="addDvd.director" /> <br/>
                            <div class="errorMessages" id="add-director-validation-error"></div>
                        </div>
                        <div class="col-md-4 col-md-offset-1">
                            <label for="add-studio">Studio</label> <br />
                            <input class="addDVDFormElements required" id="add-studio" type="text" ng-model="addDvd.studio" /> <br/>
                            <div class="errorMessages" id="add-studio-validation-error"></div>
                        </div>
                    </div>

                    <div class="space-20"></div>

                    <div class="row">
                        <div class="col-md-4 col-md-offset-1">
                            <label for="add-mpaaRating">MPAA Rating</label> <br />
                            <select class="addDVDFormElements required" id="add-mpaaRating" ng-model="addDvd.mpaaRating">
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
                            <select class="addDVDFormElements required" id="add-userRating" ng-model="addDvd.userRating">
                                <option value="">-Your Rating-</option>
                                <option ng-repeat="option in data.userRatingOptions" value="{{ option }}">{{ option }}</option>
                            </select> <br/>
                            <div class="errorMessages" id="add-userRating-validation-error"></div>
                        </div>
                    </div>

                    <div class="space-20"></div>

                    <div class="row">
                        <div class="col-md-4 col-md-offset-1">
                            <label for="noteTextCreate">Note</label> <br />
                            <input id="noteTextCreate" type="text" ng-model="addDvd.notes[0]" />
                        </div>
                    </div>

                    <div class="space-20"></div>

                    <div class="row">
                        <div class="col-md-10 col-md-offset-1">
                            <button id="addDVDButton" class="btn btn-primary btn-block" onclick="console.log('thing', addDvd)">Add DVD</button>
                        </div>
                    </div>

                    <div class="space-20"></div>

                </div>

            </form>

        </div>

    </div>

</div>

<script src="${pageContext.request.contextPath}/angular/js/angular.js"></script>
<script src="${pageContext.request.contextPath}/angular/js/ui-bootstrap-tpls-2.5.0.min.js"></script>
<script src="${pageContext.request.contextPath}/angular/app/app.js"></script>
<script src="${pageContext.request.contextPath}/angular/app/controller/dvd-controller.js"></script>

</body>

</html>