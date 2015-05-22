package sk.stefan.MVP.model.entity;

import sk.stefan.interfaces.PresentationName;

/**
 * Trieda reprezentuje este vyssi uzemny celok ako district, v nasom pripade Kraj.
 */
public class Region implements PresentationName {

    public static final String TN = "t_region";
    
    public static final String PRES_NAME = "Kraj";

    private Integer id;

    private String region_name;
    
    private Boolean visible;

    //getters:
    public Integer getId() {
        return this.id;
    }

    public String getRegion_name() {
        return this.region_name;
    }

    public static String getTN() {
        return TN;
    }

    //setters:
    public void setId(Integer id) {
        this.id = id;
    }

    public void setRegion_name(String kn) {
        this.region_name = kn;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    @Override
    public String getPresentationName() {
        return this.region_name;
    }

}
