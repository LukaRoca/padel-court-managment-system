import {a, div, h1, h2, h3, p, span} from "../../utils/elements.js";
import {API_BASE_URL} from "../../utils/configs.js";

export const renderRentalDetails = (mainContent, rental) => {
    console.log("renderRentalDetails called with mainContent:", mainContent);

    if (!rental) {
        console.error("rental is null or undefined");
        return;
    }

    const content = div(
        {className: "container py-5"},

        div(
            {className: "row mb-5 pb-4 border-bottom"},
            div(
                {className: "col-12 text-center"},
                h1({className: "display-4 fw-bold text-primary mb-3"}, "Rental Details"),
                p({className: "lead text-muted"}, `Details for rental #${rental.id}`)
            )
        ),

        div(
            {className: "row justify-content-center"},
            div(
                {className: "col-lg-8"},
                div(
                    {className: "card shadow-sm mb-4"},
                    div(
                        {className: "card-header bg-primary bg-opacity-75 text-white py-3"},
                        h2({className: "h5 mb-0 fw-bold"}, `Rental Information`)
                    ),
                    div(
                        {className: "card-body p-4"},
                        div(
                            {className: "mb-4"},
                            h3({className: "h6 fw-bold mb-3"}, "Basic Information"),
                            div(
                                {className: "row g-3"},
                                div(
                                    {className: "col-md-6"},
                                    div({className: "mb-2"},
                                        span({className: "fw-bold d-block text-muted small"}, "Rental ID"),
                                        span({className: "fs-5"}, rental.id)
                                    )
                                ),
                                div(
                                    {className: "col-md-6"},
                                    div({className: "mb-2"},
                                        span({className: "fw-bold d-block text-muted small"}, "Date"),
                                        span({className: "fs-5"}, rental.date)
                                    )
                                ),
                                div(
                                    {className: "col-md-6"},
                                    div({className: "mb-2"},
                                        span({className: "fw-bold d-block text-muted small"}, "Duration"),
                                        span({className: "fs-5"}, `${rental.duration.initDuration}h`)
                                    )
                                ),
                            )
                        ),
                        div(
                            {className: "mb-4"},
                            h3({className: "h6 fw-bold mb-3"}, "Related Information"),
                            div(
                                {className: "row g-3"},
                                div(
                                    {className: "col-md-6"},
                                    div({className: "d-flex flex-column"},
                                        span({className: "fw-bold d-block text-muted small mb-2"}, "User"),
                                        a({
                                            href: `${API_BASE_URL}#users/${rental.user}`,
                                            className: "btn btn-outline-primary btn-sm"
                                        }, `View User #${rental.user}`)
                                    )
                                ),
                                div(
                                    {className: "col-md-6"},
                                    div({className: "d-flex flex-column"},
                                        span({className: "fw-bold d-block text-muted small mb-2"}, "Court"),
                                        a({
                                            href: `${API_BASE_URL}#court/${rental.courtId}`,
                                            className: "btn btn-outline-primary btn-sm"
                                        }, `View Court #${rental.courtId}`)
                                    )
                                )
                            )
                        )
                    )
                )
            )
        ),
    );

    if(!mainContent) {
        console.error("mainContent is null or undefined");
        return;
    }

    mainContent.replaceChildren(content);
    console.log("Rental details rendered successfully");
};
