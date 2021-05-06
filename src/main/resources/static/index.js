angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/store';

    $scope.loadPage = function (page) {
        $http({
            url: contextPath + '/api/v1/products',
            method: 'GET',
            params: {
                p: page
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

            console.log("PAGE FROM BACKEND")
            console.log($scope.productsPage);
        });
    };


    $scope.createNewProduct = function () {
        $http.post(contextPath + '/api/v1/products', $scope.newProduct)
            .then(function successCallback(response) {
                $scope.loadPage(1);
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

//    $scope.addProductToCart = function (productId, productTitle, productPrice) {
//        $http({
//            url: contextPath + '/api/v1/cart/add',
//            method: 'GET',
//            params: {
//                    id: productId,
//                    title: productTitle,
//                    price: productPrice
//            }
//            }).then(function (response) {
//                $scope.initCart();
//        });
//     };

    $scope.addProductToCart = function (productId) {
        $http({
            url: contextPath + '/api/v1/cart/add',
            method: 'GET',
            params: {
                id: productId
            }
            }).then(function (response) {
                $scope.initCart();
        });
    };

    $scope.clearCart = function(){
        $http.get(contextPath + '/api/v1/cart/clear')
            .then(function (response) {
                $scope.productsToCart = null;
                $scope.initCart();
            });
    };

//    $scope.generatePagesIndexes = function (startPage, endPage) {
//        let arr = [];
//        for (let i = startPage; i < endPage + 1; i++) {
//            arr.push(i);
//        }
//        return arr;
//    };


    $scope.loadPage(1);

    $scope.initCart();
});