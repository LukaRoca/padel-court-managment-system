import {renderException} from "../views/Exeptions.js";
import {fetchRentalById, fetchRentalsByCrid, fetchRentalsByUid} from "../data/rentalData.js";
import {renderRentalDetails, renderRentalsByAnyid} from "../views/rentalView.js";

export const getRentalsByUid = async (mainContent, params) => {
    try {
        const userId = params.uid;
        const rental = await fetchRentalsByUid(userId);
        renderRentalsByAnyid(mainContent, rental, userId);
    } catch (error) {
        console.error("Erro ao encontrar rentals:", error);
        renderException(mainContent, error);
    }
}

export const getRentalByCrid = async (mainContent, params) => {
    try {
        const courtId = params.crid;
        const rentals = await fetchRentalsByCrid(courtId);
        renderRentalsByAnyid(mainContent, rentals);
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
