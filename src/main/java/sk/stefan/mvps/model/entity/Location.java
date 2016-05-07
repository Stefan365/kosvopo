package sk.stefan.mvps.model.entity;

import lombok.Data;
import sk.stefan.interfaces.PresentationName;

/**
 * Trieda reprezentuje miesto v zmysle budto obec, alebo mestka cast obce.
 */
@Data
public class Location implements PresentationName {

    public static final String TN = "t_location";
    
    public static final String PRES_NAME = "Miesto";

    private Integer id;

    private String location_name;

    private String town_section;

    private Integer district_id;

    private Boolean visible;


    
    public static String getTN() {
        return TN;
    }

    @Override
    public String getPresentationName() {
        
        String rn;
        if (town_section== null || "".equals(town_section)){
            rn = location_name;
        } else {
            rn = location_name + "-"+town_section;
        }
        return rn;
    }
}
