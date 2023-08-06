(function ($localStorage) {
    'use strict';

    angular
        .module('app', ['ngRoute', 'ngStorage', 'ngCookies'])
        .config(config)
        .run(run);

    function config($routeProvider, $httpProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'home/home.html',
                controller: 'homeController'
            })
            .when('/cart', {
                templateUrl: 'cart/cart.html',
                controller: 'cartController'
            })
            .when('/product_info/:productIdParam', {
                templateUrl: 'product_info/product_info.html',
                controller: 'productInfoController'
            })
            .when('/products', {
                templateUrl: 'products/products.html',
                controller: 'productsController'
            })
            .when('/add_product', {
                templateUrl: 'add_product/add_product.html',
                controller: 'addProductFormController'
            })
            .when('/update_product/:productIdParam', {
                templateUrl: 'update_product/update_product.html',
                controller: 'updateProductFormController'
            })
            .when('/product_management', {
                templateUrl: 'product_management/product_management.html',
                controller: 'productManagementController'
            })
            .when('/orders', {
                templateUrl: 'orders/orders.html',
                controller: 'ordersController'
            })
            .when('/users/register', {
                templateUrl: 'registration/registration_form.html',
                controller: 'registrationFormController'
            })
            .otherwise({
                redirectTo: '/'
            });
    }

    function run($rootScope, $http, $localStorage) {
        if ($localStorage.onlineStoreCurrentUser) {
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.onlineStoreCurrentUser.token;
        }

        if ($localStorage.storeCartId) {
        } else {
            const contextPath = 'http://localhost:8189/store';

            $http({
                url: contextPath + '/api/v1/cart/generate',
                method: 'GET'
            }).then(function (response) {
                $localStorage.storeCartId = response.data.str;
            });
        }
    }
})();

angular.module('app').controller('indexController', function ($scope, $http, $localStorage, $location, $cookies) {
    const contextPath = 'http://localhost:8189/store';

    $scope.mergeCarts = function () {
        $http({
            url: contextPath + '/api/v1/cart/merge',
            method: 'GET',
            params: {
                'cartId': $localStorage.storeCartId
            }
        }).then(function (response) {
        });
    };

    $scope.isUserWithAdminRole = function () {
        $http({
            url: contextPath + '/api/v1/users/ch_admin',
            method: 'GET'
        }).then(function (response) {
            $localStorage.checkUserWithAdminRole = response.data;
        });
    };

    $scope.tryToAuth = function () {
        $http.post(contextPath + '/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.onlineStoreCurrentUser = {username: $scope.user.username, token: response.data.token};

                    $scope.mergeCarts();
                    $scope.isUserWithAdminRole();

                    $scope.userLoginShow = 'Hello, ' + $scope.user.username;
                    $scope.user.username = null;
                    $scope.user.password = null;
                }
            }, function errorCallback(response) {
            });
    };

    $scope.tryToLogout = function () {
        $scope.clearUser();
        $localStorage.checkUserWithAdminRole = false;
        $location.path('/');
    };

    $scope.clearUser = function () {
        delete $localStorage.onlineStoreCurrentUser;
        $scope.userLoginShow = null;
        $http.defaults.headers.common.Authorization = '';
    };

    $scope.goToRegistrationForm = function () {
        $location.path('/users/register');
    };

    $scope.isUserLoggedIn = function () {
        if ($localStorage.onlineStoreCurrentUser) {
            return true;
        } else {
            return false;
        }
    };

    $scope.isCheckUserWithAdminRole = function () {
        if ($localStorage.checkUserWithAdminRole === true) {
            return true;
        }

        return false;
    };

});