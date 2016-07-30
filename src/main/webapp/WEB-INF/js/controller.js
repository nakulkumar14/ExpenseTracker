var app = angular.module("myModule", [])
    .controller("expenseController", function ($scope, $http) {

        $scope.details = '';
        $scope.totalExpenses = 0;

        $scope.expenseDetails = '';
        $scope.errorDelete = '';
        $scope.deleteStatus = '';
        $scope.errorAdd = '';
        $scope.errorEdit = '';
        $scope.addRequestStatus = '';
        $scope.editRequestStatus = '';

        $scope.init = function () {
            console.log("Initializing page.");
            $http.get("getExpenseDetailsForToday")
                .then(function (response) {
                    $scope.expenseDetails = response.data;
                    $scope.totalExpenses = 0;
                    console.log("init method : " + JSON.stringify(response.data));

                    //Calculate total expenses from response
                    angular.forEach($scope.expenseDetails, function (item) {
                        $scope.totalExpenses += item.amount;
                        console.log("Item amount: " + item.amount);
                    });
                    console.log("Total expenses" + $scope.totalExpenses);

                });
        };

        $scope.addExpense = function (expense) {
            console.log("Received" + expense.amount + expense.description);

            var expenseDetail = {
                id: 0,
                description: expense.description,
                amount: expense.amount,
                created: expense.created,
                updated: expense.updated,
            };

            console.log("expense detail : " + JSON.stringify(expenseDetail));

            $http({
                method: 'POST',
                dataType: 'json',
                url: 'addExpense',
                data: JSON.stringify(expenseDetail),
                headers: {
                    "Content-Type": "application/json"
                }
            }).success(function (response) {
                console.log("Response : ", JSON.stringify(response));
                $scope.addRequestStatus = 'Expense record added successfully.';

                $scope.errorDelete = '';
                $scope.deleteStatus = '';
                $scope.errorAdd = '';

                $scope.expense = '';
                $scope.init();
            }).error(function (response) {
                console.log("Error : " + response.data);
                $scope.errorAdd = 'Error occurred : ' + response.data;
                $scope.init();
            });
        };

        // Method to delete expense.
        $scope.deleteExpense = function (id) {
            console.log("Delete expense with id : " + id);
            $http({
                method: 'GET',
                dataType: 'json',
                url: "/deleteExpense/" + id,
                headers: {
                    "Content-Type": "application/json"
                }
            }).success(function (response) {
                console.log("Deleted : " + response);
                if(response){
                    $scope.deleteStatus = 'Record deleted successfully.';
                }
                else{
                    $scope.deleteStatus = 'Record deletion failed.';
                }

                $scope.errorEdit = '';
                $scope.errorAdd = '';
                $scope.addRequestStatus = '';
                $scope.editRequestStatus = '';

                $scope.init();
            }).error(function (response) {
                console.log(response);
                $scope.errorDelete = 'Error while deleting record : '+response;
                $scope.init();
            });
        };

        $scope.editExpense = function (id) {
            console.log("Making call to load expense details for id : " + id);
            $http({
                method: 'get',
                dataType: 'json',
                url: "/editExpense/" + id,
                headers: {
                    "Content-Type": "application/json"
                }
            }).success(function (response) {
                $scope.editExpenseRecord = response;
                console.log("Expense detail to edit : " + JSON.stringify($scope.expense));

                $scope.deleteStatus = '';
                $scope.addRequestStatus = '';
                $scope.errorAdd = '';
                $scope.errorDelete = '';
                $scope.expense = '';
                $scope.init();
            }).error(function (response) {
                console.log('Error occurred' + response);
                $scope.errorEdit = response;
            });
        };

        $scope.updateExpenseDetail = function(editExpenseRecord){
            console.log("Edited : "+editExpenseRecord);

            $http({
                method: 'POST',
                dataType: 'json',
                url: 'updateExpenseDetail',
                data: JSON.stringify(editExpenseRecord),
                headers: {
                    "Content-Type": "application/json"
                }
            }).success(function (response) {
                console.log("Response : ", JSON.stringify(response));
                $scope.editRequestStatus = 'Expense record updated successfully.';

                $scope.deleteStatus = '';
                $scope.addRequestStatus = '';
                $scope.errorAdd = '';
                $scope.errorDelete = '';
                $scope.expense = '';
                $scope.editExpenseRecord = '';
                $scope.init();
            }).error(function (response) {
                console.log("Error : " + response.data);
                $scope.errorAdd = 'Error occurred : ' + response.data;
                $scope.init();
            });

        };

        $scope.getMonthlyExpenses = function (month, year) {
            console.log("Request to get monthly expenses for : " + month + "/" + year);
            $http({
                method: 'POST',
                dataType: 'json',

            });
        };
    });

//$scope.editRequestStatus = 'Expense record updated successfully.';