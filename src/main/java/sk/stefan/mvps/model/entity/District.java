package sk.stefan.mvps.model.entity;

import lombok.Data;
import sk.stefan.interfaces.PresentationName;

/**
 * Trieda reprezentuje vyssi uzemny celok, v nasom pripade okres. 
 */
@Data
public final class District implements PresentationName {

    public static final String TN = "t_district";
    
    public static final String PRES_NAME = "Okres";

    private Integer id;

    private String district_name;

    private Integer region_id;

    private Boolean visible;
    
    public District() {
    }

    public District(String on, Integer rid) {
        this.setDistrict_name(on);
        this.setRegion_id(rid);
    }

    public static String getTN() {
        return TN;
    }

    @Override
    public String getPresentationName() {
        return this.district_name;
    }

}
