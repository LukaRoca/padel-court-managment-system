import {API_BASE_URL} from "../utils/configs.js";

export const fetchRentalsByUid = async (userId) => {
    try {
        const response = await fetch(`${API_BASE_URL}rentals/user/${userId}`);
        return await response.json();
    } catch (error) {
        console.error(`Erro ao encontrar rentals do User ${userId}:`, error);
        throw error;
    }
}

export const fetchRentalsByCrid = async (courtId) => {
    try {
        const response = await fetch(`${API_BASE_URL}rentals/courts/${courtId}`);
        return await response.json();
    } catch (error) {
        console.error(`Erro ao encontrar rentals do User ${courtId}:`, error);
        throw error;
    }
}

export const fetchRentalById = async (rentalId) => {
    try {
        const response = await fetch(`${API_BASE_URL}rentals/${rentalId}`);
        return await response.json();
    } catch (error) {
        console.error(`Erro ao encontrar rentals com o Id ${rentalId}`, error);
        throw error;
    }
}