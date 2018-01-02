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
        'id': 0,
        'type': "FOOD"
    };

    $scope.items = [];

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
        $scope.workingItem.startDate = Date.parse($scope.workingItem.dispalyStartDate);
        $scope.workingItem.endDate = Date.parse($scope.workingItem.dispalyEndDate);

        let url = "company/coupon/";
        $http.post(url, $scope.workingItem)
            .then((response) => {
                $scope.workingItem.id = response.data.id;
                $scope.items.push($scope.workingItem);
            }, () => {
                alert("adding coupon failed ");
            });
        $scope.closeWorkingItem();
    }

    $scope.openEditItem = function (item) {
        $scope.workingItem = item;
        $scope.itemCreateMode = false;
        openWorkingItem();
    }

    $scope.putWorkingItem = () => {
        $scope.workingItem.startDate = Date.parse($scope.workingItem.dispalyStartDate);
        $scope.workingItem.endDate = Date.parse($scope.workingItem.dispalyEndDate);

        let url = "company/coupon/";
        $http.put(url, $scope.workingItem)
            .then(() => {
                $scope.closeWorkingItem();
            }, () => {
                alert("updating the coupon failed")
            });
    }

    $scope.openDeleteConfirmation = function (id, index) {
        let answer = confirm(`Delete Coupon id: ${id} ?`);
        if (answer == true) {
            deleteItem(id, index);
        }
    }

    deleteItem = function (id, index) {

        let url = `company/coupon/${id}/`;
        $http.delete(url)
            .then(() => {
                $scope.items.splice(index, 1);
            }, () => {
                alert("deleting coupon failed");
            });
    }

    /*  ###################
    *       Find coupons
        ################### */
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
        }).then((response) => {

            if (response.data instanceof Object && response.data.constructor === Object) {
                $scope.items = [response.data];

            } else {
                $scope.items = response.data;

            }

            $scope.items.forEach(item => {
                item.dispalyStartDate = new Date(item.startDate);
                item.dispalyEndDate = new Date(item.endDate);

                //set defualt image when no image is selected
                if (item.image == null) {
                    item.image = "./resources/No_Image_Available.jpg";
                }
            });

        }, function myError(response) {
            console.error(response.statusText);
        });
    }

    //initiate data
    getTableData();


});// end of itemsTable controller