import {div, h1, p, span, a, h2} from "../utils/elements.js";

export const renderException = (mainContent, error) => {

    console.error("renderException called with error:", error);

    if (!mainContent) {
        console.error("mainContent is null or undefined. Cannot render exception.");
        return;
    }

    const errorCode = error.status || error.code || 'UNKNOWN';
    const errorMessage = error.message || 'An unexpected error occurred.';

    const content = div(
        {className: "container py-5"},

        div(
            {className: "row mb-5 pb-4 border-bottom"},
            div(
                {className: "col-12 text-center"},
                h1({className: "display-4 fw-bold text-danger mb-3"}, "An Error Occurred"),
                p({className: "lead text-muted"}, "We're sorry, but something went wrong.")
            )
        ),

        div(
            {className: "row justify-content-center"},
            div(
                {className: "col-12 col-md-8 col-lg-6"},
                div(
                    {
                        className: "card h-100 border-0 shadow-sm court-card transition-all hover-lift"
                    },
                    div(
                        {className: "card-body py-4 px-4"},
                        h2({className: "h5 mb-3 text-danger"}, "Error Details"),
                        p(
                            {className: "card-text mb-2"},
                            span({className: "fw-bold"}, "Code: "),
                            errorCode
                        ),
                        p(
                            {className: "card-text mb-2"},
                            span({className: "fw-bold"}, "Message: "),
                            errorMessage
                        )
                    ),
                    div(
                        {className: "card-footer bg-transparent border-top-0 pt-0 pb-3 px-4"},
                        a({
                            href: "#home",
                            className: "btn btn-primary w-100 d-inline-flex align-items-center justify-content-center gap-2",
                            children: [
                                span({ className: "material-icons", style: "font-size: 1.1em;" }),
                                "Go Home"
                            ]
                        })
                    )
                )
            )
        )
    );

    mainContent.replaceChildren(content);
    console.error("Exception rendered successfully with improved styling for code:", errorCode, "and message:", errorMessage);
};