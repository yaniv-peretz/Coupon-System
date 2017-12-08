var app = angular.module("publicModule", []);
app.controller('public-Controller', function ($scope, $http) {

    $scope.customer = 'customer';
    $scope.coupons = [
        {
            'title': 'title',
            'message': 'message',
            'price': 5
        }
    ];

    $scope.loggedIn = false;
    $scope.cart = [];
    $scope.customerCoupons = [];


    //initialize
    getAllCoupons();

    function getAllCoupons() {
        var url = "public/coupons";

        $http({
            'method': "GET",
            'url': url,
        }).then(function mySuccess(response) {
            $scope.coupons = response.data;

        }, function myError(response) {
            console.log(response);
        });
    }

    $scope.openCart = function () {
        var cart = document.querySelector('.cart-content');
        if (cart.style.width === '0px' || cart.style.width === '') {
            cart.style.width = '200px';

        } else {
            cart.style.width = '0px';

        }
    }

    $scope.addToCart = function (coupon) {
        if (!$scope.cart.includes(coupon)) {
            $scope.cart.push(coupon);

        }
    }

    $scope.login = function () {
        // login as customer
        var url = "/api/login";
        var password = document.querySelector('#password').value;
        var user = document.querySelector('#user').value;

        var login = {
            'compName': user,
            'password': password
        }

        $http.post(url, login)
            .then(function mySuccess(response) {

                // get all customer coupons
                $scope.loggedIn = true;
                getCustomerCoupons();

            }, function myError(response) {
                alert('user name and password incorrect, check again')
            });

    }

    function getCustomerCoupons() {
        var url = "customer/coupon/all";
        $http({
            'method': "GET",
            'url': url,
        }).then(function mySuccess(response) {
            if (response.data instanceof Object && response.data.constructor === Object) {
                $scope.customerCoupons = [response.data];

            } else {
                $scope.customerCoupons = response.data;

            }
            //remove coupons duplications
            removeCouponCustomerAlreadyOwns();

        }, function myError(response) {
            alert("fechting all coupons failed!");
        });

    }

    function removeCouponCustomerAlreadyOwns() {
        $scope.customerCoupons.forEach(removeElement => {
            $scope.coupons = $scope.coupons.filter(function (allCoupons) {
                return allCoupons.id !== removeElement.id;
            });
        });

    }




});// end of angular module