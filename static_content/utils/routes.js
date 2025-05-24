import {createClub, deleteClub, getClubById, getClubs, getClubsByName} from "../handlers/clubHandler.js";
import {getHome} from "../handlers/homeHandler.js";
import {getCourtById, getCourtsList, getCourtAvailableHoursSpecificDate, createCourt} from "../handlers/courtHandler.js";
import {getUserById, getUsers} from "../handlers/userHandler.js";
import {
    createRental,
    getRentalByCrid,
    getRentalDetail,
    getRentalsByUid, updateRental
} from "../handlers/rentalHandler.js";

export default function setupRoutes(router) {
    router.addRouteHandler("home", getHome)
    router.addRouteHandler("clubs", getClubs)
    router.addRouteHandler("clubs/:name", getClubsByName)
    router.addRouteHandler("club/:id", getClubById)
    router.addRouteHandler("clubc/create", createClub)
    router.addRouteHandler("clubd/:cid", deleteClub)
    router.addRouteHandler("courts/:cid", getCourtsList)
    router.addRouteHandler("court/hours/:crid/:date", getCourtAvailableHoursSpecificDate);
    router.addRouteHandler("court/hours/:crid", getCourtAvailableHoursSpecificDate);
    router.addRouteHandler("court/create/:cid", createCourt);
    router.addRouteHandler("court/:crid", getCourtById)
    router.addRouteHandler("users/:uid", getUserById)
    router.addRouteHandler("user", getUsers)
    router.addRouteHandler("rental/update/:rid", updateRental)
    router.addRouteHandler("rentals/:uid", getRentalsByUid)
    router.addRouteHandler("rental/create", createRental)
    router.addRouteHandler("rental/:rid", getRentalDetail)
    router.addRouteHandler("court/rentals/:crid", getRentalByCrid)


    router.addDefaultNotFoundRouteHandler(() => window.location.hash = "home")
}