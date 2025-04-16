import {createElement} from "../html/DSL.js";
import {API_BASE_URL} from "../utils/configs.js";

export const renderClubs = (clubs, mainContent) => {
    const clubsList = clubs.map(club => {
        // Criar o link com texto corretamente
        const linkElement = createElement("a", [`ID: ${club.id.id}`], { href: `${API_BASE_URL}#club/${club.id.id}` });

        const attributesList = createElement("ul", [
            linkElement,
            createElement("li", [`Owner: ${club.owner.user.name.name}`]),
            createElement("li", [`Email: ${club.owner.user.email.value}`])
        ]);

        return createElement("li", [`Clube: ${club.name.name}`, attributesList]);
    });

    const clubsListElement = createElement("ul", clubsList);

    const container = createElement("div", [
        createElement("h1", ["Clubs"]),
        clubsListElement
    ]);

    mainContent.innerHTML = '';
    mainContent.appendChild(container);
};