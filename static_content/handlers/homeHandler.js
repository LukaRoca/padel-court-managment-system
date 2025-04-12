import {API_BASE_URL} from "../utils/configs.js";

export const getHome = (mainContent) => {
    const div = document.createElement("div" )

    const h1 = document.createElement("h1")
    const text = document.createTextNode("Home")
    h1.appendChild(text)

    const clubList = document.createElement("a")
    clubList.href = `${API_BASE_URL}#clubs`;
    clubList.textContent = `Club List`

    div.appendChild(clubList)
    div.appendChild(h1)

    mainContent.replaceChildren(div)
}