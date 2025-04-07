import {Consistent} from "../utils/consistent";
import {parseUrl, renderization} from "../utils/renderization";

//incompleto falta bue
window.addEventListener('load', hashChangeHandler)
window.addEventListener('hashchange', hashChangeHandler)

function hashChangeHandler() {
    const path = window.location.hash.replace("#", "/")
    Consistent(parseUrl(path)).then(renderization)
}