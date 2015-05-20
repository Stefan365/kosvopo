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
import sk.stefan.MVP.model.entity.PublicBody;
import sk.stefan.MVP.model.service.PublicBodyService;
import sk.stefan.MVP.model.service.UserService;
import sk.stefan.MVP.model.serviceImpl.PublicBodyServiceImpl;
import sk.stefan.MVP.model.serviceImpl.UserServiceImpl;
import sk.stefan.factories.InputNewEntityButtonFactory;
import sk.stefan.MVP.view.components.NavigationComponent;
import sk.stefan.MVP.view.components.PublicBodiesLayout;
import sk.stefan.enums.UserType;
import sk.stefan.ui.KosvopoUI;

/**
 *
 * @author stefan
 */
public class V3s_PublicBodiesView extends VerticalLayout implements View {

    private static final long serialVersionUID = 10903884L;
    
    private final PublicBodyService publicBodyService;
    
    private PublicBodiesLayout publicBodiesLayout;
    
    //tlacitko na pridavanie novej verejne osoby:
    private Button addNewPublicBodyBt;
    
    private final UserService userService;

    private final Navigator nav;
    
    private TextField searchFd; 
    
    private final VerticalLayout temporaryLy;
    
//    private final NavigationComponent navComp;

    
    public V3s_PublicBodiesView (){
    
        this.setMargin(true);
        this.setSpacing(true);

        this.nav = UI.getCurrent().getNavigator();

//        navComp =  ((KosvopoUI)UI.getCurrent()).getNavComp();
//        this.addComponent(navComp);
        
        temporaryLy = new VerticalLayout();
        this.addComponent(temporaryLy);

        this.publicBodyService = new PublicBodyServiceImpl();
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
        
        temporaryLy.addComponents(searchFd, publicBodiesLayout);
        
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
        
        this.publicBodiesLayout = new PublicBodiesLayout(publicBodyService.findAll(), publicBodyService);
//        this.districtCb = new FilterComboBox<>(District.class);
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
                List<Integer> pbIds = publicBodyService.findPublicBodyIdsByFilter(tx);
                publicBodiesLayout.applyFilter(pbIds);
                
            }
        });
    }
    

    //3.
    /**
     */
    private void initSearch() {
        
        searchFd.setWidth("40%");
        searchFd.setInputPrompt("možeš použiť vyhľadávanie podľa názvu obce");
//          searchFd.setInputPrompt("všechen spěch jest od ďábla!");
        
        searchFd.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.LAZY);
        
    }

    public PublicBodiesLayout getPbComp() {
        return publicBodiesLayout;
    }
    
    /**
     * Inicializuje tlacitko na pridavanie novej verejnej osoby.
     */
    private void initNewPublicBodyButton() {
        
        this.addNewPublicBodyBt = InputNewEntityButtonFactory.createMyInputButton(PublicBody.class);
        
        temporaryLy.addComponent(addNewPublicBodyBt);
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
