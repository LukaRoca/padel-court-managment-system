import {renderException} from "../views/Exeptions.js";
import {renderCourtAvailableHours, renderCourtDetail, renderCourtsList, renderCreateCourt} from "../views/courtView.js";
import {fetchCourtAvailableHours, fetchCourtById, fetchCourts} from "../data/courtData.js";
import {LIMIT} from "../utils/configs.js";

let skip = 0;

export const getCourtsList = async (mainContent, params) => {
    try {
        const clubId = params.cid;
        const courts = await fetchCourts(clubId, LIMIT, skip);
        renderCourtsList(
            mainContent,
            courts.list,
            () => { skip += LIMIT; getCourtsList(mainContent, params)},
            () => { skip = Math.max(0, skip-LIMIT); getCourtsList(mainContent, params)},
            courts.next,
            courts.previous
        );
    } catch (error) {
        console.error("Erro ao buscar quadras:", error);
        renderException(mainContent, error);d
    }
};

export const getCourtById = async (mainContent, params) => {
    try {
        const courtId = params.crid;
        const court = await fetchCourtById(courtId)
        renderCourtDetail(mainContent,court, undefined,undefined);
    } catch (error) {
        console.error(`Erro ao encontrar um Court com este Id ${court.crid}`)
        renderException(mainContent,error)
    }
};

export const createCourt = (mainContent) => {
    renderCreateCourt(mainContent);
};

export const getCourtAvailableHoursSpecificDate = async (mainContent, params) => {
    try {
        const { crid, date } = params;
        const court = await fetchCourtById(crid);
        if (!court) {
            throw new Error("Court não encontrado");
        }
        if (date) {
            try {
                const availableHours = await fetchCourtAvailableHours(court.id, date, court.club.id);
                renderCourtAvailableHours(mainContent, court, availableHours);
            } catch (error) {
                renderCourtAvailableHours(mainContent, court, null);
            }
        } else {
            renderCourtAvailableHours(mainContent, court);
        }
    } catch (error) {
        console.error("Erro no handler:", error);
        renderException(mainContent, error);
    }
};
