    import {renderException} from "../views/Exeptions.js";
    import {renderCourtDetail, renderCourtsList} from "../views/courtView.js";
    import {fetchCourtById, fetchCourts} from "../data/courtData.js";
    import {LIMIT} from "../utils/configs.js";

    let skip = 0;

    export const getCourtsList = async (mainContent, params) => {
        try {
            const clubId = params.cid;
            const courts = await fetchCourts(clubId, LIMIT, skip);
            renderCourtsList(
                mainContent,
                courts.list,
                () => { skip += LIMIT; getCourtsList(mainContent, params)},
                () => { skip = Math.max(0, skip-LIMIT); getCourtsList(mainContent, params)},
                courts.next,
                courts.previous
            );
        } catch (error) {
            console.error("Erro ao buscar quadras:", error);
            renderException(mainContent, error);
        }
    };

    export const getCourtById = async (mainContent, params) => {
        try {
            const courtId = params.crid;
            const court = await fetchCourtById(courtId)
            renderCourtDetail(mainContent,court);
        } catch (error) {
            console.error(`Erro ao encontrar um Court com este Id ${court.crid}`)
            renderException(mainContent,error)
        }
    }
