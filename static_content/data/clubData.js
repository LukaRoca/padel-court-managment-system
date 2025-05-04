import {API_BASE_URL} from "../utils/configs.js";

export const fetchClubs = async (limit, skip) => {
    try {
        const response = await fetch(`${API_BASE_URL}clubs?limit=${limit}&skip=${skip}`);
        return await response.json();
    } catch (error) {
        console.error("Erro ao buscar clubes:", error);
        return { list: [], next: false, previous: false };
    }
};


export const fetchClubById = async (clubId) => {
    try {
        const response = await fetch(`${API_BASE_URL}clubs/${clubId}`);
        return await response.json();
    } catch (error) {
        console.error(`Erro ao buscar clube com ID ${clubId}:`, error);
        throw error;
    }
};