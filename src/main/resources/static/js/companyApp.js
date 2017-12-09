var app = angular.module("CompanyApp", []);
app.controller('compController', function ($scope, $http) {
    $scope.itemCreateMode = false;
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
        $(workingItemElement).removeClass('close-panel')
        $(workingItemElement).addClass('open-panel')
    }

    $scope.closeWorkingItem = function () {
        $(workingItemElement).removeClass('open-panel')
        $(workingItemElement).addClass('close-panel')
    }

    $scope.postWorkingItem = function () {
        $scope.workingItem.startDate = $scope.workingItem.dispalyStartDate.toLocaleDateString();
        $scope.workingItem.endDate = $scope.workingItem.dispalyEndDate.toLocaleDateString();

        var url = "company/coupon/";
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

        $scope.workingItem.startDate = $scope.workingItem.dispalyStartDate.toLocaleDateString();
        $scope.workingItem.endDate = $scope.workingItem.dispalyEndDate.toLocaleDateString();

        var url = "company/coupon/";
        $http.put(url, $scope.workingItem);
        $scope.closeWorkingItem();
        getTableData();
    }

    $scope.openDeleteConfirmation = function (item) {
        var r = confirm("Delete Coupon id:" + item.id + "?");
        if (r == true) {
            deleteItem(item);
            getTableData();
        }
    }

    deleteItem = function (item) {

        var url = "company/coupon/";
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

    //external hook for UI, will trigger items table update
    $scope.getTableData = function () {
        getTableData();
    }

    function getTableData() {
        
        var url = "company/coupon/"
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

        $http({
            'method': "GET",
            'url': url,
        }).then(function mySuccess(response) {

            if (response.data instanceof Object && response.data.constructor === Object) {
                $scope.items = [response.data];

            } else {
                $scope.items = response.data;

            }

            $scope.items.forEach(item => {
                item.dispalyStartDate = new Date(item.startDate);
                item.dispalyEndDate = new Date(item.endDate);                
            });

        }, function myError(response) {
            console.error(response.statusText);
        });
    }

    //initiate data
    getTableData();


});// end of itemsTable controller