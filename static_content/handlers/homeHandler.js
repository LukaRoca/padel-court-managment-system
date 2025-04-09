import {API_BASE_URL} from "../utils/configs.js";

export const getHome = (mainContent) => {
    const h1 = document.createElement("h1")
    const text = document.createTextNode("Home")
    h1.appendChild(text)
    mainContent.replaceChildren(h1)
}