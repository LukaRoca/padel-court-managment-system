import { API_BASE_URL } from "../utils/configs"

function getUserDetails(info, { userId }) {
    fetch(`${API_BASE_URL}users/${userId}`)
        .then(res => res.json())
        .then(user => {
            const div = document.createElement("div")
            const h1 = document.createElement("h1")
            h1.textContent = user.name
            div.appendChild(h1)

            const a = document.createElement("a")
            a.href = `#/users/${userId}/rentals`
            a.textContent = "Rentals"
            div.appendChild(a)

            info.replaceChildren(div)
        })
}

function getUserRentalsList(info, { userId }) {
    fetch(`${API_BASE_URL}users/${userId}/rentals`)
        .then(res => res.json())
        .then(rentals => {
            const div = document.createElement("div")
            const h1 = document.createElement("h1")
            h1.textContent = `Rentals of ${userId}`
            div.appendChild(h1)

            rentals.forEach(r => {
                const a = document.createElement("a")
                a.href = `#/rentals/${r.id}`
                a.textContent = `Rental ${r.id}`
                div.appendChild(a)
            })
            info.replaceChildren(div)
        })
}

export default {
    getUserDetails,
    getUserRentalsList
}
