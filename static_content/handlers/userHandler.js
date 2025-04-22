import {renderException} from "../views/Exeptions.js";
import {renderUsers, renderUserDetail} from "../views/userView.js";
import {fetchAllUsers, fetchUserById} from "../data/userData.js";
import {LIMIT} from "../utils/configs.js";

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

let skip = 0

export const getUsers = async (mainContent) => {
        try {
                const result = await fetchAllUsers(LIMIT, skip);
                renderUsers(
                    mainContent,
                    result.list,
                    () => { skip += LIMIT; getUsers(mainContent); },
                () => { skip = Math.max(0, skip - LIMIT); getUsers(mainContent); },  // Serve para subtrair o skip e o limit sem ter numeros negativos
                    result.next,
                    result.previous
                )

        } catch (error) {
                console.error("Erro ao encontrar os Users", error);
                renderException(mainContent)
        }
}
