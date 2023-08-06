angular.module('app').controller('updateProductFormController', function ($scope, $http, $localStorage, $location, $cookies, $routeParams) {
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

    $scope.tryToUpdateProduct = function () {
        if ($scope.updateProduct.categoryTitle === null || $scope.updateProduct.categoryTitle === undefined) {
            $scope.updateProduct.categoryTitle = 'not selected';
        }

        $http.put(contextPath + '/api/v1/products/admin/' + $routeParams.productIdParam, $scope.updateProduct)
            .then(function successCallback(response) {
                if (response.data) {
                    $scope.updateProduct.title = null;
                    $scope.updateProduct.price = null;
                    $scope.updateProduct.categoryTitle = null;
                    $location.path('/product_management');
                }
            }, function errorCallback(response) {
            });
    };

    $scope.loadCategories();

});