import { API_BASE_URL } from "../utils/configs"
function getCourtsList(info, { clubId }) {
    fetch(`${API_BASE_URL}clubs/${clubId}/courts`)
        .then(res => res.json())
        .then(courts => {
            const div = document.createElement("div")
            const h1 = document.createElement("h1")
            h1.textContent = "Campos"
            div.appendChild(h1)

            courts.forEach(c => {
                const p = document.createElement("p")
                const a = document.createElement("a")
                a.href = `#/courts/${c.crid}`
                a.textContent = c.name
                p.appendChild(a)
                div.appendChild(p)
            })

            info.replaceChildren(div)
        })
}

function getCourtDetails(info, { courtId }) {
    fetch(`${API_BASE_URL}courts/${courtId}`)
        .then(res => res.json())
        .then(court => {
            const div = document.createElement("div")
            const h1 = document.createElement("h1")
            h1.textContent = court.name
            div.appendChild(h1)

            const a = document.createElement("a")
            a.href = `#/courts/${courtId}/rentals`
            a.textContent = "Ver aluguéis"
            div.appendChild(a)

            info.replaceChildren(div)
        })
}

export default {
    getCourtsList,
    getCourtDetails
}
