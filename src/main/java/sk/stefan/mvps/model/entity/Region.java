package sk.stefan.mvps.model.entity;

import lombok.Data;
import sk.stefan.interfaces.PresentationName;

/**
 * Trieda reprezentuje este vyssi uzemny celok ako district, v nasom pripade Kraj.
 */
@Data
public class Region implements PresentationName {

    public static final String TN = "t_region";
    
    public static final String PRES_NAME = "Kraj";

    private Integer id;

    private String region_name;
    
    private Boolean visible;


    
    public static String getTN() {
        return TN;
    }

    @Override
    public String getPresentationName() {
        return this.region_name;
    }

}
