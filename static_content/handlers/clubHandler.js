import {fetchClubById, fetchClubs} from "../data/clubData.js";
import {renderClubDetail, renderClubs} from "../views/clubView.js";
import {renderException} from "../views/Exeptions.js";


export const getClubs = async (mainContent) => {
    try {
        const clubs = await fetchClubs();
        renderClubs(mainContent, clubs);
    } catch (error) {
        console.error("Erro ao buscar clubes:", error);
        renderException(mainContent, error);
    }
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




