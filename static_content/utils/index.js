import router from "./router.js";
import setupRoutes from "./routes.js";
import {renderHeader} from "../views/header.js";

window.addEventListener('load', loadHandler)
window.addEventListener('hashchange', hashChangeHandler)

async function loadHandler() {
    setupRoutes(router);
    const headerContainer = document.getElementById("header");

    if (!headerContainer) {
        console.error("Elemento header não encontrado");
        return;
    }

    try {
        const header = await renderHeader();
        headerContainer.appendChild(header);
    } catch (error) {
        console.error("Erro ao renderizar o header:", error);
    }

    hashChangeHandler();
}

function hashChangeHandler() {
    const mainContent = document.getElementById("home")
    const path = window.location.hash.replace("#", "")


    const handler = router.getRouteHandler(path)

    mainContent.innerHTML = ''


    handler(mainContent)
}