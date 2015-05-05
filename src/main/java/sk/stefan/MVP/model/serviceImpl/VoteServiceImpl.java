package sk.stefan.MVP.model.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import sk.stefan.MVP.model.entity.PublicBody;
import sk.stefan.MVP.model.entity.PublicPerson;
import sk.stefan.MVP.model.entity.PublicRole;
import sk.stefan.MVP.model.entity.Subject;
import sk.stefan.MVP.model.entity.Tenure;
import sk.stefan.MVP.model.entity.Theme;
import sk.stefan.MVP.model.entity.Vote;
import sk.stefan.MVP.model.entity.VoteOfRole;
import sk.stefan.MVP.model.repo.GeneralRepo;
import sk.stefan.MVP.model.repo.UniRepo;
import sk.stefan.MVP.model.service.PublicRoleService;
import sk.stefan.MVP.model.service.VoteService;
import sk.stefan.enums.VoteResult;
import sk.stefan.utils.ToolsDao;

public class VoteServiceImpl implements VoteService {

    // repa:
    private final GeneralRepo generalRepo;
    
    private final UniRepo<Vote> voteRepo;
    private final UniRepo<Tenure> tenureRepo;
    private final UniRepo<Theme> themeRepo;
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
        themeRepo = new UniRepo<>(Theme.class);
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
    public List<Vote> getAllVotesForPublicPerson(PublicPerson pp) {
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
            return sub.getBrief_description();
        } else {
            return "žiadny predmet";
        }

    }


    @Override
    public String getVoteResultAsString(Vote vote) {
        
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
    public List<Integer> findNewVoteIdsByFilter(String tx) {

        List<Integer> prIds;

//        String sql = "SELECT pr.id FROM t_public_role pr JOIN t_public_person pp "
//                + " ON (pr.public_person_id = pp.id) "
//                + " WHERE pp.first_name like '%" + tx + "%'"
//                + " OR pp.last_name like '%" + tx + "%' AND pr.visible = true";
        String sql = "SELECT vot.id FROM t_vote vot JOIN t_subject sub "
                + " ON (vot.subject_id = sub.id) JOIN t_public_role pr "
                + " ON (sub.public_role_id = pr.id) JOIN t_public_body pb "
                + " ON (pr.public_body_id = pb.id) JOIN t_public_person pp "
                + " ON (pr.public_person_id = pp.id) "
                
                + " WHERE sub.brief_description like '%" + tx + "%'"
                + " OR pp.last_name like '%" + tx + "%' "
                + " OR pp.first_name like '%" + tx + "%' "
                + " OR pb.name like '%" + tx + "%' "
                + " AND vot.visible = true"
                + " AND sub.visible = true"
                + " AND pr.visible = true"
                + " AND pb.visible = true"
                + " AND pp.visible = true";
        
        prIds = this.generalRepo.findIds(sql);

        return prIds;

    }

    @Override
    public Vote getVote (VoteOfRole vor) {
        
        Integer votId = vor.getVote_id();
        
        return voteRepo.findOne(votId);
    }

    @Override
    public List<VoteOfRole> findNewVotesOfRole(List<Integer> votesOfRoleIds) {

        List<VoteOfRole> votesOfRole = new ArrayList<>();

        for (Integer i : votesOfRoleIds) {
            votesOfRole.add(voteOfRoleRepo.findOne(i));
        }

        return votesOfRole;
    
    }

    @Override
    public List<Integer> findVoteIdsByPubRoleId(Integer pubRoleId) {
        
        List<Integer> votIds;

        //SPRAVIT TO PODLA TOHO UNIVERZALNEHO FORMULARA TJ. FIND (TABLE1, TABLE2);
//        zdola naho a zhora nadol.
        //viacnasobne viible je tam kvoli bezpecnosti.
        String sql = "SELECT vot.id FROM t_vote vot JOIN t_vote_of_role vor ON "
                + "(vot.id = vor.vote_id) JOIN t_public_role pr ON "
                + "(pr.id = vor.public_role_id) "
                + "WHERE pr.id = " + pubRoleId + " "
                + "AND pr.visible = true "
                + "AND vor.visible = true "
                + "AND vot.visible = true";
        
        votIds = this.generalRepo.findIds(sql);

        return votIds;
        
    }

    @Override
    public List<Integer> findVoteOfRoleIdsByPubRoleId(Integer pubRoleId) {
        
        List<Integer> vorIds;

        //SPRAVIT TO PODLA TOHO UNIVERZALNEHO FORMULARA TJ. FIND (TABLE1, TABLE2);
//        zdola naho a zhora nadol.
        //viacnasobne viible je tam kvoli bezpecnosti.
        String sql = "SELECT vor.id FROM t_vote_of_role vor JOIN t_public_role pr ON "
                + "(pr.id = vor.public_role_id) "
                + "WHERE pr.id = " + pubRoleId + " "
                + "AND pr.visible = true "
                + "AND vor.visible = true ";
        
        vorIds = this.generalRepo.findIds(sql);

        return vorIds;
    }

    @Override
    public String getThemeNameById(Integer theme_id) {
        
        Theme th = themeRepo.findOne(theme_id);
        
        return th.getPresentationName();
        
    }

    @Override
    public Theme getThemeById(Integer theme_id) {
        
        Theme th = themeRepo.findOne(theme_id);
        
        return th;

    }

    @Override
    public PublicRole getPublicRoleById(Integer prId) {
        
        PublicRole pr = publicRoleRepo.findOne(prId);
        return pr;
        
    }

    @Override
    public List<Theme> findNewThemes(List<Integer> themeIds) {
    
        List<Theme> themes = new ArrayList<>();

        for (Integer i : themeIds) {
            themes.add(themeRepo.findOne(i));
        }

        return themes;
    
    }

    @Override
    public List<Subject> findNewSubjects(List<Integer> subjectIds) {
    
        List<Subject> subjects = new ArrayList<>();

        for (Integer i : subjectIds) {
            subjects.add(subjectRepo.findOne(i));
        }

        return subjects;
    
    }

    @Override
    public List<Theme> findAllThemes() {
        
        List<Theme> ret = themeRepo.findAll();
        return ret;
        
    }

    @Override
    public List<Subject> findAllSubjectsForPublicBody(PublicBody pb) {
        
        Integer pbId = pb.getId();
        List<Integer> ids = ToolsDao.getLeavesIds("t_subject", "t_public_body", pbId);
        List<Subject> ret = this.findNewSubjects(ids);
        
        return ret;
        
    }

    @Override
    public List<Integer> findNewThemeIdsByFilter(String tx) {
        
        List<Integer> thIds;

        String sql = "SELECT id FROM t_theme "
                + " WHERE brief_description like '%" + tx + "%'"
                + " AND visible = true";
        
        thIds = this.generalRepo.findIds(sql);

        return thIds;
    }

    @Override
    public List<Integer> findNewSubjectIdsByFilter(String tx) {
        
        List<Integer> subIds;

        String sql = "SELECT sub.id FROM t_subject sub JOIN t_theme th "
                + " ON (sub.theme_id = th.id) JOIN t_public_role pr "
                + " ON (sub.public_role_id = pr.id) JOIN t_public_person pp "
                + " ON (pr.public_person_id = pp.id) "
                + " WHERE sub.brief_description like '%" + tx + "%' "
                + " OR th.brief_description like '%" + tx + "%' "
                + " OR pp.first_name like '%" + tx + "%' "
                + " OR pp.last_name like '%" + tx + "%' "
                + " AND sub.visible = true"
                + " AND th.visible = true"
                + " AND pr.visible = true"
                + " AND pp.visible = true";
        
        subIds = this.generalRepo.findIds(sql);

        return subIds;
    }

    @Override
    public Subject findSubjectById(Integer subject_id) {
        
        return subjectRepo.findOne(subject_id);
        
    }

    @Override
    public Theme findThemeBySubjectId(Integer subject_id) {
        
        Subject sub = subjectRepo.findOne(subject_id);
        
        return themeRepo.findOne(sub.getTheme_id());
        
        
    }

    @Override
    public List<VoteOfRole> findVoteOfRolesByVoteId(Integer vote_id) {
        
        List<VoteOfRole> hlasovaniaOsob = voteOfRoleRepo.findByParam("vote_id", "" + vote_id);
        
        return hlasovaniaOsob;
        
    }

    @Override
    public List<Vote> findAll() {
        
        return voteRepo.findAll();
    }

    @Override
    public Vote findOne(Integer voteId) {

        return voteRepo.findOne(voteId);
    
    }


}
