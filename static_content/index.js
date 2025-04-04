import router from './routers/router.js'
import handler from './handlers/homeHandler.js'

window.addEventListener('load',loadHandler)
window.addEventListener('hashchange', hashChangeHandler)

function loadHandler() { //Definir as rotas esse ultimo e caso n encontre , volta para home
    router.addRouteHandler("home", handler.getHome)
    router.addRouteHandler("clubs", handler.getClubs)
    router.addNotFoundRouteHandler(() => window.location.hash = "home")

    hashChangeHandler()
}


function hashChangeHandler() {
    const information = document.getElementById("information")
    const path = window.location.hash.replace("#", "") //retira se o # obtendo assim a propria rota
    const handler = router.getRouteHandler(path)
    handler(information) //falta corrigir isto
}

