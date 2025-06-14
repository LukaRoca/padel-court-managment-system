import {a, button, div, h1, h2, input, p, span} from "../../utils/elements.js";
import {API_BASE_URL} from "../../utils/configs.js";
import {setupDropdown} from "../../utils/utils.js";

export const renderClubs = (mainContent, clubs, onNext, onPrevious, hasNext, hasPrevious, onSearch) => {
    console.log("renderClubs called with mainContent:", mainContent);

    if (!Array.isArray(clubs)) {
        console.error("clubs is not an array:", clubs);
        clubs = [];
    }

    const filterClubs = (searchTerm) => {
        const trimmedSearch = searchTerm.trim();

        if (!trimmedSearch) {
            onSearch('');
            return;
        }

        const normalizedSearch = trimmedSearch.toLowerCase();

        const filteredClubs = clubs.filter(club =>
            club.name.toLowerCase().includes(normalizedSearch)
        );

        renderClubCards(filteredClubs);
    };

    const renderClubCards = (clubsToRender) => {
        const cards = clubsToRender.map((club) =>
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
                        p({className: "card-text mb-2"}, span({className: "fw-bold"}, "Club ID: "), club.id),
                        p({className: "small text-muted mt-3"}, "View club details and available courts")
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
        );

        const clubCardsContainer = document.querySelector('.row.row-cols-1.row-cols-md-2.row-cols-lg-3.g-4');
        if (clubCardsContainer) {
            clubCardsContainer.innerHTML = '';
            cards.forEach(card => clubCardsContainer.appendChild(card));
        }
    };

    const pagination = div(
        {className: "d-flex justify-content-between align-items-center mt-5 pt-4 border-top"},
        hasPrevious ? button({className: "btn btn-outline-primary", onclick: onPrevious}, "Previous") : div({}),
        a({
                href: `${API_BASE_URL}#home`,
                className: "btn btn-outline-secondary d-inline-flex align-items-center gap-1"
            },
            span({className: "material-icons", style: "font-size: 1.1em;"}),
            "Back to Home"
        ),
        hasNext ? button({className: "btn btn-outline-primary", onclick: onNext}, "Next") : div({})
    );

    const content = div(
        {className: "container py-5"},
        div(
            {className: "row mb-5 pb-4 border-bottom"},
            div(
                {className: "col-12 text-center"},
                h1({className: "display-4 fw-bold text-primary mb-3"}, "Padel Clubs"),
                p({className: "lead text-muted"}, "Browse our partner padel clubs and discover their available courts"),
                div(
                    {className: "mt-4 d-flex justify-content-center align-items-center gap-3"},
                    div(
                        {className: "flex-grow-1", style: "max-width: 500px;"},
                        input({
                            type: "text",
                            className: "form-control",
                            placeholder: "Search clubs by name...",
                            oninput: (event) => filterClubs(event.target.value)
                        })
                    ),
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
                                    href: `${API_BASE_URL}#clubc/create`,
                                    className: "dropdown-item d-flex align-items-center gap-2"
                                },
                                span({className: "material-icons text-success"}, "add"),
                                "Create Club"
                            ),
                            a({
                                    href: "#",
                                    className: "dropdown-item d-flex align-items-center gap-2 disabled",
                                    style: "color: #6c757d; pointer-events: none;"
                                },
                                span({className: "material-icons text-primary"}, "edit"),
                                "Update Club "
                            ),
                            a({
                                    href: `${API_BASE_URL}#clubd/delete`,
                                    className: "dropdown-item d-flex align-items-center gap-2",
                                },
                                span({className: "material-icons text-danger"}, "delete"),
                                "Delete Club"
                            )
                        )
                    )
                )
            )
        ),
        div({className: "row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4"}, ...clubs.map((club) =>
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
                        span({className: "material-icons display-1 text-muted"}, "image")
                    ),
                    div(
                        {className: "card-body py-3 px-3"},
                        p({className: "card-text mb-2"}, span({className: "fw-bold"}, "Club ID: "), club.id),
                        p({className: "small text-muted mt-3"}, "View club details and available courts")
                    ),
                    div(
                        {className: "card-footer bg-transparent border-top-0 pt-0 pb-3 px-3"},
                        a({
                                href: `${API_BASE_URL}#club/${club.id}`,
                                className: "btn btn-primary w-100 d-inline-flex align-items-center justify-content-center gap-2"
                            },
                            span({className: "material-icons"}, "sports_tennis"),
                            "Club Details"
                        )
                    )
                )
            )
        )),

        pagination
    );

    if (!mainContent) {
        console.error("mainContent is null or undefined");
        return;
    }

    mainContent.replaceChildren(content);
    console.log("Clubs rendered successfully");

    // Setup dropdown functionality after rendering
    setTimeout(() => {
        setupDropdown();
    }, 0);
};