/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.view;

import com.vaadin.event.FieldEvents;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import java.util.List;
import sk.stefan.enums.UserType;
import sk.stefan.factories.InputNewEntityButtonFactory;
import sk.stefan.mvps.model.entity.A_User;
import sk.stefan.mvps.model.entity.PublicPerson;
import sk.stefan.mvps.model.service.PublicPersonService;
import sk.stefan.mvps.model.service.UserService;
import sk.stefan.mvps.model.serviceImpl.PublicPersonServiceImpl;
import sk.stefan.mvps.model.serviceImpl.UserServiceImpl;
import sk.stefan.mvps.view.components.layouts.MyViewLayout;
import sk.stefan.mvps.view.components.layouts.PUPs_briefLayout;

/**
 *
 * @author stefan
 */
public class V4s_PublicPersonsView extends MyViewLayout implements View {

    private static final long serialVersionUID = 10903884L;

//    servisy:
    private final PublicPersonService publicPersonService;
    private final UserService userService;

//    KOMPONENTY:
    private PUPs_briefLayout publicPersonsBriefLy;
    private TextField searchFd;
    //tlacitko na pridavanie novej verejne osoby:
    private Button addNewPublicPersonBt;

    public V4s_PublicPersonsView() {

        super("verejné osoby");
        this.publicPersonService = new PublicPersonServiceImpl();
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

        this.addComponents(searchFd, publicPersonsBriefLy);

        if (isVolunteer) {
            this.initNewPublicPersonButton();
        }

    }

    /**
     *
     */
    private void initLayout() {

        this.setMargin(true);
        this.setSpacing(true);

        this.publicPersonsBriefLy = new PUPs_briefLayout(publicPersonService.findAll(), publicPersonService);
        this.searchFd = new TextField("Vyhľadávač");
        this.initSearch();

    }

    /**
     * Initializes listener
     */
    private void initSearchListener() {

        searchFd.addTextChangeListener(new FieldEvents.TextChangeListener() {
            
            private static final long serialVersionUID = 1L;

            @Override
            public void textChange(final FieldEvents.TextChangeEvent event) {

                String tx = event.getText();
                List<Integer> ppIds = publicPersonService.findPublicPersonsIdsByFilter(tx);
                publicPersonsBriefLy.applyFilter(ppIds);

            }
        });
    }

    //3.
    private void initSearch() {

        searchFd.setWidth("40%");
        searchFd.setInputPrompt("možeš použiť vyhľadávanie podľa mena");
        searchFd.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.LAZY);
    }

    public PUPs_briefLayout getPublicPersonsComponent() {
        return publicPersonsBriefLy;
    }

    /**
     * Inicializuje tlacitko na pridavanie novej verejnej osoby.
     */
    private void initNewPublicPersonButton() {

        this.addNewPublicPersonBt = InputNewEntityButtonFactory.createMyInputButton(PublicPerson.class);
        this.addComponent(addNewPublicPersonBt);
    }

    
    
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        A_User user = VaadinSession.getCurrent().getAttribute(A_User.class);

        Boolean isVolunteer = Boolean.FALSE;
        if (user != null) {
            UserType utype = userService.getUserType(user);
            //moze byt dobrovolnik, alebo admin.
            isVolunteer = ((UserType.VOLUNTEER).equals(utype) || (UserType.ADMIN).equals(utype));
        }

        initAllBasic(isVolunteer);

    }

}
