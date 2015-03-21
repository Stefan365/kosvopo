package sk.stefan.ui;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import javax.servlet.annotation.WebServlet;
import sk.stefan.MVP.model.entity.dao.*;
import sk.stefan.MVP.model.entity.dao.PersonClassification;
import sk.stefan.MVP.model.entity.dao.PublicPerson;
import sk.stefan.MVP.model.entity.dao.PublicRole;
import sk.stefan.MVP.model.entity.dao.A_Role;
import sk.stefan.MVP.model.entity.dao.Subject;
import sk.stefan.MVP.model.entity.dao.Tenure;
import sk.stefan.MVP.model.entity.dao.Theme;
import sk.stefan.MVP.model.entity.dao.A_User;
import sk.stefan.MVP.model.entity.dao.A_UserRole;
import sk.stefan.MVP.model.entity.dao.Vote;
import sk.stefan.MVP.model.entity.dao.VoteClassification;
import sk.stefan.MVP.model.entity.dao.VoteOfRole;
import sk.stefan.MVP.view.AddressbookView;
import sk.stefan.MVP.view.AdministrationView;
import sk.stefan.MVP.view.FilaManagerView;
import sk.stefan.MVP.view.HomoView;
import sk.stefan.MVP.view.InputAllView;
import sk.stefan.MVP.view.Kos2View;
import sk.stefan.MVP.view.Kos3View;
import sk.stefan.MVP.view.LoginView;
import sk.stefan.MVP.view.UniEditableTableView;
import sk.stefan.MVP.view.VotingView;
import sk.stefan.MVP.view.VstupniView;
import sk.stefan.MVP.view.components.NavigationComponent;
import sk.stefan.enums.NonEditableFields;
import sk.stefan.utils.Tools;

@com.vaadin.annotations.Theme("mytheme")
//@com.vaadin.annotations.Theme("mytheme1")
@SuppressWarnings("serial")
public class KosvopoUI extends UI {

    /**
     * @return the navigator
     */
    @Override
    public Navigator getNavigator() {
        return navigator;
    }

    /**
     *
     */
    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = KosvopoUI.class)
    public static class Servlet extends VaadinServlet {
    }

    private Navigator navigator;

    @Override
    protected void init(VaadinRequest request) {

        navigator = new Navigator(this, this);
        NavigationComponent.createNavComp(navigator);

        navigator.addView("login", new LoginView(navigator));
        navigator.addView("vstupny", new VstupniView());
        navigator.addView("homo", new HomoView());

        navigator.addView(Tools.decapit(A_User.TN),
                new UniEditableTableView<>(A_User.class, NonEditableFields.A_USER.getNonEditableParams()));
        navigator.addView(Tools.decapit(Okres.TN),
                new UniEditableTableView<>(Okres.class, NonEditableFields.OKRES.getNonEditableParams()));
        navigator.addView(Tools.decapit(Kraj.TN),
                new UniEditableTableView<>(Kraj.class, NonEditableFields.KRAJ.getNonEditableParams()));
        navigator.addView(Tools.decapit(Location.TN),
                new UniEditableTableView<>(Location.class, NonEditableFields.LOCATION.getNonEditableParams()));
        navigator.addView(Tools.decapit(PersonClassification.TN),
                new UniEditableTableView<>(PersonClassification.class, NonEditableFields.PERSON_CLASS.getNonEditableParams()));
        navigator.addView(Tools.decapit(PublicBody.TN),
                new UniEditableTableView<>(PublicBody.class, NonEditableFields.PUBLIC_BODY.getNonEditableParams()));
        navigator.addView(Tools.decapit(PublicPerson.TN),
                new UniEditableTableView<>(PublicPerson.class, NonEditableFields.PUBLIC_PERSON.getNonEditableParams()));
        navigator.addView(Tools.decapit(PublicRole.TN),
                new UniEditableTableView<>(PublicRole.class, NonEditableFields.PUBLIC_ROLE.getNonEditableParams()));
        navigator.addView(Tools.decapit(A_Role.TN),
                new UniEditableTableView<>(A_Role.class, NonEditableFields.A_ROLE.getNonEditableParams()));
        navigator.addView(Tools.decapit(Subject.TN),
                new UniEditableTableView<>(Subject.class, NonEditableFields.SUBJECT.getNonEditableParams()));
        navigator.addView(Tools.decapit(Tenure.TN),
                new UniEditableTableView<>(Tenure.class, NonEditableFields.TENURE.getNonEditableParams()));
        navigator.addView(Tools.decapit(Theme.TN),
                new UniEditableTableView<>(Theme.class, NonEditableFields.THEME.getNonEditableParams()));
        navigator.addView(Tools.decapit(A_UserRole.TN),
                new UniEditableTableView<>(A_UserRole.class, NonEditableFields.A_USER_ROLE.getNonEditableParams()));
        navigator.addView(Tools.decapit(Vote.getTN()),
                new UniEditableTableView<>(Vote.class, NonEditableFields.VOTE.getNonEditableParams()));
        navigator.addView(Tools.decapit(VoteClassification.TN),
                new UniEditableTableView<>(VoteClassification.class, NonEditableFields.VOTE_CLASS.getNonEditableParams()));
        navigator.addView(Tools.decapit(VoteOfRole.TN),
                new UniEditableTableView<>(VoteOfRole.class, NonEditableFields.VOTE_OF_ROLE.getNonEditableParams()));

        navigator.addView("adminview1", new AdministrationView(navigator));
        navigator.addView("hlasovanie", new VotingView(navigator));
        
        navigator.addView("addressbook", new AddressbookView());
        navigator.addView("filamanager", new FilaManagerView());
        navigator.addView("kos2", new Kos2View());
        navigator.addView("kos3", new Kos3View());
        navigator.addView("A_inputAll", new InputAllView(navigator));
        
        
        //pociatocna navigacia:
        navigator.navigateTo("vstupny");

    }

}
