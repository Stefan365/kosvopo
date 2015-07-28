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
import com.vaadin.ui.VerticalLayout;
import java.util.List;
import org.apache.log4j.Logger;
import sk.stefan.mvps.model.entity.A_User;
import sk.stefan.mvps.model.entity.PublicPerson;
import sk.stefan.mvps.model.entity.PublicRole;
import sk.stefan.mvps.model.entity.VoteOfRole;
import sk.stefan.mvps.model.service.PublicRoleService;
import sk.stefan.mvps.model.service.UserService;
import sk.stefan.mvps.model.service.VoteService;
import sk.stefan.mvps.model.serviceImpl.PublicRoleServiceImpl;
import sk.stefan.mvps.model.serviceImpl.UserServiceImpl;
import sk.stefan.mvps.model.serviceImpl.VoteServiceImpl;
import sk.stefan.mvps.view.components.MyTimeline;
import sk.stefan.mvps.view.components.panContents.PUR_briefPanContent;
import sk.stefan.mvps.view.components.layouts.VORs_briefLayout;
import sk.stefan.mvps.view.components.layouts.DownloaderBriefLayout;
import sk.stefan.mvps.view.components.layouts.DownAndUploaderBriefLayout;
import sk.stefan.mvps.view.components.layouts.MyViewLayout;
import sk.stefan.enums.UserType;
import sk.stefan.factories.EditEntityButtonFactory;
import sk.stefan.wrappers.FunctionalEditWrapper;

/**
 * View zobrazujúci roľu danej verejnej osoby.
 *
 * @author stefan
 */
public final class V5_PublicRoleView extends MyViewLayout implements View {

    private static final long serialVersionUID = 121322L;
    private static final Logger log = Logger.getLogger(V5_PublicRoleView.class);

    //hlavna entita tohoto VIew
    private PublicRole publicRole;

    //servisy:
    private final PublicRoleService publicRoleService;
    private final VoteService voteService;
    private final UserService userService;
    
//    private final EditEntityButtonFactory<PublicRole> editButtonFactory;
    
    
    //componenty pre TimeLine:
    private MyTimeline timeline;
    //layout pre zobrazenie zakladnych udajov danej osoby.
    private PUR_briefPanContent publicRoleComponent;
    private VORs_briefLayout votesOfRoleLayout;
    //pre uzivatela obcan   
    private DownloaderBriefLayout<PublicRole> downoaderLayout;
    //pre uzivatela admin a dobrovolnik
    private DownAndUploaderBriefLayout<PublicRole> uploaderLayout;

    //konstruktor:
    /**
     *
     */
    public V5_PublicRoleView() {
        
        super("Verejná Funkcia");
        this.publicRoleService = new PublicRoleServiceImpl();
        this.voteService = new VoteServiceImpl();
        this.userService = new UserServiceImpl();
        
    }

    /**
     */
    private void initAllBasic(Boolean isVolunteer) {

        this.removeAllComponents();

        this.initPublicPersonComponent();
        this.initVotesOfRoleLayout();
        this.initTimeline();

        this.addComponents(publicRoleComponent, votesOfRoleLayout, timeline);

        if (isVolunteer) {
            this.initEditPublicRoleButton();
            this.initUploadLayout();
            
        } else {
            this.initDownloadLayout();
        }
    }

    /**
     */
    private void setPublicRoleValue(PublicRole pr) {

        this.publicRole = pr;

    }

    /**
     */
    private void initPublicPersonComponent() {

        this.publicRoleComponent = new PUR_briefPanContent(publicRole, publicRoleService);

    }

    /**
     *
     */
    private void initVotesOfRoleLayout() {

        List<Integer> vorIds = voteService.findVoteOfRoleIdsByPubRoleId(publicRole.getId());
        List<VoteOfRole> votesOfRole = voteService.findNewVotesOfRole(vorIds);
        this.votesOfRoleLayout = new VORs_briefLayout(votesOfRole, voteService);

    }

    /**
     * Timeline inicializacia.
     */
    private void initTimeline() {
        
        List<Integer> ids = voteService.findVoteIdsByPubRoleId(this.publicRole.getId());
        timeline = new MyTimeline(ids);

    }

    /**
     * Inicializuje editovatelny layout s dokumentami prisluchajucimi entite
     * PublicBody.
     */
    private void initUploadLayout() {

        this.uploaderLayout = new DownAndUploaderBriefLayout<>(PublicRole.class, this.publicRole);
        this.addComponent(uploaderLayout);

    }
    
    /**
     * 
     */
    private void initEditPublicRoleButton() {
        
        Button editPubRoleBt;
        FunctionalEditWrapper<PublicRole> ew = new FunctionalEditWrapper<>(PublicRole.class, publicRole);
        editPubRoleBt = EditEntityButtonFactory.createMyEditButton(ew);
        this.addComponent(editPubRoleBt);
    }


    /**
     * Komponenta na zobrazovanie dokumentov prisluchajucich entite PublicBody.
     */
    private void initDownloadLayout() {

        this.downoaderLayout = new DownloaderBriefLayout<>(PublicRole.class, this.publicRole);
        this.addComponent(downoaderLayout);

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        PublicRole pr = VaadinSession.getCurrent().getAttribute(PublicRole.class);

        A_User user = VaadinSession.getCurrent().getAttribute(A_User.class);

        Boolean isVolunteer = Boolean.FALSE;
        if (user != null) {
            UserType utype = userService.getUserType(user);
            //moze byt dobrovolnik, alebo admin.
            isVolunteer = ((UserType.VOLUNTEER).equals(utype) || (UserType.ADMIN).equals(utype));
        }

        if (pr != null) {
            setPublicRoleValue(pr);
            initAllBasic(isVolunteer);
        } else {
            UI.getCurrent().getNavigator().navigateTo("V4s_PublicPersonsView");
        }

    }

}
