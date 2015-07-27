package sk.stefan.mvps.model.entity;

import sk.stefan.enums.UserType;
import sk.stefan.interfaces.PresentationName;

/**
 * Reprezentuje rolu uzivatela systemu.
 * 
 */
public class A_Role implements PresentationName {

    public static final String TN = "a_role";

    public static final String PRES_NAME = "Rola";

    private Integer id;

    private UserType role;

    private String role_name;

    private String rights_description;
    
    private Boolean visible;
    

    //getters:
    public Integer getId() {
        return this.id;
    }
    
    public UserType getRole() {
        return role;
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

    public void setRole(UserType role) {
        this.role = role;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public void setRights_description(String rights_description) {
        this.rights_description = rights_description;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    
    @Override
    public String getPresentationName() {

        return this.role_name;
    }

}
