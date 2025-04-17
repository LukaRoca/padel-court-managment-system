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