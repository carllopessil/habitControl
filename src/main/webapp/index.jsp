<%@ page import="java.util.List" %>
<%@ page import="model.Task" %>


<!DOCTYPE html>
<html>
<head>
    <title>Sistema de Gerenciamento de Tarefas</title>
    <meta charset="UTF-8">
    <style>
      body {
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        margin: 0;
        padding: 0;
        display: flex;
        justify-content: center;
        align-items: center;
        flex-direction: column;
        height: 100vh;
        background-color: #f7f7f7;
        transition: background-color 0.3s, color 0.3s;
        color: #333333;
      }

      .theme-dark {
        background-color: #000000;
        color: #ffffff;
      }

      h1 {
        color: #000000;
        margin-top: 0;
        margin-bottom: 20px;
        text-align: center;
        align-self: center;
      }

      .container {
        max-width: 600px;
        padding: 40px;
        border-radius: 10px;
        background-color: #e5efc1;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        transition: background-color 0.3s;
        color: #333333;
        margin: 0 auto;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
      }

      form {
        margin-bottom: 20px;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
      }

      label {
        display: block;
        margin-bottom: 10px;
        font-weight: bold;
        color: #333333;
        text-align: center;
      }

      input[type="text"],
      textarea,
      input[type="date"],
      input[type="number"] {
        width: 100%;
        padding: 10px;
        font-size: 14px;
        border: none;
        border-radius: 5px;
        background-color: #a2d5ab;
        box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
        transition: background-color 0.3s;
        color: #333333;
      }

      input[type="submit"] {
        padding: 10px 15px;
        font-size: 14px;
        background-color: #39aea9;
        color: #ffffff;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        transition: background-color 0.3s;
      }

      input[type="submit"]:hover {
        background-color: #557b83;
      }

      ul {
        list-style-type: none;
        padding: 0;
      }

      li {
        margin-bottom: 10px;
        padding: 10px;
        background-color: #557b83;
        border-radius: 5px;
        box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
        color: #ffffff;
      }

      li strong {
        font-weight: bold;
      }

      .theme-dark {
        background-color: #000000;
        color: #ffffff;
      }

      .theme-dark .container {
        background-color: #e5efc1;
        color: #ffffff;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
      }

      .theme-dark input[type="text"],
      .theme-dark textarea,
      .theme-dark input[type="date"],
      .theme-dark input[type="number"] {
        background-color: #557b83;
        color: #ffffff;
      }
      .theme-toggle {
        position: relative;
        width: 60px;
        height: 30px;
      }

      .toggle-checkbox {
        display: none;
      }

      .toggle-label {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background-color: #ddd;
        border-radius: 15px;
        cursor: pointer;
        transition: background-color 0.3s;
      }

      .toggle-label:before {
        content: '';
        position: absolute;
        top: 2px;
        left: 2px;
        width: 26px;
        height: 26px;
        background-color: #fff;
        border-radius: 50%;
        transition: transform 0.3s;
      }

      .toggle-checkbox:checked + .toggle-label {
        background-color: #a2d5ab;
      }

      .toggle-checkbox:checked + .toggle-label:before {
        transform: translateX(30px);
      }

      .theme-dark label,
      .theme-dark h1 {
        color: #a2d5ab;
      }
      .theme-toggle {
        display: flex;
        justify-content: center;
        margin-bottom: 20px;
      }

      .theme-toggle label {
        font-size: 14px;
        color: #333333;
      }

      .theme-toggle input[type="checkbox"] {
        margin-right: 5px;
      }

      .theme-dark input[type="submit"] {
        background-color: #39aea9;
      }

      .theme-dark ul li {
        background-color: #557b83;
        color: #ffffff;
      }

      .theme-dark ul li strong {
        color: #ffffff;
      }

      @media (max-width: 600px) {
        .container {
          padding: 20px;
        }
      }
    </style>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body>
<h1>Sistema de Gerenciamento de Tarefas</h1>
<div class="theme-toggle">
    <input type="checkbox" id="themeToggle" onchange="toggleTheme()" class="toggle-checkbox">
    <label for="themeToggle" class="toggle-label"></label>
</div>
<form action="tasks" method="post">
    <label for="title">Título:</label>
    <input type="text" id="title" name="title" required>
    <br>
    <label for="description">Descrição:</label>
    <textarea id="description" name="description" required></textarea>
    <br>
    <label for="dueDate">Data de Vencimento:</label>
    <input type="date" id="dueDate" name="dueDate" required>
    <br>
    <label for="priority">Prioridade:</label>
    <input type="number" id="priority" name="priority" min="1" max="5" required>
    <br>
    <input type="submit" value="Adicionar Tarefa">
</form>
<h2>Tarefas:</h2>

<script>
  function setTheme(theme) {
    const body = document.querySelector('body');
    const containers = document.querySelectorAll('.container');

    if (theme === 'dark') {
      body.classList.add('theme-dark');
      containers.forEach(container => container.classList.add('theme-dark'));
      document.getElementById('themeToggle').checked = true;
    } else {
      body.classList.remove('theme-dark');
      containers.forEach(container => container.classList.remove('theme-dark'));
      document.getElementById('themeToggle').checked = false;
    }
  }

  function toggleTheme() {
    const body = document.querySelector('body');
    const currentTheme = body.classList.contains('theme-dark') ? 'light' : 'dark';
    setTheme(currentTheme);
  }

  // Verifica a preferência de tema do usuário no navegador
  const userPrefersDark = window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches;

  // Define o tema com base na preferência do usuário ou tema claro por padrão
  setTheme(userPrefersDark ? 'dark' : 'light');
</script>

</body>
</html>
