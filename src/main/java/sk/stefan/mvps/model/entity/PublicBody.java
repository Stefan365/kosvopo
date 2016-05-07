package sk.stefan.mvps.model.entity;

import lombok.Data;
import sk.stefan.interfaces.PresentationName;
import sk.stefan.interfaces.TabEntity;
import sk.stefan.mvps.model.service.PublicBodyService;
import sk.stefan.mvps.model.serviceImpl.PublicBodyServiceImpl;

/**
 * Trieda reprezentuje verjny organ (mestke zastupitelstvo, parlament, ...).
 */
@Data
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

    public static String getTN() {
        return TN;
    }

    @Override
    public String getPresentationName() {
        
        return pubBodyService.getPresentationName(this);
    }

}
