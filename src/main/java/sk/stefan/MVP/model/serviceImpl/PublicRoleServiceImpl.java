package sk.stefan.MVP.model.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import sk.stefan.MVP.model.entity.PublicBody;
import sk.stefan.MVP.model.entity.PublicPerson;
import sk.stefan.MVP.model.entity.PublicRole;
import sk.stefan.MVP.model.entity.Tenure;
import sk.stefan.MVP.model.repo.GeneralRepo;
import sk.stefan.MVP.model.repo.UniRepo;
import sk.stefan.MVP.model.service.PublicRoleService;


public class PublicRoleServiceImpl implements PublicRoleService {

    private final GeneralRepo generalRepo;
    private final UniRepo<PublicRole> publicRoleRepo;
    private final UniRepo<Tenure> tenureRepo;
    private final UniRepo<PublicBody> publicBodyRepo;
    
    

    
    public PublicRoleServiceImpl() {

        generalRepo = new GeneralRepo();
        publicRoleRepo = new UniRepo<>(PublicRole.class);
        tenureRepo = new UniRepo<>(Tenure.class);
        publicBodyRepo = new UniRepo<>(PublicBody.class);
        

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
    
    /**
     * 
     * @param pp
     * @param pb
     * @return 
     */
    
    @Override
    public PublicRole getActualRole(PublicPerson pp, PublicBody pb){
        
        List<PublicRole> pubRoles = this.getActualPublicRolesOfPublicPerson(pp);
        
        Integer pbId = pb.getId();
        for (PublicRole pr : pubRoles){
            if(pr.getPublic_body_id().compareTo(pbId)==0){
                return pr;
            }
        }
        return null;
    }

    @Override
    public String getPublicBodyName(PublicRole pubRole) {
        
        Integer pbId = pubRole.getPublic_body_id();
        
        PublicBody pb = publicBodyRepo.findOne(pbId);
        if (pb != null){
            return pb.getPresentationName();
        } else {
            return "žiadny verejný orgán";
        }
    
    }
    
    @Override
    public String getTenureName(PublicRole pubRole){
        
        Integer tenId = pubRole.getTenure_id();

        Tenure ten = tenureRepo.findOne(tenId);
        if (ten != null){
            return ten.getPresentationName();
        } else {
            return "žiadne volebné obdobie";
        }
    
    }

    @Override
    public List<Integer> findPublicRoleIdsByPubBodyId(Integer publicBodyId) {

        List<Integer> prIds;

        String sql = "SELECT id FROM t_public_role WHERE public_body_id = " + publicBodyId +
                " AND visible = true";
          
        prIds = this.generalRepo.findIds(sql);

        return prIds;

    }
    
    @Override
    public List<Integer> findPublicRoleIdsByPubPersonId(Integer ppId) {

        List<Integer> prIds;

        String sql = "SELECT id FROM t_public_role WHERE public_person_id = " + ppId +
                    " AND visible = true";
        prIds = this.generalRepo.findIds(sql);

        return prIds;
        
    }

    
    /**
     *
     * @param tx
     * @return
     */
    @Override
    public List<Integer> findPublicRoleIdsByFilter(String tx) {

        List<Integer> prIds;

        String sql = "SELECT pr.id FROM t_public_role pr JOIN t_public_person pp "
                + " ON (pr.public_person_id = pp.id) "
                + " WHERE pp.first_name like '%" + tx + "%'"
                + " OR pp.last_name like '%" + tx + "%' AND visible = true";
        
        prIds = this.generalRepo.findIds(sql);

        return prIds;

    }

    
    @Override
    public List<PublicRole> getPublicRoles(List<Integer> prIds) {
        
        List<PublicRole> publicRoles = new ArrayList<>();

        for (Integer i : prIds) {
            publicRoles.add(publicRoleRepo.findOne(i));
        }

        return publicRoles;


    }


}
