import {a, div, h1, h2, li, ul} from "../utils/elements.js";
import {API_BASE_URL} from "../utils/configs.js";

export const renderCourtsList = (mainContent, courts) => {
    console.log("renderCourtsList called with:", courts);

    if (!Array.isArray(courts)) {
        console.error("courts is not an array:", courts);
        courts = [];
    }

    const clubNameLink = a({},
        a({ href: `${API_BASE_URL}#clubs` }, "Club List")
        );

    const courtDetails = div(
        h1("Courts"),
        ul(
            ...courts.map((court) =>
                li(
                    `Court: ${court.name.name}`,
                    ul(
                        li(
                            a({
                                href: `${API_BASE_URL}#court/${court.id.id}`
                            },
                                `Id: ${court.id.id}`
                            )
                        ),
                        li(`Club Id: ${court.club.id.id}`),
                        li(`Club Owner: ${court.club.owner.user.name.name}`),
                        li(`Club Owner Email: ${court.club.owner.user.email.value}`),
                        li(`Club Name: ${court.club.name.name}`)
                    )
                )
            )
        )
    );

    const content = div(
        clubNameLink,
        courtDetails

    );

    if(!mainContent) {
        console.error("mainContent is null or undefined");
        return;
    }

    mainContent.replaceChildren(content);
    console.log("Courts list rendered successfully");
};