import {renderException} from "../views/Exeptions.js";
import {fetchDeleteRental, fetchRentalById, fetchRentalsByCrid, fetchRentalsByUid} from "../data/rentalData.js";
import {
    renderCreateRental,
    renderRentalDetails,
    renderRentalsByCrid,
    renderRentalsByUid
} from "../views/rentalView.js";
import {LIMIT} from "../utils/configs.js";

let skipu = 0
let skipC = 0

export const getRentalsByUid = async (mainContent, params) => {
    try {
        const userId = params.uid;
        const rental = await fetchRentalsByUid(userId, LIMIT, skipu);
        renderRentalsByUid(mainContent,
            rental.list,
            () => { skipu += LIMIT; getRentalsByUid(mainContent, params); },
            () => { skipu = Math.max(0, skip - LIMIT); getRentalsByUid(mainContent, params); },
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
        renderRentalsByCrid(
            mainContent,
            rentals.list,
            () => { skipC += LIMIT; getRentalByCrid(mainContent, params); },
            () => { skipC = Math.max(0, skipC - LIMIT); getRentalByCrid(mainContent, params); },
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

export const createRental = (mainContent) => {
    renderCreateRental(mainContent);
};

export const deleteRental = async (mainContent, params) => {
    try {
        const rentalId = params.rid;
        await fetchDeleteRental(rentalId);
        alert("Rental eliminado com sucesso!");
        window.location.hash = `#home`
    } catch (error) {
        console.error("Erro ao eliminar rental:", error);
        renderException(mainContent, error);
    }
}
