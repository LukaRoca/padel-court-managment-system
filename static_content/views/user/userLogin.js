import {div, h1,button, input, label, form} from "../../utils/elements.js";
import {API_BASE_URL} from "../../utils/configs.js";
import {fetchLoginUser} from "../../data/userData.js";

export const renderLoginForm = (mainContent) => {
    const handleSubmit = async (event) => {
        event.preventDefault();
        const formData = new FormData(event.target);
        const loginData = {
            email: formData.get('email'),
            password: formData.get('password')
        };

        try {
            const response = await fetchLoginUser(loginData);
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
