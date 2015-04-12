package sk.stefan.MVP.model.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import sk.stefan.MVP.model.entity.dao.PublicPerson;
import sk.stefan.MVP.model.entity.dao.PublicRole;
import sk.stefan.MVP.model.entity.dao.Tenure;
import sk.stefan.MVP.model.entity.dao.Vote;
import sk.stefan.MVP.model.entity.dao.VoteOfRole;
import sk.stefan.MVP.model.repo.dao.UniRepo;
import sk.stefan.MVP.model.service.PublicRoleService;
import sk.stefan.MVP.model.service.VoteService;

public class VoteServiceImpl implements VoteService {

    // repa:
    private final UniRepo<Vote> voteRepo;
    private final UniRepo<Tenure> tenureRepo;

    private final UniRepo<PublicRole> publicRoleRepo;
    private final UniRepo<VoteOfRole> voteOfRoleRepo;

    // servisy:
    private final PublicRoleService publicRoleService;

    public VoteServiceImpl() {

        voteRepo = new UniRepo<>(Vote.class);
        tenureRepo = new UniRepo<>(Tenure.class);
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
        List<Vote> lvot;
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

}
