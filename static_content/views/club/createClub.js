import {fetchCreateClubs} from "../../data/clubData.js";
import {a, button, div, form, h2, input, label, span} from "../../utils/elements.js";
import {API_BASE_URL} from "../../utils/configs.js";

export const renderCreateClub = (mainContent) => {
    const handleSubmit = async (e) => {
        e.preventDefault();
        const form = e.target;
        const data = {
            name: form.name.value,
        };
        try {
            await fetchCreateClubs(data);
            alert("Club created with success!");
            window.location.hash = "#clubs";
        } catch (error) {
            alert("Error creating the club : " + (error.message || error));
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
                    h2({className: "mb-3"}, "Create new club"),
                    form(
                        {onsubmit: handleSubmit},
                        div(
                            {className: "mb-3"},
                            label({className: "form-label", for: "name"}, "Club Name"),
                            input({type: "text", name: "name", className: "form-control", required: true, id: "name"})
                        ),
                        button({type: "submit", className: "btn btn-primary"}, "Create")
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

    mainContent.replaceChildren(content);
};