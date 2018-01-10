var app = angular.module("publicModule", []);
app.controller('public-Controller', function ($scope, $http) {

    $scope.isLoggedIn = false;
    $scope.customer = 'customer';
    $scope.coupons = [
        {
            'title': 'title',
            'message': 'message',
            'price': 5
        }
    ];
    $scope.cart = [];
    $scope.customerCoupons = [];

    //initialize
    getAllCoupons();

    function getAllCoupons() {
        const url = "public/coupons";

        $http.get(url, ).then((response) => {
            $scope.coupons = response.data;
            if (response.data && 0 < response.data.length) {
                $scope.coupons.map(coupon => {
                    if (coupon.image === "" || coupon.image === null || coupon.image === undefined) {
                        coupon.image = "http://vollrath.com/ClientCss/images/VollrathImages/No_Image_Available.jpg";
                    };
                });
            }
        }, (response) => {
            console.error(response);
            alert("could not get all coupons - something went wrong")
        });
    }

    $scope.addToCart = (coupon) => {
        let element = document.querySelector(`#coupon${coupon.id}`);
        $(element).removeClass('animated infinite bounce')
        $(element).addClass('animated zoomOutUp')
        $(element)
            .one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend',
            () => {
                $(element).remove()
            });
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
            .then(() => {

                // get all customer coupons
                $scope.isLoggedIn = true;
                $scope.customer = user;
                getCustomerCoupons();

            }, () => {
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

                if (0 < $scope.customerCoupons.length) {
                    $scope.customerCoupons = $scope.customerCoupons.concat($scope.cart);
                } else {
                    $scope.customerCoupons = $scope.cart;
                }
                $scope.cart = [];

                if (0 < $scope.customerCoupons.length) {
                    removeCouponCustomerAlreadyOwns();
                }
                
                swal('Coupons Bought', `You can now see you'r coupons in the \'My Coupons'\ tab!`, "success")
            }, (response) => {
                alert('buying coupons failed, try again later')
            });

    }

    function getCustomerCoupons() {
        const url = "customer/coupon/all";
        $http({
            method: "GET",
            url: url,
        }).then((response) => {
            if (response.data instanceof Object && response.data.constructor === Object) {
                $scope.customerCoupons = [response.data];

            } else {
                $scope.customerCoupons = response.data;

            }
            //remove coupons duplications

            if (0 < $scope.customerCoupons.length) {
                removeCouponCustomerAlreadyOwns();
                removeCartCouponCustomerAlreadyOwns();

            }

        }, (response) => {
            alert("fechting all coupons failed!");
        });

    }

    function removeCouponCustomerAlreadyOwns() {
        $scope.customerCoupons.forEach(removeElement => {
            $scope.coupons = $scope.coupons.filter((allCoupons) => {
                return allCoupons.id !== removeElement.id;
            });
        });
    }

    function removeCartCouponCustomerAlreadyOwns() {
        $scope.customerCoupons.forEach(removeElement => {
            $scope.cart = $scope.cart.filter((cartCoupons) => {
                return cartCoupons.id !== removeElement.id;
            });
        });
    }

    $scope.bounceStart = (id) => {
        let element = document.querySelector(`#coupon${id}`);
        $(element).addClass('animated infinite bounce')
    }

    $scope.bounceEnd = (id) => {
        let element = document.querySelector(`#coupon${id}`);
        $(element).removeClass('infinite bounce')
    }

});// end of angular module