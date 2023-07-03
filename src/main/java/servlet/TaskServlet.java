package servlet;

import dao.TaskDAO;
import model.Task;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import servlet.config.ConnectionPoolConfig;

public class TaskServlet extends HttpServlet {
    private DataSource dataSource;
    private TaskDAO taskDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Connection connection = ConnectionPoolConfig.getConnection();
            taskDAO = new TaskDAO(connection);
            dataSource = ConnectionPoolConfig.getDataSource();
        } catch (SQLException e) {
            throw new ServletException("Failed to initialize TaskServlet", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");

        Task task = new Task(title, description, null, 0);
        taskDAO.addTask(task);

        response.sendRedirect("console.jsp");
    }
}
