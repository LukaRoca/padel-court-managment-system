import {API_BASE_URL} from "../utils/configs.js";

export const fetchRentalsByUid = async (userId, limit , skip) => {
    try {
        const response = await fetch(`${API_BASE_URL}rentals/user/${userId}?limit=${limit}&skip=${skip}`);
        return await response.json();
    } catch (error) {
        console.error(`Erro ao encontrar rentals do User ${userId}:`, error);
        throw error;
    }
}

export const fetchRentalsByCrid = async (courtId, limit, skip) => {
    try {
        const response = await fetch(`${API_BASE_URL}rentals/courts/${courtId}?limit=${limit}&skip=${skip}`);
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

export const fetchCreateRental = async (rentalData, token) => {
    try {
        const response = await fetch(`${API_BASE_URL}rental`, {
            method: "POST",
            headers: {
                "Authorization": `Bearer ${token}`,
                "Content-Type": "application/json"
            },
            body: JSON.stringify(rentalData)
        });
        return await response.json();
    } catch (error) {
        console.error(`Error creating rental`, error);
        throw error;
    }
}

export const fetchDeleteRental = async (rentalId) => {
    try {
        const response = await fetch(`${API_BASE_URL}rentalsd/${rentalId}`, {
            method: "DELETE",
            headers: {
                "Accept": "application/json"
            }
        });
        return await response.json()
    } catch (error) {
        console.error(`Error deleting a rental`, error);
        throw error;
    }
}

export const fetchUpdateRental = async (rentalId, rentalData, token) => {
    try {
        const response = await fetch(`${API_BASE_URL}rentalsu/${rentalId}?${rentalData}`, {
            method: "PUT",
            headers: {
                "Authorization": `Bearer ${token}`,
                "Content-Type": "application/json"
            }
        })
        return await response.json()
    } catch (error) {
        console.error(`Error Updating a rental`, error)
        throw error;
    }
}

