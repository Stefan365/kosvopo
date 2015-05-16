
package sk.stefan.MVP.model.entity;

import java.util.Date;
import sk.stefan.MVP.model.repo.UniRepo;
import sk.stefan.interfaces.PresentationName;

public class A_UserRole implements PresentationName {

    public static final String TN = "a_user_role";

    public static final String PRES_NAME = "Rola užívateľa";
    
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

        UniRepo<A_User> usrRepo = new UniRepo<>(A_User.class);
        UniRepo<A_Role> rolRepo = new UniRepo<>(A_Role.class);

        if (user_id != null && role_id != null) {
            A_User u = usrRepo.findOne(user_id);
            A_Role r = rolRepo.findOne(role_id);
            return u.getPresentationName() + ", " + r.getPresentationName() + ", " + id;
//                    + ", " + since + ((till == null) ? "" : (", " + till));
        } else {
            return id + ", nedefinované";
        }
    }
}
