<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html ng-app="myModule">
<head>
    <title>Expense Tracker</title>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.7/angular.min.js"></script>
    <link href="/css/bootstrap.css" rel="stylesheet">
    <script src="/js/controller.js"></script>
    <style>
        #container {
            padding: 2%;
        }
    </style>
</head>
<body ng-controller="expenseController">
    <div id="container">
        <input type="button" ng-click="getData()" value="Get Data"/>
        <br>
        <br>
        <table class="table table-hover">
            <thead>
                <th>Description</th>
                <th>Amount</th>
                <th>Operations</th>
            </thead>
            <tr ng-repeat="detail in details">
                <td>{{detail.description}}</td>
                <td>{{detail.amount}}</td>
                <td><button type="button" class="btn btn-info">Edit</button>
                    <button type="button" class="btn btn-info" ng-click="deleteExpense(detail.id)">Delete</button>
                </td>
            </tr>
            <tr>
                <th>Total Expenses</th>
                <th>{{totalExpenses | number:2}}</th>
            </tr>
        </table>
        <br>
        <div id="addExpenseContainer">
            <form class="form-inline" ng-submit="addExpense(expense)">
                <input type="text" name="description" ng-model="expense.description" placeholder="Description" required>
                <input type="number" name="amount" ng-model="expense.amount" step="0.01" min="0" placeholder="Amount" required>
                <input class="btn btn-info" type="submit" value="Submit">
            </form>
        </div>
    </div>
</body>
</html>
