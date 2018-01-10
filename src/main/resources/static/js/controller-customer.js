var app = angular.module("CustomerApp", []);
app.controller('customer-controller', function ($scope, $http) {

    // initilaze
    getCoupons();

    $scope.coupons = [];
    $scope.modalCoupon = {
        isValid: false,
    };
    $scope.orderCoupons = 'id';
    $scope.selectOption = "All";
    $scope.options = ["All", "By Date", "By Price", "By Type"];
    $scope.typeOptions = ['RESTURANS', 'ELECTRICITY', 'FOOD', 'HEALTH', 'SPORTS', 'CAMPING', 'TRAVELLING',];

    $scope.openNewCoupon = () => {
        $scope.modalCoupon = {};
        $scope.modalCoupon.id = 0;
    }

    $scope.openViewCoupon = (Coupon) => {
        $scope.isNewCoupon = false;
        $scope.modalCoupon = Coupon;
        $scope.modalCoupon.isValid = false;
    }

    $scope.getCoupons = () => {
        getCoupons();
    }

    $scope.getCoupon = () => {
        if (isNaN($scope.modalCoupon.id) || parseInt($scope.modalCoupon.id) < 1 || $scope.modalCoupon.id === "") {
            $scope.modalCoupon.isValid = false;
            return;
        }
        let url = `customer/coupon/${$scope.modalCoupon.id}`;
        $http({
            'method': "GET",
            'url': url,
        }).then((response) => {
            if (response.data) {
                $scope.modalCoupon = response.data;
                $scope.modalCoupon.isValid = true;
                $scope.modalCoupon.dispalyStartDate = new Date($scope.modalCoupon.startDate);
                $scope.modalCoupon.dispalyEndDate = new Date($scope.modalCoupon.endDate);

                if (0 < $scope.coupons.length) {
                    $scope.coupons.forEach(coupon => {
                        if (coupon.id === $scope.modalCoupon.id) {
                            $scope.modalCoupon.isValid = false;
                        }
                    });
                }
            } else {
                const id = $scope.modalCoupon.id;
                $scope.modalCoupon = {}
                $scope.modalCoupon.id = id;
                $scope.modalCoupon.isValid = false;
            }

        }, () => {
            const id = $scope.modalCoupon.id;
            $scope.modalCoupon = {}
            $scope.modalCoupon.id = id;
        });
    }

    function getCoupons() {
        let url;
        switch ($scope.selectOption) {
            case "All":
                url = "customer/coupon/all";
                break;
            case "By Date":
                dateAsLong = Date.parse($scope.selector.date)
                url = `customer/coupon/date/${dateAsLong}`;
                break;
            case "By Price":
                url = `customer/coupon/price/${$scope.selector.price}`;
                break;
            case "By Type":
                url = `customer/coupon/type/${$scope.selector.type}`;
                break;
            default:
                url = "customer/coupon/all";
                break;
        }
        $http({
            'method': "GET",
            'url': url,
        }).then((response) => {

            if (response.data instanceof Object && response.data.constructor === Object) {
                $scope.coupons = [response.data];
            } else {
                $scope.coupons = response.data;
            }

            if (0 < $scope.coupons.length) {
                $scope.coupons.map((coupon) => {
                    coupon.dispalyStartDate = new Date(coupon.startDate);
                    coupon.dispalyEndDate = new Date(coupon.endDate);
                });
            }


        }, (response) => {
            swal('This is Embarrassing', `getting coupons failed, please try again later`, "error")
            console.error(response);
        });
    }

    $scope.purchaseCoupon = () => {
        const url = `customer/coupon/${$scope.modalCoupon.id}`;
        $http.post(url, $scope.modalCoupon.id)
            .then(() => {
                if (0 < $scope.coupons.length) {
                    $scope.coupons.push($scope.modalCoupon);
                } else {
                    $scope.coupons = [$scope.modalCoupon]
                }
                swal(`Coupon ${$scope.modalCoupon.title} added!`)
            }, (response) => {
                swal('Coupon Not Added', `Adding coupon ${$scope.modalCoupon.title} failed!`, "error")
                console.error($scope.modalCoupon);
                console.error(response);
            });
    }

    $scope.ConfirmCouponDeletion = (couponId) => {
        const isConfirmed = confirm(`Delete Coupon id: ${couponId}`);
        if (isConfirmed === true) {
            const url = `customer/coupon/${couponId}`;
            $http.delete(url)
                .then((response) => {
                    $scope.coupons = $scope.coupons.filter(Coupon => Coupon.id != couponId)
                }, (response) => {
                    swal('Coupon Not Deleted', `Deleting coupon ${couponId} failed!`, "error")
                    console.error(response);
                });
        }
    }

    $scope.changeCouponsOrderBy = (orderBy) => {
        $scope.orderCoupons = orderBy;
    }

    $scope.couponsfilter = "";
    $scope.filterCoupons = () => {
        const filter = $scope.couponsfilter;
        let filteredCoupons = $scope.coupons;
        filteredCoupons.map(coupon => {
            coupon.hide = true;
            const { id, title, type, dispalyStartDate, dispalyEndDate, amount, message, price } = coupon;
            if (id.toString().includes(filter) || title.toString().includes(filter) || type.toString().toLowerCase().includes(filter) || dispalyStartDate.toLocaleDateString().includes(filter)
                || dispalyEndDate.toLocaleDateString().includes(filter) || amount.toString().includes(filter) || message.toString().includes(filter) || price.toString().includes(filter)) {
                coupon.hide = false;
            }
        });
    }

});