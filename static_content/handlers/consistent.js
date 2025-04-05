import router from "../router.js"

export async function Consistent({ path, params }) {
    const handler = router.getRouteHandler(path)

    if (!handler) {
        console.warn("No handler for route:", path)
        return () => {
            const div = document.createElement("div")
            div.textContent = "Página não encontrada"
            return div
        }
    }

    // Chama o handler passando os parâmetros, se houver
    return () => handler(document.getElementById("information"), params)
}
