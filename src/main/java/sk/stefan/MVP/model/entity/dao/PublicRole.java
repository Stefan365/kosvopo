package sk.stefan.MVP.model.entity.dao;

import sk.stefan.MVP.model.repo.dao.UniRepo;
import sk.stefan.interfaces.PresentationName;

public class PublicRole implements PresentationName {

    public static final String TN = "t_public_role";
    
    public static final String CLASS_PRESENTATION_NAME = "Verejná funkcia";

    private Integer id;

    private Integer public_body_id;

    private Integer tenure_id;

    private Integer public_person_id;

    private String name;

    private Boolean visible;

    // getters:
    public Integer getId() {
        return this.id;
    }

    public Integer getPublic_body_id() {
        return this.public_body_id;
    }

    public Integer getTenure_id() {
        return this.tenure_id;
    }

    public Integer getPublic_person_id() {
        return this.public_person_id;
    }

    public String getName() {
        return this.name;
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

    public void setPublic_body_id(Integer pbid) {
        this.public_body_id = pbid;
    }

    public void setTenure_id(Integer tenid) {
        this.tenure_id = tenid;
    }

    public void setPublic_person_id(Integer ppid) {
        this.public_person_id = ppid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVisible(Boolean vis) {
        this.visible = vis;
    }

    @Override
    public String getPresentationName() {

        UniRepo<PublicPerson> ppRepo = new UniRepo<>(
                PublicPerson.class);
        UniRepo<Tenure> tenRepo = new UniRepo<>(Tenure.class);

        if (public_body_id != null && public_person_id != null
                && tenure_id != null) {
            PublicPerson pp = ppRepo.findOne(public_person_id);
            Tenure ten = tenRepo.findOne(tenure_id);
            return pp.getPresentationName()
                    + ", " + ten.getPresentationName();
        } else {
            return id + ", nedefinované";
        }

    }

}
