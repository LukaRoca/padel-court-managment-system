const routes = []

function addRouteHandler(pathTemplate, handler) {
    routes.push({ pathTemplate, handler })
}
export default {
    addRouteHandler,
}
