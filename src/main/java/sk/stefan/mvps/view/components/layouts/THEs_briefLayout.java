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
import sk.stefan.mvps.model.entity.Theme;
import sk.stefan.mvps.model.service.VoteService;
import sk.stefan.mvps.view.components.panContents.THE_briefPanContent;
import sk.stefan.mvps.view.components.panels.MyBriefPanel;

/**
 *
 * @author stefan
 */
public class THEs_briefLayout extends VerticalLayout implements Filterable {
    
    private static final long serialVersionUID = 43565321L;
    
//    servisy:
    private final VoteService voteService; 
    
    
//    komponenty:
    private Map<Theme, MyBriefPanel<THE_briefPanContent>> themesMap;

    
    
    
    //0.konstruktor
    public THEs_briefLayout(List<Theme> themes, VoteService vots){
        
        this.voteService = vots;
        initLayout(themes);

        this.setSpacing(true);
        this.setMargin(true);

        
    } 
    
    /**
     */
    private void initLayout(List<Theme> themes){
        
        THE_briefPanContent theCont;
        MyBriefPanel<THE_briefPanContent> pan;
        
        this.themesMap = new HashMap<>();
        this.removeAllComponents();
        
        for (Theme th : themes){
            theCont = new THE_briefPanContent(th, voteService);
            pan = new MyBriefPanel<>(theCont);
            this.themesMap.put(th, pan);
            this.addComponent(pan);
        }
        
    }
    
    
    /**
     * @param themes
     */
    private void setThemes(List<Theme> themes){
        
        this.initLayout(themes);
        
    }

    @Override
    public void applyFilter(List<Integer> themeIds) {
        
        this.setThemes(voteService.findNewThemes(themeIds));
        
    }

    @Override
    public String getTableName() {
 
//        return "t_district";
        return null;
    
    }

    
}
