/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.model.service;

import com.vaadin.ui.Label;
import java.util.List;
import sk.stefan.MVP.model.entity.dao.PublicPerson;
import sk.stefan.MVP.model.entity.dao.PublicRole;
import sk.stefan.MVP.model.entity.dao.Vote;
import sk.stefan.MVP.model.entity.dao.VoteOfRole;
import sk.stefan.enums.VoteResult;

/**
 *
 * @author stefan
 */
public interface VoteService {

    //vote itself:
    public List<Vote> getAllRelevantVotesForPublicPerson(PublicPerson pp);

    public List<Vote> getAllVotesForPublicRole(PublicRole pr);

    //vote of role:
    public List<VoteOfRole> getAllVotesOfPublicPerson(PublicPerson pp);

    public List<PublicRole> getAllPublicRolesOfPublicPerson(PublicPerson pp);

    public List<Vote> findNewVotes(List<Integer> voteIds);
    
    public List<Integer> findVoteIdsByPubBodyId(Integer publicBodyId);
    
    public List<Integer> findVoteIdsByFilter(String tx);

    
    //for componets reason: 
    public String getVoteDate(Vote vote);
     
    public String getVoteIntNr(Vote vote);
     
    public String getVotePublicBodyName(Vote vote);
     
    public String getVoteSubjectName(Vote vote);
     
    public String getVoteResultAsString(Vote vote);
    
//    public VoteResult getVoteResult(VoteOfRole vor);
    public Vote getVote(VoteOfRole vor);
    
    public String getVoteNumbers(Vote vote);

    public List<Integer> findVoteIdsByPubPersonId(Integer id);

    public List<VoteOfRole> findNewVotesOfRole(List<Integer> votesOfRoleIds);

    public List<Integer> findVoteIdsByPubRoleId(Integer pubRoleId);

    public List<Integer> findVoteOfRoleIdsByPubRoleId(Integer pubRoleId);
    
    

}
