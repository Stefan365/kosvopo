/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view;

import com.vaadin.event.FieldEvents;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import java.util.List;
import sk.stefan.MVP.model.entity.dao.A_User;
import sk.stefan.MVP.model.entity.dao.PublicBody;
import sk.stefan.MVP.model.service.UserService;
import sk.stefan.MVP.model.service.VoteService;
import sk.stefan.MVP.model.serviceImpl.UserServiceImpl;
import sk.stefan.MVP.model.serviceImpl.VoteServiceImpl;
import sk.stefan.MVP.view.components.InputNewEntityButtonFactory;
import sk.stefan.MVP.view.components.NavigationComponent;
import sk.stefan.MVP.view.components.ThemesLayout;
import sk.stefan.enums.UserType;

/**
 *
 * @author stefan
 */
public class V10s_ThemesView extends VerticalLayout implements View {

    private static final long serialVersionUID = 10903884L;
    
    private final VoteService voteService;
    
    private final UserService userService;

    private final Navigator nav;
    private ThemesLayout themesLayout;
    //tlacitko na pridavanie novej verejne osoby:
    private Button addNewThemeBt;

    private TextField searchFd; 
    private final VerticalLayout temporaryLy;
    private final NavigationComponent navComp;

    
    public V10s_ThemesView (){
    
        this.nav = UI.getCurrent().getNavigator();

        navComp = NavigationComponent.createNavigationComponent();
        this.addComponent(navComp);
        
        temporaryLy = new VerticalLayout();
        this.addComponent(temporaryLy);

        this.voteService = new VoteServiceImpl();
        this.userService = new UserServiceImpl();

    }
    
    /**
     * 
     * @param isVolunteer
     */
    private void initAllBasic(Boolean isVolunteer) {

        temporaryLy.removeAllComponents();

        this.initLayout();
        this.initSearchListener();
        
        temporaryLy.addComponents(searchFd, themesLayout);
        
        if(isVolunteer){
            this.initNewThemeButton();
        }
        
    }

    
    /**
     * 
     */
    private void initLayout(){
        
        this.setMargin(true);
        this.setSpacing(true);
        
        this.themesLayout = new ThemesLayout(voteService.findAllThemes(), voteService);
        this.searchFd = new TextField("Vyhľadávanie");
        this.initSearch();
        
    }
    
    
    /**
     * Initializes listener
     */
    private void initSearchListener(){
        
        searchFd.addTextChangeListener(new FieldEvents.TextChangeListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void textChange(final FieldEvents.TextChangeEvent event) {
        
                String tx = event.getText();
                List<Integer> thIds = voteService.findNewThemeIdsByFilter(tx);
                themesLayout.applyFilter(thIds);
                
            }
        });
    }
    

    //3.
    /**
     */
    private void initSearch() {
        
        searchFd.setWidth("40%");
        searchFd.setInputPrompt("možeš použiť vyhľadávanie...");
        searchFd.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.LAZY);
        
    }

    /**
     * Inicializuje tlacitko na pridavanie novej verejnej osoby.
     */
    private void initNewThemeButton() {
        
        this.addNewThemeBt = InputNewEntityButtonFactory.createMyButton(PublicBody.class);
        
        temporaryLy.addComponent(addNewThemeBt);
    }

    
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        
        A_User user = VaadinSession.getCurrent().getAttribute(A_User.class);
        
        UserType utype = userService.getUserType(user);
                
        Boolean isVolunteer = Boolean.FALSE;
        if (user != null){
            isVolunteer = ((UserType.USER).equals(utype) || (UserType.ADMIN).equals(utype));
        }
        
        initAllBasic(isVolunteer);

    }
}
