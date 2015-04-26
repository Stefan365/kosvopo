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
import sk.stefan.MVP.model.entity.A_User;
import sk.stefan.MVP.model.entity.PublicPerson;
import sk.stefan.MVP.model.service.PublicPersonService;
import sk.stefan.MVP.model.service.UserService;
import sk.stefan.MVP.model.serviceImpl.PublicPersonServiceImpl;
import sk.stefan.MVP.model.serviceImpl.UserServiceImpl;
import sk.stefan.factories.InputNewEntityButtonFactory;
import sk.stefan.MVP.view.components.NavigationComponent;
import sk.stefan.MVP.view.components.PublicPersonsLayout;
import sk.stefan.enums.UserType;

/**
 *
 * @author stefan
 */
public class V4s_PublicPersonsView extends VerticalLayout implements View {

    
    private static final long serialVersionUID = 10903884L;
    
    private final PublicPersonService publicPersonService;
    private final UserService userService;

    private PublicPersonsLayout publicPersonsLayout;
    
    private TextField searchFd; 
    
    //tlacitko na pridavanie novej verejne osoby:
    private Button addNewPublicPersonBt;
    
        private final VerticalLayout temporaryLy;
    
    private final NavigationComponent navComp;


    private final Navigator nav;
    
//    private TextField searchField;// = new TextField();
        
    public V4s_PublicPersonsView (){
    
        this.setMargin(true);
        this.setSpacing(true);

        this.nav = UI.getCurrent().getNavigator();

        navComp = NavigationComponent.createNavigationComponent();
        this.addComponent(navComp);
        
        temporaryLy = new VerticalLayout();
        this.addComponent(temporaryLy);

        
        this.publicPersonService = new PublicPersonServiceImpl();
        this.userService = new UserServiceImpl();
        
//        this.initAllBasic(Boolean.FALSE);
        
    }
    
    /**
     * 
     * @param isVolunteer
     */
    private void initAllBasic(Boolean isVolunteer) {

        temporaryLy.removeAllComponents();
        
        this.initLayout();
        this.initListener();
        
        temporaryLy.addComponents(searchFd, publicPersonsLayout);
        
        if(isVolunteer){
            this.initNewPublicPersonButton();
        }
        
    }

    
    /**
     * 
     */
    private void initLayout(){
        
        this.setMargin(true);
        this.setSpacing(true);
        
        this.publicPersonsLayout = new PublicPersonsLayout(publicPersonService.findAll(), publicPersonService);
        this.searchFd = new TextField("Vyhľadávač");
        this.initSearch();
        
        
        
    }
    
    /**
     * Initializes listener
     */
    private void initListener(){
        
        searchFd.addTextChangeListener(new FieldEvents.TextChangeListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void textChange(final FieldEvents.TextChangeEvent event) {
        
                String tx = event.getText();
                List<Integer> ppIds = publicPersonService.findNewPublicPersonsIds(tx);
                publicPersonsLayout.applyFilter(ppIds);
                
            }
        });
    }
    

    //3.
    private void initSearch() {
        
        searchFd.setWidth("40%");
        searchFd.setInputPrompt("možeš použiť vyhľadávanie podľa mena");
        searchFd.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.LAZY);
        
    }

    
    
    public PublicPersonsLayout getPublicPersonsComponent() {
        return publicPersonsLayout;
    }

    /**
     * Inicializuje tlacitko na pridavanie novej verejnej osoby.
     */
    private void initNewPublicPersonButton() {
        
                
        this.addNewPublicPersonBt = InputNewEntityButtonFactory.createMyInputButton(PublicPerson.class);
        
        temporaryLy.addComponent(addNewPublicPersonBt);
    }
    
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        
        A_User user = VaadinSession.getCurrent().getAttribute(A_User.class);

                
        Boolean isVolunteer = Boolean.FALSE;
        if (user != null){
            UserType utype = userService.getUserType(user);
            //moze byt dobrovolnik, alebo admin.
            isVolunteer = ((UserType.VOLUNTEER).equals(utype) || (UserType.ADMIN).equals(utype));
        }
        
        initAllBasic(isVolunteer);
      
    }

    
}
