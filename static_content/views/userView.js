import {a, div, h1, p, span, h2, button, input, label, form} from "../utils/elements.js";
import {API_BASE_URL} from "../utils/configs.js";
import {fetchCreateUser, fetchLoginUser} from "../data/userData.js";

export const renderUserDetail = (mainContent, user) => {
    console.log("renderUserDetail called with:", user);

    const content = div(
        {className: "container py-5"},

        div(
            {className: "row mb-5 pb-4 border-bottom"},
            div(
                {className: "col-12"},
                h1({className: "display-4 fw-bold text-primary mb-3"}, "User Profile"),
                p({className: "lead text-muted"}, "View user information and related rentals")
            )
        ),

        div(
            {className: "row mb-5"},
            div(
                {className: "col-md-8 mx-auto"},
                div(
                    {className: "card border-0 shadow-sm"},
                    div(
                        {className: "card-header bg-primary bg-opacity-75 text-white py-3"},
                        h2({className: "h4 mb-0"}, "User Information")
                    ),
                    div(
                        {className: "card-body p-4"},
                        div(
                            {className: "text-center py-4 mb-4 bg-light rounded-3 border-bottom"},
                            span({className: "material-icons display-1 text-primary"}, "image")
                        ),
                        div(
                            {className: "row mb-3 pb-3 border-bottom"},
                            div({className: "col-4 fw-bold"}, "Name:"),
                            div({className: "col-8"}, user.name?.name || user.name || "N/A")
                        ),
                        div(
                            {className: "row mb-3 pb-3 border-bottom"},
                            div({className: "col-4 fw-bold"}, "User ID:"),
                            div({className: "col-8"}, user.id?.id || user.id || "N/A")
                        ),
                        div(
                            {className: "row"},
                            div({className: "col-4 fw-bold"}, "Email:"),
                            div({className: "col-8"}, user.email || "N/A")
                        )
                    )
                )
            )
        ),

        div(
            {className: "row mb-5"},
            div(
                {className: "col-md-8 mx-auto"},
                div(
                    {className: "d-grid gap-2"},
                    a({
                            href: `${API_BASE_URL}#rentals/${user.id?.id || user.id || ''}`,
                            className: "btn btn-primary d-inline-flex align-items-center justify-content-center gap-2"
                        },
                        span({className: "material-icons"}),
                        "Rentals"
                    )
                )
            ),
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

    if(!mainContent) {
        console.error("mainContent is null or undefined");
        return;
    }

    mainContent.replaceChildren(content);
    console.log("User detail rendered successfully");
};

export const renderUsers = (mainContent, users, onNext, onPrevious, hasNext, hasPrevious) => {
    const list = users.map(user =>
        p({}, `User: ${user.name}`)
    );

    const buttons = div({className: "d-flex justify-content-between mt-3"},
        hasPrevious ? button({className: "btn btn-outline-primary", onclick: onPrevious}, "Previous") : div({}),
        hasNext ? button({className: "btn btn-outline-primary", onclick: onNext}, "Next") : div({})
    );

    if(!mainContent) {
        console.error("mainContent is null or undefined");
        return;
    }

    mainContent.replaceChildren(
        div({className: "container py-5"},
            ...list,
            buttons
        )
    );
}

export const renderCreateUserForm = (mainContent) => {

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

export const renderLoginForm = (mainContent) => {
    const handleSubmit = async (event) => {
        event.preventDefault();
        const formData = new FormData(event.target);
        const loginData = {
            email: formData.get('email'),
            password: formData.get('password')
        };

        try {
            const response = await fetchLoginUser(loginData.email, loginData.password);
            sessionStorage.setItem('user', JSON.stringify(response));
            window.location.href = `${API_BASE_URL}#home`;
        } catch (error) {
            console.error("Erro no login:", error);
            alert("Email ou senha inválidos");
        }
    };

    const content = div(
        {className: "container py-5"},
        div(
            {className: "row justify-content-center"},
            div(
                {className: "col-md-6"},
                div(
                    {className: "card"},
                    div(
                        {className: "card-body"},
                        h1({className: "text-center mb-4"}, "Login"),
                        form(
                            {
                                onSubmit: handleSubmit,
                                className: "needs-validation"
                            },
                            div(
                                {className: "mb-3"},
                                label({htmlFor: "email", className: "form-label"}, "Email"),
                                input({
                                    type: "email",
                                    className: "form-control",
                                    id: "email",
                                    name: "email",
                                    required: true
                                })
                            ),
                            div(
                                {className: "mb-3"},
                                label({htmlFor: "password", className: "form-label"}, "Password"),
                                input({
                                    type: "password",
                                    className: "form-control",
                                    id: "password",
                                    name: "password",
                                    required: true
                                })
                            ),
                            button(
                                {type: "submit", className: "btn btn-primary w-100"},
                                "Login"
                            )
                        )
                    )
                )
            )
        )
    );

    mainContent.replaceChildren(content);
};
