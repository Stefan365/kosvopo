//package sk.stefan.mvps.view.components.hlasovani;
//
//import com.vaadin.annotations.DesignRoot;
//import com.vaadin.ui.VerticalLayout;
//import com.vaadin.ui.declarative.Design;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Component;
//import sk.stefan.annotations.ViewTab;
//import sk.stefan.enums.UserType;
//import sk.stefan.mvps.model.entity.Theme;
//import sk.stefan.mvps.model.service.SecurityService;
//import sk.stefan.mvps.view.tabs.TabComponent;
//
///**
// * Záložka s formulářem pro vytvoření nového tématu.
// * @author Lukas on 20.02.2016.
// */
//@Component
//@Scope("prototype")
//@ViewTab("noveTema")
//@DesignRoot
//public class NewTemaForm extends VerticalLayout implements TabComponent {
//
//    @Autowired
//    private SecurityService securityService;
//
//    //Design
//    private TemaPanel temaPanel;
//
//    public NewTemaForm() {
//        Design.read(this);
//    }
//
//    @Override
//    public void show() {
//        temaPanel.setTheme(new Theme());
//    }
//
//    @Override
//    public String getTabCaption() {
//        return "Nové téma";
//    }
//
//    @Override
//    public String getTabId() {
//        return "noveTema";
//    }
//
//    @Override
//    public boolean isUserAccessGranted() {
//        return securityService.currentUserHasRole(UserType.ADMIN) || securityService.currentUserHasRole(UserType.VOLUNTEER);
//
//    }
//}
