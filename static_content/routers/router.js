const routes = []
const router = {
    addRouteHandler,
    getRouteHandler,
    addNotFoundRouteHandler
}
let notFoundRouteHandler = () => { throw "Route handler for unknown routes not defined" }

function addRouteHandler(route, handler) { // Adiciona um manipulador de rota no routes
    routes.push({ route, handler })
}
function addNotFoundRouteHandler(nfr) { // Adiciona um manipulador de rota padrão
    notFoundRouteHandler = nfr
}
function getRouteHandler(path) { // Retorna o manipulador de rota correspondente ao caminho
    const route = routes.find(r => r.pathTemplate == path)
    return route ? route.handler : notFoundRouteHandler
}

export default router
