import {API_BASE_URL} from "../utils/configs.js";
import {a, div, h1, li, ul} from "../utils/elements.js";

export const renderRentalDetails = (mainContent, rental) => {

    const rentalDetails = ul( {},
        li({},`Id: ${rental.id}`),
        li({}, `Date: ${rental.date}`),
        li({}, `Duration: ${rental.duration}`),
        a({
            href: `${API_BASE_URL}#club/${rental.user.id}`,
            textContent: `UserId: ${rental.user.id}`
        }),
        a({
            href: `${API_BASE_URL}#court/${rental.court.id}`,
            textContent: `CourtId: ${rental.court.id}`
        })
    )

    const content = div ({},
        h1({},"Rental Details"),
        rentalDetails,
    );

    if(!mainContent) {
        console.error("mainContent is null or undefined");
        return;
    }

    mainContent.replaceChildren(content);
    console.log("User detail rendered successfully");
}

export const renderRentalsByAnyid = (mainContent, rentals) => {

    const rentalDetails = div(
        {},
        ...rentals.map(rental =>
            ul(
                {},
                li({}, `Id: ${rental.id}`),
                li({}, `Date: ${rental.date}`),
                li({}, `Duration: ${rental.duration}`),
                a({
                    href: `${API_BASE_URL}#club/${rental.user.id}`,
                    textContent: `UserId: ${rental.user.id}`
                }),
                a({
                    href: `${API_BASE_URL}#court/${rental.court.id}`,
                    textContent: `CourtId: ${rental.court.id}`
                })
            )
        )
    )

    const content = div ({},
        h1({},"Rental Details"),
        rentalDetails,
    );

    if(!mainContent) {
        console.error("mainContent is null or undefined");
        return;
    }

    mainContent.replaceChildren(content);
    console.log("User detail rendered successfully");
}

