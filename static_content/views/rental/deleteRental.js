import {fetchDeleteRental} from "../../data/rentalData.js";
import {button, div, form, h2, input, label} from "../../utils/elements.js";

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
