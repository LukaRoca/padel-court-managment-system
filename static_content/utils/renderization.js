
export function renderization(element){
    const content = document.getElementById("information")
    content.replaceChildren(element)
}

export function parseUrl(origin){ //retorna um objeto com a rota e query(parametros de consulta)
    const url= new URL(origin,window.location.origin)
    const path = url.pathname
    const query = {}
    url.searchParams.forEach(
        (value,key) =>
        {
            query[key]=value
        })
    return {path,query}
}