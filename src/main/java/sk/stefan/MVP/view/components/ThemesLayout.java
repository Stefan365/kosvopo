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
import sk.stefan.MVP.model.entity.dao.Theme;
import sk.stefan.MVP.model.entity.dao.Vote;
import sk.stefan.MVP.model.service.VoteService;
import sk.stefan.interfaces.Filterable;

/**
 *
 * @author stefan
 */
public class ThemesLayout extends VerticalLayout implements Filterable {
    
    private static final long serialVersionUID = 43565321L;
    
    private Map<Theme, ThemeComponent> themesMap;

    private final VoteService voteService; 
    
    //0.konstruktor
    public ThemesLayout(List<Theme> themes, VoteService vots){
        
        this.voteService = vots;
        initLayout(themes);

        this.setSpacing(true);
        this.setMargin(true);

        
    } 
    
    /**
     */
    private void initLayout(List<Theme> themes){
        
        ThemeComponent themeComp;
        this.themesMap = new HashMap<>();
        this.removeAllComponents();
        
        for (Theme th : themes){
            themeComp = new ThemeComponent(th, voteService);
            this.themesMap.put(th, themeComp);
            this.addComponent(themeComp);
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
