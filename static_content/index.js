import {parseUrl, renderization} from "./utils/renderization.js"
import { Consistent } from "./handlers/consistent.js"
import "./routes.js" // Registra todas as rotas

window.addEventListener('load', hashChangeHandler)
window.addEventListener('hashchange', hashChangeHandler)

function hashChangeHandler() {
    const path = window.location.hash.replace("#", "/")
    Consistent(parseUrl(path)).then(renderization)
}