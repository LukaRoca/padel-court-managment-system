import {getClubById, getClubs} from "../handlers/clubHandler.js";
import {getHome} from "../handlers/homeHandler.js";
import {getCourtDetails, getCourtsList} from "../handlers/courtHandler.js";
import {getUserById} from "../handlers/userHandler.js";
import {getRentalById, getRentalsByUid} from "../handlers/rentalHandler.js";

export default function setupRoutes(router) {
    router.addRouteHandler("home", getHome)
    router.addRouteHandler("clubs", getClubs)
    router.addRouteHandler("club/:id", getClubById)
    router.addRouteHandler("courts/:cid", getCourtsList)
    router.addRouteHandler("courts/:courtId", getCourtDetails)
    //router.addRouteHandler("courts/:courtId/rentals", getCourtRentalList)
   // router.addRouteHandler("users", getAllUsers)
    router.addRouteHandler("user/:uid", getUserById)
    router.addRouteHandler("rentals/:uid", getRentalsByUid)
    router.addRouteHandler("rental/:rid", getRentalById)

    router.addDefaultNotFoundRouteHandler(() => window.location.hash = "home")
    /*router.addRouteHandler("/rentals/:rentalId", rentalHandler.getRentalDetails)
    router.addRouteHandler("/users/:userId/rentals", userHandler.getUserRentalsList)



     */
}