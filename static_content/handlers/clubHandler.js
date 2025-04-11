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

                const idItem = document.createElement("a");
                idItem.href = `${API_BASE_URL}#club/${club.id.id}`; // Corrigido a construção da URL
                idItem.textContent = `ID: ${club.id.id}`; // Definindo o texto do link
                attributesList.appendChild(idItem);

                //const idItem = document.createElement("li");
                //idItem.textContent = `ID: ${club.id.id}`;
               // attributesList.appendChild(idItem);

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
    const clubId = params.id
    console.log(clubId)

    fetch(API_BASE_URL + "clubs/" + clubId)
        .then(res => res.json())
        .then(club => {
            const ulStd = document.createElement("ul")

            const clubName = document.createElement("li")
            const textName = document.createTextNode("Name : " + club.name.name)
            clubName.appendChild(textName)

            const liNumber = document.createElement("li")
            const textNumber = document.createTextNode("Club Id : " + club.id.id)
            liNumber.appendChild(textNumber)

            ulStd.appendChild(clubName)
            ulStd.appendChild(liNumber)

            mainContent.replaceChildren(ulStd)
        })
}
