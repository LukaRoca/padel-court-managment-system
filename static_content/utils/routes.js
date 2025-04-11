import router from "./router.js";
import {getClubById, getClubs} from "../handlers/clubHandler.js";
import {getHome} from "../handlers/homeHandler.js";
import {getAllUsers} from "../handlers/userHandler.js"

export default function setupRoutes(router) {
    router.addRouteHandler("home", getHome)
    router.addRouteHandler("clubs", getClubs)
    router.addRouteHandler("club/:id", getClubById)
    //router.addRouteHandler("user/:id", getUserById) //TODO()


    router.addRouteHandler("users", getAllUsers)

    router.addDefaultNotFoundRouteHandler(() => window.location.hash = "home")


    /*
    router.addRouteHandler("/clubs/:clubId", clubHandler.getClubDetails)
    router.addRouteHandler("/clubs/:clubId/courts", courtHandler.getCourtsList)
    router.addRouteHandler("/courts/:courtId", courtHandler.getCourtDetails)
    router.addRouteHandler("/courts/:courtId/rentals", courtHandler.getCourtRentalList)
    router.addRouteHandler("/rentals/:rentalId", rentalHandler.getRentalDetails)
    router.addRouteHandler("/users/:userId/rentals", userHandler.getUserRentalsList)
    router.addRouteHandler("/users/:userId", userHandler.getUserDetails)


     */
}