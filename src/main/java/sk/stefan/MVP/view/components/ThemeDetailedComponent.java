/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view.components;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import sk.stefan.MVP.model.entity.dao.Theme;
import sk.stefan.MVP.model.service.VoteService;

/**
 *
 * @author stefan
 */
public class ThemeDetailedComponent extends GridLayout {

    private static final long serialVersionUID = 1L;

    private final Theme theme;

    private final Navigator navigator;

    private final VoteService voteService;
    
    //graficke komponenty:
    private Label titleLb; 
    private Label DescriptionLb;

    //0.konstruktor:
    /**
     * @param th
     * @param vs
     */
    public ThemeDetailedComponent(Theme th, VoteService vs) {

        super(1, 2);//column , row
        this.navigator = UI.getCurrent().getNavigator();
        this.theme = th;
        this.voteService = vs;

        this.initLayout();
        
    }

    /**
     */
    private void initLayout() {

        this.removeAllComponents();
        
        this.setSpacing(true);

        this.titleLb = new Label(theme.getBrief_description());
        this.DescriptionLb = new Label(theme.getDescription());
        
        this.addComponent(titleLb, 0, 0);
        this.addComponent(DescriptionLb, 0, 1);
        
    }

}
