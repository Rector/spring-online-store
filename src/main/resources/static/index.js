angular.module('app', ['ngStorage']).controller('indexController', function ($scope, $http, $location, $localStorage) {
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
        });
    };


//    $scope.createNewProduct = function () {
//        $http.post(contextPath + '/api/v1/products', $scope.newProduct)
//            .then(function successCallback(response) {
//                $scope.loadPage(1);
//                $scope.newProduct = null;
//            }, function errorCallback(response) {
//                console.log(response.data);
//                alert('Error: ' + response.data.messages);
//            });
//    };

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

    $scope.generatePagesIndexes = function (startPage, endPage) {
        let arr = [];
        for (let i = startPage; i < endPage + 1; i++) {
            arr.push(i);
        }
        return arr;
    };

    $scope.tryToAuth = function () {
        $http.post(contextPath + '/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.storeOnlineCurrentUser = {username: $scope.user.username, token: response.data.token};

                    $scope.username = $scope.user.username;

                    $scope.user.username = null;
                    $scope.user.password = null;

                    $scope.showMyOrders();
                }
            }, function errorCallback(response) {
            });
    };

    $scope.tryToLogout = function () {
        $scope.clearUser();
    };

    $scope.clearUser = function () {
        delete $localStorage.storeOnlineCurrentUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $scope.isUserLoggedIn = function () {
        if ($localStorage.storeOnlineCurrentUser) {
            return true;
        } else {
            return false;
        }
    };

    $scope.createNewOrder = function () {
        $http({
            url: contextPath + '/api/v1/orders',
            method: 'POST',
        }).then(function (response) {
            $scope.showMyOrders();
            $scope.loadCart();
        });
    };

    $scope.showMyOrders = function () {
        $http({
            url: contextPath + '/api/v1/orders',
            method: 'GET',
        }).then(function (response) {
            $scope.myOrders = response.data;
        });
    };

    if ($localStorage.storeOnlineCurrentUser) {
        $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.storeOnlineCurrentUser.token;
        $scope.showMyOrders();
   }


    $scope.loadPage(1);
    $scope.loadCart();
});