angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/store';

    $scope.init = function () {
        $http.get(contextPath + '/api/v1/products')
            .then(function (response) {
                $scope.products = response.data;
            });
    };

    $scope.createNewProduct = function () {
        $http.post(contextPath + '/api/v1/products', $scope.newProduct)
            .then(function successCallback(response) {
                $scope.init();
                $scope.newProduct = null;
            }, function errorCallback(response) {
                console.log(response.data);
                alert('Error: ' + response.data.messages);
            });
    };

    $scope.initCart = function () {
        $http.get(contextPath + '/api/v1/cart')
            .then(function (response) {
                $scope.productsToCart = response.data;
            });
    };

    $scope.addProductToCart = function (productId, productTitle, productPrice) {
        $http({
            url: contextPath + '/api/v1/cart/add',
            method: 'GET',
            params: {
                    id: productId,
                    title: productTitle,
                    price: productPrice
            }
            }).then(function (response) {
                $scope.initCart();
        });
     };

    $scope.clearCart = function(){
        $http.delete(contextPath + '/api/v1/cart');
        $scope.initCart();
    };

    $scope.init();

    $scope.initCart();
});