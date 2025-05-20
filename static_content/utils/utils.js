import {renderClubs} from "../views/clubView.js";
import {renderException} from "../views/Exeptions.js";
import {fetchClubsByName} from "../data/clubData.js";
import {getClubs} from "../handlers/clubHandler.js";
import {LIMIT} from "./configs.js";

let skip = 0;

export const fetchAndRenderClubs = async (mainContent, fetchFunction, params = {}) => {
    try {
        const clubs = await fetchFunction(
            params.name || LIMIT,
            params.name ? undefined : skip
        );

        renderClubs(
            mainContent,
            clubs.list,
            () => {
                skip += LIMIT;
                fetchAndRenderClubs(mainContent, fetchFunction, params);
            },
            () => {
                skip = Math.max(0, skip - LIMIT);
                fetchAndRenderClubs(mainContent, fetchFunction, params);
            },
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

// Function to handle the dropdown menu for club actions
 export const setupDropdown = () => {
    // Find the dropdown toggle button after rendering
    const dropdownToggle = document.getElementById('clubActionsDropdown');
    const dropdownMenu = document.querySelector('.dropdown-menu');

    if (dropdownToggle && dropdownMenu) {
        dropdownToggle.addEventListener('click', (e) => {
            e.preventDefault();
            dropdownMenu.classList.toggle('show');
        });

        // Close dropdown when clicking outside
        document.addEventListener('click', (e) => {
            if (!dropdownToggle.contains(e.target)) {
                dropdownMenu.classList.remove('show');
            }
        });
    }
};

