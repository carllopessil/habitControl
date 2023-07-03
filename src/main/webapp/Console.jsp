<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Habit Control Console</title>
</head>
<body>
<h1>Habit Control Console</h1>
<table>
    <tr>
        <th>Title</th>
        <th>Description</th>
    </tr>
    <%
        List<Task> tasks = taskDAO.getAllTasks();
        for (Task task : tasks) {
            String title = task.getTitle();
            String description = task.getDescription();
    %>
    <tr>
        <td><%= title %></td>
        <td><%= description %></td>
    </tr>
    <%
        }
    %>
</table>
</body>
</html>
