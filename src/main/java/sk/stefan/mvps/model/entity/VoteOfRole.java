package sk.stefan.mvps.model.entity;

import sk.stefan.enums.VoteAction;
import sk.stefan.interfaces.PresentationName;
import sk.stefan.mvps.model.service.VoteService;
import sk.stefan.mvps.model.serviceImpl.VoteServiceImpl;

/**
 * Predstavuje hlasovaci ukon jednej verejnej funkcie. 
 * (Tj. ci hlasovala za, proti, etc. )
 */
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
    /**
     */
    public VoteOfRole(){
    }
    
    // getters:
    public Integer getId() {
        return this.id;
    }

    public Integer getPublic_role_id() {
        return this.public_role_id;
    }

    public Integer getVote_id() {
        return this.vote_id;
    }
    public VoteAction getDecision() {
        return decision;
    }

    public Boolean getVisible() {
        return this.visible;
    }

    
    public static String getTN() {
        return TN;
    }

    // setters:
    public void setId(Integer id) {
        this.id = id;
    }

    public void setPublic_role_id(Integer prid) {
        this.public_role_id = prid;
    }

    public void setVote_id(Integer votid) {
        this.vote_id = votid;
    }

    public void setDecision(VoteAction decision) {
        this.decision = decision;
    }

    public void setVisible(Boolean vis) {
        this.visible = vis;
    }

    /**
     *
     * @return
     */
    @Override
    public String getPresentationName() {

        return voteService.getVorPresentationName(this);

    }


}
