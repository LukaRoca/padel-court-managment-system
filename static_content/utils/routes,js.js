

import router from "../router";
import clubHandler from "../handlers/clubHandler";

//Home
router.addRouteHandler("/home", handlers.getHome)

//ClubsList
router.addRouteHandler("/clubs", clubHandler.getClubs())

//ClubDetails
router.addRouteHandler("/clubs/:clubId", handlers.getClubDetails)

//CourtList
router.addRouteHandler("/clubs/:clubId/courts", handlers.getCourtsList)

//CourtDetails
router.addRouteHandler("/courts/:courtId", handlers.getCourtDetails)

//CourtRentalsList
router.addRouteHandler("/courts/:courtId/rentals", handlers.getCourtRentalsList)

//RentalDetails
router.addRouteHandler("/rentals/:rentalId", handlers.getRentalDetails)

//UserRentalsList
router.addRouteHandler("/users/:userId/rentals", handlers.getUserRentalsList)

//UserDetails
router.addRouteHandler("/users/:userId", handlers.getUserDetails)

// Página 404
//router.addNotFoundRouteHandler falta pagina 404

export default router
