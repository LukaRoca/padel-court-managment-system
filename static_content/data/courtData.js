import {API_BASE_URL} from "../utils/configs.js";

export const fetchCourts = () => {
    return fetch(API_BASE_URL + "courts/")
        .then(res => res.json());
};

export const fetchCourtById = (courtId) => {
    return fetch(API_BASE_URL + "courts/" + courtId)
        .then(res => res.json());
};