//package sk.stefan.MVP.model.serviceImpl;
//
//import java.util.ArrayList;
//import java.util.List;
//import sk.stefan.MVP.model.entity.dao.PublicPerson;
//import sk.stefan.MVP.model.entity.dao.PublicRole;
//import sk.stefan.MVP.model.entity.dao.VoteOfRole;
//import sk.stefan.MVP.model.repo.dao.UniRepo;
//
//public class VoteOfRoleServiceImpl {
//
//    private final UniRepo<PublicRole> publicRoleRepo;
//    private final UniRepo<VoteOfRole> voteOfRoleRepo;
//
//    //0. konstruktor
//    /**
//     * 
//     */
//    public VoteOfRoleServiceImpl() {
//        
//        publicRoleRepo = new UniRepo<>(PublicRole.class);
//        voteOfRoleRepo = new UniRepo<>(VoteOfRole.class);
//    
//    }
//
//    public List<VoteOfRole> getAllVotesOfPublicPerson(PublicPerson pp) {
//
//        //1. step: ziskej vsechny 
//        List<PublicRole> role = this.getAllPublicRolesOfPublicPerson(pp);
//        List<VoteOfRole> hl, hlasovani = new ArrayList<>();
//
//        //2. krok: ziskaj hlasovania.
//        String prid;
//        for (PublicRole r : role) {
//            prid = r.getId().toString();
//            hl = voteOfRoleRepo.findByParam("public_role_id", prid);
//            hlasovani.addAll(hl);
//        }
//        return hlasovani;
//    
//    }
//
//    public List<PublicRole> getAllPublicRolesOfPublicPerson(PublicPerson pp) {
//        
//        List<PublicRole> role;
//
//        String id = pp.getId().toString();
//        role = this.publicRoleRepo.findByParam("public_person_id", id);
//        
//        return role;
//
//    }
//
//}
