package servlet;

import dao.TaskDAO;
import model.Task;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskServlet extends HttpServlet {
    private TaskDAO taskDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            // Configura a conexão com o banco de dados H2
            Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
            taskDAO = new TaskDAO(connection);
        } catch (SQLException e) {
            throw new ServletException("Failed to initialize TaskServlet", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        String dueDateString = req.getParameter("dueDate");
        String priorityString = req.getParameter("priority");

        // Converte a data e prioridade para os tipos corretos
        Date dueDate;
        int priority;

        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dueDate = dateFormat.parse(dueDateString);
            priority = Integer.parseInt(priorityString);
        } catch (ParseException | NumberFormatException e) {
            // Lida com erros de conversão
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Task task = new Task(title, description, dueDate, priority);
        taskDAO.addTask(task);

        // Atualiza a lista de tarefas no atributo da requisição
        req.setAttribute("taskList", taskDAO.getAllTasks());

        // Redireciona de volta para a página principal após a adição da tarefa
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}
