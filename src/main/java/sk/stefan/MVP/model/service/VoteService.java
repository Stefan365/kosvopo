/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.model.service;

import java.util.List;
import sk.stefan.MVP.model.entity.dao.PublicBody;
import sk.stefan.MVP.model.entity.dao.PublicPerson;
import sk.stefan.MVP.model.entity.dao.PublicRole;
import sk.stefan.MVP.model.entity.dao.Subject;
import sk.stefan.MVP.model.entity.dao.Theme;
import sk.stefan.MVP.model.entity.dao.Vote;
import sk.stefan.MVP.model.entity.dao.VoteOfRole;

/**
 *
 * @author stefan
 */
public interface VoteService {

    //vote itself:
    public List<Vote> getAllVotesForPublicPerson(PublicPerson pp);

    public List<Vote> getAllVotesForPublicRole(PublicRole pr);

    //vote of role:
    public List<VoteOfRole> getAllVotesOfPublicPerson(PublicPerson pp);

    public List<PublicRole> getAllPublicRolesOfPublicPerson(PublicPerson pp);

    
    public List<Integer> findVoteIdsByPubBodyId(Integer publicBodyId);
    
    public List<Integer> findNewVoteIdsByFilter(String tx);

    
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

    
    public List<Integer> findVoteIdsByPubRoleId(Integer pubRoleId);

    public List<Integer> findVoteOfRoleIdsByPubRoleId(Integer pubRoleId);

    public String getThemeNameById(Integer theme_id);
    
    public Theme getThemeById(Integer theme_id);
    
    public PublicRole getPublicRoleById(Integer theme_id);

    public List<Vote> findNewVotes(List<Integer> voteIds);
    
    public List<VoteOfRole> findNewVotesOfRole(List<Integer> votesOfRoleIds);

    public List<Theme> findNewThemes(List<Integer> themeIds);
    
    public List<Subject> findNewSubjects(List<Integer> subjectIds);
    
    //find All:

    public List<Theme> findAllThemes();
    
    public List<Subject> findAllSubjectsForPublicBody(PublicBody pb);

    public List<Integer> findNewThemeIdsByFilter(String tx);
    
    public List<Integer> findNewSubjectIdsByFilter(String tx);   

    public Subject findSubjectById(Integer subject_id);

    public Theme findThemeBySubjectId(Integer subject_id);

    public List<VoteOfRole> findVoteOfRolesByVoteId(Integer vote_id);

    public List<Vote> findAll();

    

}
