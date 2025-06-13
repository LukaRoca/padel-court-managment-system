import {fetchClubById, fetchClubs, fetchClubsByName} from "../data/clubData.js";
import {renderDeleteClub} from "../views/club/deleteClub.js";
import {renderException} from "../views/Exeptions.js";
import {fetchAndRenderClubs} from "../utils/utils.js";
import {renderClubDetail} from "../views/club/clubDetails.js";
import {renderCreateClub} from "../views/club/createClub.js";

export const getClubs = async (mainContent) => {
    await fetchAndRenderClubs(mainContent, fetchClubs);
};

export const getClubsByName = async (mainContent, params) => {
    await fetchAndRenderClubs(mainContent, fetchClubsByName, params);
};

export const getClubById = async (mainContent, params) => {
    try {
        const clubId = params.id;
        const club = await fetchClubById(clubId);
        renderClubDetail(mainContent, club);
    } catch (error) {
        console.error("Erro ao buscar clube:", error);
        renderException(mainContent, error);
    }
};


export const createClub = (mainContent) => {
    renderCreateClub(mainContent);
};

export const deleteClub = async (mainContent) => {
        renderDeleteClub(mainContent)
}
