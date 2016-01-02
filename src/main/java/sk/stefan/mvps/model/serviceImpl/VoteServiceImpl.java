package sk.stefan.mvps.model.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
import sk.stefan.enums.VoteResult;
import sk.stefan.interfaces.TabEntity;
import sk.stefan.mvps.model.entity.PublicBody;
import sk.stefan.mvps.model.entity.PublicPerson;
import sk.stefan.mvps.model.entity.PublicRole;
import sk.stefan.mvps.model.entity.Subject;
import sk.stefan.mvps.model.entity.Tenure;
import sk.stefan.mvps.model.entity.Theme;
import sk.stefan.mvps.model.entity.Vote;
import sk.stefan.mvps.model.entity.VoteOfRole;
import sk.stefan.mvps.model.repo.GeneralRepo;
import sk.stefan.mvps.model.repo.UniRepo;
import sk.stefan.mvps.model.service.VoteService;
import sk.stefan.utils.ToolsDao;

@Service
public class VoteServiceImpl implements VoteService {

    // repa:
    private final GeneralRepo generalRepo;
    private final UniRepo<Vote> voteRepo;
    private final UniRepo<Tenure> tenureRepo;
    private final UniRepo<Theme> themeRepo;
    private final UniRepo<Subject> subjectRepo;
    private final UniRepo<VoteOfRole> voteOfRoleRepo;

    //    na odlahcenie RAM, nebudem tu pouzivat objemne Service, 
//    i ked by to viac vyhovovalo MVP:
    private final UniRepo<PublicBody> pubBodyRepo;
    private final UniRepo<PublicPerson> pubPersonRepo;
    private final UniRepo<PublicRole> pubRoleRepo;


    public VoteServiceImpl() {

        generalRepo = new GeneralRepo();

        voteRepo = new UniRepo<>(Vote.class);
        tenureRepo = new UniRepo<>(Tenure.class);
        themeRepo = new UniRepo<>(Theme.class);
        subjectRepo = new UniRepo<>(Subject.class);
        voteOfRoleRepo = new UniRepo<>(VoteOfRole.class);


        pubBodyRepo = new UniRepo<>(PublicBody.class);
        pubPersonRepo = new UniRepo<>(PublicPerson.class);
        pubRoleRepo = new UniRepo<>(PublicRole.class);


    }

    /**
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
        List<PublicRole> lrole = pubRoleRepo.findByParam("public_person_id", "" + pp.getId());


        // 2. step: get list of all votes for these public bodies.
//        List<Vote> lvot;
        Set<Vote> lvotAll = new HashSet<>();

        for (PublicRole pr : lrole) {
            lvotAll.addAll(this.getAllVotesForPublicRole(pr));
        }

        return new ArrayList<>(lvotAll);
    }

    /**
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
        List<VoteOfRole> votesOfRole = getAllVotesOfPublicRole(pr);
        List<Vote> votes = new ArrayList<>();
        votesOfRole.forEach(voteOfRole -> votes.add(findOne(voteOfRole.getVote_id())));
        return votes;


//        Tenure ten = tenureRepo.findOne(pr.getTenure_id());
//        Date dSince = ten.getSince();
//        Date dTill = ten.getTill();
//
//        Date d;
//
//        List<Vote> lvotFin = new ArrayList<>();
//        List<Vote> lvot = voteRepo.findByParam("subject_id",
//                "" + pr.getPublic_body_id());
//
//        for (Vote vo : lvot) {
//            d = vo.getVote_date();
//            if ((d.compareTo(dSince) == 1 && ((dTill == null) || dTill
//                    .compareTo(d) == 1))) {
//                lvotFin.add(vo);
//            }
//        }
//        return lvotFin;
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
        role = this.pubRoleRepo.findByParam("public_person_id", id);

        return role;

    }

    @Override
    public List<Vote> getAllVotesForPublicBody(PublicBody publicBody) {

        Map<Integer, Vote> votes = new HashMap<>();
        pubRoleRepo.findByParam("public_body_id", String.valueOf(publicBody.getId()))
                .forEach(role -> getAllVotesForPublicRole(role).forEach(vote -> votes.put(vote.getId(), vote)));
        return new ArrayList<>(votes.values());
    }

    @Override
    public List<VoteOfRole> getAllVotesOfPublicRole(PublicRole publicRole) {
        return voteOfRoleRepo.findByParam("public_role_id", String.valueOf(publicRole.getId()));
    }

    @Override
    public List<Vote> findAllVotesForTabEntity(TabEntity tabEntity) {
        if (tabEntity instanceof PublicPerson) {
            return getAllVotesForPublicPerson((PublicPerson) tabEntity);
        } else if (tabEntity instanceof PublicRole) {
            return getAllVotesForPublicRole((PublicRole) tabEntity);
        } else if (tabEntity instanceof PublicBody) {
            return getAllVotesForPublicBody((PublicBody) tabEntity);
        }
        throw new RuntimeException("Nelze vyhledat hlasování pro entitu: " + tabEntity);
    }

    @Override
    public PublicBody getVotePublicBody(Vote vote) {
        Integer votId = vote.getId();

        String sql = "SELECT pb.id FROM t_public_body pb JOIN t_public_role pr ON "
                + "(pb.id = pr.public_body_id) JOIN t_subject sub "
                + "ON (pr.id = sub.public_role_id) JOIN t_vote vot ON (sub.id = vot.subject_id) WHERE "
                + " vot.id = " + votId + " AND pb.visible = true";
        List<Integer> pbIds = generalRepo.findIds(sql);
        PublicBody pb;
        if (pbIds != null && !pbIds.isEmpty()) {
            pb = pubBodyRepo.findOne(pbIds.get(0));
            return pb;
        } else {
            return null;
        }
    }

    @Override
    public Theme saveTheme(Theme theme) {
        return themeRepo.save(theme, theme.getId() != null);
    }

    @Override
    public void removeTheme(Theme theme) {
        themeRepo.deactivateOneOnly(theme, false);
    }

    @Override
    public List<Subject> findAllSubjects() {
        return subjectRepo.findAll();
    }

    @Override
    public Subject saveSubject(Subject subject) {
        return subjectRepo.save(subject, subject.getId() != null);
    }

    @Override
    public void removeSubject(Subject subject) {
        subjectRepo.deactivateOneOnly(subject, false);
    }


    @Override
    public String getVoteDate(Vote vote) {
        if (vote.getVote_date() != null) {
            return vote.getVote_date().toString();

        } else {
            return "nema datum";
        }
    }

    @Override
    public String getVoteIntNr(Vote vote) {
        return vote.getInternal_nr();
    }

    @Override
    public String getVotePublicBodyName(Vote vote) {

        PublicBody pb = getVotePublicBody(vote);
        if (pb != null) {
            return pb.getPresentationName();
        }
        return null;
    }

    @Override
    public String getVoteSubjectName(Vote vote) {

        Integer subId = vote.getSubject_id();

        Subject sub = subjectRepo.findOne(subId);
        if (sub != null) {
            return sub.getBrief_description();
        } else {
            return "žiadny predmet";
        }

    }


    @Override
    public String getVoteResultAsString(Vote vote) {

        VoteResult vr = vote.getResult_vote();
        if (vr != null) {
            return vr.getName();
        } else {
            return "žiadny výsledok";
        }


    }

    @Override
    public String getVoteNumbers(Vote vote) {

        StringBuilder numbers = new StringBuilder();

        Integer votId = vote.getId();

        int za = this.getForVote(votId);
        int proti = this.getAgainstVote(votId);
        int zadrzalSa = this.getRefrainVote(votId);
        int absent = this.getAbsentVote(votId);

        numbers.append("za: ").append(za).append("  ");
        numbers.append("proti: ").append(proti).append("  ");
        numbers.append("zadržali sa: ").append(zadrzalSa).append("  ");
        numbers.append("chýbali: ").append(absent).append("  ");

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
                + "(SELECT pr.id FROM t_public_role pr WHERE pr.public_body_id = " + publicBodyId + " AND visible = true) "
                + "AND visible = true)"
                + " AND vot.visible = true";

        prIds = this.generalRepo.findIds(sql);

        return prIds;

    }

    @Override
    public List<Integer> findVoteIdsByPubPersonId(Integer ppId) {

        List<Integer> prIds;

        //SPRAVIT TO PODLA TOHO UNIVERZALNEHO FORMULARA TJ. FIND (TABLE1, TABLE2);
        //viacnasobne viible je tam kvoli bezpecnosti.
        String sql = "SELECT vot.id FROM t_vote vot WHERE vot.subject_id IN "
                + "(SELECT sub.id FROM t_subject sub WHERE sub.public_role_id IN "
                + "(SELECT pr.id FROM t_public_role pr WHERE pr.public_person_id = " + ppId + " "
                + " AND pr.visible = true) "
                + " AND sub.visible = true) "
                + " AND vot.visible = true"
                + " UNION DISTINCT"

                + " SELECT vot.id FROM t_vote vot JOIN t_vote_of_role vor ON (vot.id = vor.vote_id) "
                + " JOIN t_public_role pur ON (vor.public_role_id = pur.id) "
                + " WHERE pur.public_person_id = " + ppId
                + " AND vot.visible = true";

        prIds = this.generalRepo.findIds(sql);

        return prIds;

    }

    /**
     * @param tx
     * @return
     */
    @Override
    public List<Integer> findNewVoteIdsByFilter(String tx) {

        List<Integer> prIds;

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
    public Vote getVote(VoteOfRole vor) {

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

        PublicRole pr = pubRoleRepo.findOne(prId);
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

    @Override
    public VoteOfRole saveVoteOfRole(VoteOfRole vor, boolean noteChange) {

        return voteOfRoleRepo.save(vor, noteChange);
    }

    @Override
    public synchronized String getVorPresentationName(VoteOfRole vor) {

        Integer prId = vor.getPublic_role_id();
        if (prId != null) {
            PublicRole pr = pubRoleRepo.findOne(prId);
            PublicPerson pp = pubPersonRepo.findOne(pr.getPublic_person_id());
            Vote vot = voteRepo.findOne(vor.getVote_id());

            return pp.getPresentationName() + ", " + vot.getPresentationName();
        } else {
            return vor.getId() + ", nedefinované";
        }

    }

    @Override
    public synchronized String getVotePresentationName(Vote vot) {

        Integer subId = vot.getSubject_id();

        if (subId != null) {
            Subject sub = subjectRepo.findOne(subId);

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date d = vot.getVote_date();
            String dateStr;
            if (d != null) {
                dateStr = sdf.format(d);
            } else {
                dateStr = "no date";
            }

            return sub.getPresentationName() + ", " + dateStr;
        } else {
            return vot.getId() + ", nedefinované";
        }
    }

    @Override
    public synchronized String getSubjectPresentationName(Subject sub) {

        PublicRole pr = pubRoleRepo.findOne(sub.getPublic_role_id());
        PublicBody pb = null;
        if (pr != null) {
            pb = pubBodyRepo.findOne(pr.getPublic_body_id());
        }
        String s = sub.getBrief_description();
        if (pb != null) {
            s += " " + pb.getPresentationName();
        }
        return s;
    }

    private int getForVote(Integer votId) {

        List<VoteOfRole> vs = voteOfRoleRepo.findByTwoParams("vote_id", votId + "", "decision", "0");
        if (vs != null) {
            return vs.size();
        } else {
            return 0;
        }

    }

    private int getAgainstVote(Integer votId) {

        List<VoteOfRole> vs = voteOfRoleRepo.findByTwoParams("vote_id", votId + "", "decision", "1");
        if (vs != null) {
            return vs.size();
        } else {
            return 0;
        }
    }

    private int getRefrainVote(Integer votId) {

        List<VoteOfRole> vs = voteOfRoleRepo.findByTwoParams("vote_id", votId + "", "decision", "2");
        if (vs != null) {
            return vs.size();
        } else {
            return 0;
        }
    }

    private int getAbsentVote(Integer votId) {
        List<VoteOfRole> vs = voteOfRoleRepo.findByTwoParams("vote_id", votId + "", "decision", "3");
        if (vs != null) {
            return vs.size();
        } else {
            return 0;
        }
    }


}
