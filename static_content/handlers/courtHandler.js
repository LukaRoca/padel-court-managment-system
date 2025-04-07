import { API_BASE_URL } from "../utils/configs"
function getCourtsList(info, { clubId }) {
    fetch(`${API_BASE_URL}clubs/${clubId}/courts`)
        .then(res => res.json())
        .then(courts => {
            const div = document.createElement("div")
            const h1 = document.createElement("h1")
            h1.textContent = "Courts"
            div.appendChild(h1)

            courts.forEach(c => {
                const p = document.createElement("p")
                const a = document.createElement("a")
                //a.href = `#/courts/${c.}` e isto
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
            a.textContent = "Rentals"
            div.appendChild(a)

            info.replaceChildren(div)
        })
}

function getCourtRentalList(info, { courtId }) {
    fetch(`${API_BASE_URL}courts/${courtId}/rentals`)
        .then(res => res.json())
        .then(rentals => {
            const div = document.createElement("div")
            const h1 = document.createElement("h1")
            h1.textContent = "Rentals"
            div.appendChild(h1)

            rentals.forEach(r => {
                const p = document.createElement("p")
                const a = document.createElement("a")
                a.href = `#/rentals/${r.id}`
                //a.textContent = `Rental ID: ${r.id}, Date: ${r.date}, Time: ${r.time}` data
                p.appendChild(a)
                div.appendChild(p)
            })

            info.replaceChildren(div)
        })
}
export default {
    getCourtsList,
    getCourtDetails,
    getCourtRentalList
}
