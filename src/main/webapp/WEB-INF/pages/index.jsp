<%--
  Created by IntelliJ IDEA.
  User: nakulkumar
  Date: 24/6/16
  Time: 5:40 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Expense Tracker</title>
</head>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.7/angular.min.js"></script>

<body>
<%@ include file="header.jsp" %>
${expenseDetails}
<br>
Add
<table>
    <thead>
    <th>Description</th>
    <th>Amount</th>
    </thead>
    <form method="post" action="/addExpense">
        <tr>
            <td><input type="text" name="description" required></td>
            <td><input type="number" name="amount" step="0.01" min="0" required></td>
        </tr>
        <tr>
            <td><input type="submit" value="Add"></td>
        </tr>
    </form>
</table>

<br>

<form action="getExpenses" method="post">
    Get Expense Details for Day :
    <input type="date" name="date">
    <input type="submit" value="Get Details">
</form>

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
    <input type="submit">
</form>

<a href="/getAll">Get all</a>

<form method="post" action="/deleteByDescription">
    Description [delete] : <input type="text" name="description" required>
    <input type="submit">
</form>

</body>
</html>
