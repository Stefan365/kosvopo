package sk.stefan.MVP.model.entity.dao;

import org.apache.log4j.Logger;
import sk.stefan.MVP.model.repo.dao.UniRepo;
import sk.stefan.interfaces.PresentationName;

public class ActClassification implements PresentationName {

    private static final Logger log = Logger.getLogger(ActClassification.class);
    
    private static final String TN = "T_Act_Classification";

    private Integer id;

    private Integer vote_of_role_id;

    private Integer subject_id;

    private Integer zhoda_s_programom;

    private Integer public_malignity;

    private Boolean visible;

    // getters:
    public Integer getId() {
        return this.id;
    }

    public Integer getVote_of_role_id() {
        return this.vote_of_role_id;
    }

    public Integer getSubject_id() {
        return this.subject_id;
    }

    public Integer getZhoda_s_programom() {
        return this.zhoda_s_programom;
    }

    public Integer getPublic_malignity() {
        return this.public_malignity;
    }

    public Boolean getVisible() {
        return this.visible;
    }

    public static String getTN() {
        return TN;
    }

    // setters:i
    public void setId(Integer id) {
        this.id = id;
    }

    public void setVote_of_role_id(Integer vorid) {
        this.vote_of_role_id = vorid;
    }

    public void setSubject_id(Integer subid) {
        this.subject_id = subid;
    }

    public void setZhoda_s_programom(Integer zp) {
        this.zhoda_s_programom = zp;
    }

    public void setPublic_malignity(Integer pm) {
        this.public_malignity = pm;
    }

    public void setVisible(Boolean vis) {
        this.visible = vis;
    }

    @Override
    public String getPresentationName() {

        UniRepo<VoteOfRole> vorRepo = new UniRepo<VoteOfRole>(VoteOfRole.class);
        UniRepo<PublicRole> prRepo = new UniRepo<PublicRole>(PublicRole.class);
        UniRepo<PublicPerson> ppRepo = new UniRepo<PublicPerson>(
                PublicPerson.class);
        if (vote_of_role_id != null) {
            //log.info("KAMILKO0: *" + vote_of_role_id + "*");
            
            VoteOfRole vor = vorRepo.findOne(vote_of_role_id);
//            log.info("KAMILKO1: *" + (vor == null) + "*");
//            log.info("KAMILKO2: *" + (prRepo == null) + "*");
//            
            PublicRole pr = prRepo.findOne(vor.getPublic_role_id());
            PublicPerson pp = ppRepo.findOne(pr.getPublic_person_id());
            return id + ", " + pp.getPresentationName();
        } else {
            return id + ", ";
        }

    }

}
