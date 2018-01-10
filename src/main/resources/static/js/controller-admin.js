var app = angular.module("adminModule", []);
app.controller('adminCtrl', function ($scope, $http) {
    // initilaze
    getCompanies();
    getCustomers();

    $scope.companies = [];
    $scope.customers = [];
    $scope.modalCompany = {};
    $scope.modalCustomer = {};
    $scope.orderCustomers = 'id';
    $scope.orderCompanies = 'id';

    $scope.isNewCompany = true;
    $scope.isNewCustomer = true;

    $scope.openNewCompany = () => {
        $scope.isNewCompany = true;
        $scope.modalCompany = {};
        $scope.modalCompany.id = 0;
    }

    $scope.openEditCompany = (company) => {
        $scope.isNewCompany = false;
        $scope.modalCompany = company;
    }

    $scope.openNewCustomer = () => {
        $scope.isNewCustomer = true;
        $scope.modalCustomer = {};
        $scope.modalCustomer.id = 0;
    }

    $scope.openEditCustomer = (customer) => {
        $scope.isNewCustomer = false;
        $scope.modalCustomer = customer;
    }

    function getCompanies() {
        const url = "admin/company/all";
        $http({
            'method': "GET",
            'url': url,
        }).then((response) => {
            if (response.data instanceof Object && response.data.constructor === Object) {
                $scope.companies = [response.data];
            } else {
                $scope.companies = response.data;
            }

        }, (response) => {
            swal('This is Embarrassing', `getting companies failed, please try again later`, "error")
            console.error(response);
        });
    }

    function getCustomers() {
        const url = "admin/customer/all";
        $http({
            'method': "GET",
            'url': url,
        }).then((response) => {
            if (response.data instanceof Object && response.data.constructor === Object) {
                $scope.customers = [response.data];
            } else {
                $scope.customers = response.data;
            }

        }, (response) => {
            swal('This is Embarrassing', `getting customers failed, please try again later`, "error")
            console.error(response);
        });
    }

    $scope.createCompany = () => {
        const url = "admin/company/";
        $scope.modalCompany.id = 0;
        $http.post(url, $scope.modalCompany)
            .then((response) => {
                $scope.modalCompany.id = response.data.id;
                $scope.companies.push($scope.modalCompany);
                swal('Company Added', `Company ${$scope.modalCompany.compName} added!`, "success")
            }, (response) => {
                swal('Company Not Added', `Adding company ${$scope.modalCompany.compName} failed!`, "error")
                console.error(response);
            });
    }

    $scope.createCustomer = () => {
        const url = "admin/customer/";
        $scope.modalCustomer.id = 0;
        $http.post(url, $scope.modalCustomer)
            .then((response) => {
                $scope.modalCustomer.id = response.data.id;
                $scope.customers.push($scope.modalCustomer);
                swal('Customer Added', `Customer ${$scope.modalCompany.custName} added!`, "success")
            }, (response) => {
                swal('Customer Not Added', `Adding customer ${$scope.modalCompany.custName} failed!`, "error")
                console.error(response);
            });
    }

    $scope.editCompany = () => {
        const url = "admin/company/";
        $http.put(url, $scope.modalCompany)
            .then(() => {
                $scope.companies.map((company) => {
                    if (company.id === $scope.modalCompany.id) {
                        company = $scope.modalCompany;
                    }
                });
                swal('Company Edited', `Company ${$scope.modalCompany.custName} edited!`, "success")
            }, (response) => {
                swal('Company Edited Failed', `Company ${$scope.modalCompany.custName} editing failed!`, "error")
                console.error(response);
            });
    }

    $scope.editCustomer = () => {
        const url = "admin/customer/";
        $http.put(url, $scope.modalCustomer)
            .then(() => {
                $scope.customers.map((customer) => {
                    if (customer.id === $scope.modalCustomer.id) {
                        customer = $scope.modalCustomer;
                    }
                    swal('Customer Edited', `Customer ${$scope.modalCustomer.custName} edited!`, "success")
                });
            }, (response) => {
                swal('Customer Edited Failed', `Customer ${$scope.modalCustomer.custName} editing failed!`, "error")
                console.error(response);
            });
    }

    $scope.ConfirmCompanyDeletion = (itemId) => {
        swal({
            title: "Are you sure?",
            text: `Delete company ${itemId}? you will not be able to recover the company!`,
            icon: "warning",
            buttons: true,
            dangerMode: true,
        }).then((willDelete) => {
            if (willDelete) {
                const url = `admin/company/${itemId}`;
                $http.delete(url)
                    .then((response) => {
                        $scope.companies = $scope.companies.filter(company => company.id != itemId)
                    }, (response) => {
                        swal("Deleting Company Failed!", `Deleting company ${itemId} faild!`, "error");
                        console.error(response);
                    });
            }
        })
    }

    $scope.ConfirmCustomerDeletion = (itemId) => {
        swal({
            title: "Are you sure?",
            text: `Delete Customer ${itemId}? you will not be able to recover the customer!`,
            icon: "warning",
            buttons: true,
            dangerMode: true,
        }).then((willDelete) => {
            if (willDelete === true) {
                console.log(willDelete + " here");
                console.log(willDelete + " here");
                console.log(willDelete + " here");
                console.log(willDelete + " here");
                
                const url = `admin/customer/${itemId}`;
                $http.delete(url)
                    .then((response) => {
                        $scope.customers = $scope.customers.filter(customer => customer.id != itemId)
                    }, (response) => {
                        swal("Deleting Customer Failed!", `Deleting customer ${itemId} faild!`, "error");
                        console.error(response);
                    });
            }
        });
    }

    $scope.changeCompaniesOrderBy = (orderBy) => {
        $scope.orderCompanies = orderBy;
    }

    $scope.changeCustomersOrderBy = (orderBy) => {
        $scope.orderCustomers = orderBy;
    }

    $scope.Companiesfilter = "";
    $scope.filterCompanies = () => {
        const filter = $scope.Companiesfilter;
        let filteredCompanies = $scope.companies;
        filteredCompanies.map(comp => {
            comp.hide = true;
            const { id, compName, email, password } = comp;
            if (id.toString().includes(filter) || compName.toString().includes(filter) || email.toString().includes(filter) || password.toString().includes(filter)) {
                comp.hide = false;
            }
        });
    }

    $scope.Customersfilter = "";
    $scope.filterCustomers = () => {
        const filter = $scope.Customersfilter;
        let filteredCustomers = $scope.customers;
        filteredCustomers.map(cust => {
            cust.hide = true;
            const { id, custName, password } = cust;
            if (id.toString().includes(filter) || custName.toString().includes(filter) || password.toString().includes(filter)) {
                cust.hide = false;
            }
        });
    }
});