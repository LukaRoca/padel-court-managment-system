import {API_BASE_URL} from "../utils/configs.js";


export const fetchUserById = async (userId) => {
    try {
        const response = await fetch(`${API_BASE_URL}users/${userId}`);
        return await response.json();
    } catch (error) {
        console.error(`Erro ao buscar usuário com ID ${userId}:`, error);
        throw error;
    }
};

export const fetchAllUsers = async (limit, skip) => {
    try {
        const response = await fetch(`${API_BASE_URL}users?limit=${limit}&skip=${skip}`)
        return await response.json()
    } catch (error) {
        console.error(`Erro ao encontrar Usuários`, error);
        throw error;
    }
}