package sk.stefan.mvps.model.entity;

import java.sql.Date;
import lombok.Data;
import sk.stefan.mvps.model.service.ClassificationService;
import sk.stefan.mvps.model.serviceImpl.ClassificationServiceImpl;
import sk.stefan.enums.PublicUsefulness;
import sk.stefan.enums.Stability;
import sk.stefan.interfaces.PresentationName;

/**
 * Trida reprezentuje hodnotenie verejnej osoby.
 */
@Data
public class PersonClassification implements PresentationName {

    public static final String TN = "t_person_classification";
    
    public static final String PRES_NAME = "Hodnotenie verejnej osoby";
    
    private static final ClassificationService classService = new ClassificationServiceImpl();
    

    private Integer id;

    private Date classification_date;

    private Integer public_person_id;

    private Stability stability;

    private PublicUsefulness public_usefulness;

    private Boolean actual;

    private Boolean visible;

  
    
    
    
    
    public static String getTN() {
        return TN;
    }

    @Override
    public String getPresentationName() {
        return classService.getPersonClassPresentationName(this);
    }

}
