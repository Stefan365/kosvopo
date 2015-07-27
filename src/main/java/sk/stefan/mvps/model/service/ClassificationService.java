/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.model.service;

import java.util.List;
import sk.stefan.mvps.model.entity.PersonClassification;
import sk.stefan.mvps.model.entity.Vote;
import sk.stefan.mvps.model.entity.VoteClassification;

/**
 * Ma na starosti klasifikaciu osob aj hlasovania.
 *
 * @author stefan
 */
public interface ClassificationService {

    /**
     * Vrati zoznam vsetkych hodnoteni danej osoby. Vzdy len jedno bude aktualne,
     * tj. hodnotenie osoby sa vyvija v case.
     * 
     * @param pclId
     * @return 
     */
    public List<PersonClassification> findNewPersonClass(List<Integer> pclId);

    /**
     * Najde aktualne hodnotenia verejnej osoby (malo by byt len jedno).
     * 
     * @param ppId
     * @return 
     */
    public List<Integer> findActualPersonClassIds(Integer ppId);
    
    /**
     * Najde klasifikaciu hlasovania (bude len jedna, tj .nebude sa vyvijat v case).
     * 
     * @param votId
     * @return 
     */
    public VoteClassification findVoteClassByVoteId(Integer votId);

    /**
     * Najde hlasovanie podla jeho id. 
     * toto by malo patrit do Vote repa, potom premiestnit.
     * 
     * @param votId
     * @return 
     */
    public Vote findVoteByVoteId(Integer votId);

    public String getVotClassPresentationName(VoteClassification aThis);

    public String getPersonClassPresentationName(PersonClassification aThis);
    
}
