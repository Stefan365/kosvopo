/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import sk.stefan.mvps.model.entity.A_User;
import sk.stefan.mvps.model.entity.Vote;
import sk.stefan.mvps.model.service.UserService;
import sk.stefan.mvps.model.service.VoteService;
import sk.stefan.mvps.model.serviceImpl.UserServiceImpl;
import sk.stefan.mvps.model.serviceImpl.VoteServiceImpl;
import sk.stefan.mvps.view.components.layouts.VOTs_briefLayout;
import sk.stefan.mvps.view.components.layouts.MyViewLayout;
import sk.stefan.enums.UserType;
import sk.stefan.factories.InputNewEntityButtonFactory;

/**
 *
 * @author stefan
 */
public class V6s_VotesView extends MyViewLayout implements View {

    private static final long serialVersionUID = 10903884L;
    
    //servisy
    private final VoteService voteService;
    private final UserService userService;
    
    //komponenty
    private VOTs_briefLayout votesBriefLayout;
    //tlacitko na pridavanie novej verejne osoby:
    private Button addVoteBt;

    
    
    
    public V6s_VotesView (){

        super("hlasovania");
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
        this.addComponents(votesBriefLayout);
        
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
        this.votesBriefLayout = new VOTs_briefLayout(voteService.findAll(), voteService);
        
    }
    
    


    /**
     * Inicializuje tlacitko na pridavanie novej verejnej osoby.
     */
    private void initNewPublicBodyButton() {
        
        this.addVoteBt = InputNewEntityButtonFactory.createMyInputButton(Vote.class);
        this.addComponent(addVoteBt);
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
