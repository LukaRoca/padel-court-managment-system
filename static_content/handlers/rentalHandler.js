import {renderException} from "../views/Exeptions.js";
import {fetchRentalById, fetchRentalsByCrid, fetchRentalsByUid} from "../data/rentalData.js";
import {renderRentalDetails, renderRentalsByAnyid} from "../views/rentalView.js";
import {LIMIT} from "../utils/configs.js";

let skipu = 0
let skipC = 0

export const getRentalsByUid = async (mainContent, params) => {
    try {
        const userId = params.uid;
        const rental = await fetchRentalsByUid(userId, LIMIT, skipu);
        renderRentalsByAnyid(mainContent,
            rental.list,
            () => { skipu += LIMIT; getRentalsByUid(mainContent); },
            () => { skipu = Math.max(0, skip - LIMIT); getRentalsByUid(mainContent); },
            rental.next,
            rental.previous
        );
    } catch (error) {
        console.error("Erro ao encontrar rentals:", error);
        renderException(mainContent, error);
    }
}

export const getRentalByCrid = async (mainContent, params) => {
    try {
        const courtId = params.crid;
        const rentals = await fetchRentalsByCrid(courtId, LIMIT, skipC);
        renderRentalsByAnyid(
            mainContent,
            rentals.list,
            () => { skipu += LIMIT; getRentalByCrid(mainContent); },
            () => { skipu = Math.max(0, skip - LIMIT); getRentalByCrid(mainContent); },
            rentals.next,
            rentals.previous
        );
    } catch (error) {
        console.error("Erro ao encontrar rentals:", error);
        renderException(mainContent, error);
    }
}

export const getRentalDetail = async (mainContent, params) => {
    try {
        const rentalId = params.rid;
        const rental = await fetchRentalById(rentalId);
        renderRentalDetails(mainContent, rental);
    } catch (error) {
        console.error("Erro ao encontrar rentals:", error);
        renderException(mainContent, error);
    }
}
