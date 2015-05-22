package sk.stefan.MVP.model.entity;

import sk.stefan.interfaces.PresentationName;

/**
 * Trieda reprezentuje vyssi uzemny celok, v nasom pripade okres. 
 */
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

    //getters:
    public Integer getId() {
        return this.id;
    }

    public String getDistrict_name() {
        return this.district_name;
    }

    public Integer getRegion_id() {
        return this.region_id;
    }

    public static String getTN() {
        return TN;
    }

    //setters:
    public void setId(Integer id) {
        this.id = id;
    }

    public void setDistrict_name(String on) {
        this.district_name = on;
    }

    public void setRegion_id(Integer kr) {
        this.region_id = kr;
    }


    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
    
    
    @Override
    public String getPresentationName() {
        return this.district_name;
    }

}
