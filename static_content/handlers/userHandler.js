import {renderException} from "../views/Exeptions.js";
import {renderLoginForm} from "../views/user/userLogin.js";
import {fetchAllUsers,fetchUserById} from "../data/userData.js";
import {LIMIT} from "../utils/configs.js";
import {renderUserDetails} from "../views/user/userDetails.js";
import {renderUsers} from "../views/user/usersList.js";
import {renderCreateUser} from "../views/user/createUser.js";

export const getUserById = async (mainContent, params) => {
        try {
                const userId = params.uid;
                const user = await fetchUserById(userId);
                renderUserDetails(mainContent, user);
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

export const createUser = (mainContent) => {
        try {
                renderCreateUser(mainContent);
        } catch (error) {
                console.error("Erro ao criar usuário:", error);
                renderException(mainContent, error);
        }

}

export const loginUser = async (mainContent) => {
        try {

                renderLoginForm(mainContent);
        } catch (error) {
                console.error("Error on login:", error);
                renderException(mainContent, error);
        }
}

