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
                    },
                        'Club Id: ' + club.id.id,
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

export const renderClubDetail = (mainContent, club) => {
    console.log("renderClubDetail called with:", club);

    const courtsLink = a({
        href: `${API_BASE_URL}#courts/${club.id.id}`,
        textContent: "CourtsList"
    });

    const clubDetails = ul(
        li(`Club Name: ${club.name.name}`),
        li(`Club Id: ${club.id.id}`),
        li(
            a({
                href: `${API_BASE_URL}#user/${club.owner.user.uid.id}`
            },
                `Owner : ${club.owner.user.uid.id}`
            )
        )
    );

    const content = div(
        h1("Club Details"),
        div(courtsLink),
        clubDetails
    );

    if(!mainContent) {
        console.error("mainContent is null or undefined");
        return;
    }

    mainContent.replaceChildren(content);
    console.log("Club detail rendered successfully");
};