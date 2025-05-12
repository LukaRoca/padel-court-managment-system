import {renderClubs} from "../views/clubView.js";
import {renderException} from "../views/Exeptions.js";
import {fetchClubsByName} from "../data/clubData.js";
import {getClubs} from "../handlers/clubHandler.js";
import {LIMIT} from "./configs.js";

let skip = 0;

const handlePagination = (mainContent, fetchFunction, params) => {
    return async (direction) => {
        skip = Math.max(0, skip + (direction === 'next' ? LIMIT : -LIMIT));
        await fetchAndRenderClubs(mainContent, fetchFunction, params);
    };
};

export const fetchAndRenderClubs = async (mainContent, fetchFunction, params = {}) => {
    try {
        const clubs = await fetchFunction(params.name || LIMIT, params.name ? undefined : skip);
        renderClubs(
            mainContent,
            clubs.list,
            handlePagination(mainContent, fetchFunction, params, 'next'),
            handlePagination(mainContent, fetchFunction, params, 'previous'),
            clubs.next,
            clubs.previous,
            async (searchTerm) => {
                if (!searchTerm || searchTerm.trim() === '') {
                    skip = 0;
                    await getClubs(mainContent);
                    return;
                }
                await fetchAndRenderClubs(mainContent, fetchClubsByName, { name: searchTerm });
            }
        );
    } catch (error) {
        console.error("Error fetching clubs:", error);
        renderException(mainContent, error);
    }
};