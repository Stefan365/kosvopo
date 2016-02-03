/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.view.components.verejnaRole;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import sk.stefan.annotations.ViewTab;
import sk.stefan.interfaces.TabEntity;
import sk.stefan.listeners.RemoveListener;
import sk.stefan.listeners.SaveListener;
import sk.stefan.mvps.model.entity.PublicRole;
import sk.stefan.mvps.model.service.PublicRoleService;
import sk.stefan.mvps.model.service.UserService;
import sk.stefan.mvps.model.service.VoteService;
import sk.stefan.mvps.model.serviceImpl.PublicRoleServiceImpl;
import sk.stefan.mvps.model.serviceImpl.UserServiceImpl;
import sk.stefan.mvps.model.serviceImpl.VoteServiceImpl;
import sk.stefan.mvps.view.components.DokumentyPanel;
import sk.stefan.mvps.view.components.TimelinePanel;
import sk.stefan.mvps.view.tabs.TabComponent;

/**
 * View zobrazujúci roľu danej verejnej osoby.
 *
 * @author stefan
 */
@SpringComponent
@Scope("prototype")
@ViewTab("verejnaRole")
@DesignRoot
public final class V5_PublicRoleView extends VerticalLayout implements TabComponent {

    private static final Logger log = Logger.getLogger(V5_PublicRoleView.class);

    private static final long serialVersionUID = 1L;

    // Design
    private DetailVerejneFunkcePanel detailPanel;
    private DokumentyPanel dokumentyPanel;
    private HlasovaniFunkcePanel hlasovaniFunkcePanel;
    private TimelinePanel timelinePanel;

    //data
    private PublicRole publicRole;

//    servisy:
    private final PublicRoleService publicRoleService;
    private final VoteService voteService;
    private final UserService userService;
    
//    KOMPONENTY:
//    private MyDetailedPanel<PUR_detPanContent> publicRoleDetPanel;
//    private VORs_briefLayout votesOfRoleBriefLy;
//    private MyTimelinePanel timelinePanel;
//    pre uzivatela obcan:
//    private DownloaderBriefLayout<PublicRole> downoaderLayout;
//    pre uzivatela admin a dobrovolnik:
//    private DownAndUploaderBriefLayout<PublicRole> uploaderLayout;

    //konstruktor:
    /**
     *
     */
    public V5_PublicRoleView() {
        Design.read(this);

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

//        this.addComponents(publicRoleDetPanel, votesOfRoleBriefLy, timelinePanel);

        if (isVolunteer) {
//            this.initEditPublicRoleButton();
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

//        PUR_detPanContent purCont = new PUR_detPanContent(publicRole, publicRoleService);
//        this.publicRoleDetPanel = new MyDetailedPanel<>(purCont);

    }

    /**
     *
     */
    private void initVotesOfRoleLayout() {
//
//        List<Integer> vorIds = voteService.findVoteOfRoleIdsByPubRoleId(publicRole.getId());
//        List<VoteOfRole> votesOfRole = voteService.findNewVotesOfRole(vorIds);
//        this.votesOfRoleBriefLy = new VORs_briefLayout(votesOfRole, voteService);

    }

    /**
     * Timeline inicializacia.
     */
    private void initTimeline() {
//
//        List<Integer> ids = voteService.findVoteIdsByPubRoleId(this.publicRole.getId());
//        MyTimeline tl = new MyTimeline(ids);
//        timelinePanel = new MyTimelinePanel(tl);
        

    }

    /**
     * Inicializuje editovatelny layout s dokumentami prisluchajucimi entite
     * PublicBody.
     */
    private void initUploadLayout() {
//
//        this.uploaderLayout = new DownAndUploaderBriefLayout<>(PublicRole.class, this.publicRole);
//        this.addComponent(uploaderLayout);

    }
    
//    /**
//     *
//     */
//    private void initEditPublicRoleButton() {
//
//        Button editPubRoleBt;
//        FunctionalEditWrapper<PublicRole> ew = new FunctionalEditWrapper<>(PublicRole.class, publicRole);
//        editPubRoleBt = EditEntityButtonFactory.createMyEditButton(ew);
//        this.addComponent(editPubRoleBt);
//    }


    /**
     * Komponenta na zobrazovanie dokumentov prisluchajucich entite PublicBody.
     */
    private void initDownloadLayout() {
//
//        this.downoaderLayout = new DownloaderBriefLayout<>(PublicRole.class, this.publicRole);
//        this.addComponent(downoaderLayout);

    }

//    @Override
//    public void enter(ViewChangeListener.ViewChangeEvent event) {
//
//        PublicRole pr = VaadinSession.getCurrent().getAttribute(PublicRole.class);
//
//        A_User user = VaadinSession.getCurrent().getAttribute(A_User.class);
//
//        Boolean isVolunteer = Boolean.FALSE;
//        if (user != null) {
//            UserType utype = userService.getUserType(user);
//            //moze byt dobrovolnik, alebo admin.
//            isVolunteer = ((UserType.VOLUNTEER).equals(utype) || (UserType.ADMIN).equals(utype));
//        }
//
//        if (pr != null) {
//            setPublicRoleValue(pr);
//            initAllBasic(isVolunteer);
//        } else {
//            UI.getCurrent().getNavigator().navigateTo("V4s_PublicPersonsView");
//        }
//
//    }

    @Override
    public void setSaveListener(SaveListener<TabEntity> saveListener) {
        detailPanel.setSaveListener(new SaveListener<PublicRole>() {
            @Override
            public void accept(Object l) {
                saveListener.save(publicRole);
            }
        });
    }

//    @Override
//    public void setRemoveListener(RemoveListener<TabEntity> removeListener) {
//        detailPanel.setRemoveListener(l -> removeListener.remove(publicRole));
//    }
    @Override
    public void setRemoveListener(RemoveListener<TabEntity> removeListener) {
        detailPanel.setRemoveListener(new RemoveListener<PublicRole>() {
            @Override
            public void accept(Object l) {
                removeListener.remove(publicRole);
            }
        });
    }

    public void setEntity(TabEntity entity) {
        this.publicRole = (PublicRole) entity;
        detailPanel.setPublicRole(publicRole);
        hlasovaniFunkcePanel.setPublicRole(publicRole);
        timelinePanel.setRelatedEntity(publicRole);
        timelinePanel.setVotes(voteService.getAllVotesForPublicRole(publicRole));
        dokumentyPanel.setEntity(publicRole);
    }

    @Override
    public String getTabCaption() {
        return publicRole.getPresentationName();
    }

    @Override
    public void show() {

    }

    @Override
    public String getTabId() {
        return "verejnaRole" + publicRole.getId();
    }

    @Override
    public TabEntity getEntity() { return publicRole; }
}
