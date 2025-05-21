import {a, button, div, h1, h2, h3, p, span, table, tbody, td, th, thead, tr, form, input, label} from "../utils/elements.js";
import {API_BASE_URL} from "../utils/configs.js";
import {fetchCreateRental, fetchDeleteRental, fetchRentalById, fetchUpdateRental} from "../data/rentalData.js";
import {setupDropdown} from "../utils/utils.js";
import {getHashParams} from "../utils/utils.js";

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
                                        span({className: "fs-5"}, `${rental.duration.hours}h`)
                                    )
                                ),
                                div(
                                    {className: "col-md-6"},
                                    div({className: "mb-2"},
                                        span({className: "fw-bold d-block text-muted small"}, "Initial Hours"),
                                        span({className: "fs-5"},  `${rental.duration.initDuration}h`)
                                    )
                                ),
                                div(
                                    {className: "col-md-6"},
                                    div({className: "mb-2"},
                                        span({className: "fw-bold d-block text-muted small"}, "Final Hours"),
                                        span({className: "fs-5"}, `${rental.duration.endDuration}h`)
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
                                            href: `${API_BASE_URL}#users/${rental.user.id}`,
                                            className: "btn btn-outline-primary btn-sm"
                                        }, `View User #${rental.user.id}`)
                                    )
                                ),
                                div(
                                    {className: "col-md-6"},
                                    div({className: "d-flex flex-column"},
                                        span({className: "fw-bold d-block text-muted small mb-2"}, "Court"),
                                        a({
                                            href: `${API_BASE_URL}#court/${rental.court.id}`,
                                            className: "btn btn-outline-primary btn-sm"
                                        }, `View Court #${rental.court.id}`)
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
            td({className: "px-3 py-3"}, rental.user.id),
            td({className: "px-3 py-3"}, rental.court.id),
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
                            window.location.hash = `#rentals/${rental.user.id}`;
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
                href: `${API_BASE_URL}#users/${rentals[0].user.id}`,
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

export const renderRentalsByCrid = (mainContent, rentals, onNext, onPrevious, hasNext, hasPrevious) => {
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
            td({className: "px-3 py-3"}, rental.user.id),
            td({className: "px-3 py-3"}, rental.court.id),
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
                            console.log(rental.court.id)
                            window.location.hash = `#court/rentals/${rental.court.id}`;
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
                href: `${API_BASE_URL}#court/${rentals[0].court.id}`,
                className: "btn btn-outline-secondary d-inline-flex align-items-center gap-1"
            },
            span({className: "material-icons", style: "font-size: 1.1em;"}),
            "Back to Court Details"
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
                p({className: "lead text-muted"}, "Browse all rentals of your court")
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
            )
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

export const renderCreateRental = (mainContent) => {
    const handleSubmit = async (e) => {
        e.preventDefault();
        const form = e.target;
        const data = {
            cid: form.cid.value,
            crid: form.crid.value,
            date: form.date.value,
            initDuration: form.initDuration.value,
            endDuration: form.endDuration.value
        };
        const token = form.token.value;
        try {
            const created = await fetchCreateRental(data, token);
            alert("Rental created with success!");
            const rental = await fetchRentalById(created.id);
            window.location.hash = `#rental/${rental.id}`;
        } catch (error) {
            alert("Error creating rental : " + (error.message || error));
        }
    };

    const content = div(
        {className: "container py-5"},
        div(
            {className: "row justify-content-center"},
            div(
                {className: "col-md-6"},
                div(
                    {className: "card p-4 border rounded shadow-sm"},
                    h2({className: "mb-3"}, "Create new Rental"),
                    form(
                        {onsubmit: handleSubmit},
                        div(
                            {className: "mb-3"},
                            label({className: "form-label", for: "cid"}, "Club ID"),
                            input({type: "number", name: "cid", className: "form-control", required: true, id: "cid"})
                        ),
                        div(
                            {className: "mb-3"},
                            label({className: "form-label", for: "crid"}, "Court ID"),
                            input({type: "number", name: "crid", className: "form-control", required: true, id: "crid"})
                        ),
                        div(
                            {className: "mb-3"},
                            label({className: "form-label", for: "date"}, "Rental Date"),
                            input({type: "text", name: "date", className: "form-control", required: true, id: "date"})
                        ),
                        div(
                            {className: "mb-3"},
                            label({className: "form-label", for: "initDuration"}, "Start Time"),
                            input({type: "number", name: "initDuration", className: "form-control", required: true, id: "initDuration"})
                        ),
                        div(
                            {className: "mb-3"},
                            label({className: "form-label", for: "endDuration"}, "End Time"),
                            input({type: "number", name: "endDuration", className: "form-control", required: true, id: "endDuration"})
                        ),
                        div(
                            {className: "mb-3"},
                            label({className: "form-label", for: "token"}, "User Token"),
                            input({type: "text", name: "token", className: "form-control", required: true, id: "token"})
                        ),
                        button({type: "submit", className: "btn btn-primary"}, "Create")
                    )
                )
            )
        )
    );

    mainContent.replaceChildren(content);
};

export const renderUpdateRental = (mainContent, rentalId) => {
    const handleSubmit = async (e) => {
        e.preventDefault();
        const form = e.target;
        const data = `date=${form.date.value}&initD=${form.initDuration.value}&endD=${form.endDuration.value}`
        const token = form.token.value;
        const context = getHashParams().context
        try {
            const update = await fetchUpdateRental(rentalId, data, token);
            alert("Rental updated with sucess")
            const rental = await fetchRentalById(update.id)
            if (context == "user") {
                window.location.hash = `rentals/${rental.user.id}`
            } else window.location.hash = `court/rentals/${rental.court.id}`
        } catch (error) {
            alert("Error creating rental : " + (error.message || error));
        }
    };
    const content = div(
        {className: "container py-5"},
        div(
            {className: "row justify-content-center"},
            div(
                {className: "col-md-6"},
                div(
                    {className: "card p-4 border rounded shadow-sm"},
                    h2({className: "mb-3"}, "Update a Rental"),
                    form(
                        {onsubmit: handleSubmit},
                        div(
                            {className: "mb-3"},
                            label({className: "form-label", for: "date"}, "Rental Date"),
                            input({type: "text", name: "date", className: "form-control", required: true, id: "date"})
                        ),
                        div(
                            {className: "mb-3"},
                            label({className: "form-label", for: "initDuration"}, "Start Time"),
                            input({type: "number", name: "initDuration", className: "form-control", required: true, id: "initDuration"})
                        ),
                        div(
                            {className: "mb-3"},
                            label({className: "form-label", for: "endDuration"}, "End Time"),
                            input({type: "number", name: "endDuration", className: "form-control", required: true, id: "endDuration"})
                        ),
                        div(
                            {className: "mb-3"},
                            label({className: "form-label", for: "token"}, "User Token"),
                            input({type: "text", name: "token", className: "form-control", required: true, id: "token"})
                        ),
                        button({type: "submit", className: "btn btn-primary"}, "Update")
                    )
                )
            )
        )
    );

    mainContent.replaceChildren(content);
}

export const renderDeleteRental = (mainContent, setter) => {
    const handleSubmit = async (e) => {
        e.preventDefault();
        const form = e.target;
        const rentalId = form.rid.value
        try {
            await fetchDeleteRental(rentalId);
            alert("Rental updated with sucess")
            window.location.hash = `#rentals/${rentalId}`;
        } catch (error) {
            alert("Error deleting rental : " + (error.message || error));
        }
    };
    const content = div(
        {className: "container py-5"},
        div(
            {className: "row justify-content-center"},
            div(
                {className: "col-md-6"},
                div(
                    {className: "card p-4 border rounded shadow-sm"},
                    h2({className: "mb-3"}, "Update a Rental"),
                    form(
                        {onsubmit: handleSubmit},
                        div(
                            {className: "mb-3"},
                            label({className: "form-label", for: "cid"}, "Rental ID"),
                            input({type: "number", name: "rid", className: "form-control", required: true, id: "cid"})
                        ),
                        button({type: "submit", className: "btn btn-primary"}, "Delete")
                    )
                )
            )
        )
    );

    mainContent.replaceChildren(content);
}