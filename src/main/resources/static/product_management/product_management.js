angular.module('app').controller('productManagementController', function ($scope, $http, $localStorage, $location) {
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

    $scope.loadPage = function (page) {
        $http({
            url: contextPath + '/api/v1/products',
            method: 'GET',
            params: {
                p: page,
                title: $scope.filter ? $scope.filter.title : null,
                min_price: $scope.filter ? $scope.filter.min_price : null,
                max_price: $scope.filter ? $scope.filter.max_price : null
            }
        }).then(function (response) {
            $scope.productsPage = response.data;

            let minPageIndex = page - 2;
            if (minPageIndex < 1) {
                minPageIndex = 1;
            }

            let maxPageIndex = page + 2;
            if (maxPageIndex > $scope.productsPage.totalPages) {
                maxPageIndex = $scope.productsPage.totalPages;
            }

            $scope.paginationArray = $scope.generatePagesIndexes(minPageIndex, maxPageIndex);
        });
    };

    $scope.goToAddProductForm = function () {
        $location.path('/add_product');
    };

    $scope.goToUpdateProductForm = function (productId) {
        $location.path('/update_product/' + productId);
    };

    $scope.tryToDeleteProduct = function (productId) {
        $http.delete(contextPath + '/api/v1/products/admin/' + productId)
            .then(function successCallback(response) {
               $scope.loadPage(1);
            }, function errorCallback(response) {
            });
    };

    $scope.generatePagesIndexes = function (startPage, endPage) {
        let arr = [];
        for (let i = startPage; i < endPage + 1; i++) {
            arr.push(i);
        }
        return arr;
    };

    $scope.loadPage(1);
});