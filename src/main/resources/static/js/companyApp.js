var app = angular.module("CompanyApp", []);
app.controller('compController', function ($scope, $http) {
    $scope.client = {
        'id': 'set id in compApp.js'
    }
    workingItemElement = document.querySelector("#workingItem");    
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
    
    $scope.items;

    $scope.openCreateNewItem = function () {
        $scope.workingItem = {};
        $scope.itemCreateMode = true;
        openWorkingItem();
        
    }

    function openWorkingItem() {
        $(workingItemElement).removeClass('close')
        $(workingItemElement).addClass('open')
    }

    $scope.closeWorkingItem = function () {
        $(workingItemElement).removeClass('open')
        $(workingItemElement).addClass('close')
    }

    $scope.postWorkingItem = function () {

        url = "company/coupon/";
        $http.post(url, $scope.workingItem);
        $scope.closeWorkingItem();
        getTableData();
    }

    $scope.openEditItem = function (item){
        $scope.workingItem = item;
        $scope.itemCreateMode = false;        
        openWorkingItem();
    }

    $scope.putWorkingItem = function () {

        url = "company/coupon/";
        $http.put(url, $scope.workingItem);
        $scope.closeWorkingItem();
        getTableData();
    }

    $scope.openDeleteConfirmation = function (item) {
        r = confirm("Delete Coupon id:" + item.id + "?");
        if (r == true) {
            deleteItem(item);
            getTableData();
        }
    }

    deleteItem = function (item) {

        url = "company/coupon/";
        $http({
            'url': url,
            'dataType': "json",
            'method': "DELETE",
            'data': item,
            'headers': {
                "Content-Type": "application/json"
            }
        });
        $scope.workingItem = {};
    }

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
    $scope.filter = '';

    $scope.getTableData = function () {
        getTableData();
    }

    function getTableData() {
        
        url = "company/coupon/"
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
                url += "all";
                return;
        }

        console.log(url);
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

    //initiate data
    getTableData();


});// end of itemsTable controller