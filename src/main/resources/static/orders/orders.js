angular.module('app').controller('ordersController', function ($scope, $http, $localStorage) {
    const contextPath = 'http://localhost:8189/store';

    $scope.isUserLoggedIn = function () {
        if ($localStorage.storeOnlineCurrentUser) {
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

    if($scope.isUserLoggedIn()){
        $scope.showMyOrders();
    };

});