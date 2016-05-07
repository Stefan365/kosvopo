package sk.stefan.mvps.model.entity;

import java.io.Serializable;
import lombok.Data;

import sk.stefan.interfaces.TabEntity;
import sk.stefan.mvps.model.service.SecurityService;
import sk.stefan.mvps.model.serviceImpl.SecurityServiceImpl;

/**
 * Reprezentuje uzivatela nasho systemu.
 */
@Data
public class A_User implements Serializable, TabEntity {

    public static final long serialVersionUID = 1L;

    public static final String TN = "a_user";
    
    public static final String PRES_NAME = "uživateľ";
    
    private Integer id;

    private String first_name;

    private String last_name;

    private String e_mail;

    private String login;

    private byte[] password;

    private byte[] image;
    
    private Boolean visible;
    
    
    private final SecurityService securityService = new SecurityServiceImpl();

    public A_User(String fn, String ln, String em, String lg, String pw) {
        this.first_name = fn;
        this.last_name = ln;
        this.e_mail = em;
        this.login = lg;
        this.password = securityService.encryptPassword(pw);
    }

    public A_User(String meno) {
        this.first_name = meno;
    }

    public A_User() {
    }

    @Override
    public String getEntityName() {
        return "user";
    }

    @Override
    public String getRelatedTabName() {
        return "uzivatel";
    }

    public static String getTN() {
        return TN;
    }

    @Override
    public String getPresentationName() {
        return this.first_name + " " + this.last_name;
    }
}
