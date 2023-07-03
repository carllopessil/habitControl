package servlet;

import dao.TaskDAO;
import model.Task;
import servlet.config.ConnectionPoolConfig;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class TaskServlet extends HttpServlet {

    private DataSource dataSource;
    private TaskDAO taskDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            dataSource = ConnectionPoolConfig.getDataSource();
            Connection connection = dataSource.getConnection();
            taskDAO = new TaskDAO(connection);
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

    @Override
    public void destroy() {
        super.destroy();
        ConnectionPoolConfig.closeDataSource();
    }
}
