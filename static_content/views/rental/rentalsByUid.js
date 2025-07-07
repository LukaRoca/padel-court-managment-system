import {a, button, div, h1, h2, p, span, table, tbody, td, th, thead, tr} from "../../utils/elements.js";
import {API_BASE_URL} from "../../utils/configs.js";
import {fetchDeleteRental} from "../../data/rentalData.js";
import {setupDropdown} from "../../utils/utils.js";

export const renderRentalsByUid = (mainContent, rentals, onNext, onPrevious, hasNext, hasPrevious) => {
    console.log("renderRentalsByUid called with mainContent:", mainContent);

    if (!Array.isArray(rentals)) {
        console.error("rentals is not an array:", rentals);
        rentals = [];
    }

    const tableRows = rentals.map(rental =>
        tr(
            {className: "align-middle"},
            td({className: "px-3 py-3"}, rental.id),
            td({className: "px-3 py-3"}, rental.date),
            td({className: "px-3 py-3"}, rental.user),
            td({className: "px-3 py-3"}, rental.courtId),
            td(
                {className: "px-3 py-3 text-center"},
                a({
                    href: `${API_BASE_URL}#rental/${rental.id}`,
                    className: "btn btn-sm btn-primary"
                }, "Details"),
                a({
                    href: `${API_BASE_URL}#rental/update/${rental.id}`,
                    className: "btn btn-sm btn-success ms-2"
                }, "Update"),
                button({
                    className: "btn btn-sm btn-danger ms-2",
                    onclick: async (e) => {
                        e.preventDefault();
                        if (confirm("Tens a certeza que queres eliminar este rental?")) {
                            await fetchDeleteRental(rental.id);
                            console.log(rental.user.id)
                            window.location.hash = `#rentals/${rental.user}`;
                            window.location.reload(); // força o refresh da página
                        }
                    }
                }, "Delete")
            )
        )
    );

    const pagination = div(
        {className: "d-flex justify-content-between align-items-center mt-5 pt-4 border-top"},
        hasPrevious ? button({className: "btn btn-outline-primary", onclick: onPrevious}, "Previous") : div({}),
        rentals[0] ? a({
                href: `${API_BASE_URL}#users/${rentals[0].user}`,
                className: "btn btn-outline-secondary d-inline-flex align-items-center gap-1"
            },
            span({className: "material-icons", style: "font-size: 1.1em;"}),
            "Back to User Details"
        ) : div({}),
        hasNext ? button({className: "btn btn-outline-primary", onclick: onNext}, "Next") : div({})
    );

    const content = div(
        {className: "container py-5"},
        div(
            {className: "row mb-5 pb-4 border-bottom"},
            div(
                {className: "col-12 text-center"},
                h1({className: "display-4 fw-bold text-primary mb-3"}, "Padel Rentals"),
                p({className: "lead text-muted"}, "Browse all rentals of your user")
            )
        ),
        div(
            {className: "card shadow-sm mb-4"},
            div(
                {className: "card-header bg-primary bg-opacity-75 text-white py-3 d-flex justify-content-between align-items-center"},
                h2({className: "h5 mb-0 fw-bold"}, "Rental List"),
                div(
                    {className: "dropdown"},
                    button({
                        id: "clubActionsDropdown",
                        className: "btn btn-primary rounded-circle d-flex justify-content-center align-items-center",
                        style: "width: 40px; height: 40px;",
                        type: "button",
                        "data-bs-toggle": "dropdown",
                        "aria-expanded": "false"
                    }, span({className: "material-icons"}, "more_vert")),
                    div({
                            className: "dropdown-menu shadow",
                            "aria-labelledby": "clubActionsDropdown"
                        },
                        a({
                                href: `${API_BASE_URL}#rental/create`,
                                className: "dropdown-item d-flex align-items-center gap-2"
                            },
                            span({className: "material-icons text-success"}, "add"),
                            "Create Rental"
                        )
                    )
                ),
            ),
            div(
                {className: "card-body p-0"},
                div(
                    {className: "table-responsive"},
                    table(
                        {className: "table table-hover table-striped mb-0"},
                        thead(
                            {},
                            tr(
                                {className: "bg-light"},
                                th({className: "px-3 py-3"}, "Rental ID"),
                                th({className: "px-3 py-3"}, "Date"),
                                th({className: "px-3 py-3"}, "User Id"),
                                th({className: "px-3 py-3"}, "Court Id"),
                                th({className: "px-3 py-3 text-center"}, "Actions")
                            )
                        ),
                        tbody({}, ...tableRows)
                    )
                )
            ),
        ),
        pagination
    );

    if (!mainContent) {
        console.error("mainContent is null or undefined");
        return;
    }
    mainContent.replaceChildren(content);
    console.log("Rentals list rendered successfully");

    // Setup dropdown functionality after rendering
    setTimeout(() => {
        setupDropdown();
    }, 0);
};