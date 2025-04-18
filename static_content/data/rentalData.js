import {API_BASE_URL} from "../utils/configs.js";

export const fetchCourts = async (clubId) => {
    try {
        const response = await fetch(`${API_BASE_URL}clubs/${clubId}/courts`);
        return await response.json();
    } catch (error) {
        console.error(`Erro ao buscar quadras do clube ${clubId}:`, error);
        throw error;
    }
};