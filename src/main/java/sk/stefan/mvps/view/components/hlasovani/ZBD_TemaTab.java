//package sk.stefan.mvps.view.components.hlasovani;
//
//import com.vaadin.annotations.DesignRoot;
//import com.vaadin.spring.annotation.SpringComponent;
//import com.vaadin.ui.VerticalLayout;
//import com.vaadin.ui.declarative.Design;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Scope;
//import sk.stefan.annotations.ViewTab;
//import sk.stefan.interfaces.TabEntity;
//import sk.stefan.listeners.RemoveListener;
//import sk.stefan.listeners.SaveListener;
////import sk.stefan.mvps.model.entity.Theme;
//import sk.stefan.mvps.model.service.LinkService;
//import sk.stefan.mvps.model.service.SecurityService;
//import sk.stefan.mvps.model.service.VoteService;
//import sk.stefan.mvps.view.tabs.TabComponent;
//
///**
// * Záložka pro zobrazení tématu hlasování.
// * @author Lukas on 20.02.2016.
// */
//@ViewTab("tema")
//@SpringComponent
//@Scope("prototype")
//@DesignRoot
//public class TemaTab extends VerticalLayout implements TabComponent {
//
//    @Autowired
//    private VoteService voteService;
//
//    @Autowired
//    private LinkService linkService;
//
//    @Autowired
//    private SecurityService securityService;
//
//    private TemaPanel temaPanel;
//
//    private SeznamHlasovaniTemaPanel seznamHlasovaniTemaPanel;
//
//    //data
////    private Theme tema;
//
//    public TemaTab() {
//        Design.read(this);
//    }
//
//    @Override
//    public void setSaveListener(SaveListener<TabEntity> saveListener) {
//        temaPanel.setSaveListener(l -> saveListener.accept(tema));
//    }
//
//    @Override
//    public void setRemoveListener(RemoveListener<TabEntity> removeListener) {
//        temaPanel.setRemoveListener(l -> removeListener.accept(tema));
//    }
//
//    @Override
//    public void setEntity(TabEntity tabEntity) {
//        this.tema = (Theme) tabEntity;
//        temaPanel.setTheme(tema);
//        seznamHlasovaniTemaPanel.setTema(tema);
//    }
//
//    @Override
//    public TabEntity getEntity() {
//        return tema;
//    }
//
//    @Override
//    public void show() {
//            tema = voteService.findThemaById(tema.getId());
//            setEntity(tema);
//    }
//
//    @Override
//    public String getTabCaption() {
//        return tema.getPresentationName();
//    }
//
//    @Override
//    public String getTabId() {
//        return "tema" + tema.getId();
//    }
//}
