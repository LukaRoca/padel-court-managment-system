import { API_BASE_URL } from "../utils/configs.js";

/*
export function getUserDetails(mainContent) {

    const userId = window.location.hash.split("/")[2];

    fetch(API_BASE_URL + "users/" + userId)
        .then(res => res.json())
        .then(user => {
            const div = document.createElement("div");

            const h1 = document.createElement("h1");
            const text = document.createTextNode("User Details");
            h1.appendChild(text);
            div.appendChild(h1);

            const userDetailsList = document.createElement("ul");

            const idItem = document.createElement("li");
            idItem.textContent = `ID: ${user.id.id}`;
            userDetailsList.appendChild(idItem);

            const nameItem = document.createElement("li");
            nameItem.textContent = `Name: ${user.name.name}`;
            userDetailsList.appendChild(nameItem);

            const emailItem = document.createElement("li");
            emailItem.textContent = `Email: ${user.email.value}`;
            userDetailsList.appendChild(emailItem);

            div.appendChild(userDetailsList);

            mainContent.replaceChildren(div);
        });
}

 */

export function getAllUsers(mainContent) {
        fetch(API_BASE_URL + "users", {
                method: "GET",
                headers: {
                        "Content-Type": "application/json"
                }
        })
            .then(res => res.json())
            .then(users => {
                    const div = document.createElement("div");

                    const h1 = document.createElement("h1");
                    h1.textContent = "Users";
                    div.appendChild(h1);

                    const usersList = document.createElement("ul");

                    users.forEach(user => {
                            const userItem = document.createElement("li");
                            userItem.textContent = `User: ${user.name.name}`;

                            const attributesList = document.createElement("ul");

                            const idItem = document.createElement("li");
                            idItem.textContent = `ID: ${user.uid.id}`;
                            attributesList.appendChild(idItem);

                            const emailItem = document.createElement("li");
                            emailItem.textContent = `Email: ${user.email.value}`;
                            attributesList.appendChild(emailItem);

                            userItem.appendChild(attributesList);
                            usersList.appendChild(userItem);
                    });

                    div.appendChild(usersList);

                    mainContent.replaceChildren(div);
            })
            .catch(error => {
                    console.error("Erro ao buscar usuários:", error);
                    mainContent.textContent = "Erro ao carregar a lista de usuários.";
            });
}