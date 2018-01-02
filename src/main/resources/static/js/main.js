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
        }).then((response) => {
            $scope.coupons = response.data;

            $scope.coupons.forEach(coupn => {
                if(coupn.image == "" || coupn.image == null || coupn.image == undefined ){
                    coupn.image = "http://vollrath.com/ClientCss/images/VollrathImages/No_Image_Available.jpg";
                }    
            });                

        }, (response) => {
            alert("could not get all coupons - something went wrong")
        });
    }

    // $scope.openCart = () => {
    //     var cart = document.querySelector('.cart-content');
    //     if (cart.style.width === '0px' || cart.style.width === '') {
    //         cart.style.width = '225px';

    //     } else {
    //         cart.style.width = '0px';

    //     }
    // }

    $scope.addToCart = function (coupon) {
        if (!$scope.cart.includes(coupon)) {
            $scope.cart.push(coupon);

        }
    }

    $scope.login = () => {
        // login as customer
        var user = document.querySelector('#username').value;
        var password = document.querySelector('#password').value;
        
        var login = {
            'custName': user,
            'password': password,
        }

        var url = "api/login";
        $http.post(url, login)
            .then(function mySuccess(response) {

                // get all customer coupons
                $scope.loggedIn = true;
                $scope.customer = user;
                getCustomerCoupons();

            }, function myError(response) {
                alert('user name and password incorrect, check again')
            });

    }

    $scope.buyAllCoupons = () => {
        //purchase all coupons in cart
        let couponsIds = [];
        $scope.cart.forEach(element => {
            couponsIds.push(element.id);
        });

        var url = "customer/coupons";
        $http.post(url, couponsIds)
            .then((response) => {
                $scope.customerCoupons = $scope.customerCoupons.concat($scope.cart);
                $scope.cart = [];
                removeCouponCustomerAlreadyOwns();

            }, (response) => {
                alert('buying coupons failed, try again later')
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
            removeCartCouponCustomerAlreadyOwns();

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

    function removeCartCouponCustomerAlreadyOwns() {
        $scope.customerCoupons.forEach(removeElement => {
            $scope.cart = $scope.cart.filter(function (cartCoupons) {
                return cartCoupons.id !== removeElement.id;
            });
        });
    }



});// end of angular module