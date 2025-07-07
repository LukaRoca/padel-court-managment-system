import {a, div, h1, h2, p, span} from "../../utils/elements.js";
import {API_BASE_URL} from "../../utils/configs.js";

export const renderClubDetail = (mainContent, club) => {
    console.log("renderClubDetail called with:", club);

    const content = div(
        {className: "container py-5"},

        div(
            {className: "row mb-5 pb-4 border-bottom"},
            div(
                {className: "col-12"},
                h1({className: "display-4 fw-bold text-primary mb-3"}, club.name || "Club Details"),
                p({className: "lead text-muted"}, "View club information and browse available courts")
            )
        ),

        div(
            {className: "row mb-5"},
            div(
                {className: "col-md-8 mx-auto"},
                div(
                    {className: "card border-0 shadow-sm"},
                    div(
                        {className: "card-header bg-primary bg-opacity-75 text-white py-3"},
                        h2({className: "h4 mb-0"}, "Club Information")
                    ),
                    div(
                        {className: "card-body p-4"},
                        div(
                            {className: "row mb-3 pb-3 border-bottom"},
                            div({className: "col-4 fw-bold"}, "Club Name:"),
                            div({className: "col-8"}, club.name || "N/A")
                        ),
                        div(
                            {className: "row mb-3 pb-3 border-bottom"},
                            div({className: "col-4 fw-bold"}, "Club ID:"),
                            div({className: "col-8"}, club.id || "N/A")
                        ),
                        div(
                            {className: "row"},
                            div({className: "col-4 fw-bold"}, "Owner:"),
                            div(
                                {className: "col-8"},
                                a({
                                        href: `${API_BASE_URL}#users/${club.ownerId || ''}`,
                                        className: "d-inline-flex align-items-center gap-1 text-decoration-none"
                                    },
                                    span({className: "material-icons", style: "font-size: 1.1em;"}),
                                    club.ownerId || "N/A"
                                )
                            )
                        )
                    )
                )
            )
        ),

        div(
            {className: "row mb-5"},
            div(
                {className: "col-md-8 mx-auto"},
                div(
                    {className: "d-grid gap-2"},
                    a({
                            href: `${API_BASE_URL}#courts/${club.id || ''}`,
                            className: "btn btn-primary d-inline-flex align-items-center justify-content-center gap-2"
                        },
                        span({className: "material-icons"}, "sports_tennis"),
                        "Club Courts"
                    )
                )
            )
        ),

        div(
            {className: "d-flex justify-content-between align-items-center mt-5 pt-4 border-top"},
            a({
                    href: `${API_BASE_URL}#clubs`,
                    className: "btn btn-outline-secondary d-inline-flex align-items-center gap-1"
                },
                span({className: "material-icons", style: "font-size: 1.1em;"}),
                "Back to Clubs"
            ),
            div({className: "d-flex gap-2"})
        )
    );

    if(!mainContent) {
        console.error("mainContent is null or undefined");
        return;
    }

    mainContent.replaceChildren(content);
    console.log("Club detail rendered successfully");
};
