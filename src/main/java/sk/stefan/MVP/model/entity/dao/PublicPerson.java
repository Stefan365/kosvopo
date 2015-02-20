package sk.stefan.MVP.model.entity.dao;

import sk.stefan.interfaces.PresentationName;

import java.util.Date;

public class PublicPerson implements PresentationName {

    public static final String TN = "T_Public_Person";
    
    public static final String CLASS_PRESENTATION_NAME = "Verejná osoba";

    private Integer id;

    private String first_name;

    private String last_name;

    private Date date_of_birth;

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
