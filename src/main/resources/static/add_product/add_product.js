angular.module('app').controller('addProductFormController', function ($scope, $http, $localStorage, $location, $cookies) {
    const contextPath = 'http://localhost:8189/store';

    $scope.isCheckUserWithAdminRole = function () {
        if ($localStorage.checkUserWithAdminRole === true) {
            return true;
        }

        return false;
    };

    if(!$scope.isCheckUserWithAdminRole()){
        $location.path('/');
    };

    $scope.loadCategories = function () {
        $http({
            url: contextPath + '/api/v1/categories',
            method: 'GET'
            }).then(function (response) {
                $scope.categories = response.data;
            });
    };

    $scope.tryToAddProduct = function () {
        if ($scope.addProduct.categoryTitle === null || $scope.addProduct.categoryTitle === undefined) {
            $scope.addProduct.categoryTitle = 'not selected';
        }

        $http.post(contextPath + '/api/v1/products/admin', $scope.addProduct)
            .then(function successCallback(response) {
                if (response.data) {
                    $scope.addProduct.title = null;
                    $scope.addProduct.price = null;
                    $scope.addProduct.categoryTitle = null;
                    $location.path('/product_management');
                }
            }, function errorCallback(response) {
            });
    };

    $scope.loadCategories();

});