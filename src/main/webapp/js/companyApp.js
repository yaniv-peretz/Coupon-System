var app = angular.module("CompanyApp", []);

app.controller('main', function ($scope, $http) {
    $scope.client = {
        'type': 'Company'
    }
});// end of angular main controller

app.controller('workingItem', function ($scope, $http) {

    $scope.selectedId = 1;
    $scope.typeOptions = [
        'RESTURANS',
        'ELECTRICITY',
        'FOOD',
        'HEALTH',
        'SPORTS',
        'CAMPING',
        'TRAVELLING',
    ];
    $scope.itemCreateMode = false;

    $scope.workingItem = {
        "id": 1,
        "type": "RESTURANS",
        "title": "",
        "startDate": "",
        "endDate": "",
        "message": "",
        "amount": "",
        "price": ""
    }

    $scope.changeWorkingItemEditMode = function () {
        $scope.workingItem = {};
        $scope.itemCreateMode = !$scope.itemCreateMode;
    }

    $scope.getWorkingItem = function () {

        url = "/CouponSystemServer/webapi/company/coupon/";
        url += $scope.selectedId;

        $http({
            'method': "GET",
            'url': url,
        }).then(function mySuccess(response) {
            $scope.workingItem = response.data;

        }, function myError(response) {
            $scope.workingItem = response.statusText;
        });
    }

    $scope.postWorkingItem = function () {

        url = "/CouponSystemServer/webapi/company/coupon/";
        $http.post(url, $scope.workingItem);
        $scope.workingItem = {};
    }


    $scope.putWorkingItem = function () {

        url = "/CouponSystemServer/webapi/company/coupon/";
        $http.put(url, $scope.workingItem);
        $scope.workingItem = {};
    }

    $scope.deleteWorkingItem = function () {

        url = "/CouponSystemServer/webapi/company/coupon/";
        $http({
            'url': url,
            'dataType': "json",
            'method': "DELETE",
            'data': $scope.workingItem,
            'headers': {
                "Content-Type": "application/json"
            }
        });
        $scope.workingItem = {};
    }

});// end of angular workingItem controller


app.controller('itemsTable', function ($scope, $http) {

    $scope.options = ["All", "By Date", "By Price", "By Type"];
    $scope.selectedOption = "All";
    $scope.typeOptions = [
        'RESTURANS',
        'ELECTRICITY',
        'FOOD',
        'HEALTH',
        'SPORTS',
        'CAMPING',
        'TRAVELLING',
    ];
    $scope.filter = 1;
    $scope.items = [];

    $scope.getTableData = function () {

        url = "/CouponSystemServer/webapi/company/coupon/"
        switch ($scope.selectedOption) {

            case 'All':
                url += "all";
                break;

            case 'By Date':
                url += "date/";
                url += $scope.filter.substr(0, 4) + "/";
                url += $scope.filter.substr(5, 2) + "/";
                url += $scope.filter.substr(8, 2) + "/";
                break;
            case 'By Price':
                url += "price/";
                url += $scope.filter;
                break;

            case 'By Type':
                url += "type/";
                url += $scope.filter;
                break;

            default:
                return;
        }

        $http({
            method: "GET",
            url: url,
        }).then(function mySuccess(response) {

            if (response.data instanceof Object && response.data.constructor === Object) {
                $scope.items = [response.data];

            } else {
                $scope.items = response.data;

            }

        }, function myError(response) {
            console.error(response.statusText);
        });
    }

});// end of itemsTable controller