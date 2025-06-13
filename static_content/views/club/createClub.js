import {fetchCreateClubs} from "../../data/clubData.js";
import {button, div, form, h2, input, label} from "../../utils/elements.js";

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
        )
    );

    mainContent.replaceChildren(content);
};