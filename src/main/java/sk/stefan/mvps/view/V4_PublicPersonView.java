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
import com.vaadin.ui.UI;
import java.util.List;
import org.apache.log4j.Logger;
import sk.stefan.enums.UserType;
import sk.stefan.factories.EditEntityButtonFactory;
import sk.stefan.factories.InputNewEntityButtonFactory;
import sk.stefan.mvp.view.components.MyBriefPanel;
import sk.stefan.mvps.model.entity.A_User;
import sk.stefan.mvps.model.entity.PersonClassification;
import sk.stefan.mvps.model.entity.PublicPerson;
import sk.stefan.mvps.model.entity.PublicRole;
import sk.stefan.mvps.model.service.ClassificationService;
import sk.stefan.mvps.model.service.PublicRoleService;
import sk.stefan.mvps.model.service.UserService;
import sk.stefan.mvps.model.service.VoteService;
import sk.stefan.mvps.model.serviceImpl.ClassificationServiceImpl;
import sk.stefan.mvps.model.serviceImpl.PublicRoleServiceImpl;
import sk.stefan.mvps.model.serviceImpl.UserServiceImpl;
import sk.stefan.mvps.model.serviceImpl.VoteServiceImpl;
import sk.stefan.mvps.view.components.MyTimeline;
import sk.stefan.mvps.view.components.layouts.DownAndUploaderBriefLayout;
import sk.stefan.mvps.view.components.layouts.DownloaderBriefLayout;
import sk.stefan.mvps.view.components.layouts.PCLs_briefLayout;
import sk.stefan.mvps.view.components.layouts.PURs_briefLayout;
import sk.stefan.mvps.view.components.layouts.ViewLayout;
import sk.stefan.mvps.view.components.panContents.PUP_briefPanContent;
import sk.stefan.wrappers.FunctionalEditWrapper;

/**
 *
 * @author stefan
 */
public final class V4_PublicPersonView extends ViewLayout implements View {

    private static final long serialVersionUID = 121322L;
    private static final Logger log = Logger.getLogger(V3_PublicBodyView.class);

    //servisy:
    private final PublicRoleService publicRoleService;
    private final VoteService voteService;
    private final UserService userService;
    private final ClassificationService classificationService;

    //hlavna entita tohoto VIew
    private PublicPerson publicPerson;

    //dvolezita entita, ktora predstavuje aktualnu verejnu funkciu danej osoby. 
    //bude vyznacena farebne.
    private PublicRole actualPublicRole;

    
    
    //komponenty pre zobrazeneie verejnych roli dane osoby (tj. jedna aktivna a 
    //zvysok stare): 
    private PURs_briefLayout pubRolesBriefLayout;
    //layout pre zobrazenie zakladnych udajov danej osoby.
    private PUP_briefPanContent pubPersonBriefPanel;
    
//    private final PublicPersonService publicPersonService;
    //componenty pre TimeLine:
    private MyTimeline timeline;

    private PCLs_briefLayout classPersonLayout;
    //tlacitko na pridavanie novej entity:
    private Button addNewPublicRoleBt;
    //pre uzivatela obcan   
    private DownloaderBriefLayout<PublicPerson> downoaderLayout;
    //pre uzivatela admin a dobrovolnik
    private DownAndUploaderBriefLayout<PublicPerson> uploaderLayout;

    //konstruktor:
    public V4_PublicPersonView() {
        
        super("Verejná Osoba");
        this.publicRoleService = new PublicRoleServiceImpl();
        this.voteService = new VoteServiceImpl();
        this.userService = new UserServiceImpl();
        this.classificationService = new ClassificationServiceImpl();
        
        if (publicPerson != null) {
//          toto sa nikdy nezrealizuje:
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

        this.addComponents(pubPersonBriefPanel, pubRolesBriefLayout, classPersonLayout, timeline);

        if (isVolunteer) {

            this.initNewPublicRoleButton();
            this.initEditPublicPersonButton();
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

//        this.pubPersonBriefPanel = new PUP_briefPanContent(publicPerson, null);
        PUP_briefPanContent cont = new PUP_briefPanContent(publicPerson, null);
        MyBriefPanel pan = new MyBriefPanel(cont);
        this.pubPersonBriefPanel = new PUP_briefPanContent(publicPerson, null);

    }

    /**
     *
     */
    private void initPublicRolesLayout() {

        List<Integer> prIds = publicRoleService.findPublicRoleIdsByPubPersonId(publicPerson.getId());

        List<PublicRole> publicRoles = publicRoleService.getPublicRoles(prIds);

        this.pubRolesBriefLayout = new PURs_briefLayout(publicRoles, publicRoleService);

        if (actualPublicRole != null) {
            this.pubRolesBriefLayout.setActual(actualPublicRole);
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

    public PURs_briefLayout getPublicRolesLayout() {
        return pubRolesBriefLayout;
    }

    public void setPublicRolesLayout(PURs_briefLayout publicRolesLy) {
        this.pubRolesBriefLayout = publicRolesLy;
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

        this.uploaderLayout = new DownAndUploaderBriefLayout<>(PublicPerson.class, this.publicPerson);

        this.addComponent(uploaderLayout);

    }

    /**
     * Komponenta na zobrazovanie dokumentov prisluchajucich entite PublicBody.
     */
    private void initDownloadLayout() {

        this.downoaderLayout = new DownloaderBriefLayout<>(PublicPerson.class, this.publicPerson);

        this.addComponent(downoaderLayout);

    }

    /**
     */
    private void classPersonLayout() {

        List<Integer> pclIds = classificationService.findActualPersonClassIds(publicPerson.getId());

        List<PersonClassification> pcls = classificationService.findNewPersonClass(pclIds);

        this.classPersonLayout = new PCLs_briefLayout(pcls, classificationService);

    }

    
    /**
     * Sluzi na inicializaciu - tj. vytvorenie editovacieho tlacitka. 
     * 
     */
    private void initEditPublicPersonButton() {
        
        Button editPUPbt;
        FunctionalEditWrapper<PublicPerson> ew = new FunctionalEditWrapper<>(PublicPerson.class, publicPerson);
        editPUPbt = EditEntityButtonFactory.createMyEditButton(ew);
        this.addComponent(editPUPbt);
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
