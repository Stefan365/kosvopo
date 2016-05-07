package sk.stefan.mvps.model.entity;

import java.sql.Date;
import lombok.Data;
import sk.stefan.interfaces.PresentationName;

/**
 * Volebne obdobie, vztahuje sa k verejnej roli.
 */
@Data
public class Tenure implements PresentationName {

    public static final String TN = "t_tenure";
    
    public static final String PRES_NAME = "Volebné obdobie";

    private Integer id;

    private Date since;

    private Date till;

    private Boolean visible;

    public static String getTN() {
        return TN;
    }

    @Override
    public String getPresentationName() {
        if (till != null) {
            return since + "-" + till;
        } else {
            return since + "- ...";
        }
    }

}
