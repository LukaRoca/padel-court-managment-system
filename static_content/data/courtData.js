import {API_BASE_URL} from "../utils/configs.js";

export const fetchCourts = async (clubId, limit, skip) => {
    try {
        const response = await fetch(`${API_BASE_URL}clubs/${clubId}/courts?limit=${limit}&skip=${skip}`);
        return await response.json();
    } catch (error) {
        console.error(`Erro ao buscar quadras do clube ${clubId}:`, error);
        throw error;
    }
};

export const fetchCourtById = async (courtId) => {
    try {
        const response = await fetch(`${API_BASE_URL}courts/${courtId}`);
        return await response.json();
    } catch (error) {
        console.error(`Erro ao buscar clube com ID ${courtId}:`, error);
        throw error;
    }
};

export const fetchCreateCourt = async (courtData, token) => {
    try {
        console.log("Enviando courtData:", courtData);

        const response = await fetch(`${API_BASE_URL}courts`, {
            method: "POST",
            headers: {
                "Authorization": `Bearer ${token}`,
                "Content-Type": "application/json"
            },
            body: JSON.stringify(courtData)
        });

        if (!response.ok) {
            const errorData = await response.json();
            console.error("Erro ao criar court:", errorData);
            throw new Error(`Erro ${response.status}: ${errorData.message || 'Bad Request'}`);
        }

        return await response.json();
    } catch (error) {
        console.error("Erro ao criar court:", error);
        throw error;
    }
};