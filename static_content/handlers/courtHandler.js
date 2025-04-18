import { API_BASE_URL } from "../utils/configs.js"
import {renderException} from "../views/Exeptions.js";
import {fetchCourts} from "../data/rentalData.js";
import {renderCourtsList} from "../views/courtView.js";

export const getCourtsList = async (mainContent, params) => {
    try {
        const clubId = params.cid;
        const courts = await fetchCourts(clubId);
        renderCourtsList(mainContent, courts);
    } catch (error) {
        console.error("Erro ao buscar quadras:", error);
        renderException(mainContent, error);
    }
};

export const getCourtDetails = (mainContent, params) => {
    const courtId = params.crid

    fetch(API_BASE_URL + "courts/" + courtId)
        .then(res => res.json())
        .then(court => {
            const ulStd = document.createElement("ul");

            const courtName = document.createElement("li");
            const textName = document.createTextNode("Name : " + court.name.name);
            courtName.appendChild(textName);

            const courtId = document.createElement("li");
            const textNumber = document.createTextNode("Court Id : " + court.id.id);
            courtId.appendChild(textNumber);

            const clubId = document.createElement("li");
            const clubIdLink = document.createElement("a");
            clubIdLink.href = `${API_BASE_URL}#club/${court.club.id.id}`;
            clubIdLink.textContent = `Club Id: ${court.club.id.id}`;
            clubId.appendChild(clubIdLink);

            const rentalList = document.createElement("li");
            const rentalListLink = document.createElement("a");
            rentalListLink.href = `${API_BASE_URL}#court/rentals/${court.id.id}`;
            rentalListLink.textContent = "Court Rentals List";
            rentalList.appendChild(rentalListLink);

            ulStd.appendChild(courtName);
            ulStd.appendChild(courtId);
            ulStd.appendChild(clubId);
            ulStd.appendChild(rentalList)

            mainContent.replaceChildren(ulStd);
        });
};