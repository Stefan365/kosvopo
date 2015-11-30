package sk.stefan.mvps.model.entity;

import java.util.Date;

import sk.stefan.interfaces.TabEntity;
import sk.stefan.mvps.model.service.VoteService;
import sk.stefan.mvps.model.serviceImpl.VoteServiceImpl;
import sk.stefan.enums.VoteResult;
import sk.stefan.interfaces.PresentationName;

/**
 * Predstavuje jedno konkretne hlasovanie verejneho organu.
 */
public class Vote implements TabEntity {

    public static final String TN = "t_vote";

    public static final String PRES_NAME = "Hlasovanie";
    
    private static final VoteService voteService = new VoteServiceImpl(); 


    /**
     * @return the PRES_NAME
     */
    public static String getPRES_NAME() {
        return PRES_NAME;
    }

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

    //     getters adn setters:
    public Integer getId() {
        return this.id;
    }

    public Date getVote_date() {
        return this.vote_date;
    }

    public Integer getSubject_id() {
        return this.subject_id;
    }

    public String getInternal_nr() {
        return this.internal_nr;
    }

    public VoteResult getResult_vote() {
        return this.result_vote;
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

    public void setVote_date(Date date) {
        this.vote_date = date;
    }

    public void setSubject_id(Integer subid) {
        this.subject_id = subid;
    }

    public void setInternal_nr(String inr) {
        this.internal_nr = inr;
    }

    public void setResult_vote(VoteResult rv) {
        this.result_vote = rv;
    }
    
//    public void setResult_vote(Short rv) {
//        this.result_vote = VoteResult.values()[rv];
//    }

    public void setVisible(Boolean vis) {
        this.visible = vis;
    }


    @Override
    public String getPresentationName() {
        
        return voteService.getVotePresentationName(this);

    }


}
