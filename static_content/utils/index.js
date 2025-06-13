import router from "./router.js";
import setupRoutes from "./routes.js";

window.addEventListener('load', loadHandler) // Esta linha vai servir para carregar a página inicial
window.addEventListener('hashchange', hashChangeHandler) // Esta linha vai servir para mostrar cada página do servidor

function loadHandler() {
    setupRoutes(router)
    hashChangeHandler()
}

function hashChangeHandler() {

    const mainContent = document.getElementById("home")
    const path = window.location.hash.replace("#", "")

    const handler = router.getRouteHandler(path)
    handler(mainContent)
}