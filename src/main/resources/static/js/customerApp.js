var app = angular.module("CustomerApp", []);
app.controller('main', function ($scope, $http) {
    $scope.client = {
        'type': 'Customer'
    }
});//end of main controller

app.controller('workingItem', function ($scope, $http) {
    workingItemElement = document.querySelector("#workingItem");

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

    $scope.openCouponDetails = function () {
        openWorkingItem();
        getWorkingItem();

    }

    $scope.postWorkingItem = function () {
        let url = `customer/coupon/${$scope.workingItem.id}/`;
        $scope.workingItem.startDate = $scope.workingItem.displayStartDate.toLocaleDateString();
        $scope.workingItem.endDate = $scope.workingItem.displayEndDate.toLocaleDateString();
        
        $http.post(url)
        .then(()=>{
            getTableData();

        },()=>{
            alert("purchasing coupon failed");
        });
        $scope.closeWorkingItem();
    }

    $scope.closeWorkingItem = () => {
        $(workingItemElement).removeClass('open-panel')
        $(workingItemElement).addClass('close-panel')
    }


    function openWorkingItem() {
        if ($scope.selectedId !== null &&
            $scope.selectedId > 0 &&
            $scope.selectedId !== undefined) {

            $(workingItemElement).removeClass('close-panel')
            $(workingItemElement).addClass('open-panel')
        }
    }

    function getWorkingItem() {

        var url = "customer/coupon/";
        url += $scope.selectedId;

        $http({
            'method': "GET",
            'url': url,
        }).then(function mySuccess(response) {
            $scope.workingItem = response.data;

            $scope.workingItem.displayStartDate = new Date($scope.workingItem.startDate);
            $scope.workingItem.displayEndDate = new Date($scope.workingItem.endDate);

        }, function myError(response) {
            $scope.workingItem = response.statusText;
        });
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

    $scope.getTableData = () => {
        getTableData();
    }

    function getTableData() {

        var url = "customer/coupon/"
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
                
                //set defualt image when no image is selected
                if(item.image == null){
                    item.image = "./resources/No_Image_Available.jpg";
                }                
            });

        }, function myError(response) {
            console.error(response.statusText);
        });

    }

    getTableData();

});  //end of items controller
