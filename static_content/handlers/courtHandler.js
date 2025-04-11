import { API_BASE_URL } from "../utils/configs.js"

export const getCourtsList = (mainContent) => {
    fetch(API_BASE_URL + "courts")
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
                idItem.textContent = `ID: ${court.id.id}`;
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
            });
            div.appendChild(courtList)
            mainContent.replaceChildren(div)
        })
}

export const getCourtDetails = (mainContent, params) => {
    const courtId = params.id
    fetch(API_BASE_URL + "courts/" + courtId)
        .then(res => res.json())
        .then(court => {
            const ulStd = document.createElement("ul")
            const courtName = document.createElement("li")
            const textName = document.createTextNode("Name : " + court.name.name)
            court.appendChild(textName)
            const liNumber = document.createElement("li")
            const textNumber = document.createTextNode("Court Id : " + court.id.id)
            liNumber.appendChild(textNumber)
            ulStd.appendChild(courtName)
            ulStd.appendChild(liNumber)
            mainContent.replaceChildren(ulStd)
        })
}
export const getCourtRentalList = (mainContent, params) => {
    const courtId = params.id

    fetch(API_BASE_URL + "courts/" + courtId + "/rentals")
        .then(res => res.json())
        .then(rentals => {
            const div = document.createElement("div");
            const h1 = document.createElement("h1");
            const text = document.createTextNode(`Rentals for Court: ${courtId}`);
            h1.appendChild(text)
            div.appendChild(h1);
            if(rentals.length == 0) {
                const p = document.createElement("p");
                p.textContent = "No rentals available for this court";
                div.appendChild(p);}
            else {
                const rentalList = document.createElement("ul");
                rentals.forEach(rental => {
                    const rentalItem = document.createElement("li");
                    rentalItem.textContent = `Rental ID: ${rental.rid.id}`;
                    const attributesList = document.createElement("ul");

                    const dateItem = document.createElement("li");
                    dateItem.textContent = `Date: ${rental.date}`;
                    attributesList.appendChild(dateItem);

                    const durationItem = document.createElement("li");
                    durationItem.textContent = `Duration: ${rental.duration.initDuration}h to ${rental.duration.endDuration}h`;
                    attributesList.appendChild(durationItem);

                    const userNameItem = document.createElement("li");
                    userNameItem.textContent = `User name: ${rental.user.name.name}`;
                    attributesList.appendChild(userNameItem);

                    const userEmailItem = document.createElement("li");
                    userEmailItem.textContent = `User email: ${user.email.value}`;
                    attributesList.appendChild(userEmailItem);

                    const courtItem = document.createElement("li");
                    courtItem.textContent = `Court name: ${rental.court.name.name}`;
                    attributesList.appendChild(courtItem);

                    rentalItem.appendChild(attributesList);
                    rentalList.appendChild(rentalItem);
                });

                div.appendChild(rentalList);
            }
            mainContent.replaceChildren(div);
            })
}

