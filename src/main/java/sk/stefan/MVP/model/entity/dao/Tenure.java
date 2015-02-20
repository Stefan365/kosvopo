package sk.stefan.MVP.model.entity.dao;

import sk.stefan.interfaces.PresentationName;

import java.util.Date;

public class Tenure implements PresentationName {

    public static final String TN = "T_Tenure";
    
    public static final String CLASS_PRESENTATION_NAME = "Volebné obdobie";

    private Integer id;

    private Date since;

    private Date till;

    private Boolean visible;

    //getters:
    public Integer getId() {
        return this.id;
    }

    public Date getSince() {
        return this.since;
    }

    public Date getTill() {
        return this.till;
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

    public void setSince(Date since) {
        this.since = since;
    }

    public void setTill(Date till) {
        this.till = till;
    }

    public void setVisible(Boolean vis) {
        this.visible = vis;
    }

    @Override
    public String getPresentationName() {
        if (till != null) {
            return since + " - " + till;
        } else {
            return since + "";
        }
    }

}
