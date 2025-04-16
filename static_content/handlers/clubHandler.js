import {API_BASE_URL} from "../utils/configs.js";
import {fetchClubs} from "../data/clubData.js";
import {renderClubs} from "../views/clubView.js";
import {renderException} from "../views/Exeptions.js";


export const getClubs = async (mainContent) => {
    try {
        const clubs = await fetchClubs();
        renderClubs(clubs, mainContent);
    } catch (error) {
        console.error("Erro ao buscar clubes:", error);
        renderException(error,mainContent)
    }
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




