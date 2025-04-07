import {API_BASE_URL} from "../utils/configs";

function getClubs(info) {
    fetch(API_BASE_URL + "clubs")
        .then(res => res.json())
        .then(clubs => {
            const div = document.createElement("div")
            const h1 = document.createElement("h1")
            h1.textContent = "Clubs"
            div.appendChild(h1)

            clubs.forEach(c => {
                const p = document.createElement("p")
                const a = document.createElement("a")
                a.textContent = c.name
                //a.href = `#/clubs/${c.cid}` n sei passar o id do clube aqui
                p.appendChild(a)
                div.appendChild(p)
            })

            info.replaceChildren(div)
        })
}

function getClubDetails(info, { clubId }) {
    fetch(API_BASE_URL + `clubs/${clubId}`)
        .then(res => res.json())
        .then(club => {
            const div = document.createElement("div")
            const h1 = document.createElement("h1")
            h1.textContent = `Club details: ${club.name}`
            div.appendChild(h1)

            const courtsLink = document.createElement("a")
            courtsLink.href = `#/clubs/${clubId}/courts`
            courtsLink.textContent = "Courts"
            div.appendChild(courtsLink)

            info.replaceChildren(div)
        })
}

export default {
    getClubs,
    getClubDetails
}
