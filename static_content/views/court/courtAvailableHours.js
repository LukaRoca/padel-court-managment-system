import {fetchCourtAvailableHours} from "../../data/courtData.js";
import {renderException} from "../Exeptions.js";
import {button, div, form, h2, h3, input, label, span} from "../../utils/elements.js";

export const renderCourtAvailableHours = (mainContent, court, availableHours = null) => {
    const handleDateSubmit = async (e) => {
        e.preventDefault();
        try {
            const selectedDate = e.target.date.value;
            if (!court.id) {
                new Error("ID of court is invalid or not provided.");
            }
            const hours = await fetchCourtAvailableHours(court.id, selectedDate, court.club.id);
            renderCourtAvailableHours(mainContent, court, hours);
            window.location.hash = `court/hours/${court.id}/${selectedDate}`;
        } catch (error) {
            renderException(mainContent, error);
        }
    };
    const content = div(
        { className: "container py-4" },
        h2({ className: "mb-4" }, `Horas disponíveis para o court: ${court.name || court.id}`),
        form(
            { onsubmit: handleDateSubmit, className: "mb-4" },
            div(
                { className: "mb-3" },
                label({
                    className: "form-label",
                    for: "date"
                }, "Selecione uma data:"),
                input({
                    type: "date",
                    id: "date",
                    name: "date",
                    className: "form-control",
                    required: true
                })
            ),
            button({
                type: "submit",
                className: "btn btn-primary"
            }, "Buscar horários")
        ),

        availableHours && div(
            { className: "row mt-4" },
            h3({ className: "mb-3" }, "Horas disponíveis :"),
            div(
                { className: "d-flex flex-wrap gap-2" },
                ...availableHours.map(hour =>
                    span(
                        {
                            className: "badge bg-success fs-5 px-3 py-2",
                            style: "min-width: 60px; cursor: default;"
                        },
                        `${hour}:00`
                    )
                )
            )
        )
    );

    mainContent.replaceChildren(content);
};
