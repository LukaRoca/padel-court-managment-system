import {a, button, div, h1, h2, h3, p, span, table, tbody, td, th, thead, tr, form} from "../utils/elements.js";
import {API_BASE_URL} from "../utils/configs.js";
import {fetchCreateRental, fetchRentalById} from "../data/rentalData.js";

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
            {className: "mt-4"},
            a(
                {
                    href: `${API_BASE_URL}#rental/create`,
                    className: "btn btn-success d-inline-flex align-items-center gap-2"
                },
                "Novo Rental"
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
                }, "Details")
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
                {className: "card-header bg-primary bg-opacity-75 text-white py-3"},
                h2({className: "h5 mb-0 fw-bold"}, "Rental List")
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
                }, "Details")
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
                {className: "card-header bg-primary bg-opacity-75 text-white py-3"},
                h2({className: "h5 mb-0 fw-bold"}, "Rental List")
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
};

export const renderCreateRental = (mainContent) => {
    const form = document.createElement("form");
    form.className = "p-4 border rounded";
    form.innerHTML = `
        <h2 class="mb-3">Criar Novo Rental</h2>
        <div class="mb-3">
            <label class="form-label">ID do Clube</label>
            <input type="number" name="cid" class="form-control" required>
        </div>
        <div class="mb-3">
            <label class="form-label">ID do Court</label>
            <input type="number" name="crid" class="form-control" required>
        </div>
        <div class="mb-3">
            <label class="form-label">Data do Rental</label>
            <input type="text" name="date" class="form-control" required>
        </div>
        <div class="mb-3">
            <label class="form-label">Hora do Começo</label>
            <input type="number" name="initDuration" class="form-control" required>
        </div>
        <div class="mb-3">
            <label class="form-label">Hora do Fim</label>
            <input type="number" name="endDuration" class="form-control" required>
        </div>
        <div class="mb-3">
            <label class="form-label">Token do User</label>
            <input type="text" name="token" class="form-control" required>
        </div>
        <button type="submit" class="btn btn-primary">Criar</button>
    `;
    form.onsubmit = async (e) => {
        e.preventDefault();
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
            alert("Rental criado com sucesso!");
            console.log(created)
            const rental = await fetchRentalById(created.id)
            console.log(rental)
            window.location.hash = `#rental/${rental.id}`;
        } catch (error) {
            alert("Erro ao criar rental : " + (err.message || err));
        }

    };
    mainContent.replaceChildren(form);
}