/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.view.components.layouts;

import com.vaadin.ui.VerticalLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import sk.stefan.interfaces.Filterable;
import sk.stefan.mvps.model.entity.Subject;
import sk.stefan.mvps.model.service.VoteService;
import sk.stefan.mvps.view.components.panContents.SUB_briefPanContent;
import sk.stefan.mvps.view.components.panels.MyBriefPanel;

/**
 *
 * @author stefan
 */
public class SUBs_briefLayout extends VerticalLayout implements Filterable {
    
    private static final long serialVersionUID = 4233421L;
   
//    servisy:
    private final VoteService voteService; 
    
//    komponenty:
    private Map<Subject, MyBriefPanel<SUB_briefPanContent>> subjectsMap;

    
    
    //0.konstruktor
    public SUBs_briefLayout(List<Subject> subjects, VoteService vots){
        
        this.voteService = vots;
        initLayout(subjects);

        this.setSpacing(true);
        this.setMargin(true);

    } 
    
    /**
     */
    private void initLayout(List<Subject> subjects){
        
        SUB_briefPanContent subCont;
        MyBriefPanel<SUB_briefPanContent> pan;
        this.subjectsMap = new HashMap<>();
        this.removeAllComponents();
        
        for (Subject sub : subjects){
            subCont = new SUB_briefPanContent(sub, voteService);
            pan = new MyBriefPanel<>(subCont);
            this.subjectsMap.put(sub, pan);
            this.addComponent(pan);
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
