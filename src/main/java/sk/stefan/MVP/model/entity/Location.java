package sk.stefan.MVP.model.entity;

import sk.stefan.interfaces.PresentationName;

/**
 * Trieda reprezentuje miesto v zmysle budto obec, alebo mestka cast obce.
 */
public class Location implements PresentationName {

    public static final String TN = "t_location";
    
    public static final String PRES_NAME = "Miesto";

    private Integer id;

    private String location_name;

    private String town_section;

    private Integer district_id;

    private Boolean visible;

    //getters:
    public Integer getId() {
        return this.id;
    }

    public String getLocation_name() {
        return this.location_name;
    }

    public String getTown_section() {
        return this.town_section;
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

    public void setLocation_name(String ln) {
        this.location_name = ln;
    }

    public void setTown_section(String ts) {
        this.town_section = ts;
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
        if (town_section== null || "".equals(town_section)){
            rn = location_name;
        } else {
            rn = location_name + "-"+town_section;
        }
        return rn;
    }

}
