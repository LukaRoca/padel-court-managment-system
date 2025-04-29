import {a, div, h1, h2, span, p, ul, li, button} from "../utils/elements.js";
import {API_BASE_URL} from "../utils/configs.js";


/*
export const renderCourtsList = (mainContent, courts) => {
    console.log("renderCourtsList called with:", courts);

    if (!mainContent) {
        console.error("mainContent is null or undefined. Cannot render courts list.");
        return;
    }

    if (!Array.isArray(courts)) {
        console.error("courts data is not an array:", courts);
        courts = [];
    }

    const content = div(
        {className: "container py-5"},

        div(
            {className: "row mb-5 pb-4 border-bottom"},
            div(
                {className: "col-12 text-center"},
                h1({className: "display-4 fw-bold text-primary mb-3"}, "Courts"),
                p({className: "lead text-muted"}, "Browse available courts and check their details")
            )
        ),

        div(
            {className: "row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4"},
            ...courts.map((court) =>
                div(
                    {className: "col"},
                    div(
                        {
                            className: "card h-100 border-0 shadow-sm court-card transition-all hover-lift"
                        },
                        div(
                            {className: "card-header bg-primary bg-opacity-75 text-white py-3"},
                            h2({className: "h5 mb-0 fw-bold"}, court?.name || 'Unnamed Court')
                        ),
                        div(
                            {className: "text-center py-5 bg-light border-top border-bottom"},
                            span({className: "material-icons display-1 text-muted"}, "Image")
                        ),
                        div(
                            {className: "card-body py-3 px-3"},
                            p(
                                {className: "card-text mb-2"},
                                span({className: "fw-bold"}, "Club: "),
                                court.club?.name || 'N/A'
                            ),
                            p(
                                {className: "card-text mb-2"},
                                span({className: "fw-bold"}, "Owner: "),
                                court.club.owner?.name || 'N/A'
                            ),
                            p(
                                {className: "small text-muted mt-3"},
                                `ID: ${court?.id || 'Unknown ID'}`
                            )
                        ),
                        div(
                            {className: "card-footer bg-transparent border-top-0 pt-0 pb-3 px-3"},
                            a({
                                href: `${API_BASE_URL}#court/${court?.id || ''}`,
                                textContent: `Court Details`,
                                className: "btn btn-primary w-100 d-inline-flex align-items-center justify-content-center gap-2",
                                children: [
                                    span({ className: "material-icons" }),
                                    "Court Details"
                                ]
                            })
                        )
                    )
                )
            )
        ),

        div(
            {className: "d-flex justify-content-between align-items-center mt-5 pt-4 border-top"},
            a({
                href: `${API_BASE_URL}#clubs`,
                className: "btn btn-outline-secondary d-inline-flex align-items-center gap-1",
            },
                span({ className: "material-icons", style: "font-size: 1.1em;" }),
                    "Club Details"
            ),
            div(
                {className: "d-flex gap-2"},
            )
        )
    );

    mainContent.replaceChildren(content);
    console.log("Courts list rendered successfully with improved styling.");
};

 */

export const renderCourtsList = (mainContent, courts, onNext, onPrevious, hasNext, hasPrevious) => {
    console.log("renderCourtsList called with:", courts);

    if (!mainContent) {
        console.error("mainContent is null or undefined. Cannot render courts list.");
        return;
    }

    if (!Array.isArray(courts)) {
        console.error("courts data is not an array:", courts);
        courts = [];
    }

    const courtCards = courts.map((court) =>
        div(
            {className: "col"},
            div(
                {className: "card h-100 border-0 shadow-sm court-card transition-all hover-lift"},
                div(
                    {className: "card-header bg-primary bg-opacity-75 text-white py-3"},
                    h2({className: "h5 mb-0 fw-bold"}, court?.name || 'Unnamed Court')
                ),
                div(
                    {className: "text-center py-5 bg-light border-top border-bottom"},
                    span({className: "material-icons display-1 text-muted"}, "Image")
                ),
                div(
                    {className: "card-body py-3 px-3"},
                    p({className: "card-text mb-2"}, span({className: "fw-bold"}, "Club: "), court.club?.name || 'N/A'),
                    p({className: "card-text mb-2"}, span({className: "fw-bold"}, "Owner: "), court.club?.owner?.name || 'N/A'),
                    p({className: "small text-muted mt-3"}, `ID: ${court?.id || 'Unknown ID'}`)
                ),
                div(
                    {className: "card-footer bg-transparent border-top-0 pt-0 pb-3 px-3"},
                    a({
                            href: `${API_BASE_URL}#court/${court?.id || ''}`,
                            className: "btn btn-primary w-100 d-inline-flex align-items-center justify-content-center gap-2"
                        },
                        span({className: "material-icons"}),
                        "Court Details"
                    )
                )
            )
        )
    );

    const pagination = div(
        {className: "d-flex justify-content-between align-items-center mt-5 pt-4 border-top"},
        hasPrevious ? button({className: "btn btn-outline-primary", onclick: onPrevious}, "Previous") : div({}),
        a({
                href: `${API_BASE_URL}#clubs`,
                className: "btn btn-outline-secondary d-inline-flex align-items-center gap-1"
            },
            span({className: "material-icons", style: "font-size: 1.1em;"}),
            "Club Details"
        ),
        hasNext ? button({className: "btn btn-outline-primary", onclick: onNext}, "Next") : div({})
    );

    const content = div(
        {className: "container py-5"},
        div(
            {className: "row mb-5 pb-4 border-bottom"},
            div(
                {className: "col-12 text-center"},
                h1({className: "display-4 fw-bold text-primary mb-3"}, "Courts"),
                p({className: "lead text-muted"}, "Browse available courts and check their details")
            )
        ),
        div({className: "row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4"}, ...courtCards),
        pagination
    );

    mainContent.replaceChildren(content);
    console.log("Courts list rendered successfully with pagination.");
};


export const renderCourtDetail = (mainContent, court) => {

    console.log("renderCourtDetail called with:", court);

    if (!mainContent) {
        console.error("mainContent is null or undefined. Cannot render court detail.");
        return;
    }

    const content = div(
        {className: "container py-5"},

        div(
            {className: "row mb-5 pb-4 border-bottom"},
            div(
                {className: "col-12 text-center"},
                h1({className: "display-4 fw-bold text-primary mb-3"}, `Court ${court?.id || 'Unknown'}`),
                p({className: "lead text-muted"}, "View court details and available rentals")
            )
        ),

        div(
            {className: "row mb-5"},
            div(
                {className: "col-md-8 mx-auto"},
                div(
                    {className: "card border-0 shadow-sm court-card transition-all hover-lift"},
                    div(
                        {className: "card-header bg-primary bg-opacity-75 text-white py-3"},
                        h2({className: "h5 mb-0 fw-bold"}, court?.name || 'Unnamed Court')
                    ),
                    div(
                        {className: "text-center py-5 bg-light border-top border-bottom"},
                        span({className: "material-icons display-1 text-muted"}, "Image")
                    ),
                    div(
                        {className: "card-body py-3 px-3"},
                        p(
                            {className: "card-text mb-2"},
                            span({className: "fw-bold"}, "Club: "),
                            court.club?.name || 'N/A'
                        ),
                        p(
                            {className: "card-text mb-2"},
                            span({className: "fw-bold"}, "Club Owner: "),
                            court.club.owner?.name || 'N/A'
                        ),
                        p(
                            {className: "small text-muted mt-3"},
                            `ID: ${court?.id || 'Unknown ID'}`
                        )
                    ),

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
                            href: `${API_BASE_URL}#court/rentals/${court?.id || ''}`,
                            className: "btn btn-primary d-inline-flex align-items-center justify-content-center gap-2"
                        },
                        span({className: "material-icons"}),
                        "Rentals"
                    )
                )
            )
        ),
        div(
            {className: "d-flex justify-content-between align-items-center mt-5 pt-4 border-top"},
            a({
                href: `${API_BASE_URL}#clubs`,
                className: "btn btn-outline-secondary d-inline-flex align-items-center gap-1",
            },
                span({ className: "material-icons", style: "font-size: 1.1em;" }),
                    "Club Details"
            ),
            div(
                {className: "d-flex gap-2"},
            )
        )
    );

    mainContent.replaceChildren(content);
}