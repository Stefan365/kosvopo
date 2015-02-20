
package sk.stefan.MVP.model.entity.dao;

import java.util.Date;
import sk.stefan.MVP.model.repo.dao.UniRepo;
import sk.stefan.interfaces.PresentationName;

public class UserRole implements PresentationName {

    public static final String TN = "A_User_Role";

    public static final String CLASS_PRESENTATION_NAME = "Rola užívateľa";
    
    private Integer id;

    private Integer user_id;

    private Integer role_id;

    private Date since;

    private Date till;

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

    @Override
    public String getPresentationName() {

        UniRepo<User> usrRepo = new UniRepo<User>(User.class);
        UniRepo<Role> rolRepo = new UniRepo<Role>(Role.class);

        if (user_id != null && role_id != null) {
            User u = usrRepo.findOne(user_id);
            Role r = rolRepo.findOne(role_id);
            return u.getPresentationName() + ", " + r.getPresentationName()
                    + ", " + since + ((till == null) ? "" : (", " + till));
        } else {
            return id + ", ";
        }

    }

}
