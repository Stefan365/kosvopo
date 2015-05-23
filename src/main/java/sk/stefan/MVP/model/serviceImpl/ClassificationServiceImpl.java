/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.model.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import sk.stefan.MVP.model.entity.PersonClassification;
import sk.stefan.MVP.model.entity.PublicPerson;
import sk.stefan.MVP.model.entity.Vote;
import sk.stefan.MVP.model.entity.VoteClassification;
import sk.stefan.MVP.model.repo.GeneralRepo;
import sk.stefan.MVP.model.repo.UniRepo;
import sk.stefan.MVP.model.service.ClassificationService;

/**
 *
 * @author stefan
 */
public class ClassificationServiceImpl implements ClassificationService {

    private final UniRepo<PersonClassification> personClassRepo;
    private final UniRepo<VoteClassification> voteClassRepo;
    private final UniRepo<Vote> voteRepo;
    private final UniRepo<PublicPerson> pubPersonRepo;
    
    private final GeneralRepo generalRepo;
    
    //servisy:
//    na odlahcenie RAM, nebudem tu pouzivat objemne Service.:
//    private final VoteService voteService;
//    private final PublicPersonService pubPersonService;

    
    //0.konstruktor
    /**
     */
    public ClassificationServiceImpl() {

        personClassRepo = new UniRepo<>(PersonClassification.class);
        voteClassRepo = new UniRepo<>(VoteClassification.class);
        voteRepo = new UniRepo<>(Vote.class);
        pubPersonRepo = new UniRepo<>(PublicPerson.class);
        
        generalRepo = new GeneralRepo();

//        voteService = new VoteServiceImpl();
//        pubPersonService = new PublicPersonServiceImpl();
        
    }

    @Override
    public List<PersonClassification> findNewPersonClass(List<Integer> pclIds) {

        List<PersonClassification> personClass = new ArrayList<>();

        for (Integer i : pclIds) {
            personClass.add(personClassRepo.findOne(i));
        }

        return personClass;

    }

    @Override
    public List<Integer> findActualPersonClassIds(Integer ppId) {

        List<Integer> pclIds;

        String sql = "SELECT id FROM t_person_classification WHERE public_person_id = " + ppId
                + " AND actual = true AND visible = true";
        pclIds = this.generalRepo.findIds(sql);

        return pclIds;

    }

    @Override
    public VoteClassification findVoteClassByVoteId(Integer votId) {

        List<VoteClassification> vcls = voteClassRepo.findByParam("vote_id", "" + votId);

        if (vcls != null && !vcls.isEmpty()) {
            //predpoklada sa, ze je len 1 clasifikacia daneho hlasovania. a tak by to malo byt.
            return vcls.get(0);
        }
        return null;

    }

    @Override
    public Vote findVoteByVoteId(Integer voteId) {

        return voteRepo.findOne(voteId);

    }

    @Override
    public synchronized String getVotClassPresentationName(VoteClassification vcl) {
        
        Integer vote_id = vcl.getVote_id();
        
        if (vote_id != null) {
            Vote vot = voteRepo.findOne(vote_id);
            return vcl.getId() + ", " + vot.getPresentationName();
        } else {
            return vcl.getId() + ", nedefinované";
        }
    }

    @Override
    public synchronized String getPersonClassPresentationName(PersonClassification pcl) {
        
        Integer ppId = pcl.getPublic_person_id();

        if (ppId != null) {
            PublicPerson pp = pubPersonRepo.findOne(ppId);
            return pcl.getId() + ", " + pp.getPresentationName();
        } else {
            return pcl.getId() + ", ";
        }
    }

}
