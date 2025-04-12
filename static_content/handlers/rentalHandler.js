import { API_BASE_URL } from "../utils/configs.js"

export const getRentalById = (mainContent, params) => {
    const rid = params.rid;
    fetch(API_BASE_URL + "rentals/" + rid)
        .then(res => res.json())
        .then(rental => {

            // Conteúdo principal
            const ulStd = document.createElement("ul");

            const rentalId = document.createElement("li");
            rentalId.textContent = "Rental Id : " + rental.rid.id;

            const rentalDate = document.createElement("li")
            rentalDate.textContent = `Date: ${rental.date.value}`

            const rentalDuration = document.createElement("li")
            rentalDuration.textContent = `Duration: ${rental.duration.endDuration - rental.duration.initDuration} Hours`

            const rentalUserId = document.createElement("a")
            rentalUserId.href = `${API_BASE_URL}#user/${rental.user.uid.id}`
            rentalUserId.textContent = `User Id: ${rental.user.uid.id} `

            const breakElement = document.createElement("br") // Dar um Enter entre os 2 botões

            const rentalCourtId = document.createElement("a")
            rentalCourtId.href = `${API_BASE_URL}#court/${rental.court.id.id}`
            rentalCourtId.textContent = `Court Id: ${rental.court.id.id}`

            ulStd.appendChild(rentalId);
            ulStd.appendChild(rentalDate);
            ulStd.appendChild(rentalDuration);
            ulStd.appendChild(rentalUserId);
            ulStd.appendChild(breakElement)
            ulStd.appendChild(rentalCourtId);

            mainContent.replaceChildren(ulStd);
        });
}

export const getRentalsByUid = (mainContent, params) => {
    const uid = params.uid
    fetch(API_BASE_URL + "rentals/user/" + uid)
        .then(res => res.json())
        .then(rentals => {

            const div = document.createElement("div");

            const UserLink = document.createElement("a");
            UserLink.href = `${API_BASE_URL}#user/${uid}`;
            UserLink.textContent = "User";

            const h1 = document.createElement("h1");
            const text = document.createTextNode(`Rentals of User: ${uid}`)
            h1.appendChild(text)

            div.appendChild(UserLink);
            div.appendChild(h1)

            rentals.forEach( rental => {
                const rentalAtributes = document.createElement("div")

                const rentalId = document.createElement("a");
                rentalId.href = `${API_BASE_URL}#rental/${rental.rid.id}`;
                rentalId.textContent = `ID: ${rental.rid.id}`;

                const rentalDate = document.createElement("li")
                rentalDate.textContent = `Date: ${rental.date.value}`

                const rentalDuration = document.createElement("li")
                rentalDuration.textContent = `Duration: ${rental.duration.endDuration - rental.duration.initDuration} Hours`

                rentalAtributes.appendChild(rentalId);
                rentalAtributes.appendChild(rentalDate);
                rentalAtributes.appendChild(rentalDuration);
                div.appendChild(rentalAtributes);
            })

            mainContent.replaceChildren(div);
        });
}

export const getRentalByCrid = (mainContent, params) => {
    const crid = params.crid
    fetch(API_BASE_URL + "rentals/courts/" + crid)
        .then(res => res.json())
        .then(rentals => {

            const div = document.createElement("div");

            const CourtLink = document.createElement("a");
            CourtLink.href = `${API_BASE_URL}#court/${crid}`;
            CourtLink.textContent = "Court";

            const h1 = document.createElement("h1");
            const text = document.createTextNode(`Rentals of Court: ${crid}`)
            h1.appendChild(text)

            div.appendChild(CourtLink);
            div.appendChild(h1)

            rentals.forEach( rental => {
                const rentalAtributes = document.createElement("div")

                const rentalId = document.createElement("a");
                rentalId.href = `${API_BASE_URL}#rental/${rental.rid.id}`;
                rentalId.textContent = `ID: ${rental.rid.id}`;

                const rentalDate = document.createElement("li")
                rentalDate.textContent = `Date: ${rental.date.value}`

                const rentalDuration = document.createElement("li")
                rentalDuration.textContent = `Duration: ${rental.duration.endDuration - rental.duration.initDuration} Hours`

                rentalAtributes.appendChild(rentalId);
                rentalAtributes.appendChild(rentalDate);
                rentalAtributes.appendChild(rentalDuration);
                div.appendChild(rentalAtributes);
            })

            mainContent.replaceChildren(div);
        });
}