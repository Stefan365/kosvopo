package sk.stefan.mvps.model.entity;

import org.apache.log4j.Logger;
import sk.stefan.enums.PublicUsefulness;
import sk.stefan.interfaces.PresentationName;
import sk.stefan.mvps.model.service.ClassificationService;
import sk.stefan.mvps.model.serviceImpl.ClassificationServiceImpl;

/**
 * Klasifikacia navrhu hlasovania. Niektore navrhy mozu byt verejne prospesne, 
 * ine verejne skodlive. Tato trieda to ma podchytit.
 * 
 * Toto by vlastne mohlo byt vztiahnute aj na subject, ale takto sa to bude lepsie 
 * prenasat na akterov hlasovania - tych co hlasovali. tj. do ich osobneho hodnotenia.
 */
public class VoteClassification implements PresentationName {

    private static final Logger log = Logger.getLogger(VoteClassification.class);
    
    public static final String TN = "t_vote_classification";
    
    public static final String PRES_NAME = "Hodnotenie hlasovania";
    
    private static final ClassificationService classService = new ClassificationServiceImpl();



    private Integer id;

    private Integer vote_id;

    private PublicUsefulness public_usefulness;
    
    private String brief_description;

    private Boolean visible;

    
    
    //0. konstruktor
    /**
     */
    public VoteClassification(){
        
    }
    
    
    // getters and setters:
    public Integer getId() {
        return this.id;
    }
    
    public Integer getVote_id() {
        return this.vote_id;
    }

    public String getBrief_description() {
        return this.brief_description;
    }

    public PublicUsefulness getPublic_usefulness() {
        return this.public_usefulness;
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

    public void setPublic_usefulness(PublicUsefulness pu) {
        this.public_usefulness = pu;
    }
    /**
     * @param brief_description the brief_description to set
     */
    public void setBrief_description(String brief_description) {
        this.brief_description = brief_description;
    }

    public void setVisible(Boolean vis) {
        this.visible = vis;
    }

    @Override
    public String getPresentationName() {
        
        return classService.getVotClassPresentationName(this);
        
    }
}
