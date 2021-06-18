angular.module('app').controller('productInfoController', function ($scope, $http, $localStorage, $routeParams) {
    const contextPath = 'http://localhost:8189/store';

    $scope.loadProduct = function () {
        $http({
            url: contextPath + '/api/v1/products/' + $routeParams.productIdParam,
            method: 'GET'
        }).then(function (response) {
            $scope.prodComments = response.data;
        });
    };

    $scope.loadComments = function (prodCommentsId) {
        $http({
            url: contextPath + '/api/v1/comments/cre_com',
            method: 'GET',
            params: {
                productId: prodCommentsId,
                comment: $scope.createComment.title
            }
        }).then(function (response) {
            $scope.prodComments = response.data;
            $scope.createComment.title = null;
        });
    };

    $scope.loadProduct();
});