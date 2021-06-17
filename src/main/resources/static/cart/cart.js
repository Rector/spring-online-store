angular.module('app').controller('cartController', function ($scope, $http, $localStorage) {
    const contextPath = 'http://localhost:8189/store';

    $scope.loadCart = function (page) {
        $http({
            url: contextPath + '/api/v1/cart',
            method: 'GET',
            params: {
                cartName: $localStorage.storeCartId
            }
        }).then(function (response) {
            $scope.cartDto = response.data;
        });
    };

    $scope.clearCart = function(){
        $http({
            url: contextPath + '/api/v1/cart/clear',
            method: 'GET',
            params: {
                cartName: $localStorage.storeCartId
            }
        }).then(function (response) {
            $scope.loadCart();
        });
    };

    $scope.isUserLoggedIn = function () {
        if ($localStorage.onlineStoreCurrentUser) {
            return true;
        } else {
            return false;
        }
    };

    $scope.createNewOrder = function () {
        $http.post(contextPath + '/api/v1/orders', $scope.deliveryAddressAndPhone)
            .then(function(response) {
                $scope.loadCart();

                $scope.deliveryAddressAndPhone = null;
            });
    };

    $scope.addProductToCart = function (productId) {
        $http({
            url: contextPath + '/api/v1/cart/add/',
            method: 'GET',
            params: {
                prodId: productId,
                cartName: $localStorage.storeCartId
            }
        }).then(function (response) {
            $scope.loadCart();
        });
    };

    $scope.decrementProduct = function (productId) {
        $http({
            url: contextPath + '/api/v1/cart/dec/',
            method: 'GET',
            params: {
                prodId: productId,
                cartName: $localStorage.storeCartId
            }
        }).then(function (response) {
            $scope.loadCart();
        });
    };

    $scope.loadCart();
});