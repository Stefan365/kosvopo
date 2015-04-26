package sk.stefan.MVP.model.entity;

import sk.stefan.MVP.model.repo.UniRepo;
import sk.stefan.interfaces.PresentationName;

public class PublicBody implements PresentationName {

    public static final String TN = "t_public_body";
    
    public static final String PRES_NAME = "Verejný orgán";

    private Integer id;

    private String name;

    private Integer location_id;

    private Boolean visible;

    // getters:
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

        UniRepo<Location> locRepo = new UniRepo<>(Location.class);

        if (location_id != null) {
            Location loc = locRepo.findOne(location_id);
            return name + ", " + loc.getPresentationName();
        } else {
            return name;
        }

    }

}
