package sk.stefan.mvps.model.entity;

import lombok.Data;
import sk.stefan.enums.UserType;
import sk.stefan.interfaces.PresentationName;

/**
 * Reprezentuje rolu uzivatela systemu.
 * 
 */
@Data
public class A_Role implements PresentationName {

    public static final String TN = "a_role";

    public static final String PRES_NAME = "Rola";

    private Integer id;

    private UserType role;

    private String role_name;

    private String rights_description;
    
    private Boolean visible;
    

    public static String getTN() {
        return TN;
    }

    @Override
    public String getPresentationName() {
        return this.role_name;
    }

}
