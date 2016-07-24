<%--
  Created by IntelliJ IDEA.
  User: nakulkumar
  Date: 24/6/16
  Time: 5:40 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Expense Tracker</title>
</head>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.7/angular.min.js"></script>
<link href="/css/bootstrap.css" rel="stylesheet">
<script src="/js/controller.js"></script>

<style>
    #container {
        padding: 2%;
    }
</style>
<body>
<div id="container">
    <%@ include file="header.jsp" %>
    <table class="table table-hover">
        <tr>
            <th>Description</th>
            <th>Amount</th>
            <th></th>
            <th></th>
        </tr>
        <c:forEach var="expense" items="${expenseDetails}">
            <tr>
                <td id="expense_description"><c:out value="${expense.description}"/></td>
                <td id="expense_amount  "><c:out value="${expense.amount}"/></td>
                <td><a href="<c:url value='/editExpense/${expense.id}'/>">Edit</a></td>
                <td><a href="<c:url value='/delete/${expense.id}'/>">Delete</a></td>
            </tr>
        </c:forEach>
        <tr>
            <th>Total</th>
            <th>${totalExpenses}</th>
            <th></th>
            <th></th>
        </tr>
    </table>
    <br>
    <c:if test="${deleted}">
        <div id="deleteMessage" style="color: red">Deletion successful!</div>
    </c:if>
    <c:if test="${isUpdated}">
        <div id="updateMessage" style="color:#4cee2d;">Expense Detail updated successfully!</div>
    </c:if>

    <%--Edit Expense Detail Form--%>
    <c:if test="${not empty expenseDetailToEdit}">
        <form class="form-inline" method="post" action="/editExpenseDetail">
            <input type="hidden" name="id" value="${expenseDetailToEdit.id}">
            <input type="text" name="description" value="${expenseDetailToEdit.description}" required>
            <input type="number" name="amount" step="0.01" min="0" value="${expenseDetailToEdit.amount}" required>
            <input type="hidden" name="created" value="${expenseDetailToEdit.created}">
            <input type="submit" value="Edit" class="btn btn-info">
        </form>
    </c:if>

    <%--Add new expense--%>
    <form class="form-inline" method="post" action="/addExpense">
        <input type="text" name="description" placeholder="Description" required>
        <input type="number" name="amount" step="0.01" min="0" placeholder="Amount" required>
        <c:if test="${not empty expenseDetails}">
            <input type="hidden" name="created" value="${expenseDetails[0].created}">
        </c:if>
        <input type="submit" value="Add New Expense" class="btn btn-info">
    </form>

    <%--Show expenses for day--%>
    <form class="form-inline" action="getExpenses" method="post">
        Get Expense Details :
        <input type="date" name="date">
        <input type="submit" value="Get Details" class="btn btn-info">
    </form>

    <div id="exportDailyReport">
        <form class="form-inline" action="exportToXLS" method="post">
            <label id="report_label">Mail Report for the day : </label>
            <input type="date" name="date">
            <input type="submit" value="Generate Report" class="btn btn-info">
        </form>
    </div>

    <h3>To be done : monthly expenses</h3>

    <form action="/getMonthlyExpenses" method="post">
        <select name="month" required>
            <option value='0'>--Select Month--</option>
            <option selected value='01'>Janaury</option>
            <option value='02'>February</option>
            <option value='03'>March</option>
            <option value='04'>April</option>
            <option value='05'>May</option>
            <option value='06'>June</option>
            <option value='07'>July</option>
            <option value='08'>August</option>
            <option value='09'>September</option>
            <option value='10'>October</option>
            <option value='11'>November</option>
            <option value='12'>December</option>
        </select>
        <select id="year" name="year" required>
            <script>
                var myDate = new Date();
                var year = myDate.getFullYear();
                for (var i = 2012; i < year + 1; i++) {
                    document.write('<option value="' + i + '">' + i + '</option>');
                }
            </script>
        </select>
        <input type="submit" class="btn btn-info">
    </form>
</div>
</body>
</html>
