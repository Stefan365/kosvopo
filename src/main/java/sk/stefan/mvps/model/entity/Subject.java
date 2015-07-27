package sk.stefan.mvps.model.entity;

import sk.stefan.interfaces.PresentationName;
import sk.stefan.mvps.model.service.VoteService;
import sk.stefan.mvps.model.serviceImpl.VoteServiceImpl;

/**
 * Trieda reprezentuje predmet hlasovania, tj. konkretne to, k comu sa 
 * dane hlasovanie vztahuje.
 */
public class Subject implements PresentationName {

    public static final String TN = "t_subject";
    
    public static final String PRES_NAME = "Predmet hlasovania";

    private static final VoteService voteService = new VoteServiceImpl(); 
    
    
    private Integer id;

    private String brief_description;

    private String description;

    private Integer public_role_id;

    private Integer theme_id;

    private Boolean visible;

    //getters:
    public Integer getId() {
        return this.id;
    }

    public String getBrief_description() {
        return this.brief_description;
    }

    public String getDescription() {
        return this.description;
    }

    public Integer getPublic_role_id() {
        return this.public_role_id;
    }

    public Integer getTheme_id() {
        return this.theme_id;
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

    public void setBrief_description(String brdes) {
        this.brief_description = brdes;
    }

    public void setDescription(String des) {
        this.description = des;
    }

    public void setPublic_role_id(Integer prid) {
        this.public_role_id = prid;
    }

    public void setTheme_id(Integer thid) {
        this.theme_id = thid;
    }

    public void setVisible(Boolean vis) {
        this.visible = vis;
    }

    @Override
    public String getPresentationName() {
        
        return voteService.getSubjectPresentationName(this);
//        UniRepo<PublicBody> pbRepo = new UniRepo<>(PublicBody.class);
//        UniRepo<PublicRole> prRepo = new UniRepo<>(PublicRole.class);
//        
//        PublicRole pr = prRepo.findOne(this.public_role_id);
//        PublicBody pb = null;
//        if(pr != null){
//            pb = pbRepo.findOne(pr.getPublic_body_id());
//        }
//        String s = this.brief_description;
//        if (pb != null){
//            s += " " + pb.getPresentationName();
//        }
//        return s;
    }

}
