import {API_BASE_URL} from "../utils/configs.js";

export const fetchClubs = async () => {
    try {
        const response = await fetch(`${API_BASE_URL}clubs`);
        const data = await response.json();
        return Array.isArray(data) ? data : [];
    } catch (error) {
        console.error("Erro ao buscar clubes:", error);
        return [];
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