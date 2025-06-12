import {button, div, h2, h3, p, form, input, label,strong, h5} from "../../utils/elements.js";
import {searchRentalsByDate} from "../../handlers/rentalHandler.js";

export const renderRentalsByDate = (mainContent, rentals = null) => {
    const handleSubmit = async (e) => {
        e.preventDefault();
        const form = e.target;
        const date = form.date.value;
        try {
            await searchRentalsByDate(mainContent, { date });
        } catch (error) {
            alert("Error searching rentals: " + (error.message || error));
        }
    };

    const renderRentalsList = (rentals) => {
        if (!rentals || rentals.length === 0) {
            return div(
                {className: "alert alert-warning text-center mt-4"},
                "No rentals found for the selected date."
            );
        }

        return div(
            {className: "mt-4"},
            h3({className: "mb-3 text-success"}, `Found ${rentals.length} rental${rentals.length === 1 ? '' : 's'}`),
            div(
                {className: "row"},
                ...rentals.map(rental =>
                    div(
                        {className: "col-md-6 col-lg-4 mb-3"},
                        div(
                            {className: "card h-100 shadow-sm"},
                            div(
                                {className: "card-body"},
                                rental.rid && h5({className: "card-title"}, `Rental #${rental.rid}`),
                                rental.user ?
                                    p({className: "card-text"}, strong({}, "User: "), rental.user.name || rental.user.username || `User ${rental.user.id || rental.user.uid}`) : null,
                                rental.court ?
                                    p({className: "card-text"}, strong({}, "Court: "), rental.court.name || rental.court.location || `Court ${rental.court.id || rental.court.cid}`) : null,
                                rental.date ?
                                    p({className: "card-text"}, strong({}, "Date: "), new Date(rental.date).toLocaleDateString()) : null,
                                rental.duration ?
                                    p({className: "card-text"}, strong({}, "Duration: "), `${rental.duration.hours || 0}h `) : null,
                            )
                        )
                    )
                )
            )
        );
    };

    const content = div(
        {className: "container py-5"},
        div(
            {className: "row justify-content-center"},
            div(
                {className: "col-md-8"},
                div(
                    {className: "card p-4 border rounded shadow-sm"},
                    h2({className: "mb-4 text-center"}, "Search Rentals by Date"),
                    form(
                        {onsubmit: handleSubmit},
                        div(
                            {className: "row g-3 align-items-end"},
                            div(
                                {className: "col-md-8"},
                                label({className: "form-label", for: "date"}, "Select Date"),
                                input({
                                    type: "date",
                                    name: "date",
                                    className: "form-control",
                                    required: true,
                                    id: "date"
                                })
                            ),
                            div(
                                {className: "col-md-4"},
                                button({type: "submit", className: "btn btn-primary w-100"}, "Search")
                            )
                        )
                    ),
                    rentals !== null ? renderRentalsList(rentals) : null
                )
            )
        )
    );

    mainContent.replaceChildren(content);
};