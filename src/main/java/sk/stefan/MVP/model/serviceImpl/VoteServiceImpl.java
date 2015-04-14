package sk.stefan.MVP.model.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import sk.stefan.MVP.model.entity.dao.PublicBody;
import sk.stefan.MVP.model.entity.dao.PublicPerson;
import sk.stefan.MVP.model.entity.dao.PublicRole;
import sk.stefan.MVP.model.entity.dao.Subject;
import sk.stefan.MVP.model.entity.dao.Tenure;
import sk.stefan.MVP.model.entity.dao.Vote;
import sk.stefan.MVP.model.entity.dao.VoteOfRole;
import sk.stefan.MVP.model.repo.dao.GeneralRepo;
import sk.stefan.MVP.model.repo.dao.UniRepo;
import sk.stefan.MVP.model.service.PublicRoleService;
import sk.stefan.MVP.model.service.VoteService;
import sk.stefan.enums.VoteResult;

public class VoteServiceImpl implements VoteService {

    // repa:
    private final GeneralRepo generalRepo;
    
    private final UniRepo<Vote> voteRepo;
    private final UniRepo<Tenure> tenureRepo;
    private final UniRepo<Subject> subjectRepo;
    private final UniRepo<PublicBody> publicBodyRepo;

    private final UniRepo<PublicRole> publicRoleRepo;
    private final UniRepo<VoteOfRole> voteOfRoleRepo;

    // servisy:
    private final PublicRoleService publicRoleService;

    public VoteServiceImpl() {
        
        generalRepo = new GeneralRepo();

        voteRepo = new UniRepo<>(Vote.class);
        tenureRepo = new UniRepo<>(Tenure.class);
        subjectRepo = new UniRepo<>(Subject.class);
        publicBodyRepo = new UniRepo<>(PublicBody.class);
        
        publicRoleRepo = new UniRepo<>(PublicRole.class);
        voteOfRoleRepo = new UniRepo<>(VoteOfRole.class);

        publicRoleService = new PublicRoleServiceImpl();

    }

    /**
     *
     * Vracia vsetky hlasovania pre danu verejnu rolu (tj. aj historicku).
     *
     * @param pp
     * @return
     */
    @Override
    public List<Vote> getAllRelevantVotesForPublicPerson(PublicPerson pp) {
        //mozno zbytocne - preverit
        if (pp == null) {
            return new ArrayList<>();
        }

        // 1. step: ziskej vsechny public Roles for that public person
        List<PublicRole> lrole = publicRoleService
                .getAllPublicRolesOfPublicPerson(pp);

        // 2. step: get list of all votes for these public bodies.
//        List<Vote> lvot;
        Set<Vote> lvotAll = new HashSet<>();

        for (PublicRole pr : lrole) {
            lvotAll.addAll(this.getAllVotesForPublicRole(pr));
        }

        return new ArrayList<>(lvotAll);
    }

    /**
     *
     * Vracia vsetky hlasovania pre danu verejnu rolu (tj. aj historicku).
     *
     * @param pr
     * @return
     */
    @Override
    public List<Vote> getAllVotesForPublicRole(PublicRole pr) {

        if (pr == null) {
            return new ArrayList<>();
        }
        Tenure ten = tenureRepo.findOne(pr.getTenure_id());
        Date dSince = ten.getSince();
        Date dTill = ten.getTill();

        Date d;

        List<Vote> lvotFin = new ArrayList<>();
        List<Vote> lvot = voteRepo.findByParam("public_body_id",
                "" + pr.getPublic_body_id());

        for (Vote vo : lvot) {
            d = vo.getVote_date();
            if ((d.compareTo(dSince) == 1 && ((dTill == null) || dTill
                    .compareTo(d) == 1))) {
                lvotFin.add(vo);
            }
        }
        return lvotFin;
    }

//*888**************************************************************************
//VOTE OF ROLE SECTION:
//*888**************************************************************************
    @Override
    public List<VoteOfRole> getAllVotesOfPublicPerson(PublicPerson pp) {

        //1. step: ziskej vsechny 
        List<PublicRole> role = this.getAllPublicRolesOfPublicPerson(pp);
        List<VoteOfRole> hl, hlasovani = new ArrayList<>();

        //2. krok: ziskaj hlasovania.
        String prid;
        for (PublicRole r : role) {
            prid = r.getId().toString();
            hl = voteOfRoleRepo.findByParam("public_role_id", prid);
            hlasovani.addAll(hl);
        }
        return hlasovani;

    }

    @Override
    public List<PublicRole> getAllPublicRolesOfPublicPerson(PublicPerson pp) {

        List<PublicRole> role;

        String id = pp.getId().toString();
        role = this.publicRoleRepo.findByParam("public_person_id", id);

        return role;

    }
    

    @Override
    public String getVoteDate(Vote vote) {
        return vote.getVote_date().toString();
    }

    @Override
    public String getVoteIntNr(Vote vote) {
        return vote.getInternal_nr();
    }

    @Override
    public String getVotePublicBodyName(Vote vote) {
        
        Integer votId = vote.getId();
                
        String sql = "SELECT pb.id FROM t_public_body pb JOIN t_public_role pr ON "
                + "(pb.id = pr.public_body_id) JOIN t_subject sub "
                + "ON (pr.id = sub.public_role_id) JOIN t_vote vot ON (sub.id = vot.subject_id) WHERE "
                + " vot.id = " + votId +" AND pb.visible = true";
        List<Integer> pbIds = generalRepo.findIds(sql);
        
//        Integer subId = vote.getSubject_id();
//        Subject sub = subjectRepo.findOne(subId);
//        Integer prId = sub.getPublic_role_id();
//        PublicRole pr = publicRoleRepo.findOne(prId);
//        Integer pbId = pr.getPublic_body_id();
        PublicBody pb;
        if (pbIds != null && !pbIds.isEmpty()){
            pb = publicBodyRepo.findOne(pbIds.get(0));
            return pb.getPresentationName();
        } else {
            return null;
        }
    }

    @Override
    public String getVoteSubjectName(Vote vote) {
        
        Integer subId = vote.getSubject_id(); 
        
        Subject sub = subjectRepo.findOne(subId);
        if (sub != null){
            return sub.getPresentationName();
        } else {
            return "žiadny predmet";
        }

    }


    @Override
    public String getVoteResult(Vote vote) {
        
        VoteResult vr = vote.getResult_vote();
        if (vr != null){
            return vr.getName();
        } else {
            return "žiadny výsledok";
        }
                
        
    }

    @Override
    public String getVoteNumbers(Vote vote) {

        StringBuilder numbers = new StringBuilder();
        
        numbers.append("za: ").append(vote.getFor_vote()).append(" \n");
        numbers.append("proti: ").append(vote.getAgainst_vote()).append(" \n");
        numbers.append("zadržali sa: ").append(vote.getRefrain_vote()).append(" \n");
        numbers.append("chýbali: ").append(vote.getFor_vote()).append(" \n");

        return numbers.toString();
    }
    
    @Override
    public List<Vote> findNewVotes(List<Integer> voteIds) {
        
        List<Vote> votes = new ArrayList<>();

        for (Integer i : voteIds) {
            votes.add(voteRepo.findOne(i));
        }

        return votes;

    }

    
    @Override
    public List<Integer> findVoteIdsByPubBodyId(Integer publicBodyId) {

        List<Integer> prIds;

        //SPRAVIT TO PODLA TOHO UNIVERZALNEHO FORMULARA TJ. FIND (TABLE1, TABLE2);
//        zdola naho a zhora nadol.
//        SELECT pr.public_body_id FROM t_public_role pr JOIN t_subject sub ON (pr.id = sub.public_role_id) JOIN t_vote vot ON (sub.id = vot.subject_id) WHERE vot.id = 4;
        String sql = "SELECT vot.id FROM t_vote vot WHERE vot.subject_id IN "
                + "(SELECT sub.id FROM t_subject sub WHERE sub.public_role_id IN "
                + "(SELECT pr.id FROM t_public_role pr WHERE pr.public_body_id = " + publicBodyId +" AND visible = true) "
                + "AND visible = true)"
                + " AND vot.visible = true";
        
        prIds = this.generalRepo.findIds(sql);

        return prIds;

    }

    @Override
    public List<Integer> findVoteIdsByPubPersonId(Integer ppId) {

        List<Integer> prIds;

        //SPRAVIT TO PODLA TOHO UNIVERZALNEHO FORMULARA TJ. FIND (TABLE1, TABLE2);
//        zdola naho a zhora nadol.
        //viacnasobne viible je tam kvoli bezpecnosti.
        String sql = "SELECT vot.id FROM t_vote vot WHERE vot.subject_id IN "
                + "(SELECT sub.id FROM t_subject sub WHERE sub.public_role_id IN "
                + "(SELECT pr.id FROM t_public_role pr WHERE pr.public_person_id = " + ppId +" "
                + "AND pr.visible = true) "
                + "AND sub.visible = true) "
                + "AND vot.visible = true";
        
        prIds = this.generalRepo.findIds(sql);

        return prIds;
        
    }

    /**
     *
     * @param tx
     * @return
     */
    @Override
    public List<Integer> findVoteIdsByFilter(String tx) {

        List<Integer> prIds;

        String sql = "SELECT pr.id FROM t_public_role pr JOIN t_public_person pp "
                + " ON (pr.public_person_id = pp.id) "
                + " WHERE pp.first_name like '%" + tx + "%'"
                + " OR pp.last_name like '%" + tx + "%' AND visible = true";
        
        prIds = this.generalRepo.findIds(sql);

        return prIds;

    }



}
