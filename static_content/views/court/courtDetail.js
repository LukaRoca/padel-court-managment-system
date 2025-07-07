import {a, div, h1, h2, p, span} from "../../utils/elements.js";
import {API_BASE_URL} from "../../utils/configs.js";

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
            ),
            div(
                {className: "d-flex justify-content-between align-items-center"},
                a(
                    {
                        href: `${API_BASE_URL}#rentals/court/date`,
                        className: "btn btn-primary d-inline-flex align-items-center gap-2"
                    },
                    "Search"
                )
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
                        span({className: "material-icons display-1 text-muted"}, "image")
                    ),
                    div(
                        {className: "card-body py-3 px-3"},
                        p(
                            {className: "card-text mb-2"},
                            span({className: "fw-bold"}, "Club ID: "),
                            court.clubId || 'N/A'
                        ),
                        p(
                            {className: "small text-muted mt-3"},
                            `ID: ${court?.id || 'Unknown ID'}`
                        ),
                    ),

                )
            )
        ),
        div(
            {className: "row mb-5"},
            div(
                {className: "col-md-8 mx-auto"},
                div(
                    {className: "d-flex gap-3 justify-content-center"},
                    a({
                            href: `${API_BASE_URL}#court/rentals/${court?.id || ''}`,
                            className: "btn btn-primary d-inline-flex align-items-center justify-content-center gap-2 px-4 py-3 flex-grow-1"
                        },
                        span({className: "material-icons"}, "calendar_month"),
                        "Rentals"
                    ),
                    a({
                            href: `${API_BASE_URL}#court/hours/${court?.id || ''}`,
                            className: "btn btn-success d-inline-flex align-items-center justify-content-center gap-2 px-4 py-3 flex-grow-1"
                        },
                        span({className: "material-icons"}, "schedule"),
                        "Available Hours"
                    )
                )
            )
        ),
        div(
            {className: "d-flex justify-content-between align-items-center mt-5 pt-4 border-top"},
            a({
                    href: `${API_BASE_URL}#club/${court.clubId}`,
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
