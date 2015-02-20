package sk.stefan.MVP.model.entity.dao;

import sk.stefan.interfaces.PresentationName;

public class Location implements PresentationName {

    public static final String TN = "T_Location";
    
    public static final String CLASS_PRESENTATION_NAME = "Miesto";

    private Integer id;

    private String obec_name;

    private String mestka_cast;

    private Integer okres_id;

    private Boolean visible;

    //getters:
    public Integer getId() {
        return this.id;
    }

    public String getObec_name() {
        return this.obec_name;
    }

    public String getMestka_cast() {
        return this.mestka_cast;
    }

    public Integer getOkres_id() {
        return this.okres_id;
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

    public void setObec_name(String obn) {
        this.obec_name = obn;
    }

    public void setMestka_cast(String mc) {
        this.mestka_cast = mc;
    }

    public void setOkres_id(Integer okrid) {
        this.okres_id = okrid;
    }

    public void setVisible(Boolean vis) {
        this.visible = vis;
    }

    @Override
    public String getPresentationName() {
        return this.obec_name;
    }

}
