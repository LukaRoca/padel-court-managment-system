import {createElement} from "../html/DSL.js";

export const renderException = (mainContent,error) => {

    const errorCode = error.status || error.code || 'UNKNOWN';

    const errorDiv = createElement("div", [
        createElement("h2", ["Erro ao carregar dados"], { class: "error-title" }),
        createElement("p", [`Código: ${errorCode}`], { class: "error-code" }),
        createElement("p", [`Mensagem: ${error.message}`], { class: "error-message" }),
    ] );

    mainContent.innerHTML = '';
    mainContent.appendChild(errorDiv);
};