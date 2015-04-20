/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import sk.stefan.MVP.view.components.NavigationComponent;

/**
 *
 * @author stefan
 */
public class V6_VoteView extends VerticalLayout implements View {
    
    private static final long serialVersionUID = 1L;
    
    private final Navigator nav;
    
    private final VerticalLayout temporaryLy;
    
    private final NavigationComponent navComp;

    
    public V6_VoteView (){
        
        this.nav = UI.getCurrent().getNavigator();

        navComp = NavigationComponent.createNavigationComponent();
        this.addComponent(navComp);
        
        temporaryLy = new VerticalLayout();
        this.addComponent(temporaryLy);

    }
    
    
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        
    }
    
}
