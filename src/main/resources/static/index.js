angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/store';

    $scope.init = function () {
        $http.get(contextPath + '/api/v1/products')
            .then(function (response) {
                $scope.products = response.data;
            });
    };

    $scope.init();
});