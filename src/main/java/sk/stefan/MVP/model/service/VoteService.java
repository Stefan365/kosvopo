package sk.stefan.MVP.model.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import sk.stefan.MVP.model.entity.dao.PublicPerson;
import sk.stefan.MVP.model.entity.dao.PublicRole;
import sk.stefan.MVP.model.entity.dao.Tenure;
import sk.stefan.MVP.model.entity.dao.Vote;
import sk.stefan.MVP.model.repo.dao.UniRepo;

public class VoteService {

    // repa:

    private UniRepo<Vote> voteRepo;
    private UniRepo<Tenure> tenureRepo;

    // servisy:
    private PublicRoleService publicRoleService;

    public VoteService() {
        voteRepo = new UniRepo<Vote>(Vote.class);
        tenureRepo = new UniRepo<Tenure>(Tenure.class);

        publicRoleService = new PublicRoleService();

    }

    /**
     *
     * Vracia vsetky hlasovania pre danu verejnu rolu (tj. aj historicku).
     *
     */
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

        return new ArrayList<Vote>(lvotAll);
    }

    /**
     *
     * Vracia vsetky hlasovania pre danu verejnu rolu (tj. aj historicku).
     *
     */
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

}
