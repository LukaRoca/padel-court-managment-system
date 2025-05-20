import {fetchClubById, fetchClubs, fetchClubsByName, fetchDeleteClub} from "../data/clubData.js";
import {renderClubDetail, renderCreateClub, renderDeleteClub} from "../views/clubView.js";
import {renderException} from "../views/Exeptions.js";
import {fetchAndRenderClubs} from "../utils/utils.js";

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
