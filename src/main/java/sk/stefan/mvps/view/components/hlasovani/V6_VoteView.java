/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.view.components.hlasovani;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import sk.stefan.annotations.ViewTab;
import sk.stefan.enums.UserType;
import sk.stefan.factories.EditEntityButtonFactory;
import sk.stefan.factories.InputNewEntityButtonFactory;
import sk.stefan.interfaces.TabEntity;
import sk.stefan.mvps.model.entity.A_User;
import sk.stefan.mvps.model.entity.PublicRole;
import sk.stefan.mvps.model.entity.Subject;
import sk.stefan.mvps.model.entity.Theme;
import sk.stefan.mvps.model.entity.Vote;
import sk.stefan.mvps.model.entity.VoteClassification;
import sk.stefan.mvps.model.service.ClassificationService;
import sk.stefan.mvps.model.service.UserService;
import sk.stefan.mvps.model.service.VoteService;
import sk.stefan.mvps.model.serviceImpl.ClassificationServiceImpl;
import sk.stefan.mvps.model.serviceImpl.UserServiceImpl;
import sk.stefan.mvps.model.serviceImpl.VoteServiceImpl;
import sk.stefan.mvps.view.components.DokumentyPanel;
import sk.stefan.mvps.view.components.layouts.DownAndUploaderBriefLayout;
import sk.stefan.mvps.view.components.layouts.DownloaderBriefLayout;
import sk.stefan.mvps.view.components.layouts.MyViewLayout;
import sk.stefan.mvps.view.components.panContents.SUB_briefPanContent;
import sk.stefan.mvps.view.components.panContents.THE_briefPanContent;
import sk.stefan.mvps.view.components.panContents.VCL_detPanContent;
import sk.stefan.mvps.view.components.panContents.VOT_detPanContent;
import sk.stefan.mvps.view.components.panels.MyBriefPanel;
import sk.stefan.mvps.view.components.panels.MyDetailedPanel;
import sk.stefan.mvps.view.tabs.TabComponent;
import sk.stefan.wrappers.FunctionalEditWrapper;

import javax.xml.soap.Detail;


/**
 *
 * @author stefan
 */
@ViewTab("hlasovani")
@SpringComponent
@Scope("prototype")
@DesignRoot
public final class V6_VoteView extends VerticalLayout implements TabComponent {

    @Autowired
    private VoteService voteService;

    @Autowired
    private UserService userService;

    // Design
    private DetailHlasovaniPanel detailPanel;
    private GrafHlasovaniPanel grafHlasovaniPanel;
    private DokumentyPanel dokumentyPanel;
    private HlasovaniPanel hlasovaniPanel;

    // data
    private Vote vote;
    
    //konstruktor:
    public V6_VoteView() {
        Design.read(this);

//        this.classificationService = new ClassificationServiceImpl();
        
        
    }

    @Override
    public void setEntity(TabEntity tabEntity) {
        this.vote = (Vote) tabEntity;
        detailPanel.setVote(vote);
    }

    @Override
    public String getTabCaption() {
        return vote.getPresentationName();
    }

    @Override
    public void show() {
        vote = voteService.findOne(vote.getId());
        setEntity(vote);
    }

    @Override
    public String getTabId() {
        return "hlasovani" + vote.getId();
    }

//    /**
//     */
//    private void initAllBasic(Boolean isVolunteer) {
//
//        this.removeAllComponents();
//
//        initVoteDetailedPanel();
//        this.initSubjectComponent();
//        initThemeComponent();
//        this.initVoteClassComponent();
//
//        this.addComponents(voteDetailedPanel, subjectBriefPanel, themeBriefPanel,
//                voteClassDetPanel);
//
//        if (isVolunteer) {
//
//            this.initNewPublicRoleButton();
//            this.initEditVoteButton();
//            this.initUploadLayout();
//
//        } else {
//            this.initDownloadLayout();
//        }

//    }

//    private void setVoteValue(Vote vot) {
//
//        this.vote = vot;
//        this.subject = voteService.findSubjectById(vote.getSubject_id());
//        this.theme = voteService.findThemeBySubjectId(vote.getSubject_id());
//        this.voteClassification = classificationService.findVoteClassByVoteId(vote.getId());
//
//    }

//    private void initVoteDetailedPanel() {
//
//        VOT_detPanContent votCont = new VOT_detPanContent(vote, voteService);
//        this.voteDetailedPanel = new MyDetailedPanel<>(votCont);
//
//    }

//    /**
//     */
//    private void initSubjectComponent() {
//
//        SUB_briefPanContent subCont = new SUB_briefPanContent(subject, voteService);
//        this.subjectBriefPanel = new MyBriefPanel<>(subCont);
//
//    }
    
//    /**
//     */
//    private void initThemeComponent() {
//
//        THE_briefPanContent theCont = new THE_briefPanContent(theme, voteService);
//        this.themeBriefPanel = new MyBriefPanel<>(theCont);
//
//    }

//    /**
//     */
//    private void initVoteClassComponent() {
//
//        VCL_detPanContent vclCont = new VCL_detPanContent(voteClassification, classificationService);
//        this.voteClassDetPanel = new MyDetailedPanel<>(vclCont);
//
//    }


//    /**
//     * Prida tlacitko na pridavanie novej entity PublicBody.
//     */
//    private void initNewPublicRoleButton() {
//
//        this.editVoteBt = InputNewEntityButtonFactory.createMyInputButton(PublicRole.class);
//        this.addComponent(editVoteBt);
//
//    }

//    /**
//     * Inicializuje editovatelny layout s dokumentami prisluchajucimi entite
//     * PublicBody
//     */
//    private void initUploadLayout() {
//
//        this.uploaderLayout = new DownAndUploaderBriefLayout<>(Vote.class, this.vote);
//        this.addComponent(uploaderLayout);
//
//    }

//    /**
//     *
//     */
//    private void initEditVoteButton() {
//
//        FunctionalEditWrapper<Vote> ew = new FunctionalEditWrapper<>(Vote.class, vote);
//        editVoteBt = EditEntityButtonFactory.createMyEditButton(ew);
//        this.addComponent(editVoteBt);
//    }
//
//
//    /**
//     * Komponenta na zobrazovanie dokumentov prisluchajucich entite PublicBody.
//     */
//    private void initDownloadLayout() {
//
//        this.downoaderLayout = new DownloaderBriefLayout<>(Vote.class, this.vote);
//        this.addComponent(downoaderLayout);
//
//    }


//    @Override
//    public void enter(ViewChangeListener.ViewChangeEvent event) {
//
//        Vote vot = VaadinSession.getCurrent().getAttribute(Vote.class);
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
//        if (vot != null) {
//            this.setVoteValue(vot);
//            initAllBasic(isVolunteer);
//        } else {
//            UI.getCurrent().getNavigator().navigateTo("V6s_VotesView");
//        }
//    }

}
