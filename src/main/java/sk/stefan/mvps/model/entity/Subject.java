package sk.stefan.mvps.model.entity;

import lombok.Data;
import sk.stefan.interfaces.PresentationName;
import sk.stefan.interfaces.TabEntity;
import sk.stefan.mvps.model.service.VoteService;
import sk.stefan.mvps.model.serviceImpl.VoteServiceImpl;

/**
 * Trieda reprezentuje predmet hlasovania, tj. konkretne to, k comu sa dane
 * hlasovanie vztahuje.
 */
@Data
public class Subject implements PresentationName, TabEntity {

    public static final String TN = "t_subject";

    public static final String PRES_NAME = "Predmet hlasovania";

    private static final VoteService voteService = new VoteServiceImpl();

    private Integer id;

    private String brief_description;

    private String description;

    private String submitter_name;

    private Integer public_body_id;

    private Boolean visible;

    public static String getTN() {
        return TN;
    }

    @Override
    public String getPresentationName() {
        return voteService.getSubjectPresentationName(this);
    }

    @Override
    public String getEntityName() {
        return "subject";
    }

    @Override
    public String getRelatedTabName() {
        return "subject";
    }

}
