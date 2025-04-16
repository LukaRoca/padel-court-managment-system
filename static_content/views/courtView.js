import {createElement} from "../html/DSL.js";
import {API_BASE_URL} from "../utils/configs.js";

export const renderCourts = (courts, mainContent) => {
    const courtsList = courts.map(court => {
        const linkElement = createElement("a", [`ID: ${court.id.id}`], { href: `${API_BASE_URL}#court/${court.id.id}` });

        const attributesList = createElement("ul", [
            linkElement,
            createElement("li", [`Club Id: ${court.club.id.id}`]),
            createElement("li", [`Club Owner: ${court.club.owner.user.name.name}`]),
            createElement("li", [`Club Owner Email: ${court.club.owner.user.email.value}`])
        ]);

        return createElement("li", [`Court: ${court.name.name}`, attributesList]);
    });

    const courtsListElement = createElement("ul", courtsList);

    const container = createElement("div", [
        createElement("h1", ["Courts"]),
        courtsListElement
    ]);

    mainContent.innerHTML = '';
    mainContent.appendChild(container);
};

export const renderCourtDetails = (court, mainContent) => {
    const linkElement = createElement("a", [`ID: ${court.id.id}`], { href: `${API_BASE_URL}#court/${court.id.id}` });

    const attributesList = createElement("ul", [
        linkElement,
        createElement("li", [`Club Id: ${court.club.id.id}`]),
        createElement("li", [`Club Owner: ${court.club.owner.user.name.name}`]),
        createElement("li", [`Club Owner Email: ${court.club.owner.user.email.value}`])
    ]);

    const container = createElement("div", [
        createElement("h1", ["Court Details"]),
        attributesList
    ]);

    mainContent.innerHTML = '';
    mainContent.appendChild(container);
};