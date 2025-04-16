import {API_BASE_URL} from "../utils/configs.js";

export const fetchClubs = () => {
    return fetch(API_BASE_URL + "clubs/")
        .then(res => res.json());
};