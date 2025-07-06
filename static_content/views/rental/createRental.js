import {fetchClubs} from "../../data/clubData.js";
import {button, div, form, h2, input, label, option, select} from "../../utils/elements.js";
import {fetchCourts} from "../../data/courtData.js";
import {fetchCreateRental, fetchRentalById} from "../../data/rentalData.js";
import {renderException} from "../Exeptions.js";

export const renderSelectClub = async (mainContent) => {
    const clubs = await fetchClubs(10, 0) // Valores provisórios depois mudar

    return select(
        { className: "form-select", id: "clubSelect", name: "cid", required: true },
        option({ value: "" }, "Escolha o clube"),
        ...(clubs.list || []).map(club =>
            option({ value: club.id }, club.name)
        )
    );

};

export const renderSelectCourt = async (mainContent, clubID) => {
    const courts = await fetchCourts(clubID, 10, 0);

    return select(
        { className: "form-select", id: "courtSelect", name: "crid", required: true },
        option({ value: "" }, "Escolha o court"),
        ...(courts.list || []).map(court =>
            option({ value: court.id }, court.name)
        )
    );
}


export const renderCreateRental = async (mainContent) => {
    if (!mainContent) {
        throw new Error("mainContent is required");
    }

    const clubDiv = div({});
    const courtDiv = div({});
    const formDiv = div({});
    let currentSelectCourt = null;

    const selectClub = await renderSelectClub(mainContent);
    clubDiv.appendChild(selectClub);

    mainContent.replaceChildren(
        div(
            { className: "container py-5" },
            h2({ className: "mb-4" }, "Create New Rental"),
            clubDiv,
            courtDiv,
            formDiv
        )
    );

    const handleSubmit = async (e, selectedClubId, selectedCourtId) => {
        e.preventDefault();
        const form = e.target;
        const date = form.date.value;
        const initDuration = parseInt(form.initDuration.value);
        const endDuration = parseInt(form.endDuration.value);


        const data = { cid: selectedClubId, crid: selectedCourtId, date, initDuration, endDuration };

        try {
            const submitButton = form.querySelector('button[type="submit"]');
            submitButton.disabled = true;
            submitButton.textContent = "Creating...";

            const created = await fetchCreateRental(data);
            const rental = await fetchRentalById(created.id);
            window.location.hash = `#rental/${rental.id}`;
        } catch (error) {
            renderException(mainContent, error);
        }
    };

    selectClub.addEventListener("change", async (e) => {
        const selectedClubId = e.target.value;
        selectClub.disabled = true;

        try {
            const selectCourt = await renderSelectCourt(mainContent, selectedClubId);
            courtDiv.replaceChildren(selectCourt);
            currentSelectCourt = selectCourt;

            selectCourt.addEventListener("change", (e) => {
                const selectedCourtId = e.target.value;
                selectCourt.disabled = true;

                formDiv.replaceChildren(
                    form(
                        { onsubmit: (e) => handleSubmit(e, selectedClubId, selectedCourtId) },
                        div(
                            { className: "mb-3" },
                            label({ className: "form-label", for: "date" }, "Rental Date"),
                            input({
                                type: "date",
                                name: "date",
                                className: "form-control",
                                required: true,
                                id: "date",
                                min: new Date().toISOString().split('T')[0]
                            })
                        ),
                        div(
                            { className: "mb-3" },
                            label({ className: "form-label", for: "initDuration" }, "Start Time"),
                            input({
                                type: "number",
                                name: "initDuration",
                                className: "form-control",
                                required: true,
                                id: "initDuration",
                                min: "0",
                                max: "23",
                                placeholder: "Enter hour (0-23)"
                            })
                        ),
                        div(
                            { className: "mb-3" },
                            label({ className: "form-label", for: "endDuration" }, "End Time"),
                            input({
                                type: "number",
                                name: "endDuration",
                                className: "form-control",
                                required: true,
                                id: "endDuration",
                                min: "0",
                                max: "23",
                                placeholder: "Enter hour (0-23)"
                            })
                        ),
                        button({ type: "submit", className: "btn btn-primary" }, "Create Rental")
                    )
                );
            });
        } catch (error) {
            renderException(mainContent, error);
            selectClub.disabled = false;
        }
    });
};
