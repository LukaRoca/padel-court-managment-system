import {API_BASE_URL} from "../homeHandler";


function getClubs(information) {

    const h1 = document.createElement("html1")
    const text = document.createTextNode("ClubsList")
    h1.appendChild(text)
    information.replaceChildren(h1)
}

function getClub(mainContent){
    fetch(API_BASE_URL + "clubs/10")
        .then(res => res.json())
        .then(student => {
            const ulStd = document.createElement("ul")

            const liName = document.createElement("li")
            const textName = document.createTextNode("Name : " + club.name)
            liName.appendChild(textName)

            const liNumber = document.createElement("li")
            const textNumber = document.createTextNode("Number : " + club.number)
            liNumber.appendChild(textNumber)

            ulStd.appendChild(liName)
            ulStd.appendChild(liNumber)

            mainContent.replaceChildren(ulStd)
        })
}
function getCourts(information) {
    fetch(API_BASE_URL + "courts")
        .then(res => res.json())
        .then(courts => {
            const div = document.createElement("div")

            const h1 = document.createElement("h1")
            const text = document.createTextNode("CourtsList")
            h1.appendChild(text)
            div.appendChild(h1)

            courts.forEach(court => {
                const p = document.createElement("p")
                const a = document.createElement("a")
                const aText = document.createTextNode("Link Example to courts/" + court.name)
                a.appendChild(aText)
                a.href = "#courts/" + court.name
                p.appendChild(a)
                div.appendChild(p)
            })
            information.replaceChildren(div)
        })
        .catch(error => console.error("Error fetching courts:", error))
}

function getUserDetails(information) {
    const userId = window.location.hash.replace("#users/", "")
    fetch(API_BASE_URL + "users/" + userId)
        .then(res => res.json())
        .then(user => {
            const div = document.createElement("div")

            const h1 = document.createElement("h1")
            const text = document.createTextNode("User Details")
            h1.appendChild(text)
            div.appendChild(h1)

            const ulUser = document.createElement("ul")

            const liName = document.createElement("li")
            const textName = document.createTextNode("Name: " + user.name)
            liName.appendChild(textName)

            const liEmail = document.createElement("li")
            const textEmail = document.createTextNode("Email: " + user.email)
            liEmail.appendChild(textEmail)

            ulUser.appendChild(liName)
            ulUser.appendChild(liEmail)

            div.appendChild(ulUser)
            information.replaceChildren(div)
        })
        .catch(error => console.error("Error fetching user details:", error))
}

export const handlers = {
    getClubs,
    getClub,
    getCourts,
    getUserDetails
}

export default handlers