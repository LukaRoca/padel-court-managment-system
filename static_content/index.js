import {parseUrl, renderization} from "./utils/renderization.js"
import { Consistent } from "./utils/consistent.js"


window.addEventListener('load', hashChangeHandler)
window.addEventListener('hashchange', hashChangeHandler)

function hashChangeHandler() {
    const path = window.location.hash.replace("#", "/")
    Consistent(parseUrl(path)).then(renderization)
}