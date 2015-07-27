package sk.stefan.mvps.model.entity;

import sk.stefan.mvps.model.service.PublicRoleService;
import sk.stefan.mvps.model.serviceImpl.PublicRoleServiceImpl;
import sk.stefan.enums.PublicRoleType;
import sk.stefan.interfaces.PresentationName;

/**
 * Trieda reprezentuje rolu, resp. funkciu verejne osoby (napr. poslanec, predseda zastupitelstva, etc).
 */
public class PublicRole implements PresentationName {

    public static final String TN = "t_public_role";
    
    public static final String PRES_NAME = "Verejná funkcia";

    public static final PublicRoleService publicRoleService = new PublicRoleServiceImpl();
    
    
    private Integer id;

    private Integer public_body_id;

    private Integer tenure_id;

    private Integer public_person_id;

    private PublicRoleType name;

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

    public PublicRoleType getName() {
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

    public void setName(PublicRoleType nam) {
        this.name = nam;
    }

    public void setVisible(Boolean vis) {
        this.visible = vis;
    }

    /**
     * Reprezentativne meno pre comboboxy.
     * @return 
     */
    @Override
    public String getPresentationName() {
        
        return publicRoleService.getPresentationName(this);


    }
    
    /**
     * Na ziskanie reprezentacneho mena v pripada formulara na nove hlasovanie.
     * @return 
     */
    public String getPresentationName2() {

        return publicRoleService.getPresentationName2(this);
        
    }

}
