import {API_BASE_URL} from "../utils/configs.js";
import {a, div, h1, li, ul} from "../utils/elements.js";

export const renderClubs = (mainContent, clubs) => {
    console.log("renderClubs called with mainContent:", mainContent);


    if (!Array.isArray(clubs)) {
        console.error("clubs is not an array:", clubs);
        clubs = [];
    }

    const content = div(
        h1("Clubs"),
        ul(
            ...clubs.map((club) =>
                li(
                    a({
                        href: `${API_BASE_URL}#club/${club.id.id}`,
                        textContent: `Club: ${club.name.name}`,
                    }),
                    ul(
                        li(`Club ID: ${club.id.id}`),
                        li(`Owner: ${club.owner.user.name.name}`),
                        li(`Owner Email: ${club.owner.user.email.value}`),
                    ),
                ),
            ),
        ),
    );

    console.log("content:", content);

    if(!mainContent) {
        console.error("mainContent is null or undefined");
        return;
    }
    mainContent.replaceChildren(content);
    console.log("Clubs rendered successfully");
};