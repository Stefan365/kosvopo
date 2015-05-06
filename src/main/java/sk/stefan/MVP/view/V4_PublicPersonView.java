/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import java.util.List;
import org.apache.log4j.Logger;
import sk.stefan.MVP.model.entity.A_User;
import sk.stefan.MVP.model.entity.PersonClassification;
import sk.stefan.MVP.model.entity.PublicPerson;
import sk.stefan.MVP.model.entity.PublicRole;
import sk.stefan.MVP.model.service.ClassificationService;
import sk.stefan.MVP.model.service.PublicRoleService;
import sk.stefan.MVP.model.service.UserService;
import sk.stefan.MVP.model.service.VoteService;
import sk.stefan.MVP.model.serviceImpl.ClassificationServiceImpl;
import sk.stefan.MVP.model.serviceImpl.PublicRoleServiceImpl;
import sk.stefan.MVP.model.serviceImpl.UserServiceImpl;
import sk.stefan.MVP.model.serviceImpl.VoteServiceImpl;
import sk.stefan.MVP.view.components.MyTimeline;
import sk.stefan.MVP.view.components.PersonClassLayout;
import sk.stefan.MVP.view.components.PublicPersonComponent;
import sk.stefan.MVP.view.components.PublicRolesLayout;
import sk.stefan.MVP.view.components.documents.DownloaderLayout;
import sk.stefan.MVP.view.components.documents.UploaderLayout;
import sk.stefan.enums.UserType;
import sk.stefan.factories.InputNewEntityButtonFactory;

/**
 *
 * @author stefan
 */
public final class V4_PublicPersonView extends VerticalLayout implements View {

    private static final long serialVersionUID = 121322L;
    private static final Logger log = Logger.getLogger(V3_PublicBodyView.class);

    //hlavna entita tohoto VIew
    private PublicPerson publicPerson;

    //dvolezita entita, ktora predstavuje aktualnu verejnu funkciu danej osoby. 
    //bude vyznacena farebne.
    private PublicRole actualPublicRole;

    //komponenty pre zobrazeneie verejnych roli dane osoby (tj. jedna aktivna a 
    //zvysok stare): 
    private PublicRolesLayout publicRolesLayout;

    //layout pre zobrazenie zakladnych udajov danej osoby.
    private PublicPersonComponent publicPersonComponent;

    //servisy:
    private final PublicRoleService publicRoleService;
    private final VoteService voteService;
    private final UserService userService;
    private final ClassificationService classificationService;

//    private final PublicPersonService publicPersonService;
    //componenty pre TimeLine:
    private MyTimeline timeline;

    private PersonClassLayout classPersonLayout;
    //tlacitko na pridavanie novej entity:
    private Button addNewPublicRoleBt;
    //pre uzivatela obcan   
    private DownloaderLayout<PublicPerson> downoaderLayout;
    //pre uzivatela admin a dobrovolnik
    private UploaderLayout<PublicPerson> uploaderLayout;

    //konstruktor:
    public V4_PublicPersonView() {

        this.setMargin(true);
        this.setSpacing(true);

        this.publicRoleService = new PublicRoleServiceImpl();
        this.voteService = new VoteServiceImpl();
        this.userService = new UserServiceImpl();
        this.classificationService = new ClassificationServiceImpl();

        if (publicPerson != null) {
            initAllBasic(Boolean.FALSE);
        }

    }

    /**
     */
    private void initAllBasic(Boolean isVolunteer) {

        this.removeAllComponents();

        this.initPublicPersonComponent();
        this.initPublicRolesLayout();
//        this.initVoteLayout();
        this.initTimeline();
        this.classPersonLayout();

        this.addComponents(publicPersonComponent, publicRolesLayout, classPersonLayout, timeline);

        if (isVolunteer) {

            this.initNewPublicRoleButton();
            this.initUploadLayout();

        } else {
            this.initDownloadLayout();
        }

    }

    //
    private void setPublicPersonValue(PublicPerson pp) {

        this.publicPerson = pp;
        List<PublicRole> proles = publicRoleService.getActualPublicRolesOfPublicPerson(pp);
        if (proles != null && !proles.isEmpty()) {
            this.actualPublicRole = proles.get(0);
        } else {
            this.actualPublicRole = null;
        }

    }

    /**
     */
    private void initPublicPersonComponent() {

        this.publicPersonComponent = new PublicPersonComponent(publicPerson, null);

    }

    /**
     *
     */
    private void initPublicRolesLayout() {

        List<Integer> prIds = publicRoleService.findPublicRoleIdsByPubPersonId(publicPerson.getId());

        List<PublicRole> publicRoles = publicRoleService.getPublicRoles(prIds);

        this.publicRolesLayout = new PublicRolesLayout(publicRoles, publicRoleService);

        if (actualPublicRole != null) {
            this.publicRolesLayout.setActual(actualPublicRole);
        }

    }

    private void initTimeline() {

        List<Integer> ids = voteService.findVoteIdsByPubPersonId(this.publicPerson.getId());
        timeline = new MyTimeline(ids);

    }
    

    public PublicPerson getPublicPerson() {
        return publicPerson;
    }

    public void setPublicPerson(PublicPerson publicPerson) {
        this.publicPerson = publicPerson;
    }

    public PublicRolesLayout getPublicRolesLayout() {
        return publicRolesLayout;
    }

    public void setPublicRolesLayout(PublicRolesLayout publicRolesLy) {
        this.publicRolesLayout = publicRolesLy;
    }

    /**
     * Prida tlacitko na pridavanie novej entity PublicBody.
     */
    private void initNewPublicRoleButton() {

        this.addNewPublicRoleBt = InputNewEntityButtonFactory.createMyInputButton(PublicRole.class);

        this.addComponent(addNewPublicRoleBt);

    }

    /**
     * Inicializuje editovatelny layout s dokumentami prisluchajucimi entite
     * PublicBody
     */
    private void initUploadLayout() {

        this.uploaderLayout = new UploaderLayout<>(PublicPerson.class, this.publicPerson);

        this.addComponent(uploaderLayout);

    }

    /**
     * Komponenta na zobrazovanie dokumentov prisluchajucich entite PublicBody.
     */
    private void initDownloadLayout() {

        this.downoaderLayout = new DownloaderLayout<>(PublicPerson.class, this.publicPerson);

        this.addComponent(downoaderLayout);

    }

    /**
     */
    private void classPersonLayout() {

        List<Integer> pclIds = classificationService.findActualPersonClassIds(publicPerson.getId());

        List<PersonClassification> pcls = classificationService.findNewPersonClass(pclIds);

        this.classPersonLayout = new PersonClassLayout(pcls, classificationService);

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        PublicPerson pp = UI.getCurrent().getSession().getAttribute(PublicPerson.class);

        A_User user = VaadinSession.getCurrent().getAttribute(A_User.class);

//        Notification.show("V4: " + (pp == null));

        Boolean isVolunteer = Boolean.FALSE;
        if (user != null) {

            UserType utype = userService.getUserType(user);
            //moze byt dobrovolnik, alebo admin.
            isVolunteer = ((UserType.VOLUNTEER).equals(utype) || (UserType.ADMIN).equals(utype));
        }

        if (pp != null) {
            setPublicPersonValue(pp);
            initAllBasic(isVolunteer);
        } else {
            UI.getCurrent().getNavigator().navigateTo("V4s_PublicPersonsView");
        }

    }

}
