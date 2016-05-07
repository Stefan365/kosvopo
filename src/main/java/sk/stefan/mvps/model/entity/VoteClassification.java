package sk.stefan.mvps.model.entity;

import lombok.Data;
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
@Data
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
    public VoteClassification(){
    }
    
    public static String getTN() {
        return TN;
    }

    @Override
    public String getPresentationName() {
        return classService.getVotClassPresentationName(this);
    }
}
