package sk.stefan.MVP.model.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import sk.stefan.MVP.model.repo.UniRepo;
import sk.stefan.enums.VoteResult;
import sk.stefan.interfaces.PresentationName;

/**
 * Predstavuje jedno konkretne hlasovanie verejneho organu.
 */
public class Vote implements PresentationName {

    public static final String TN = "t_vote";

    public static final String PRES_NAME = "Hlasovanie";

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

    private Integer for_vote;

    private Integer against_vote;

    private Integer refrain_vote;

    private Integer absent;
    
    private Boolean visible;

    // getters:
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

    
    public Integer getFor_vote() {
        return this.for_vote;
    }

    public Integer getAgainst_vote() {
        return this.against_vote;
    }

    public Integer getRefrain_vote() {
        return this.refrain_vote;
    }

    public Integer getAbsent() {
        return this.absent;
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

    public void setFor_vote(Integer fv) {
        this.for_vote = fv;
    }

    public void setAgainst_vote(Integer av) {
        this.against_vote = av;
    }

    public void setRefrain_vote(Integer rf) {
        this.refrain_vote = rf;
    }

    public void setAbsent(Integer a) {
        this.absent = a;
    }

    public void setVisible(Boolean vis) {
        this.visible = vis;
    }


    @Override
    public String getPresentationName() {

        UniRepo<Subject> subRepo = new UniRepo<>(Subject.class);
        
        if (getSubject_id() != null) {
            Subject sub = subRepo.findOne(getSubject_id());
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date d = this.getVote_date();
            String dateStr;
            if (d != null){
                dateStr = sdf.format(this.getVote_date());
            } else {
                dateStr ="no date";
            }

            return sub.getPresentationName() + ", " + dateStr;
        } else {
            return getId() + ", nedefinované";
        }

    }


}
