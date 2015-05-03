/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import sk.stefan.MVP.model.entity.A_User;
import sk.stefan.MVP.model.entity.Vote;
import sk.stefan.MVP.model.service.UserService;
import sk.stefan.MVP.model.service.VoteService;
import sk.stefan.MVP.model.serviceImpl.UserServiceImpl;
import sk.stefan.MVP.model.serviceImpl.VoteServiceImpl;
import sk.stefan.MVP.view.components.NavigationComponent;
import sk.stefan.MVP.view.components.VotesLayout;
import sk.stefan.enums.UserType;
import sk.stefan.factories.InputNewEntityButtonFactory;
import sk.stefan.ui.KosvopoUI;

/**
 *
 * @author stefan
 */
public class V6s_VotesView extends VerticalLayout implements View {

    private static final long serialVersionUID = 10903884L;
    
    private final VoteService voteService;
    
    private VotesLayout votesLayout;
    
    //tlacitko na pridavanie novej verejne osoby:
    private Button addVoteBt;
    
    private final UserService userService;
    
    private final VerticalLayout temporaryLy;   
//    private final NavigationComponent navComp;
    private final Navigator nav;
    
    public V6s_VotesView (){

        this.setMargin(true);
        this.setSpacing(true);
    
        this.nav = UI.getCurrent().getNavigator();

//        navComp =  ((KosvopoUI)UI.getCurrent()).getNavComp();
//        this.addComponent(navComp);
        
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
        
        temporaryLy.addComponents(votesLayout);
        
        if(isVolunteer){
            this.initNewPublicBodyButton();
        }
        
    }

    
    /**
     * 
     */
    private void initLayout(){
        
        this.setMargin(true);
        this.setSpacing(true);
        
        this.votesLayout = new VotesLayout(voteService.findAll(), voteService);
        
        
    }
    
    


    /**
     * Inicializuje tlacitko na pridavanie novej verejnej osoby.
     */
    private void initNewPublicBodyButton() {
        
        this.addVoteBt = InputNewEntityButtonFactory.createMyInputButton(Vote.class);
        
        temporaryLy.addComponent(addVoteBt);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        
        A_User user = VaadinSession.getCurrent().getAttribute(A_User.class);
        
        Boolean isVolunteer = Boolean.FALSE;
        if (user != null){
            UserType utype = userService.getUserType(user);
            isVolunteer = ((UserType.VOLUNTEER).equals(utype) || (UserType.ADMIN).equals(utype));
        } 
        
        initAllBasic(isVolunteer);

    }
    
}
