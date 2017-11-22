// var serverurl = "/CouponSystemServer/webapi/";
var serverUrl = "";

var app = angular.module("CustomerApp", []);
app.controller('main', function ($scope, $http) {
    $scope.client = {
        'type': 'Customer'
    }
});//end of main controller

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

    $scope.getWorkingItem = function () {

        url = serverUrl + "customer/coupon/";
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

        url = serverUrl + "customer/coupon/";
        $http.post(url, $scope.workingItem);
        $scope.workingItem = {};
    }


});// end of angular controller

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

        url = serverUrl + "customer/coupon/"
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

});  //end of items controller
