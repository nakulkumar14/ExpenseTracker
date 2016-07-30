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
        #error{
            color: red;
            font-weight: bold;
        }
        #successMessage,#deleteMessage{
            color: green;
            font-weight: bold;
        }
    </style>
</head>
<body ng-controller="expenseController" ng-init="init()">
    <div id="container">
        <h2>Expense Tracker</h2>
        <hr>
        <table class="table table-hover" id="expenseDetailTable">
            <thead>
                <th>Description</th>
                <th>Amount</th>
                <th>Operations</th>
            </thead>
            <tr ng-repeat="detail in expenseDetails">
                <td>{{detail.description}}</td>
                <td>{{detail.amount}}</td>
                <td><button type="button" class="btn btn-info" ng-click="editExpense(detail.id)">Edit</button>
                    <button type="button" class="btn btn-info" ng-click="deleteExpense(detail.id)">Delete</button>
                </td>
            </tr>
            <tr>
                <th>Total Expenses</th>
                <th>{{totalExpenses | number:2}}</th>
            </tr>
        </table>
        <div>
            <p id="error">{{errorDelete}}</p>
            <p id="deleteMessage">{{deleteStatus}}</p>
        </div>

        <br>
        <div id="addExpenseContainer">
            <form class="form-inline" ng-submit="addExpense(expense)">
                <table>
                    <thead>
                        <th>Description</th>
                        <th>Amount</th>
                    </thead>
                    <tr>
                        <td><input type="text" name="description" ng-model="expense.description" placeholder="Description" required></td>
                        <td><input type="number" name="amount" ng-model="expense.amount" step="0.01" min="0" placeholder="Amount" required></td>
                        <td><input type="hidden" name="created" ng-model="expense.created"></td>
                        <td><input type="hidden" name="updated" ng-model="expense.updated"></td>
                    </tr>
                    <tr>
                        <td><input class="btn btn-info" type="submit" value="Add Expense"></td>
                    </tr>
                </table>
                <div>
                    <h4 id="error">{{errorAdd}}</h4>
                    <h4 id="successMessage">{{addRequestStatus}}</h4>
                </div>
            </form>
        </div>

        <div id="editExpenseContainer">
            <form class="form-inline" ng-submit="updateExpenseDetail(editExpenseRecord)">
                <table>
                    <thead>
                    <th>Description</th>
                    <th>Amount</th>
                    </thead>
                    <tr>
                        <td><input type="text" name="description" ng-model="editExpenseRecord.description" placeholder="Description" required></td>
                        <td><input type="number" name="amount" ng-model="editExpenseRecord.amount" step="0.01" min="0" placeholder="Amount" required></td>
                        <td><input type="hidden" name="created" ng-model="editExpenseRecord.created"></td>
                        <td><input type="hidden" name="updated" ng-model="editExpenseRecord.updated"></td>
                    </tr>
                    <tr>
                        <td><input class="btn btn-info" type="submit" value="Edit Expense"></td>
                    </tr>
                </table>
                <div>
                    <h4 id="error">{{errorEdit}}</h4>
                    <h4 id="successMessage">{{editRequestStatus}}</h4>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
