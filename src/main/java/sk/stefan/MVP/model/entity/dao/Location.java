package sk.stefan.MVP.model.entity.dao;

import sk.stefan.interfaces.PresentationName;

public class Location implements PresentationName {

    public static final String TN = "t_location";
    
    public static final String PRES_NAME = "Miesto";

    private Integer id;

    private String obec_name;

    private String mestka_cast;

    private Integer district_id;

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

    public Integer getDistrict_id() {
        return this.district_id;
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

    public void setDistrict_id(Integer okrid) {
        this.district_id = okrid;
    }

    public void setVisible(Boolean vis) {
        this.visible = vis;
    }

    @Override
    public String getPresentationName() {
        String rn;
        if (mestka_cast== null || "".equals(mestka_cast)){
            rn = obec_name;
        } else {
            rn = obec_name + "-"+mestka_cast;
        }
        return rn;
    }

}
