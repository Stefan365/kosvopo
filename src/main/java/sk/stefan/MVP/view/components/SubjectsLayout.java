/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view.components;

import com.vaadin.ui.VerticalLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import sk.stefan.MVP.model.entity.dao.Subject;
import sk.stefan.MVP.model.service.VoteService;
import sk.stefan.interfaces.Filterable;

/**
 *
 * @author stefan
 */
public class SubjectsLayout extends VerticalLayout implements Filterable {
    
    private static final long serialVersionUID = 4233421L;
    
    private Map<Subject, SubjectComponent> subjectsMap;

    private final VoteService voteService; 
    
    //0.konstruktor
    public SubjectsLayout(List<Subject> subjects, VoteService vots){
        
        this.voteService = vots;
        initLayout(subjects);

        this.setSpacing(true);
        this.setMargin(true);

        
    } 
    
    /**
     */
    private void initLayout(List<Subject> subjects){
        
        SubjectComponent subComp;
        this.subjectsMap = new HashMap<>();
        this.removeAllComponents();
        
        for (Subject sub : subjects){
            subComp = new SubjectComponent(sub, voteService);
            this.subjectsMap.put(sub, subComp);
            this.addComponent(subComp);
        }
        
    }
    
    
    /**
     * @param subjects
     */
    private void setSubjects(List<Subject> subjects){
        
        this.initLayout(subjects);
        
    }

    @Override
    public void applyFilter(List<Integer> subjectIds) {
        
        this.setSubjects(voteService.findNewSubjects(subjectIds));
        
    }

    @Override
    public String getTableName() {
 
//        return "t_district";
        return null;
    
    }

    
}
