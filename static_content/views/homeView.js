import {div, a, h1, p, span} from "../utils/elements.js";
import {API_BASE_URL} from "../utils/configs.js";

export const renderHome = (mainContent) => {
    console.log("renderHome called with:", mainContent);

    const content = div(
        {className: "container py-5"},

        div(
            {className: "row align-items-center mb-5 pb-5 border-bottom"},
            div(
                {className: "col-lg-6 mb-4 mb-lg-0"},
                h1({className: "display-4 fw-bold text-primary mb-3"}, "Padel Court Finder"),
                p({className: "lead mb-4"}, "Find and book the perfect padel court in Chelas. Browse our selection of premium courts and start playing today."),
                div(
                    {className: "d-flex"},
                )
            ),
            div(
                {className: "col-lg-6 text-center"},
                div(
                    {className: "bg-light rounded-3 p-5 d-flex align-items-center justify-content-center"},
                    span({className: "material-icons display-1 text-primary"}, "Chelas Padel")
                )
            )
        ),

        div(
            {className: "row mb-5"},
            div(
                {className: "col-12 text-center mb-4"},
                h1({className: "h2 text-secondary"}, "How It Works")
            ),
            div(
                {className: "col-md-4 mb-4 mb-md-0"},
                div(
                    {className: "text-center p-4 h-100 border rounded-3 shadow-sm"},
                    div({className: "bg-primary text-white rounded-circle d-inline-flex justify-content-center align-items-center mb-3", style: "width: 50px; height: 50px;"}, "1"),
                    h1({className: "h5 mb-3"}, "Browse Clubs"),
                    p({className: "text-muted"}, "Find padel clubs in your area with available courts.")
                )
            ),
            div(
                {className: "col-md-4 mb-4 mb-md-0"},
                div(
                    {className: "text-center p-4 h-100 border rounded-3 shadow-sm"},
                    div({className: "bg-primary text-white rounded-circle d-inline-flex justify-content-center align-items-center mb-3", style: "width: 50px; height: 50px;"}, "2"),
                    h1({className: "h5 mb-3"}, "Select Court"),
                    p({className: "text-muted"}, "View court details and choose the one that suits your needs.")
                )
            ),
            div(
                {className: "col-md-4"},
                div(
                    {className: "text-center p-4 h-100 border rounded-3 shadow-sm"},
                    div({className: "bg-primary text-white rounded-circle d-inline-flex justify-content-center align-items-center mb-3", style: "width: 50px; height: 50px;"}, "3"),
                    h1({className: "h5 mb-3"}, "Book & Play"),
                    p({className: "text-muted"}, "Make your reservation and enjoy your game of padel.")
                )
            )
        ),

        div(
            {className: "row py-4 bg-light rounded-3 mb-5"},
            div(
                {className: "col-12 text-center"},
                p({className: "lead mb-4"}, "Ready to find your perfect padel court?"),
                a(
                    {
                        href: `${API_BASE_URL}#clubs`,
                        className: "btn btn-primary btn-lg d-inline-flex align-items-center gap-2"
                    },
                    span({className: "material-icons"}),
                    "View All Clubs"
                )
            )
        ),

        div(
            {className: "row pt-4 border-top text-center"},
            p({className: "text-muted mb-0"}, "Chelas Tennis © 2025 • Authors: Afonso Santos, Bernardo Jaco, Luka Roca")
        )
    );

    console.log("Generated content:", content);

    if (mainContent) {
        mainContent.replaceChildren(content);
        console.log("Content replaced successfully.");
    } else {
        console.error("mainContent is null or undefined.");
    }
};