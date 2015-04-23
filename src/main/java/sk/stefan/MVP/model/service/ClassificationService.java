/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.model.service;

import java.util.List;
import sk.stefan.MVP.model.entity.dao.PersonClassification;
import sk.stefan.MVP.model.entity.dao.Vote;
import sk.stefan.MVP.model.entity.dao.VoteClassification;

/**
 *
 * @author stefan
 */
public interface ClassificationService {

    public List<PersonClassification> findNewPersonClass(List<Integer> pclId);

    public List<Integer> findActualPersonClassIds(Integer ppId);

    public VoteClassification findVoteClassByVoteId(Integer votId);

    public Vote findVoteByVoteId(Integer id);
    
}
