const routes = []
let notFoundRouteHandler = () => { throw "Route handler for unknown routes not defined" }

function addRouteHandler(pathTemplate, handler) { // Adiciona um objeto com o template da rota e o manipulador ao array de rotas
    routes.push({ pathTemplate, handler })
}

function addDefaultNotFoundRouteHandler(notFoundRH) {
    notFoundRouteHandler = notFoundRH
}

function getRouteHandler(path){
    const route = routes.find(r => r.pathTemplate == path)
    return route ? route.handler : notFoundRouteHandler
}

const router = {
    addRouteHandler,
    getRouteHandler,
    addDefaultNotFoundRouteHandler
}

export default router
