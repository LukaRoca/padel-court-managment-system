import {API_BASE_URL} from "../utils/configs.js";
import {a, div, h1, p, span, h2, button, input} from "../utils/elements.js";
import {fetchCreateClubs} from "../data/clubData";

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
                    {className: "mt-4"},
                    input({
                        type: "text",
                        className: "form-control",
                        placeholder: "Search clubs by name...",
                        oninput: (event) => filterClubs(event.target.value)
                    })
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
        )),
        pagination
    );

    if (!mainContent) {
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

export const renderCreateClub = (mainContent) => {
    const form = document.createElement("form");
    form.className = "p-4 border rounded";
    form.innerHTML = `
        <h2 class="mb-3">Criar Novo Clube</h2>
        <div class="mb-3">
            <label class="form-label">Nome do Clube</label>
            <input type="text" name="name" class="form-control" required>
        </div>
        <div class="mb-3">
            <label class="form-label">ID do Proprietário</label>
            <input type="text" name="ownerId" class="form-control" required>
        </div>
        <button type="submit" class="btn btn-primary">Criar</button>
    `;
    form.onsubmit = async (e) => {
        e.preventDefault();
        const data = {
            name: form.name.value,
            ownerId: form.ownerId.value
        };
        await fetchCreateClubs(data);
    };
    mainContent.replaceChildren(form);
}