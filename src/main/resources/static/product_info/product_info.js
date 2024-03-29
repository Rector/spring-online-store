angular.module('app').controller('productInfoController', function ($scope, $http, $localStorage, $routeParams) {
    const contextPath = 'http://localhost:8189/store';

    $scope.isUserLoggedIn = function () {
        if ($localStorage.onlineStoreCurrentUser) {
            return true;
        } else {
            return false;
        }
    };

    $scope.checkUserBoughtProduct = function (prodId) {
        $http({
            url: contextPath + '/api/v1/comments/ch_buy',
            method: 'GET',
            params: {
                productId: prodId
            }
        }).then(function (response) {
            $scope.isUserBoughtProduct = response.data;
        });
    };

    $scope.loadProduct = function () {
        $http({
            url: contextPath + '/api/v1/products/' + $routeParams.productIdParam,
            method: 'GET'
        }).then(function (response) {
            $scope.prod = response.data;

            if($localStorage.onlineStoreCurrentUser){
                $scope.checkUserBoughtProduct($routeParams.productIdParam);
            }
        });
    };

    $scope.createComment = function (prodId) {
        $scope.comment.productId = prodId;
        $http.post(contextPath + '/api/v1/comments', $scope.comment)
            .then(function (response) {
                $scope.comment.productId = null;
                $scope.comment.title = null;
                $scope.loadProduct();
        });
    };

    $scope.loadProduct();
});