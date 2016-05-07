package sk.stefan.mvps.model.entity;

import lombok.Data;
import sk.stefan.enums.PublicRoleType;
import sk.stefan.interfaces.TabEntity;
import sk.stefan.mvps.model.service.PublicRoleService;
import sk.stefan.mvps.model.serviceImpl.PublicRoleServiceImpl;

/**
 * Trieda reprezentuje rolu, resp. funkciu verejne osoby (napr. poslanec, predseda zastupitelstva, etc).
 */
@Data
public class PublicRole implements TabEntity {

    public static final String TN = "t_public_role";
    
    public static final String PRES_NAME = "Verejná funkcia";

    public static final PublicRoleService publicRoleService = new PublicRoleServiceImpl();
    
    
    private Integer id;

    private Integer public_body_id;

    private Integer tenure_id;

    private Integer public_person_id;

    private PublicRoleType name;

    private Boolean visible;


    @Override
    public String getEntityName() {
        return "role";
    }

    @Override
    public String getRelatedTabName() {
        return "verejnaRole";
    }

    public static String getTN() {
        return TN;
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
