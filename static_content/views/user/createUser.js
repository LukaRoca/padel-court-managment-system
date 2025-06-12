import {fetchCreateUser} from "../../data/userData.js";
import {API_BASE_URL} from "../../utils/configs.js";
import {button, div, form, h1, input, label, p} from "../../utils/elements.js";

export const renderCreateUser = (mainContent) => {

    const handleSubmit = async (event) => {
        event.preventDefault();
        const formData = new FormData(event.target);
        const userData = {
            name: formData.get('name'),
            email: formData.get('email'),
            password: formData.get('password')
        };

        try {
            await fetchCreateUser(userData);
            alert("User created successfully!");
            window.location.href = `${API_BASE_URL}#home`;
        } catch (error) {
            console.error("Error creating user:", error);
        }

    };

    const content = div(
        {className: "container py-5"},
        div(
            {className: "row mb-5 pb-4 border-bottom"},
            div(
                {className: "col-12"},
                h1({className: "display-4 fw-bold text-primary mb-3"}, "Create User"),
                p({className: "lead text-muted"}, "Fill in the details to create a new user")
            )
        ),
        div(
            {className: "row mb-5"},
            div(
                {className: "col-md-6 mx-auto"},
                form(
                    {onSubmit: handleSubmit, className: "card border-0 shadow-sm p-4"},
                    div(
                        {className: "mb-3"},
                        label({htmlFor: "name", className: "form-label"}, "Name"),
                        input({type: "text", id: "name", name: "name", className: "form-control", required: true})
                    ),
                    div(
                        {className: "mb-3"},
                        label({htmlFor: "email", className: "form-label"}, "Email"),
                        input({type: "email", id: "email", name: "email", className: "form-control", required: true})
                    ),
                    div(
                        {className: "mb-3"},
                        label({htmlFor: "password", className: "form-label"}, "Password"),
                        input({type: "password", id: "password", name: "password", className: "form-control", required: true})
                    ),
                    button({type: "submit", className: "btn btn-primary w-100"}, "Create User")
                )
            )
        )
    );

    mainContent.replaceChildren(content);
}
