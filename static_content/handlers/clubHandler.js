import {fetchClubById, fetchClubs} from "../data/clubData.js";
import {renderClubDetail, renderClubs} from "../views/clubView.js";
import {renderException} from "../views/Exeptions.js";
import {LIMIT} from "../utils/configs.js";

let skip = 0

export const getClubs = async (mainContent) => {
    try {
        const clubs = await fetchClubs(LIMIT, skip);
        renderClubs(
            mainContent,
            clubs.list,
            () => { skip += LIMIT; getClubs(mainContent)},
            () => { skip = Math.max(0, skip-LIMIT); getClubs(mainContent)},
            clubs.next,
            clubs.previous
        )
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




