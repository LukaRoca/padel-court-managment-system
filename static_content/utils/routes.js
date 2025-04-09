import router from "./router";
import {getClubs} from "../handlers/clubHandler";
import {getHome} from "../handlers/homeHandler";
import courtHandler from "../handlers/courtHandler";
import rentalHandler from "../handlers/rentalHandler";
import userHandler from "../handlers/userHandler";

export default function setupRoutes(router) {
    router.addRouteHandler("home", getHome)
    router.addRouteHandler("clubs", getClubs)

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