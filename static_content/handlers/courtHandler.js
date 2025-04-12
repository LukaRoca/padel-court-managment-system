import { API_BASE_URL } from "../utils/configs.js"

export const getCourtsList = (mainContent,params) => {
    const clubId = params.cid
    fetch(API_BASE_URL + "clubs/" + clubId + "/courts")
        .then(res => res.json())
        .then(courts => {
            const div = document.createElement("div")
            const h1 = document.createElement("h1")
            const text = document.createTextNode("Courts")
            h1.appendChild(text)
            div.appendChild(h1)
            const courtList = document.createElement("ul")

            courts.forEach(court => {
                const courtItem = document.createElement("li")
                courtItem.textContent = `Court: ${court.name.name}`;

                const attributesList = document.createElement("ul")
                const idItem = document.createElement("a");
                idItem.href = `${API_BASE_URL}#court/${court.id.id}`;
                idItem.textContent = `Court ID: ${court.id.id}`;
                attributesList.appendChild(idItem);

                const clubIdItem = document.createElement("li")
                clubIdItem.textContent = `ClubId: ${court.club.id.id}`
                attributesList.appendChild(clubIdItem);

                const clubOwnerNameItem = document.createElement("li")
                clubOwnerNameItem.textContent = `Club Owner: ${court.club.owner.user.name.name}`
                attributesList.appendChild(clubOwnerNameItem);

                const clubOwnerEmailItem = document.createElement("li")
                clubOwnerEmailItem.textContent = `Club Owner Email: ${court.club.owner.user.email.value}`
                attributesList.appendChild(clubOwnerEmailItem);

                const clubNameItem = document.createElement("li")
                clubNameItem.textContent = `Club Name: ${court.club.name.name}`
                attributesList.appendChild(clubNameItem);

                courtItem.appendChild(attributesList);
                courtList.appendChild(courtItem);
            });
            div.appendChild(courtList)
            mainContent.replaceChildren(div)
        })
}

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
            rentalListLink.href = `${API_BASE_URL}#court/${court.id.id}/rentals`;
            rentalListLink.textContent = "Court Rentals List";
            rentalList.appendChild(rentalListLink);

            ulStd.appendChild(courtName);
            ulStd.appendChild(courtId);
            ulStd.appendChild(clubId);
            ulStd.appendChild(rentalList)

            mainContent.replaceChildren(ulStd);
        });
};