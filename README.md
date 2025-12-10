# ⚽ FixtureUTN - Sistema de Gestión Deportiva

Aplicación **Fullstack** desarrollada con **Java** y **Spring Boot** para la administración integral de torneos de fútbol. El sistema implementa una arquitectura híbrida que combina un panel de administración web seguro y una API REST pública.

## 🚀 Tecnologías Principales
*   **Backend:** Java 17, Spring Boot 3 (Web, Security).
*   **Frontend:** Thymeleaf, Bootstrap 5.
*   **Seguridad:** Spring Security (Autenticación y Autorización).
*   **Datos:** Persistencia en memoria (Estructuras de datos optimizadas).
*   **Herramientas:** Maven, Git/GitHub, IntelliJ IDEA, Postman.

## ⚙️ Funcionalidades Clave

### 🔒 Panel Admin (Web)
*   **Autenticación:** Login seguro para administradores.
*   **Gestión de Contenidos:** ABM (Alta, Baja, Modificación) de **Jugadores**, **Partidos** y **Noticias**.
*   **Manejo de Imágenes:** Servicio personalizado para subida y almacenamiento de fotos y escudos.

### 📱 API RESTful (Pública)
*   Diseñada para consumo de aplicaciones externas (Móvil/React).
*   Endpoints JSON separados (`/api/fixture/data/...`).
*   Soporte para métodos HTTP completos: GET, POST, PUT, DELETE y PATCH.

## 📂 Estructura del Proyecto
El proyecto sigue el patrón **MVC (Modelo-Vista-Controlador)**:
*   `controller`: Controladores Web y REST separados para mantener el código limpio.
*   `service`: Lógica de negocio centralizada.
*   `model`: Entidades del dominio (Match, Player, News).
*   `security`: Configuración de filtros y permisos de acceso.

## 🛠️ Instalación
1.  Clonar el repositorio: `git clone https://github.com/L-Carranza/fixture-utn.git`
2.  Abrir con **IntelliJ IDEA**.
3.  Ejecutar la clase `FixtureUtnApplication`.
4.  Acceder a `http://localhost:8080/`

---
**Desarrollado por:** Leandro Carranza
*Java Backend Developer en formación.*
