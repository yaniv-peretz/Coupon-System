var app = angular.module("adminModule", []);
app.controller('adminCtrl', function ($scope, $http) {
    $scope.client = 'admin';
    $scope.mode = 'comps';
    $scope.workingItem = {};
    $scope.selectedId = 1;
    $scope.itemCreateMode = false;
    $scope.items = [];
    workingItemElement = document.querySelector("#workingItem");

    //initialze
    getItems();

    $scope.changeScope = function () {
        if ($(workingItemElement).hasClass('open-panel')) {
            $scope.closeWorkingItem();
        }
        getItems();
    }

    $scope.getItemById = function () {

        var url = "admin/";
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

    $scope.openCreateNewItem = function () {
        $scope.workingItem = {};
        $scope.workingItem.id = 1;
        $scope.itemCreateMode = true;
        openWorkingItem();
    }

    $scope.closeWorkingItem = function () {
        $(workingItemElement).removeClass('open-panel')
        $(workingItemElement).addClass('close-panel')
    }

    $scope.openUpdateItem = function (item) {
        $scope.workingItem = item;
        $scope.itemCreateMode = false;
        openWorkingItem();
    }

    $scope.openDeleteConfirmation = function (itemId, index) {
        var r = confirm("Delete item id:" + itemId + "?");
        if (r == true) {
            deleteItem(itemId, index);
        }
    }

    $scope.sendNewItem = function () {
        postItem();
        $scope.closeWorkingItem();
        $scope.items.push($scope.workingItem);
    }

    $scope.sendUpdateItem = function () {
        putItem();
        $scope.closeWorkingItem();
    }

    $scope.getItems = function () {
        getItems();
    }

    function getItems() {
        var url = "admin/";
        if ($scope.mode.includes('comps')) {
            url += "company/all";

        } else {
            url += "customer/all";

        }

        $http({
            'method': "GET",
            'url': url,
        }).then((response) => {
            if (response.data instanceof Object && response.data.constructor === Object) {
                $scope.items = [response.data];

            } else {
                $scope.items = response.data;

            }

        }, (response) => {
            alert('getting items failed');
            console.log(response);
        });
    }

    function openWorkingItem() {
        $(workingItemElement).removeClass('close-panel')
        $(workingItemElement).addClass('open-panel')
    }

    function postItem() {
        let item = $scope.mode.includes('comps') ? "company" : "customer";
        var url = `admin/${item}/`;

        $scope.workingItem.id = 0;
        $http.post(url, $scope.workingItem)
            .then((response) => {
                var json = response.data;
                $scope.items.forEach(element => {
                    if (element.id == 0) {
                        element.id = json.id;
                    }
                });
            }, () => {
                console.log("not saved");
            });
        $scope.closeWorkingItem();
    }

    function deleteItem(itemId, index) {

        let itemType = $scope.mode.includes('comps') ? "company" : "customer";
        var _url = `admin/${itemType}/${itemId}`;

        $http({
            'url': _url,
            'method': "DELETE"
        }).then((response) => {
        }, (response) => {
            console.log("not deleted");
        });


        console.log(`itemId:${itemId} index:${index}`);
        $scope.items.splice(index, 1);
        //refresh the table
        $scope.items = $scope.items;
    }

    function putItem() {

        var url = "admin/";
        if ($scope.mode.includes('comps')) {
            url += "company/";

        } else {
            url += "customer/";

        }

        $http.put(url, $scope.workingItem);
        $scope.closeWorkingItem();
    }



});// end of angular module