import { API_BASE_URL } from "../utils/configs.js"

function getRentalDetails(info, { rentalId }) {
    fetch(`${API_BASE_URL}rentals/${rentalId}`)
        .then(res => res.json())
        .then(rental => {
            const div = document.createElement("div")
            const h1 = document.createElement("h1")
            h1.textContent = `Rental Details`
            div.appendChild(h1)

            const p = document.createElement("p")

            p.textContent = `Rental ID: ${rental.id}, Date: ${rental.date}, Time: ${rental.time}, Court ID: ${rental.id}`// Nao sei como passar a data nem id aqui
            div.appendChild(p)

            info.replaceChildren(div)
        })
}

export default {
    getRentalDetails
}

/*
export const getCourtRentalList = (mainContent, params) => { Afinal isto e suposto fazer aqui

 */