app.controller("DvdController", function ($scope) {

    $scope.sortBy = "title";
    $scope.reverse = false;

    $scope.dvds = [
        { dvdId : 1, title : 'Toy Story', releaseDate : '1995-11-22', mpaaRating : 'G' },
        { dvdId : 2, title : 'Toy Story 2', releaseDate : '1999-11-13', mpaaRating : 'G' },
        { dvdId : 3, title : 'Toy Story 3', releaseDate : '2010-06-28', mpaaRating : 'G' },
        { dvdId : 4, title : 'God\'s Not Dead', releaseDate : '2014-03-21', mpaaRating : 'PG' },
        { dvdId : 4, title : 'God\'s Not Dead 2', releaseDate : '2016-04-01', mpaaRating : 'PG' },
    ];

    $scope.addDvd = { title : '', releaseDate : '', mpaaRating : '' };

    $scope.userRatingOptions = [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 ];

    $scope.sort = function (propertyName) {

        if (propertyName === $scope.sortBy) {
            $scope.reverse = !$scope.reverse;
        } else {
            $scope.reverse = false;
            $scope.sortBy = propertyName;
        }

    }

});