const routes = []
let notFoundRouteHandler = () => { throw "Route handler for unknown routes not defined" }

function addRouteHandler(pathTemplate, handler) { // Adiciona um objeto com o template da rota e o manipulador ao array de rotas
    routes.push({ pathTemplate, handler })
}

function addDefaultNotFoundRouteHandler(notFoundRH) {
    notFoundRouteHandler = notFoundRH
}

function getRouteHandler(path){
    for (let i = 0; i < routes.length; i++) {
        const match = matchHandlerPath(routes[i].pathTemplate, path);
        if (match.isMatch) {
            // Encontrámos o handler correto e os parâmetros
            return (mainContent) => routes[i].handler(mainContent, match.params);
        }
    }
    const route = routes.find(r => r.pathTemplate === path)
    return route ? route.handler : notFoundRouteHandler
}

const router = {
    addRouteHandler,
    getRouteHandler,
    addDefaultNotFoundRouteHandler
}

export default router



/*
handlerPath - path associado a um handler por exemplo clubs/:id
path - path atual por exemplo clubs/2
*/

function matchHandlerPath(handlerPath, path) {
    const params = {};
    const handlerParts = handlerPath.split('/');
    const pathParts = path.split('/');

    if (handlerParts.length !== pathParts.length) {
        return { isMatch: false };
    }

    for (let i = 0; i < handlerParts.length; i++) {
        const handlerPart = handlerParts[i];
        const pathPart = pathParts[i];
        // Se o manipulador for um parâmetro (indicado por `:`), extraímos o valor
        if (handlerPart.startsWith(':')) {
            const paramName = handlerPart.slice(1);  // Remove o `:` do nome do parâmetro
            params[paramName] = pathPart;  // Armazena o valor extraído no objeto `params`
        } else if (handlerPart !== pathPart) {
            // Se as partes não são iguais e não são parâmetros, a rota não corresponde
            return { isMatch: false };
        }
    }

    // Se passamos por todas as partes sem problemas, a rota corresponde
    return { isMatch: true, params, handlerPath };
}



