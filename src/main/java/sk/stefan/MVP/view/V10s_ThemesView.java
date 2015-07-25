/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view;

import com.vaadin.event.FieldEvents;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import java.util.List;
import sk.stefan.MVP.model.entity.A_User;
import sk.stefan.MVP.model.entity.PublicBody;
import sk.stefan.MVP.model.service.UserService;
import sk.stefan.MVP.model.service.VoteService;
import sk.stefan.MVP.model.serviceImpl.UserServiceImpl;
import sk.stefan.MVP.model.serviceImpl.VoteServiceImpl;
import sk.stefan.MVP.view.components.layouts.ThemesBriefLayout;
import sk.stefan.MVP.view.components.layouts.ViewLayout;
import sk.stefan.enums.UserType;
import sk.stefan.factories.InputNewEntityButtonFactory;

/**
 * View zahrnujuce vsetky tematicke okruhy.
 *
 * @author stefan
 */
public class V10s_ThemesView extends ViewLayout implements View {

    private static final long serialVersionUID = 10903884L;
    
    //servisy
    private final VoteService voteService;
    private final UserService userService;

    //komponenty
    private ThemesBriefLayout themesLayout;
    //tlacitko na pridavanie novej verejne osoby:
    private Button addNewThemeBt;
    private TextField searchFd; 
    
    
    public V10s_ThemesView (){
        super("tématické okruhy hlasovaní");
        this.voteService = new VoteServiceImpl();
        this.userService = new UserServiceImpl();

    }
    
    /**
     * 
     * @param isVolunteer
     */
    private void initAllBasic(Boolean isVolunteer) {

        this.removeAllComponents();

        this.initLayout();
        this.initSearchListener();
        
        this.addComponents(searchFd, themesLayout);
        
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
        
        this.themesLayout = new ThemesBriefLayout(voteService.findAllThemes(), voteService);
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
        
        this.addNewThemeBt = InputNewEntityButtonFactory.createMyInputButton(PublicBody.class);
        this.addComponent(addNewThemeBt);
    }

    
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        
        A_User user = VaadinSession.getCurrent().getAttribute(A_User.class);
        
        UserType utype = userService.getUserType(user);
                
        Boolean isVolunteer = Boolean.FALSE;
        if (user != null){
            isVolunteer = ((UserType.VOLUNTEER).equals(utype) || (UserType.ADMIN).equals(utype));
        }
        
        initAllBasic(isVolunteer);

    }
}
