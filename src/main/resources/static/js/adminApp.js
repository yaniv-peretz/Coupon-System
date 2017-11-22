// var serverurl = "/CouponSystemServer/webapi/";
var serverUrl = "";

var app = angular.module("adminModule", []);
app.controller('adminCtrl', function ($scope, $http) {
    $scope.client = 'admin';
    $scope.mode = 'comps';
    $scope.workingItem = {};
    $scope.selectedId = 1;
    $scope.itemCreateMode = false;
    $scope.items = [];


    $scope.getItemById = function () {

        url = serverUrl + "admin/";
        if ($scope.mode.includes('comps')) {
            url += "company/" + $scope.selectedId;

        } else {
            url += "customer/" + $scope.selectedId;

        }

        $http({
            'method': "GET",
            'url': url,
        }).then(function mySuccess(response) {
            $scope.workingItem = response.data;
        }, function myError(response) {
            $scope.workingItem = response.statusText;
        });
    }

    $scope.clearItems = function () {
        $scope.items = {};
    }

    $scope.changeCreateMode = function () {
        $scope.workingItem = {};
        $scope.itemCreateMode = !$scope.itemCreateMode;
        $scope.workingItem.id = 1;
    }

    $scope.postItem = function () {
        console.log($scope.workingItem);
        

        url = serverUrl + "admin/";
        if ($scope.mode.includes('comps')) {
            url += "company/";

        } else {
            url += "customer/";

        }

        $http.post(url, $scope.workingItem);
        $scope.changeCreateMode;
    }


    $scope.deleteItem = function () {

        url = serverUrl + "admin/";
        if ($scope.mode.includes('comps')) {
            url += "company/";

        } else {
            url += "customer/";

        }

        $http({
            'url': url,
            dataType: "json",
            method: "DELETE",
            data: $scope.workingItem,
            headers: {
                "Content-Type": "application/json"
            }
        });
        $scope.workingItem = {};
    }


    $scope.updateItem = function () {

        url = serverUrl + "admin/";
        if ($scope.mode.includes('comps')) {
            url += "company/";

        } else {
            url += "customer/";

        }
        
        $http.put(url, $scope.workingItem);
        $scope.workingItem = {};
    }

    $scope.getItems = function () {
        url = serverUrl + "admin/";
        if ($scope.mode.includes('comps')) {
            url += "company/all";

        } else {
            url += "customer/all";

        }

        $http({
            'method': "GET",
            'url': url,
        }).then(function mySuccess(response) {
            if (response.data instanceof Object && response.data.constructor === Object) {
                $scope.items = [response.data];

            } else {
                $scope.items = response.data;

            }

        }, function myError(response) {
            $scope.items = response.statusText;
        });
    }

});// end of angular module