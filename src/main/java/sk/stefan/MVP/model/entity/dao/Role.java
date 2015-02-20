package sk.stefan.MVP.model.entity.dao;

import sk.stefan.interfaces.PresentationName;

public class Role implements PresentationName {

    public static final String TN = "A_Role";
    
    public static final String CLASS_PRESENTATION_NAME = "Rola";

    private Integer id;

    private String role_name;

    private String rights_description;

    //getters:
    public Integer getId() {
        return this.id;
    }

    public String getRole_name() {
        return this.role_name;
    }

    public String getRights_description() {
        return this.rights_description;
    }

    public static String getTN() {
        return TN;
    }

    //setters:
    public void setId(Integer id) {
        this.id = id;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public void setRights_description(String rights_description) {
        this.rights_description = rights_description;
    }

    @Override
    public String getPresentationName() {

        return this.role_name;
    }

}
