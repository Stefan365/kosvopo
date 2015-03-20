package sk.stefan.MVP.model.entity.dao;

import java.sql.Date;
import sk.stefan.MVP.model.repo.dao.UniRepo;
import sk.stefan.enums.PublicUsefulness;
import sk.stefan.enums.Stability;
import sk.stefan.interfaces.PresentationName;

public class PersonClassification implements PresentationName {

    public static final String TN = "t_person_classification";
    
    public static final String PRES_NAME = "Hodnotenie verejnej osoby";

    private Integer id;

    private Date classification_date;

    private Integer public_person_id;

    private Stability stability;

    private PublicUsefulness public_usefulness;

    private Boolean visible;

    // getters:
    public Integer getId() {
        return this.id;
    }

    public Date getClassification_date() {
        return this.classification_date;
    }

    public Integer getPublic_person_id() {
        return this.public_person_id;
    }

    public Stability getStability() {
        return this.stability;
    }

    public PublicUsefulness getPublic_usefulness() {
        return this.public_usefulness;
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

    public void setClassification_date(Date dt) {
        this.classification_date = dt;
    }

    public void setPublic_person_id(Integer ppid) {
        this.public_person_id = ppid;
    }

    public void setStability(Stability stab) {
        this.stability = stab;
    }

    public void setPublic_usefulness(PublicUsefulness usness) {
        this.public_usefulness = usness;
    }

    public void setVisible(Boolean vis) {
        this.visible = vis;
    }

    @Override
    public String getPresentationName() {

        UniRepo<PublicPerson> ppRepo = new UniRepo<>(PublicPerson.class);

        if (public_person_id != null) {
            PublicPerson pp = ppRepo.findOne(public_person_id);
            return id + ", " + pp.getPresentationName();
        } else {
            return id + ", ";
        }

    }

}
