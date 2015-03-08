package sk.stefan.MVP.model.entity.dao;

import sk.stefan.interfaces.PresentationName;

public final class Okres implements PresentationName {

    public static final String TN = "t_okres";
    
    public static final String CLASS_PRESENTATION_NAME = "Okres";

    private Integer id;

    private String okres_name;

    private Integer kraj_id;

    public Okres() {
    }

    public Okres(String on, Integer kid) {
        this.setOkres_name(on);
        this.setKraj_id(kid);
    }

    //getters:
    public Integer getId() {
        return this.id;
    }

    public String getOkres_name() {
        return this.okres_name;
    }

    public Integer getKraj_id() {
        return this.kraj_id;
    }

    public static String getTN() {
        return TN;
    }

    //setters:
    public void setId(Integer id) {
        this.id = id;
    }

    public void setOkres_name(String on) {
        this.okres_name = on;
    }

    public void setKraj_id(Integer kr) {
        this.kraj_id = kr;
    }

    @Override
    public String getPresentationName() {
        return this.okres_name;
    }
}
