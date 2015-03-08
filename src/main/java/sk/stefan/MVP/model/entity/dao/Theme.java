package sk.stefan.MVP.model.entity.dao;

import sk.stefan.interfaces.PresentationName;

public class Theme implements PresentationName {

    public static final String TN = "t_theme";
    
    public static final String CLASS_PRESENTATION_NAME = "Tématický okruh hlasovania";

    private Integer id;

    private String brief_description;

    private String description;

    private Boolean visible;
    
    
    

    //getters:
    public Integer getId() {
        return this.id;
    }

    public String getBrief_description() {
        return this.brief_description;
    }

    public String getDescription() {
        return this.description;
    }

    public Boolean getVisible() {
        return this.visible;
    }

    public static String getTN() {
        return TN;
    }

    //setters:
    public void setId(Integer id) {
        this.id = id;
    }

    public void setBrief_description(String brdes) {
        this.brief_description = brdes;
    }

    public void setDescription(String des) {
        this.description = des;
    }

    public void setVisible(Boolean vis) {
        this.visible = vis;
    }

    @Override
    public String getPresentationName() {
        return this.brief_description;
    }

}
