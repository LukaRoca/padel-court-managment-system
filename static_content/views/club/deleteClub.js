import { div, h2, button, input, form, label} from "../../utils/elements.js";
import { fetchDeleteClub} from "../../data/clubData.js";

export const renderDeleteClub = (mainContent) => {
    const handleSubmit = async (e) => {
        e.preventDefault();
        const form = e.target;
        const cid = form.cid.value.trim();
        if (!cid) {
            alert("Please insert the club ID.");
            return;
        }
        try {
            await fetchDeleteClub(cid);
            alert("Club deleted with success!");
            window.location.hash = "#clubs";
        } catch (error) {
            alert("Error deleting the club: " + (error.message || error));
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
                    h2({className: "mb-3"}, "Delete Club"),
                    form(
                        {onsubmit: handleSubmit},
                        div(
                            {className: "mb-3"},
                            label({className: "form-label", for: "cid"}, "Club ID"),
                            input({type: "text", name: "cid", className: "form-control", required: true, id: "cid"})
                        ),
                        button({type: "submit", className: "btn btn-danger"}, "Delete Club")
                    )
                )
            )
        )
    );

    mainContent.replaceChildren(content);
};