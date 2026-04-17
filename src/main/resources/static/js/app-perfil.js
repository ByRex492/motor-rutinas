const dias = ["Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"];
let diaSeleccionado = "";

document.addEventListener("DOMContentLoaded", async () => {
    const token = localStorage.getItem("token");
    if (!token) {
        window.location.href = "/acceso.html";
        return;
    }

    // 1. Cargar nombre de usuario
    try {
        const resUser = await fetch("/api/perfil/me", { headers: { "Authorization": `Bearer ${token}` } });
        if (resUser.ok) {
            const user = await resUser.json();
            document.getElementById("perfil-nombre").innerText = `Atleta: ${user.username}`;
        }
    } catch (e) { console.error(e); }

    // 2. Preparar el HTML y cargar datos
    renderizarSemana();
    cargarEjerciciosParaSelect();
    cargarRutinaGuardada();

    // 3. Botón de Cerrar Sesión
    document.getElementById("btn-logout").addEventListener("click", () => {
        localStorage.removeItem("token");
        window.location.href = "/acceso.html";
    });
});

function renderizarSemana() {
    const container = document.getElementById("semana-container");
    container.innerHTML = dias.map(dia => `
        <div class="glass-card p-4 flex flex-col min-h-[250px]">
            <h4 class="text-sm font-bold text-cyan-400 uppercase tracking-widest mb-3 text-center border-b border-slate-700 pb-2">${dia}</h4>
            <div id="lista-${dia}" class="flex-1 space-y-2 mb-4 overflow-y-auto">
                <p class="text-[11px] text-slate-500 italic text-center mt-4">Descanso</p>
            </div>
            <button onclick="abrirModal('${dia}')" class="w-full bg-slate-800 hover:bg-cyan-900 text-cyan-400 border border-slate-700 rounded py-1 text-sm transition-colors">+ Añadir</button>
        </div>
    `).join("");
}

async function cargarEjerciciosParaSelect() {
    try {
        const res = await fetch("/api/perfil/ejercicios", {
            headers: { "Authorization": `Bearer ${localStorage.getItem("token")}` }
        });
        if (res.ok) {
            const ejercicios = await res.json();
            const select = document.getElementById("select-ejercicio");
            select.innerHTML = ejercicios.map(ej => `<option value="${ej.nombre}">${ej.nombre}</option>`).join("");
        }
    } catch (e) { console.error(e); }
}

async function cargarRutinaGuardada() {
    try {
        const res = await fetch("/api/perfil/rutina", {
            headers: { "Authorization": `Bearer ${localStorage.getItem("token")}` }
        });
        if (res.ok) {
            const rutina = await res.json();
            
            // Limpiar listas primero
            dias.forEach(dia => document.getElementById(`lista-${dia}`).innerHTML = "");

            // Pintar los ejercicios guardados
            rutina.forEach(item => {
                const lista = document.getElementById(`lista-${item.diaSemana}`);
                if(lista) {
                    lista.innerHTML += `
                        <div class="bg-slate-900/50 p-2 rounded border border-slate-700/50">
                            <p class="text-xs font-bold text-white">${item.nombreEjercicio}</p>
                            <p class="text-[10px] text-cyan-400">${item.series} series x ${item.repeticiones} reps</p>
                        </div>
                    `;
                }
            });
        }
    } catch (e) { console.error(e); }
}

function abrirModal(dia) {
    diaSeleccionado = dia;
    document.getElementById("modal-titulo").innerText = `Añadir a ${dia}`;
    document.getElementById("modal-ejercicio").classList.remove("hidden");
}

function cerrarModal() {
    document.getElementById("modal-ejercicio").classList.add("hidden");
}

document.getElementById("btn-guardar-item").addEventListener("click", async () => {
    const ejercicio = document.getElementById("select-ejercicio").value;
    const series = document.getElementById("input-series").value;
    const reps = document.getElementById("input-reps").value;
    
    const res = await fetch("/api/perfil/rutina", {
        method: "POST",
        headers: { 
            "Content-Type": "application/json",
            "Authorization": `Bearer ${localStorage.getItem("token")}` 
        },
        body: JSON.stringify({ diaSemana: diaSeleccionado, nombreEjercicio: ejercicio, series, repeticiones: reps })
    });

    if (res.ok) {
        cerrarModal();
        cargarRutinaGuardada();
    }
});