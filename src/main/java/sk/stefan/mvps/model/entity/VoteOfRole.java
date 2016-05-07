package sk.stefan.mvps.model.entity;

import lombok.Data;
import sk.stefan.enums.VoteAction;
import sk.stefan.interfaces.PresentationName;
import sk.stefan.mvps.model.service.VoteService;
import sk.stefan.mvps.model.serviceImpl.VoteServiceImpl;

/**
 * Predstavuje hlasovaci ukon jednej verejnej funkcie. (Tj. ci hlasovala za,
 * proti, etc. )
 */
@Data
public class VoteOfRole implements PresentationName {

    public static final String TN = "t_vote_of_role";

    public static final String PRES_NAME = "Hlasovaneie verejného činiteľa";

    private static final VoteService voteService = new VoteServiceImpl();

    private Integer id;

    private Integer public_role_id;

    private Integer vote_id;

    private VoteAction decision;

    private Boolean visible;

    //0. konstruktor.
    public VoteOfRole() {
    }

    public static String getTN() {
        return TN;
    }

    @Override
    public String getPresentationName() {
        return voteService.getVorPresentationName(this);
    }

}
