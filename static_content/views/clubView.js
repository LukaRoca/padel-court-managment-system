import {API_BASE_URL} from "../utils/configs.js";
import {a, div, h1, li, ul, p, span, h2} from "../utils/elements.js";

export const renderClubs = (mainContent, clubs, onNext, onPrevious, hasNext, hasPrevious) => {
    console.log("renderClubs called with mainContent:", mainContent);

    if (!Array.isArray(clubs)) {
        console.error("clubs is not an array:", clubs);
        clubs = [];
        console.log(clubs)
    }

    const content = div(
        {className: "container py-5"},

        div(
            {className: "row mb-5 pb-4 border-bottom"},
            div(
                {className: "col-12 text-center"},
                h1({className: "display-4 fw-bold text-primary mb-3"}, "Padel Clubs"),
                p({className: "lead text-muted"}, "Browse our partner padel clubs and discover their available courts")
            )
        ),

        div(
            {className: "row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4"},
            ...clubs.map((club) =>
                div(
                    {className: "col"},
                    div(
                        {className: "card h-100 border-0 shadow-sm club-card transition-all hover-lift"},
                        div(
                            {className: "card-header bg-primary bg-opacity-75 text-white py-3"},
                            h2({className: "h5 mb-0 fw-bold"}, club.name || `Club ${club.id}`)
                        ),
                        div(
                            {className: "text-center py-5 bg-light border-top border-bottom"},
                            span({className: "material-icons display-1 text-muted"}, "Image")
                        ),
                        div(
                            {className: "card-body py-3 px-3"},
                            p(
                                {className: "card-text mb-2"},
                                span({className: "fw-bold"}, "Club ID: "),
                                club.id
                            ),
                            p(
                                {className: "small text-muted mt-3"},
                                "View club details and available courts"
                            )
                        ),
                        div(
                            {className: "card-footer bg-transparent border-top-0 pt-0 pb-3 px-3"},
                            a({
                                    href: `${API_BASE_URL}#club/${club.id}`,
                                    className: "btn btn-primary w-100 d-inline-flex align-items-center justify-content-center gap-2"
                                },
                                span({className: "material-icons"}),
                                "Club Details"
                            )
                        )
                    )
                )
            )
        ),

        div(
            {className: "d-flex justify-content-between align-items-center mt-5 pt-4 border-top"},
            a({
                    href: `${API_BASE_URL}#`,
                    className: "btn btn-outline-secondary d-inline-flex align-items-center gap-1"
                },
                span({className: "material-icons", style: "font-size: 1.1em;"}),
                "Back to Home"
            ),
            div({className: "d-flex gap-2"})
        )
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

    const content = div(
        {className: "container py-5"},

        // Header section
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
                                        href: `${API_BASE_URL}#users/${club.owner.id || ''}`,
                                        className: "d-inline-flex align-items-center gap-1 text-decoration-none"
                                    },
                                    span({className: "material-icons", style: "font-size: 1.1em;"}),
                                    club.owner.name || club.owner.id || "N/A"
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
                        span({className: "material-icons"}),
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