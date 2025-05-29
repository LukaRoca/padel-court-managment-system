import {API_BASE_URL} from "../utils/configs.js";
import {getToken} from "../utils/token_utilis.js";

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

export const fetchCourtAvailableHours = async (courtId, date, clubId) => {
    try {
        if (!courtId || !date || !clubId) {
            throw new Error('courtId, clubId e date são obrigatórios');
        }
        const url = `http://localhost:8080/rentals/available?date=${date}&crid=${courtId}&cid=${clubId}`;
        const response = await fetch(url);
        if (!response.ok) {
            const responseText = await response.text();
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json();
        return data;
    } catch (error) {
        throw new Error('Erro ao obter horários disponíveis');
    }
};

export const fetchCreateCourt = async (courtData) => {
    try {
        const token = getToken()
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