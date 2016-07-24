var app = angular.module("myModule", [])
    .controller("expenseController", function ($scope, $http) {

        $scope.details = '';
        $scope.totalExpenses = 0;

        $scope.getData = function () {
            $http.get("test")
                .then(function (response) {
                    $scope.details = response.data;
                    $scope.totalExpenses = 0;

                    //Calculate total expenses from response
                    angular.forEach($scope.details, function(item){
                        $scope.totalExpenses+=item.amount;
                        console.log(item.amount);
                    });
                });
        };

        $scope.addExpense = function (expense) {
            console.log("Received"+expense.amount+expense.description);

            var expenseDetail = {
                id:0,
                description:expense.description,
                amount:expense.amount,
                created:'',
                updated:'',
            };

            console.log("expense detail : "+JSON.stringify(expenseDetail));

            $http({
                method  : 'POST',
                dataType: 'json',
                url     : 'addExpense1',
                data    : JSON.stringify(expenseDetail),
                headers: {
                    "Content-Type": "application/json"
                }
            }).success(function (response) {
                console.log("Response : ",JSON.stringify(response));
            }).error(function(response){
                console.log("Error : "+response.data);
            });
        };
    });