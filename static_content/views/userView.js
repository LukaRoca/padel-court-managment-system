import {a, div, h1, p, span, h2} from "../utils/elements.js";
import {API_BASE_URL} from "../utils/configs.js";

export const renderUserDetail = (mainContent, user) => {
    console.log("renderUserDetail called with:", user);

    const content = div(
        {className: "container py-5"},

        // Header section
        div(
            {className: "row mb-5 pb-4 border-bottom"},
            div(
                {className: "col-12"},
                h1({className: "display-4 fw-bold text-primary mb-3"}, "User Profile"),
                p({className: "lead text-muted"}, "View user information and related rentals")
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
                        h2({className: "h4 mb-0"}, "User Information")
                    ),
                    div(
                        {className: "card-body p-4"},
                        div(
                            {className: "text-center py-4 mb-4 bg-light rounded-3 border-bottom"},
                            span({className: "material-icons display-1 text-primary"}, "Image")
                        ),
                        div(
                            {className: "row mb-3 pb-3 border-bottom"},
                            div({className: "col-4 fw-bold"}, "Name:"),
                            div({className: "col-8"}, user.name?.name || user.name || "N/A")
                        ),
                        div(
                            {className: "row mb-3 pb-3 border-bottom"},
                            div({className: "col-4 fw-bold"}, "User ID:"),
                            div({className: "col-8"}, user.id?.id || user.id || "N/A")
                        ),
                        div(
                            {className: "row"},
                            div({className: "col-4 fw-bold"}, "Email:"),
                            div({className: "col-8"}, user.email || "N/A")
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
                            href: `${API_BASE_URL}#rentals/${user.id?.id || user.id || ''}`,
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
    console.log("User detail rendered successfully");
};