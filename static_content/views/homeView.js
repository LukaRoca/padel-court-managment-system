
import { createElement } from "../html/DSL.js";
import { API_BASE_URL } from "../utils/configs.js";

export const renderHome = (mainContent) => {
    console.log("renderHome called with:", mainContent);

    const div = createElement("div", [
        createElement("a", ["Club List"], { href: `${API_BASE_URL}#clubs` }),
        createElement("h1", ["Home"])
    ]);

    console.log("Generated div:", div);

    if (mainContent) {
        mainContent.replaceChildren(div);
        console.log("Content replaced successfully.");
    } else {
        console.error("mainContent is null or undefined.");
    }
};