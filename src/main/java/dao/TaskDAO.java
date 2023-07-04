package dao;

import model.Task;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {
    private static final String JDBC_URL ="jdbc:h2:~/test";
    private static final String JDBC_USER = "sa";
    private static final String JDBC_PASSWORD = "sa";

    private static final String INSERT_TASK = "INSERT INTO TASKS (title, description, due_date, priority) VALUES (?, ?, ?, ?)";
    private static final String SELECT_ALL_TASKS = "SELECT * FROM TASKS";

    public void addTask(Task task) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TASK)) {

            preparedStatement.setString(1, task.getTitle());
            preparedStatement.setString(2, task.getDescription());
            preparedStatement.setObject(3, task.getDueDate());
            preparedStatement.setInt(4, task.getPriority());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_TASKS)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                LocalDate dueDate = resultSet.getObject("due_date", LocalDate.class);
                int priority = resultSet.getInt("priority");

                Task task = new Task();
                task.setId(id);
                task.setTitle(title);
                task.setDescription(description);
                task.setDueDate(dueDate);
                task.setPriority(priority);

                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tasks;
    }
}

