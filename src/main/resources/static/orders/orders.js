angular.module('app').controller('ordersController', function ($scope, $http, $localStorage, $location) {
    const contextPath = 'http://localhost:8189/store';

    $scope.isUserLoggedIn = function () {
        if ($localStorage.onlineStoreCurrentUser) {
            return true;
        } else {
            return false;
        }
    };

    $scope.showMyOrders = function () {
        $http({
            url: contextPath + '/api/v1/orders',
            method: 'GET',
        }).then(function (response) {
            $scope.myOrders = response.data;
        });
    };

    if(!$scope.isUserLoggedIn()){
        $location.path('/');
    };

    $scope.showMyOrders();
});