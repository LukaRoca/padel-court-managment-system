import {a, button, div, span} from "../utils/elements.js";
import {API_BASE_URL} from "../utils/configs.js";

// Verify changes in sessionStorage
const createStorageObserver = (callback) => {
    let previousUserState = sessionStorage.getItem('user');

    return () => {
        const currentUserState = sessionStorage.getItem('user');
        if (previousUserState !== currentUserState) {
            previousUserState = currentUserState;
            callback();
        }
    };
};

export function renderHeader() {
    const headerContainer = document.getElementById("header");

    const updateHeader = () => {
        const userJson = sessionStorage.getItem('user');
        const user = userJson ? JSON.parse(userJson) : null;
        const userId = user?.uid;

        const handleLogout = () => {
            sessionStorage.clear();
            window.location.hash = '#login';
            window.location.reload();
        };

        const newHeader = div(
            { className: 'navbar navbar-expand-lg navbar-light bg-light px-4' },
            div(
                { className: 'container-fluid d-flex justify-content-between' },
                a({
                        href: `${API_BASE_URL}#home`,
                        className: "btn btn-outline-secondary d-inline-flex align-items-center gap-1"
                    },
                    span({className: "material-icons", style: "font-size: 1.1em;"}, "home")),
                div(
                    { className: 'd-flex align-items-center gap-2' },
                    user ?
                        div(
                            {className: "d-flex align-items-center gap-3"},
                            span({className: "text-primary"}, `User ${userId}`),
                            button(
                                {
                                    className: 'btn btn-outline-danger',
                                    onclick: handleLogout
                                },
                                'Logout'
                            )
                        ) :
                        div(
                            {className: "d-flex gap-2"},
                            a(
                                {
                                    href: "#user/login",
                                    className: "btn btn-primary"
                                },
                                "Login"
                            ),
                            a(
                                {
                                    href: "#userc/create",
                                    className: "btn btn-outline-secondary"
                                },
                                "Sign Up"
                            )
                        )
                )
            )
        );

        if (headerContainer) {
            headerContainer.replaceChildren(newHeader);
        }
    };


    window.addEventListener('hashchange', updateHeader);


    const storageObserver = createStorageObserver(updateHeader);
    setInterval(storageObserver, 100);


    updateHeader();
}