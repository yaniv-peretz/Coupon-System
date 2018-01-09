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
        if(isNaN($scope.modalCoupon.id) || parseInt($scope.modalCoupon.id) < 1){
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
                $scope.modalCoupon.dispalyStartDate = new Date($scope.modalCoupon.startDate);
                $scope.modalCoupon.dispalyEndDate = new Date($scope.modalCoupon.endDate);
            } else {
                const id = $scope.modalCoupon.id;
                $scope.modalCoupon = {}
                $scope.modalCoupon.id = id;
            }

            $scope.modalCoupon.isValid = true;
            $scope.coupons.forEach(coupon => {
                if(coupon.id === $scope.modalCoupon.id){
                    $scope.modalCoupon.isValid = false;
                }
            });

        }, (response) => {
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

            $scope.coupons.map((coupon) => {
                coupon.dispalyStartDate = new Date(coupon.startDate);
                coupon.dispalyEndDate = new Date(coupon.endDate);
            });

        }, (response) => {
            alert('getting Coupons failed');
            console.error(response);
        });
    }

    $scope.createCoupon = () => {
        const url = "customer/coupon/";
        $scope.modalCoupon.id = 0;
        $scope.modalCoupon.startDate = Date.parse($scope.modalCoupon.dispalyStartDate);
        $scope.modalCoupon.endDate = Date.parse($scope.modalCoupon.dispalyEndDate);

        $http.post(url, $scope.modalCoupon)
            .then((response) => {
                $scope.modalCoupon.id = response.data.id;
                $scope.coupons.push($scope.modalCoupon);
            }, (response) => {
                alert('Coupon not saved')
                console.error($scope.modalCoupon);
                console.error(response);
            });
    }

    $scope.editCoupon = () => {
        const url = "customer/coupon/";
        $http.put(url, $scope.modalCoupon)
            .then(() => {
                $scope.coupons.map((Coupon) => {
                    if (Coupon.id === $scope.modalCoupon.id) {
                        Coupon = $scope.modalCoupon;
                    }
                });

            }, (response) => {
                alert('Coupon not saved')
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
                    alert(`Coupon id: ${couponId} - not deleted`)
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