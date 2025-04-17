import {a, div, h1, li, ul} from "../utils/elements.js";
import {API_BASE_URL} from "../utils/configs.js";

export const renderUserDetail = (mainContent, user) => {
    console.log("renderUserDetail called with:", user);

    const userDetails = ul(
        li(`Name: ${user.name.name}`),
        li(`Id: ${user.uid.id}`),
        li(`Email: ${user.email.value}`),
    );

    const rentalLink = a({
        href: `${API_BASE_URL}#rentals/${user.uid.id}`,
        textContent: "RentalsList"
    });

    const content = div(
        h1("User Details"),
        userDetails,
        div(rentalLink)
    );

    if(!mainContent) {
        console.error("mainContent is null or undefined");
        return;
    }

    mainContent.replaceChildren(content);
    console.log("User detail rendered successfully");
};