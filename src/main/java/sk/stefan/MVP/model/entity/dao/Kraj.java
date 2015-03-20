package sk.stefan.MVP.model.entity.dao;

import sk.stefan.interfaces.PresentationName;

public class Kraj implements PresentationName {

    public static final String TN = "t_kraj";
    
    public static final String PRES_NAME = "Kraj";

    private Integer id;

    private String kraj_name;

    //getters:
    public Integer getId() {
        return this.id;
    }

    public String getKraj_name() {
        return this.kraj_name;
    }

    public static String getTN() {
        return TN;
    }

    //setters:
    public void setId(Integer id) {
        this.id = id;
    }

    public void setKraj_name(String kn) {
        this.kraj_name = kn;
    }

    @Override
    public String getPresentationName() {
        return this.kraj_name;
    }

}
