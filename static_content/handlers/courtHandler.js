import {renderException} from "../views/Exeptions.js";
import {renderCreateCourt} from "../views/court/createCourt.js";
import {fetchCourtAvailableHours, fetchCourtById, fetchCourts} from "../data/courtData.js";
import {LIMIT} from "../utils/configs.js";
import {renderCourtsList} from "../views/court/courtList.js";
import {renderCourtDetail} from "../views/court/courtDetail.js";
import {renderCourtAvailableHours} from "../views/court/courtAvailableHours.js";

let skip = 0;

export const getCourtsList = async (mainContent, params) => {
    try {
        const clubId = params.cid;
        const courts = await fetchCourts(clubId, LIMIT, skip);
        renderCourtsList(
            clubId,
            mainContent,
            courts.list,
            () => { skip += LIMIT; getCourtsList(mainContent, params)},
            () => { skip = Math.max(0, skip-LIMIT); getCourtsList(mainContent, params)},
            courts.next,
            courts.previous
        );
    } catch (error) {
        console.error("Erro searching courts:", error);
        renderException(mainContent, error);
    }
};

export const getCourtById = async (mainContent, params) => {
    try {
        const courtId = params.crid;
        const court = await fetchCourtById(courtId)
        renderCourtDetail(mainContent,court, undefined,undefined);
    } catch (error) {
        console.error(`No court with this Id: ${court.crid}`)
        renderException(mainContent,error)
    }
};

export const createCourt = (mainContent, params) => {
    try {
        const cid = params.cid
        renderCreateCourt(mainContent, cid);
    } catch (error) {
        console.error("Error creating court", error);
        renderException(mainContent, error);
    }
};

export const getCourtAvailableHoursSpecificDate = async (mainContent, params) => {
    try {
        const { crid, date } = params;
        const court = await fetchCourtById(crid);
        if (!court) {
            throw new Error("Court not found");
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
        console.error(`Error fetching available hours for court ${params.crid}:`, error);
        renderException(mainContent, error);
    }
};
