package servlet;

import model.Task;
import dao.TaskDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/teste")
public class TaskServlet extends HttpServlet {
    private TaskDAO taskDAO;

    public void init() {
        taskDAO = new TaskDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        LocalDate dueDate = LocalDate.parse(request.getParameter("dueDate"));
        int priority = Integer.parseInt(request.getParameter("priority"));

        Task task = new Task(title, description, dueDate, priority);
        taskDAO.addTask(task);

        List<Task> tasks = taskDAO.getAllTasks();
        request.setAttribute("tasks", tasks);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
