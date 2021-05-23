angular.module('app').controller('cartController', function ($scope, $http, $localStorage) {
    const contextPath = 'http://localhost:8189/store';

    $scope.loadCart = function () {
        $http.get(contextPath + '/api/v1/cart')
            .then(function (response) {
                $scope.cartDto = response.data;
            });
    };

    $scope.addProductToCart = function (productId) {
        $http.get(contextPath + '/api/v1/cart/add/' + productId)
            .then(function (response) {
                $scope.loadCart();
                $scope.totalSumProductsToCart = 'Total price products to Cart: ';
        });
    };

    $scope.clearCart = function(){
        $http.get(contextPath + '/api/v1/cart/clear')
            .then(function (response) {
                $scope.cartDto = null;;
                $scope.totalSumProductsToCart = null;

                $scope.loadCart();
            });
    };

    $scope.isUserLoggedIn = function () {
        if ($localStorage.storeOnlineCurrentUser) {
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

    $scope.loadCart();

});