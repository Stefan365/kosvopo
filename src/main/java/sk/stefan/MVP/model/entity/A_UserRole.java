
package sk.stefan.MVP.model.entity;

import java.util.Date;
import sk.stefan.MVP.model.service.UserService;
import sk.stefan.MVP.model.serviceImpl.UserServiceImpl;
import sk.stefan.interfaces.PresentationName;

/**
 * Resprezentuje vazbu medzi uzivatelom a rolou.
 */
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

    
    
    
    
    
    
    
    
    
    
    // getters:
    public Integer getId() {
        return this.id;
    }

    public Integer getUser_id() {
        return this.user_id;
    }

    public Integer getRole_id() {
        return this.role_id;
    }

    public Date getSince() {
        return this.since;
    }

    public Date getTill() {
        return this.till;
    }

    public static String getTN() {
        return TN;
    }

    // setters:
    public void setId(Integer id) {
        this.id = id;
    }

    public void setUser_id(Integer uid) {
        this.user_id = uid;
    }

    public void setRole_id(Integer rid) {
        this.role_id = rid;
    }

    public void setSince(Date since) {
        this.since = since;
    }

    public void setTill(Date till) {
        this.till = till;
    }
    

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Boolean getActual() {
        return actual;
    }

    public void setActual(Boolean actual) {
        this.actual = actual;
    }

    @Override
    public String getPresentationName() {
        
        return userService.getUserRolePresentationName(this);
        
    }
}
