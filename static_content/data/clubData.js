import {API_BASE_URL} from "../utils/configs.js";

export const fetchClubs = async (limit, skip) => {
    try {
        const response = await fetch(`${API_BASE_URL}clubs?limit=${limit}&skip=${skip}`);
        return await response.json();
    } catch (error) {
        console.error("Error fetching clubs:", error);
        return { list: [], next: false, previous: false };
    }
};

export const fetchClubById = async (clubId) => {
    try {
        const response = await fetch(`${API_BASE_URL}clubs/${clubId}`);
        return await response.json();
    } catch (error) {
        console.error(`Error fetching club with ID ${clubId}:`, error);
        throw error;
    }
};

export const fetchClubsByName = async (clubName) => {
    try {
        const response = await fetch(`${API_BASE_URL}clubs?name=${clubName}`);
        return await response.json();
    } catch (error) {
        console.error(`Error fetching club with name ${clubName}:`, error);
        throw error;
    }
};

export const fetchCreateClubs = async (clubData) => {
    try {
        const response = await fetch(`${API_BASE_URL}clubs`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(clubData)
        });
        return await response.json();
    } catch (error) {
        console.error(`Error creating club with name ${clubData.name}:`, error);
        throw error;
    }
}
