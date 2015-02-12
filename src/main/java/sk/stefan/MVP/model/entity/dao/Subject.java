package sk.stefan.MVP.model.entity.dao;

import sk.stefan.interfaces.PresentationName;

public class Subject implements PresentationName {

    private static final String TN = "T_Subject";

    private Integer id;

    private String brief_description;

    private String description;

    private Integer public_role_id;

    private Integer theme_id;

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

    public Integer getPublic_role_id() {
        return this.public_role_id;
    }

    public Integer getTheme_id() {
        return this.theme_id;
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

    public void setPublic_role_id(Integer prid) {
        this.public_role_id = prid;
    }

    public void setTheme_id(Integer thid) {
        this.theme_id = thid;
    }

    public void setVisible(Boolean vis) {
        this.visible = vis;
    }

    @Override
    public String getPresentationName() {
        return this.brief_description;
    }

}
