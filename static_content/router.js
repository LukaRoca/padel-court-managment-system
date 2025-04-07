const routes = []
let notFoundRouteHandler = () => { throw "Route handler for unknown routes not defined" }

function addRouteHandler(pathTemplate, handler) { // Adiciona um objeto com o template da rota e o manipulador ao array de rotas
    routes.push({ pathTemplate, handler })
}
function getRouteHandler(path){ //isto basicamente procura no array de rotas ja criado, o path que foi passado, ou seja
    // procura o handler que corresponde a esse path se nao encontrar da erro notFound
    const route = routes.find(r => r.pathTemplate === path)
    return route ? route.handler : notFoundRouteHandler
}
export default {
    addRouteHandler,
    getRouteHandler
}
