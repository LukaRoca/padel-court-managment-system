import {button, div, p} from "../../utils/elements.js";

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