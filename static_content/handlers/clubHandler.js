import {API_BASE_URL} from "../utils/configs.js";

export const getClubs = (mainContent) => {
    fetch(API_BASE_URL + "clubs")
        .then(res => res.json())
        .then(clubs => {
            const div = document.createElement("div");

            const h1 = document.createElement("h1");
            const text = document.createTextNode("Clubs")
            h1.appendChild(text)
            div.appendChild(h1);

            const clubsList = document.createElement("ul");

            clubs.forEach(club => {
                const clubItem = document.createElement("li");
                clubItem.textContent = `Clube: ${club.name.name}`;

                const attributesList = document.createElement("ul");

                const idItem = document.createElement("a");
                idItem.href = `${API_BASE_URL}#club/${club.id.id}`;
                idItem.textContent = `ID: ${club.id.id}`;
                attributesList.appendChild(idItem);

                const ownerItem = document.createElement("li");
                ownerItem.textContent = `Owner: ${club.owner.user.name.name}`;
                attributesList.appendChild(ownerItem);

                const emailItem = document.createElement("li");
                emailItem.textContent = `Email: ${club.owner.user.email.value}`;
                attributesList.appendChild(emailItem);

                clubItem.appendChild(attributesList);
                clubsList.appendChild(clubItem);
            });

            div.appendChild(clubsList);

            mainContent.replaceChildren(div);
        });
}

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

