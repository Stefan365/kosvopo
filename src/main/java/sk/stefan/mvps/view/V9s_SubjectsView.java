/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.view;

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
import sk.stefan.mvps.model.entity.A_User;
import sk.stefan.mvps.model.entity.PublicBody;
import sk.stefan.mvps.model.service.PublicBodyService;
import sk.stefan.mvps.model.service.UserService;
import sk.stefan.mvps.model.serviceImpl.PublicBodyServiceImpl;
import sk.stefan.mvps.model.serviceImpl.UserServiceImpl;
import sk.stefan.mvps.view.components.layouts.PUBs_briefLayout;
import sk.stefan.mvps.view.components.layouts.ViewLayout;
import sk.stefan.enums.UserType;
import sk.stefan.factories.InputNewEntityButtonFactory;

/**
 *
 * @author stefan
 */
public class V9s_SubjectsView extends ViewLayout implements View {

    private static final long serialVersionUID = 10903884L;
    
    private final PublicBodyService publicBodyService;
    
    private PUBs_briefLayout publicBodiesLayout;
    
    //tlacitko na pridavanie novej verejne osoby:
    private Button addNewPublicBodyBt;
    
    private final UserService userService;

    private final Navigator nav;
    
    private TextField searchFd; 
    
    private VerticalLayout temporaryLy;
    
//    private final NavigationComponent navComp;

    
    public V9s_SubjectsView (){

        super("predmety hlasovania");
        
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
        
        this.publicBodiesLayout = new PUBs_briefLayout(publicBodyService.findAll(), publicBodyService);
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
        searchFd.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.LAZY);
        
    }

    public PUBs_briefLayout getPbComp() {
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

        UserType utype = userService.getUserType(user);
                
        Boolean isVolunteer = Boolean.FALSE;
        if (user != null){
            isVolunteer = ((UserType.VOLUNTEER).equals(utype) || (UserType.ADMIN).equals(utype));
        }
        
        initAllBasic(isVolunteer);

    }
    
 
    
}
