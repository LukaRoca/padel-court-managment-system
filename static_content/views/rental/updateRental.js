import {getHashParams} from "../../utils/utils.js";
import {fetchRentalById, fetchUpdateRental} from "../../data/rentalData.js";
import {button, div, form, h2, input, label} from "../../utils/elements.js";

export const renderUpdateRental = (mainContent, rentalId) => {
    const handleSubmit = async (e) => {
        e.preventDefault();
        const form = e.target;
        const data = `date=${form.date.value}&initD=${form.initDuration.value}&endD=${form.endDuration.value}`
        const context = getHashParams().context
        try {
            const update = await fetchUpdateRental(rentalId, data);
            alert("Rental updated with sucess")
            const rental = await fetchRentalById(update.id)
            if (context === "user") {
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
                        button({type: "submit", className: "btn btn-primary"}, "Update")
                    )
                )
            )
        )
    );

    mainContent.replaceChildren(content);
}