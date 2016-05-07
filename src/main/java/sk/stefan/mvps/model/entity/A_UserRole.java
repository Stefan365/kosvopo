
package sk.stefan.mvps.model.entity;

import java.util.Date;
import lombok.Data;
import sk.stefan.interfaces.PresentationName;
import sk.stefan.mvps.model.service.UserService;
import sk.stefan.mvps.model.serviceImpl.UserServiceImpl;

/**
 * Resprezentuje vazbu medzi uzivatelom a rolou.
 */
@Data
public class A_UserRole implements PresentationName {

    public static final String TN = "a_user_role";

    public static final String PRES_NAME = "Rola užívateľa";
    
    private static final UserService userService = new UserServiceImpl();
    
    
    
    private Integer id;

    private Integer user_id;

    private Integer role_id;
    
    private Boolean actual;

    private Date since;

    private Date till;
    
    private Boolean visible;

    
    
    public static String getTN() {
        return TN;
    }

    @Override
    public String getPresentationName() {
        return userService.getUserRolePresentationName(this);
    }
}
