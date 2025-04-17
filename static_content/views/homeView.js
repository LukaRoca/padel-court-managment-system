import {div, a, h1, p} from "../utils/elements.js";
import { API_BASE_URL } from "../utils/configs.js";

export const renderHome = (mainContent) => {
    console.log("renderHome called with:", mainContent);

    const content =  div(
        {},
        a({ href: `${API_BASE_URL}#clubs` }, "Club List"),
        h1({}, "Home"),
        p({}, "Chelas 2025"),
    );

    console.log("Generated content:", content);

    if (mainContent) {
        mainContent.replaceChildren(content);
        console.log("Content replaced successfully.");
    } else {
        console.error("mainContent is null or undefined.");
    }
};