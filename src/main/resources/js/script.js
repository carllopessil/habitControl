function setTheme(theme) {
  const body = document.querySelector('body');
  const containers = document.querySelectorAll('.container');

  if (theme === 'dark') {
    body.classList.add('theme-dark');
    containers.forEach(container => container.classList.add('theme-dark'));
  } else {
    body.classList.remove('theme-dark');
    containers.forEach(container => container.classList.remove('theme-dark'));
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
