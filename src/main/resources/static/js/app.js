// ✅ FUNCIÓN GLOBAL (FUERA)
function showTab(tab) {
  const loginForm = document.getElementById("login-form");
  const registerForm = document.getElementById("register-form");
  const tabLogin = document.getElementById("tab-login");
  const tabRegister = document.getElementById("tab-register");

  // Si estos elementos no existen (por ejemplo, estamos en index.html), salimos de la función
  if (!loginForm || !registerForm) return;

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

// ✅ SE EJECUTA CUANDO CARGA LA PÁGINA
document.addEventListener("DOMContentLoaded", () => {

  // ==========================================
  // 1. LÓGICA DE LOGIN Y REGISTRO (Para acceso.html)
  // ==========================================
  
  const loginForm = document.getElementById("login-form");
  if (loginForm) {
    loginForm.addEventListener("submit", async (e) => {
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
        localStorage.setItem("token", data.token); // Guardamos el token
        window.location.href = "/index.html"; // Redirigimos a la app
      } else {
        alert("Error en login. Verifica tus credenciales.");
      }
    });
  }

  const registerForm = document.getElementById("register-form");
  if (registerForm) {
    registerForm.addEventListener("submit", async (e) => {
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
        alert("Registrado correctamente. Ahora puedes iniciar sesión.");
        showTab("login"); 
      } else {
        alert("Error al registrar el usuario.");
      }
    });
  }

  // ==========================================
  // 2. LÓGICA DE LA APLICACIÓN (Para index.html)
  // ==========================================

  const btnGenerar = document.getElementById("btnGenerar");
  const btnPdf = document.getElementById("btnPdf");
  const resultContainer = document.getElementById("rutinaResult");

  if (btnGenerar) {
    btnGenerar.addEventListener("click", async () => {
      // ✅ 1. AHORA SÍ RECOGEMOS TODOS LOS DATOS
      const equipo = document.getElementById("equipo").value;
      const peso = document.getElementById("peso").value;
      const altura = document.getElementById("altura").value;
      const genero = document.getElementById("genero").value;
      
      // Intentamos recoger la edad (si el input existe en el HTML), si no, por defecto 25
      const edadInput = document.getElementById("edad");
      const edad = edadInput && edadInput.value ? edadInput.value : 25;

      const token = localStorage.getItem("token");
      if (!token) {
        alert("Debes iniciar sesión para generar una rutina.");
        window.location.href = "/acceso.html"; 
        return;
      }

      resultContainer.innerHTML = `<p class="loading-text">Generando tu rutina con IA... 🏋️‍♂️</p>`;

      try {
        const res = await fetch("/api/rutinas/generar", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}` 
          },
          // ✅ 2. ENVIAMOS TODOS LOS PARÁMETROS, INCLUIDA LA EDAD
          body: JSON.stringify({ equipo, peso, altura, genero, edad })
        });

        if (res.ok) {
          if (res.status === 204) {
             resultContainer.innerHTML = `<p class="text-yellow-400 text-center">No hay ejercicios para estos parámetros.</p>`;
             return; 
          }
          
          const rutina = await res.json();
          pintarRutina(rutina); 

        } else if (res.status === 403) {
          alert("Tu sesión ha caducado o no tienes permiso. Vuelve a iniciar sesión.");
          localStorage.removeItem("token"); 
          window.location.href = "/acceso.html"; 
        } else {
          resultContainer.innerHTML = `<p class="error-text">Error del servidor: ${res.status}</p>`;
        }
      } catch (err) {
        console.error("Error exacto del fetch:", err);
        resultContainer.innerHTML = `<p class="error-text">Error de conexión con el servidor.</p>`;
      }
    });
  }

  if (btnPdf) {
    btnPdf.addEventListener("click", async () => {
       const token = localStorage.getItem("token");
       if (!token) {
           alert("Inicia sesión primero.");
           window.location.href = "/acceso.html"; 
           return;
       }

       // ✅ 3. PARA EL PDF TAMBIÉN NECESITAMOS ENVIAR TODOS LOS DATOS
       const equipo = document.getElementById("equipo").value;
       const peso = document.getElementById("peso").value;
       const altura = document.getElementById("altura").value;
       const genero = document.getElementById("genero").value;
       const edadInput = document.getElementById("edad");
       const edad = edadInput && edadInput.value ? edadInput.value : 25;

       try {
           const res = await fetch("/api/rutinas/pdf", {
               method: "POST",
               headers: { 
                   "Content-Type": "application/json",
                   "Authorization": `Bearer ${token}` 
               },
               // ✅ 4. SE LO MANDAMOS TODO A JAVA
               body: JSON.stringify({ equipo, peso, altura, genero, edad })
           });

           if (res.ok) {
               const blob = await res.blob();
               const url = window.URL.createObjectURL(blob);
               const a = document.createElement("a");
               a.href = url;
               a.download = "Mi_Rutina_PROGEN.pdf";
               a.click();
               window.URL.revokeObjectURL(url);
           } else {
               alert("Error al generar el PDF.");
           }
       } catch (err) {
           console.error("Error al descargar PDF:", err);
           alert("Error de conexión.");
       }
    });
  }

  if (btnPdf) {
    btnPdf.addEventListener("click", async () => {
       const token = localStorage.getItem("token");
       if (!token) {
           alert("Inicia sesión primero.");
           window.location.href = "/acceso.html"; // ✅ Añadida redirección aquí también
           return;
       }

       const equipo = document.getElementById("equipo").value;

       try {
           const res = await fetch("/api/rutinas/pdf", {
               method: "POST",
               headers: { 
                   "Content-Type": "application/json",
                   "Authorization": `Bearer ${token}` 
               },
               body: JSON.stringify({ equipo: equipo })
           });

           if (res.ok) {
               const blob = await res.blob();
               const url = window.URL.createObjectURL(blob);
               const a = document.createElement("a");
               a.href = url;
               a.download = "rutina.pdf";
               a.click();
               window.URL.revokeObjectURL(url);
           } else {
               alert("Error al generar el PDF.");
           }
       } catch (err) {
           console.error("Error al descargar PDF:", err);
           alert("Error de conexión.");
       }
    });
  }
});
// --- FUNCIÓN PARA DIBUJAR LAS TARJETAS (PINTAR EL HTML) ---
function pintarRutina(rutinaDatos) {
    // Buscamos el contenedor donde vamos a pintar
    const resultContainer = document.getElementById("rutinaResult");
    if (!resultContainer) return;
    
    resultContainer.innerHTML = ""; // Limpiamos el texto de carga

    // Iteramos sobre el diccionario que nos manda Java (Ej: "Día 1": [ejercicios...])
    for (const [nombreDia, listaEjercicios] of Object.entries(rutinaDatos)) {
        
        const card = document.createElement("div");
        card.className = "day-card";
        
        let htmlEjercicios = `<h3>${nombreDia}</h3>`;
        
        listaEjercicios.forEach(ejercicio => {
            htmlEjercicios += `
                <div class="exercise-item">
                    <p class="exercise-name">💪 ${ejercicio.nombre}</p>
                    <p class="exercise-detail">Series: ${ejercicio.series} | Reps: ${ejercicio.repeticiones}</p>
                    <p class="exercise-detail italic text-gray-400" style="margin-top: 4px;">${ejercicio.descripcion}</p>
                </div>
                <hr style="border-color: rgba(255,255,255,0.08); margin: 12px 0;">
            `;
        });

        card.innerHTML = htmlEjercicios;
        resultContainer.appendChild(card);
        
    }
}