package sk.stefan.mvps.view.components.hlasovani.subject;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sk.stefan.annotations.ViewTab;
import sk.stefan.enums.UserType;
import sk.stefan.mvps.model.entity.Subject;
import sk.stefan.mvps.model.service.SecurityService;
import sk.stefan.mvps.view.tabs.TabComponent;

/**
 * Záložka s formulářem pro vytvoření nového tématu.
 * @author Lukas on 20.02.2016.
 */
@Component
@Scope("prototype")
@ViewTab("newSubject")
@DesignRoot
public class NewSubjectForm extends VerticalLayout implements TabComponent {

    @Autowired
    private SecurityService securityService;

    //Design
    private SubjectPanel subjectPanel;

    public NewSubjectForm() {
        Design.read(this);
    }

    @Override
    public void show() {
        subjectPanel.setSubject(new Subject());
    }

    @Override
    public String getTabCaption() {
        return "Nový predmet";
    }

    @Override
    public String getTabId() {
        return "newSubject";
    }

    @Override
    public boolean isUserAccessGranted() {
        return securityService.currentUserHasRole(UserType.ADMIN) || securityService.currentUserHasRole(UserType.VOLUNTEER);

    }
}
