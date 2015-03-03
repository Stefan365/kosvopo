package sk.stefan.MVP.model.entity.dao;

import sk.stefan.interfaces.PresentationName;

import java.util.Date;

import sk.stefan.MVP.model.repo.dao.UniRepo;
import sk.stefan.enums.VoteResults;

public class Vote implements PresentationName {

    public static final String TN = "T_Vote";

    public static final String CLASS_PRESENTATION_NAME = "Hlasovanie";

    private Integer id;

    private Date vote_date;

    private Integer public_body_id;

    private Integer subject_id;

    private String internal_nr;

    private VoteResults result_vote;

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

    public Integer getPublic_body_id() {
        return this.public_body_id;
    }

    public Integer getSubject_id() {
        return this.subject_id;
    }

    public String getInternal_nr() {
        return this.internal_nr;
    }

    public VoteResults getResult_vote() {
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

    public void setPublic_body_id(Integer pbid) {
        this.public_body_id = pbid;
    }

    public void setSubject_id(Integer subid) {
        this.subject_id = subid;
    }

    public void setInternal_nr(String inr) {
        this.internal_nr = inr;
    }

    public void setResult_vote(VoteResults rv) {
        this.result_vote = rv;
    }
    
//    public void setResult_vote(Short rv) {
//        this.result_vote = VoteResults.values()[rv];
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

        UniRepo<PublicBody> pbRepo = new UniRepo<PublicBody>(PublicBody.class);
        UniRepo<Subject> subRepo = new UniRepo<Subject>(Subject.class);

        if (public_body_id != null && subject_id != null) {
            PublicBody pb = pbRepo.findOne(public_body_id);
            Subject sub = subRepo.findOne(subject_id);

            return pb.getPresentationName() + ", " + sub.getPresentationName()
                    + ", " + this.vote_date;
        } else {
            return id + ", ";
        }

    }

}
