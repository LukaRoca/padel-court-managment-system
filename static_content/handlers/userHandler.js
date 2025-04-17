import {renderException} from "../views/Exeptions.js";
import {renderUserDetail} from "../views/userView.js";
import {fetchUserById} from "../data/userData.js";


export const getUserById = async (mainContent, params) => {
        try {
                const userId = params.uid;
                const user = await fetchUserById(userId);
                renderUserDetail(mainContent, user);
        } catch (error) {
                console.error("Erro ao buscar usuário:", error);
                renderException(mainContent, error);
        }
};


/*
export const getUserById = (mainContent, params) => {
    const userId = params.uid
    fetch(API_BASE_URL + "users/" + userId)
        .then(res => res.json())
        .then(user => {

            const rental = document.createElement("div");

            const rentalLink = document.createElement("a");
            rentalLink.href = `${API_BASE_URL}#rentals/${user.uid.id}`;
            rentalLink.textContent = "RentalsList";

            rental.appendChild(rentalLink);

            const ulStd = document.createElement("ul");

            const UserName = document.createElement("li");
            UserName.textContent = "User Name : " + user.name.name;

            const UserId = document.createElement("li");
            UserId.textContent = "User Id: " + user.uid.id;

            const UserEmail = document.createElement("li");
            UserEmail.textContent = "User Email: " + user.email.email

            ulStd.appendChild(UserName);
            ulStd.appendChild(UserId);
            ulStd.appendChild(UserId);

            const container = document.createElement("div");
            container.appendChild(ulStd);
            container.appendChild(rental);

            mainContent.replaceChildren(container);
        });
}

 */