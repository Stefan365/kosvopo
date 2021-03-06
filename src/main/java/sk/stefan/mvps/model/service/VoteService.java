/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.model.service;

import java.util.Collection;
import java.util.List;

import sk.stefan.interfaces.TabEntity;
import sk.stefan.mvps.model.entity.PublicBody;
import sk.stefan.mvps.model.entity.PublicPerson;
import sk.stefan.mvps.model.entity.PublicRole;
import sk.stefan.mvps.model.entity.Subject;
import sk.stefan.mvps.model.entity.Theme;
import sk.stefan.mvps.model.entity.Vote;
import sk.stefan.mvps.model.entity.VoteOfRole;

/**
 * Obsluhuje Hlasovanie.
 *
 * @author stefan
 */
public interface VoteService {

    /**
     * Vsetky hlasovanie danej verejnej osoby.
     * @param pp
     * @return 
     */
    public List<Vote> getAllVotesForPublicPerson(PublicPerson pp);

    public List<Vote> getAllVotesForPublicRole(PublicRole pr);

    /**
     * Vrati vsetky hlasovacie ukony danej verejnej osoby.
     * @param pp
     * @return 
     */
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
    
    public VoteOfRole saveVoteOfRole(VoteOfRole vor, boolean noteChange);

    public List<Theme> findNewThemes(List<Integer> themeIds);
    
    public List<Subject> findNewSubjects(List<Integer> subjectIds);
    
    //find All:

    public List<Theme> findAllThemes();

    List<Vote> getAllVotesForTheme(Theme theme);

    public List<Subject> findAllSubjectsForPublicBody(PublicBody pb);

    public List<Integer> findNewThemeIdsByFilter(String tx);
    
    public List<Integer> findNewSubjectIdsByFilter(String tx);   

    public Subject findSubjectById(Integer subject_id);

    public Theme findThemeBySubjectId(Integer subject_id);

    public List<VoteOfRole> findVoteOfRolesByVoteId(Integer vote_id);

    public List<Vote> findAll();

    public Vote findOne(Integer voteId);

    public String getVorPresentationName(VoteOfRole aThis);

    public String getVotePresentationName(Vote aThis);

    public String getSubjectPresentationName(Subject sub);


    List<Vote> getAllVotesForPublicBody(PublicBody publicBody);

    List<VoteOfRole> getAllVotesOfPublicRole(PublicRole publicRole);

    List<Vote> findAllVotesForTabEntity(TabEntity tabEntity);

    PublicBody getVotePublicBody(Vote vote);

    Theme saveTheme(Theme theme);

    void removeTheme(Theme theme);

    List<Subject> findAllSubjects();

    Subject saveSubject(Subject subject);

    void removeSubject(Subject subject);

    VoteOfRole findVoteOfRoleForVoteAndPublicRole(Vote vote, PublicRole publicRole);

    Theme findThemaById(Integer temaId);
}
