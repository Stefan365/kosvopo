//package sk.stefan.mvps.view.components.hlasovani;
//
//import com.vaadin.annotations.DesignRoot;
//import com.vaadin.data.util.BeanItemContainer;
//import com.vaadin.server.Page;
//import com.vaadin.shared.ui.grid.HeightMode;
//import com.vaadin.ui.Grid;
//import com.vaadin.ui.Panel;
//import com.vaadin.ui.TextField;
//import com.vaadin.ui.declarative.Design;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Component;
//import sk.stefan.interfaces.TabEntity;
//import sk.stefan.mvps.model.entity.Theme;
//import sk.stefan.mvps.model.entity.Vote;
//import sk.stefan.mvps.model.service.LinkService;
//import sk.stefan.mvps.model.service.VoteService;
//import sk.stefan.utils.Localizator;
//
///**
// * Panel se seznamem hlasování k tématu.
// * @author Lukas on 21.02.2016.
// */
//@Component
//@Scope("prototype")
//@DesignRoot
//public class SeznamHlasovaniTemaPanel extends Panel {
//
//    @Autowired
//    private VoteService voteService;
//
//    @Autowired
//    private LinkService linkService;
//
//    //Design
//    private TextField searchFd;
//    private Grid grid;
//
//    //data
//    private BeanItemContainer<Vote> container;
//
//    public SeznamHlasovaniTemaPanel() {
//        Design.read(this);
//        Localizator.localizeDesign(this);
//
//        container = new BeanItemContainer<>(Vote.class);
//
//        grid.setContainerDataSource(container);
//        grid.setHeightMode(HeightMode.ROW);
//        grid.addSelectionListener(event -> Page.getCurrent()
//                .open(linkService.getUriFragmentForEntity((TabEntity) grid.getSelectedRow()), null));
//    }
//
//    public void setTema(Theme tema) {
//        container.removeAllItems();
//        container.addAll(voteService.findAllVotesForTabEntity(tema));
//        grid.setHeightByRows(container.size() >= 6 ? 6 : container.size() + 1);
//    }
//}
