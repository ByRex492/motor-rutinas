// ✅ FUNCIÓN GLOBAL (FUERA)
function showTab(tab) {
  const loginForm = document.getElementById("login-form");
  const registerForm = document.getElementById("register-form");

  const tabLogin = document.getElementById("tab-login");
  const tabRegister = document.getElementById("tab-register");

  if (tab === "login") {
    loginForm.classList.remove("hidden");
    registerForm.classList.add("hidden");

    tabLogin.classList.add("active");
    tabRegister.classList.remove("active");
  } else {
    loginForm.classList.add("hidden");
    registerForm.classList.remove("hidden");

    tabLogin.classList.remove("active");
    tabRegister.classList.add("active");
  }
}

document.addEventListener("DOMContentLoaded", () => {

  // LOGIN
  document.getElementById("login-form").addEventListener("submit", async (e) => {
    e.preventDefault();

    const username = document.getElementById("login-user").value;
    const password = document.getElementById("login-pass").value;

    const res = await fetch("/api/usuarios/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ username, password })
    });

    if (res.ok) {
      const data = await res.json();
      localStorage.setItem("token", data.token);
      alert("Login correcto");
      window.location.href = "/index.html";
    } else {
      alert("Error en login");
    }
  });

  // REGISTER
  document.getElementById("register-form").addEventListener("submit", async (e) => {
    e.preventDefault();

    const name = document.getElementById("reg-user").value;
    const email = document.getElementById("reg-email").value;
    const password = document.getElementById("reg-pass").value;

    const res = await fetch("/api/usuarios/registrar", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ name, email, password })
    });

    if (res.ok) {
      alert("Registrado correctamente");
      showTab("login"); // 🔥 extra: vuelve al login
    } else {
      alert("Error al registrar");
    }
  });
});