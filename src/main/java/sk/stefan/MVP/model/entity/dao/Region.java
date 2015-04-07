package sk.stefan.MVP.model.entity.dao;

import sk.stefan.interfaces.PresentationName;

public class Region implements PresentationName {

    public static final String TN = "t_kraj";
    
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

    @Override
    public String getPresentationName() {
        return this.region_name;
    }

    /**
     * @return the visible
     */
    public Boolean getVisible() {
        return visible;
    }

    /**
     * @param visible the visible to set
     */
    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

}
