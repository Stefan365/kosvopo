package sk.stefan.MVP.model.entity.dao;

import org.apache.log4j.Logger;
import sk.stefan.MVP.model.repo.dao.UniRepo;
import sk.stefan.interfaces.PresentationName;

public class VoteClassification implements PresentationName {

    private static final Logger log = Logger.getLogger(VoteClassification.class);
    
    public static final String TN = "t_vote_classification";
    
    public static final String CLASS_PRESENTATION_NAME = "Hodnotenie hlasovania";

    private Integer id;

    private Integer vote_id;

    private Integer public_malignity;
    
    private String brief_description;

    private Boolean visible;

    // getters:
    public Integer getId() {
        return this.id;
    }
    
    public Integer getVote_id() {
        return this.vote_id;
    }

    public String getBrief_description() {
        return this.brief_description;
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

    public void setVote_id(Integer vid) {
        this.vote_id = vid;
    }

    public void setPublic_malignity(Integer pm) {
        this.public_malignity = pm;
    }

    public void setVisible(Boolean vis) {
        this.visible = vis;
    }

    @Override
    public String getPresentationName() {

        UniRepo<Vote> voteRepo = new UniRepo<>(Vote.class);
        if (vote_id != null) {
            
            Vote vot = voteRepo.findOne(vote_id);
            return id + ", " + vot.getPresentationName();
        } else {
            return id + ", ";
        }

    }

}
