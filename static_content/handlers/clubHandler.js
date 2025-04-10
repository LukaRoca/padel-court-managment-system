import {API_BASE_URL} from "../utils/configs.js";

export const getClubs = (mainContent) => {
    console.log("API_BASE_URL:", API_BASE_URL);

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

                const idItem = document.createElement("li");
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

/*
function getClubs {
    fetch(API_BASE_URL + "clubs")
        .then(res => res.json())
        .then(clubs => {
            const div = document.createElement("div")
            const h1 = document.createElement("h1")
            h1.textContent = "Clubs"
            div.appendChild(h1)

            clubs.forEach(c => {
                const p = document.createElement("p")
                const a = document.createElement("a")
                a.textContent = c.name
                //a.href = `#/clubs/${c.cid}` n sei passar o id do clube aqui
                p.appendChild(a)
                div.appendChild(p)
            })

            info.replaceChildren(div)
        })
}

function getClubDetails(info, { clubId }) {
    fetch(API_BASE_URL + `clubs/${clubId}`)
        .then(res => res.json())
        .then(club => {
            const div = document.createElement("div")
            const h1 = document.createElement("h1")
            h1.textContent = `Club details: ${club.name}`
            div.appendChild(h1)

            const courtsLink = document.createElement("a")
            courtsLink.href = `#/clubs/${clubId}/courts`
            courtsLink.textContent = "Courts"
            div.appendChild(courtsLink)

            info.replaceChildren(div)
        })
}

export default {
    getClubs,
    getClubDetails
}

 */
