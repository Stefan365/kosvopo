package sk.stefan.MVP.model.entity;

import java.sql.Date;
import sk.stefan.interfaces.PresentationName;

/**
 * Trieda reprezentuje verejne cinnu fyzicku osobu (napr. Vaclav Havel, etc...).
 */
public class PublicPerson implements PresentationName {

    public static final String TN = "t_public_person";
    
    public static final String PRES_NAME = "Verejná osoba";

    private Integer id;

    private String first_name;

    private String last_name;

    private Date date_of_birth;
    
    //potom pridat
//    private byte[] photo;
    

    private Boolean visible;

    
    
    
    
    
    
    
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

    public Date getDate_of_birth() {
        return this.date_of_birth;
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

    public void setFirst_name(String fn) {
        this.first_name = fn;
    }

    public void setLast_name(String ln) {
        this.last_name = ln;
    }

    public void setDate_of_birth(Date bd) {
        this.date_of_birth = bd;
    }

    public void setVisible(Boolean vis) {
        this.visible = vis;
    }

    @Override
    public String getPresentationName() {
        return (this.getFirst_name() + " " + this.getLast_name());
    }
}
