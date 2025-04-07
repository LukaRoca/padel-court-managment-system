import router from "../router";
import clubHandler from "../handlers/clubHandler";
import homeHandler from "../handlers/homeHandler";
import courtHandler from "../handlers/courtHandler";
import rentalHandler from "../handlers/rentalHandler";
import userHandler from "../handlers/userHandler";


//ClubsList
router.addRouteHandler("/clubs", clubHandler.getClubs)

//ClubDetails
router.addRouteHandler("/clubs/:clubId", clubHandler.getClubDetails)

//CourtList
router.addRouteHandler("/clubs/:clubId/courts", courtHandler.getCourtsList)

//CourtDetails
router.addRouteHandler("/courts/:courtId", courtHandler.getCourtDetails)

//CourtRentalsList
router.addRouteHandler("/courts/:courtId/rentals", courtHandler.getCourtRentalList)

//RentalDetails
router.addRouteHandler("/rentals/:rentalId", rentalHandler.getRentalDetails)

//UserRentalsList
router.addRouteHandler("/users/:userId/rentals", userHandler.getUserRentalsList)

//UserDetails
router.addRouteHandler("/users/:userId", userHandler.getUserDetails)

// Página 404
//router.addNotFoundRouteHandler falta pagina 404

export default router
