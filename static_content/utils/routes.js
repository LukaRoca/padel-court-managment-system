import {createClub, getClubById, getClubs, getClubsByName} from "../handlers/clubHandler.js";
import {getHome} from "../handlers/homeHandler.js";
import {getCourtById, getCourtsList, getCourtAvailableHoursSpecificDate, createCourt} from "../handlers/courtHandler.js";
import {getUserById, getUsers} from "../handlers/userHandler.js";
import {getRentalByCrid, getRentalDetail, getRentalsByUid} from "../handlers/rentalHandler.js";

export default function setupRoutes(router) {
    router.addRouteHandler("home", getHome)
    router.addRouteHandler("clubs", getClubs)
    router.addRouteHandler("clubs/:name", getClubsByName)
    router.addRouteHandler("club/:id", getClubById)
    router.addRouteHandler("clubc/create", createClub)
    router.addRouteHandler("courts/:cid", getCourtsList)
    router.addRouteHandler("court/:crid",getCourtAvailableHoursSpecificDate)
    router.addRouteHandler("court/create", createCourt);
    router.addRouteHandler("court/:crid", getCourtById)
    router.addRouteHandler("users/:uid", getUserById)
    router.addRouteHandler("user", getUsers)
    router.addRouteHandler("rentals/:uid", getRentalsByUid)
    router.addRouteHandler("rental/:rid", getRentalDetail)
    router.addRouteHandler("court/rentals/:crid", getRentalByCrid)


    router.addDefaultNotFoundRouteHandler(() => window.location.hash = "home")
}