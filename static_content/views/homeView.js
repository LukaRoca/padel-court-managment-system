import { div, a, h1 } from "../utils/elements.js";
import { API_BASE_URL } from "../utils/configs.js";

export const renderHome = async (mainContent) => {
    console.log("renderHome called with:", mainContent);

    const content = await div(
        {},
        await a({ href: `${API_BASE_URL}#clubs` }, "Club List"),
        await h1({}, "Home")
    );

    console.log("Generated content:", content);

    if (mainContent) {
        mainContent.replaceChildren(content);
        console.log("Content replaced successfully.");
    } else {
        console.error("mainContent is null or undefined.");
    }
};