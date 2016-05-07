package sk.stefan.mvps.model.entity;

import java.util.Date;
import lombok.Data;
import sk.stefan.enums.VoteResult;
import sk.stefan.interfaces.TabEntity;
import sk.stefan.mvps.model.service.VoteService;
import sk.stefan.mvps.model.serviceImpl.VoteServiceImpl;

/**
 * Predstavuje jedno konkretne hlasovanie verejneho organu.
 */
@Data
public class Vote implements TabEntity {

    public static final String TN = "t_vote";

    public static final String PRES_NAME = "Hlasovanie";

    private static final VoteService voteService = new VoteServiceImpl();

    private Integer id;

    private Date vote_date;

    private Integer subject_id;

    private String internal_nr;

    private VoteResult result_vote;

    private Boolean visible;

    @Override
    public String getEntityName() {
        return "vote";
    }

    @Override
    public String getRelatedTabName() {
        return "hlasovani";
    }

    public static String getPRES_NAME() {
        return PRES_NAME;
    }

    public static String getTN() {
        return TN;
    }

    @Override
    public String getPresentationName() {
        return voteService.getVotePresentationName(this);
    }
}
