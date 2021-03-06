package sk.stefan.mvps.model.entity;

import sk.stefan.interfaces.PresentationName;
import sk.stefan.interfaces.TabEntity;
import sk.stefan.mvps.model.service.PublicBodyService;
import sk.stefan.mvps.model.serviceImpl.PublicBodyServiceImpl;

/**
 * Trieda reprezentuje verjny organ (mestke zastupitelstvo, parlament, ...).
 */
public class PublicBody implements TabEntity {

    public static final String TN = "t_public_body";
    
    public static final String PRES_NAME = "Verejný orgán";

    private static final PublicBodyService pubBodyService = new PublicBodyServiceImpl();
    
    
    
    private Integer id;

    private String name;

    private Integer location_id;

    private byte[] image;

    private Boolean visible;

    @Override
    public String getEntityName() {
        return "body";
    }

    @Override
    public String getRelatedTabName() {
        return "verejnyOrgan";
    }

    // getters:
    @Override
    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Integer getLocation_id() {
        return this.location_id;
    }

    public Boolean getVisible() {
        return this.visible;
    }

    public static String getTN() {
        return TN;
    }

    // setters:
    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String nam) {
        this.name = nam;
    }

    public void setLocation_id(Integer locid) {
        this.location_id = locid;
    }

    public void setVisible(Boolean vis) {
        this.visible = vis;
    }

    @Override
    public String getPresentationName() {
        
        return pubBodyService.getPresentationName(this);
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
