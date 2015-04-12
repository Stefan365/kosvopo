package sk.stefan.MVP.model.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import sk.stefan.MVP.model.entity.dao.PublicPerson;
import sk.stefan.MVP.model.entity.dao.PublicRole;
import sk.stefan.MVP.model.entity.dao.Tenure;
import sk.stefan.MVP.model.repo.dao.UniRepo;
import sk.stefan.MVP.model.service.PublicRoleService;


public class PublicRoleServiceImpl implements PublicRoleService {

    private final UniRepo<PublicRole> publicRoleRepo;
    private final UniRepo<Tenure> tenureRepo;

    
    public PublicRoleServiceImpl() {

        publicRoleRepo = new UniRepo<>(PublicRole.class);
        tenureRepo = new UniRepo<>(Tenure.class);

    }

    /**
     * Get All public roles for public person (historical included)
     * @param pp
     * @return 
     */
    @Override
    public List<PublicRole> getAllPublicRolesOfPublicPerson(PublicPerson pp) {
        if (pp == null) {
            return new ArrayList<>();
        }
        return publicRoleRepo.findByParam("public_person_id", "" + pp.getId());

    }

    /**
     * Get actual public roles for public person
     *
     * @param pp
     * @return 
     */
    @Override
    public List<PublicRole> getActualPublicRolesOfPublicPerson(PublicPerson pp) {

        Tenure ten;
        Date dSince;
        Date dTill;

        List<PublicRole> lprAct = new ArrayList<>();
        List<PublicRole> lpr = this.getAllPublicRolesOfPublicPerson(pp);
        // actual date
        java.util.Date ad = new java.util.Date();
        java.sql.Date sad = new java.sql.Date(ad.getTime()); // actual date in
        // sql mode

        for (PublicRole pr : lpr) {
            Integer tid = pr.getTenure_id();
            ten = tenureRepo.findOne(tid);
            dSince = ten.getSince();
            dTill = ten.getTill();
            if ((sad.compareTo(dSince) == 1 && ((dTill == null) || dTill
                    .compareTo(sad) == 1))) {
                lprAct.add(pr);
            }

        }

        return lprAct;
    }

    @Override
    public String getPublicBody(PublicRole pubRole) {
        
        return "MENO VEREJNEHO ORGANu";
        
    }
    
    @Override
    public String getTenure(PublicRole pubRole){
        
        return "TENURE";
    
    }

}
