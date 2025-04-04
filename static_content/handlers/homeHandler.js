export const API_BASE_URL = "http://localhost:9000/"

function getHome(information) {

    const h1 = document.createElement("html1")
    const text = document.createTextNode("Home")
    h1.appendChild(text)
    information.replaceChildren(h1)
}

function getClubs(information) {
    fetch(API_BASE_URL + "clubs")
        .then(res => res.json())
        .then(clubs => {
            const div = document.createElement("div")

            const h1 = document.createElement("h1")
            const text = document.createTextNode("Clubs")
            h1.appendChild(text)
            div.appendChild(h1)

            clubs.forEach(c => {
                const p = document.createElement("p")
                const a = document.createElement("a")
                const aText = document.createTextNode("Link Example to clubs/" + c.name);
                a.appendChild(aText)
                a.href = "#clubs/" + c.name
                p.appendChild(a)
                div.appendChild(p)
            })
            information.replaceChildren(div)
        })
}

export const handlers = {
    getHome,
    getClubs,
}

export default handlers