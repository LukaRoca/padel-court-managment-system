import {renderException} from "../views/Exeptions.js";
import {renderUserDetail} from "../views/userView.js";
import {fetchUserById} from "../data/userData.js";


export const getUserById = async (mainContent, params) => {
        try {
                const userId = params.uid;
                const user = await fetchUserById(userId);
                renderUserDetail(mainContent, user);
        } catch (error) {
                console.error("Erro ao buscar usuário:", error);
                renderException(mainContent, error);
        }
};
