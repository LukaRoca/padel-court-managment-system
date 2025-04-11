import router from "./router.js";
import {getClubById, getClubs} from "../handlers/clubHandler.js";
import {getHome} from "../handlers/homeHandler.js";
import {getCourtDetails, getCourtRentalList, getCourtsList} from "../handlers/courtHandler";
import {getAllUsers} from "../handlers/userHandler";

export default function setupRoutes(router) {
    router.addRouteHandler("home", getHome)
    router.addRouteHandler("clubs", getClubs)
    router.addRouteHandler("club/:id", getClubById)
    router.addRouteHandler("courts", getCourtsList)
    router.addRouteHandler("courts/:courtId", getCourtDetails)
    router.addRouteHandler("courts/:courtId/rentals", getCourtRentalList)
    router.addRouteHandler("users", getAllUsers)

    router.addDefaultNotFoundRouteHandler(() => window.location.hash = "home")
    /*router.addRouteHandler("/rentals/:rentalId", rentalHandler.getRentalDetails)
    router.addRouteHandler("/users/:userId/rentals", userHandler.getUserRentalsList)



     */
}