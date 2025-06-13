import { div, h2, button, form, input, label} from "../../utils/elements.js";
import {fetchCreateCourt, fetchCourtById} from "../../data/courtData.js";

export const renderCreateCourt = (mainContent, cid) => {
    const handleSubmit = async (e) => {
        e.preventDefault();
        const form = e.target;
        const data = {
            name: form.name.value,
            cid: cid
        };
        try {
            const created = await fetchCreateCourt(data);
            if (!created || !created.id) {
                throw new Error("The Court created did not return an ID");
            }
            await fetchCourtById(created.id);
            alert("Court created successfully");
            window.location.hash = `#court/${created.id}`;
        } catch (err) {
            alert("Error creating the court : " + (err.message || err));
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
                    form(
                        {onsubmit: handleSubmit},
                        h2({className: "mb-3"}, "Create new Court"),
                        div(
                            {className: "mb-3"},
                            label({className: "form-label", for: "name"}, "Court Name"),
                            input({type: "text", name: "name", className: "form-control", required: true, id: "name"})
                        ),
                        button({type: "submit", className: "btn btn-primary"}, "Create"),
                    )
                )
            )
        )
    );

    mainContent.replaceChildren(content);
};