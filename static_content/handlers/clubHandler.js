import {API_BASE_URL} from "../utils/configs.js";
import {createElement} from "../html/DSL";


export const getClubs = (mainContent) => {
    fetch(API_BASE_URL + "clubs")
        .then(res => res.json())
        .then(clubs => {
            const clubsList = clubs.map(club => {
                const attributesList = createElement("ul", [
                    createElement("a", [], { href: `${API_BASE_URL}#club/${club.id.id}` }).replace("</a>", `ID: ${club.id.id}</a>`),
                    createElement("li", [`Owner: ${club.owner.user.name.name}`]),
                    createElement("li", [`Email: ${club.owner.user.email.value}`])
                ]);

                return createElement("li", [`Clube: ${club.name.name}`, attributesList]);
            }).join("");

            mainContent.innerHTML = createElement("div", [
                createElement("h1", ["Clubs"]),
                createElement("ul", [clubsList])
            ]);
        });
};

export const getClubById = (mainContent, params) => {
    const clubId = params.id;
    fetch(API_BASE_URL + "clubs/" + clubId)
        .then(res => res.json())
        .then(club => {

            const court = document.createElement("div");

            const courtsLink = document.createElement("a");
            courtsLink.href = `${API_BASE_URL}#courts/${club.id.id}`;
            courtsLink.textContent = "CourtsList";

            court.appendChild(courtsLink);

            // Conteúdo principal
            const ulStd = document.createElement("ul");

            const clubName = document.createElement("li");
            clubName.textContent = "Club Name : " + club.name.name;

            const liNumber = document.createElement("li");
            liNumber.textContent = "Club Id: " + club.id.id;

            const ownerclubId = document.createElement("a");
            ownerclubId.href = `${API_BASE_URL}#user/${club.owner.user.uid.id}`;
            ownerclubId.textContent = `Owner: ${club.owner.user.name.name}`;

            ulStd.appendChild(clubName);
            ulStd.appendChild(liNumber);
            ulStd.appendChild(ownerclubId);

            // Agrupar tudo
            const container = document.createElement("div");
            container.appendChild(court);
            container.appendChild(ulStd);

            mainContent.replaceChildren(container);
        });
};




