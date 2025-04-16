import {API_BASE_URL} from "../utils/configs.js";
import {a, div, h1, li, ul} from "../utils/elements.js";

export const renderClubs = (clubs) => {
    return div(
        h1({}, "Club List"),
        ul(
            {},
            clubs.map(club =>
                li(
                    {},
                    a({ href: `${API_BASE_URL}#clubs/${club.id}` }, club.name)
                )
            )
        )
    );
};