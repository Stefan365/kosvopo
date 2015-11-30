package sk.stefan.mvps.model.entity;

import java.io.Serializable;
import sk.stefan.mvps.model.service.SecurityService;
import sk.stefan.mvps.model.serviceImpl.SecurityServiceImpl;
import sk.stefan.interfaces.PresentationName;

/**
 * Reprezentuje uzivatela nasho systemu.
 */
public class A_User implements Serializable, PresentationName {

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

    //getters:
    public Integer getId() {
        return this.id;
    }

    public String getFirst_name() {
        return this.first_name;
    }

    public String getLast_name() {
        return this.last_name;
    }

    public String getE_mail() {
        return this.e_mail;
    }

    public String getLogin() {
        return this.login;
    }

    public byte[] getPassword() {
        return this.password;
    }

    public static String getTN() {
        return TN;
    }

    //setters:
    public void setId(Integer id) {
        this.id = id;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    @Override
    public String getPresentationName() {
        return "USER: " + this.first_name + " " + this.last_name;
    }


    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
