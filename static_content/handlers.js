/*
const API_BASE_URL = "http://localhost:8080/"

function getHome(mainContent){

    const h1 = document.createElement("h1")
    const text = document.createTextNode("Home")
    h1.appendChild(text)
    mainContent.replaceChildren(h1)
}

function getClubs(mainContent) {
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

export const handlers = {
    getHome,
    getClubs
}

export default handlers

 */