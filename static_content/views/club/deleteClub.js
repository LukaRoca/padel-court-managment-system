import {div, h2, button, tbody, th, tr, thead, table, td, a, span} from "../../utils/elements.js";
import {fetchClubs, fetchDeleteClub} from "../../data/clubData.js";
import {getUserId} from "../../utils/token_utilis.js";
import {API_BASE_URL} from "../../utils/configs.js";

export const renderDeleteClub = async (mainContent) => {
    const handleDelete = async (cid, isOwner) => {
        try {
            if (!isOwner) {
                alert("You don't have permission to delete this club. Only the owner can delete it.");
                return;
            }

            if (confirm("Are you sure you want to delete this club?")) {
                await fetchDeleteClub(cid);
                alert("Club deleted successfully!");
                window.location.hash = "#clubs";
            }
        } catch (error) {
            alert("Error deleting club: " + (error.message || error));
        }
    };

    try {
        const clubs = await fetchClubs(100, 0);
        const currentUserId = getUserId();

        console.log('Current User ID:', currentUserId);

        const content = div(
            {className: "container py-5"},
            div(
                {className: "row justify-content-center"},
                div(
                    {className: "col-md-8"},
                    div(
                        {className: "card p-4 border rounded shadow-sm"},
                        h2({className: "mb-4 text-center"}, "Delete Club"),
                        table(
                            {className: "table"},
                            thead(
                                {},
                                tr(
                                    {},
                                    th({}, "Name"),
                                    th({}, "ID"),
                                    th({className: "text-end"}, "Action")
                                )
                            ),
                            tbody(
                                {},
                                ...(clubs.list || []).map(club => {
                                    // Debug log for each club's ownerId
                                    console.log(`Club "${club.name}" - Owner ID:`, club.owner.id);
                                    console.log('Comparison result:', club.owner.id === currentUserId);

                                    return tr(
                                        {},
                                        td({}, club.name),
                                        td({}, club.id),
                                        td(
                                            {className: "text-end"},
                                            button(
                                                {
                                                    className: `btn btn-${club.owner.id === currentUserId ? 'danger' : 'secondary'} btn-sm`,
                                                    onClick: () => handleDelete(club.id, club.owner.id === currentUserId)
                                                },
                                                "Delete"
                                            )
                                        )
                                    );
                                })
                            )
                        )
                    )
                )
            ),
        div(
            {className: "d-flex justify-content-between align-items-center mt-5 pt-4 border-top"},
            a({
                    href: `${API_BASE_URL}#clubs`,
                    className: "btn btn-outline-secondary d-inline-flex align-items-center gap-1"
                },
                span({className: "material-icons", style: "font-size: 1.1em;"}),
                "Back to Clubs"
            ),
            div({className: "d-flex gap-2"})
        )
        );

        mainContent.replaceChildren(content);
    } catch (error) {
        alert("Error loading clubs: " + (error.message || error));
    }
};