import {Consistent} from "./consistent";
import {parseUrl, renderization} from "../utils/renderization";


window.addEventListener('load', hashChangeHandler)
window.addEventListener('hashchange', hashChangeHandler)

function hashChangeHandler() {
    const path = window.location.hash.replace("#", "/")
    Consistent(parseUrl(path)).then(renderization)
}