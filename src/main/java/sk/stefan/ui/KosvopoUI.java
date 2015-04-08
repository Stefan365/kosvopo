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
import sk.stefan.MVP.view.AdministrationView;
import sk.stefan.MVP.view.DocumentView;
import sk.stefan.MVP.view.superseded.FilaManagerView;
import sk.stefan.MVP.view.superseded.HomoView;
import sk.stefan.MVP.view.InputAllView;
import sk.stefan.MVP.view.superseded.Kos2View;
import sk.stefan.MVP.view.superseded.Kos3View;
import sk.stefan.MVP.view.LoginView;
import sk.stefan.MVP.view.UniEditableTableView;
import sk.stefan.MVP.view.EnterView;
import sk.stefan.MVP.view.components.NavigationComponent;
import sk.stefan.MVP.view.DocumentSkusView;
import sk.stefan.MVP.view.TimeLineView;
import sk.stefan.enums.NonEditableFields;
import sk.stefan.utils.ToolsNazvy;

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
    @VaadinServletConfiguration(productionMode = false, ui = KosvopoUI.class, 
            widgetset="sk.stefan.ui.AppWidgetSet")

    public static class Servlet extends VaadinServlet {
    }

    private Navigator navigator;

    @Override
    protected void init(VaadinRequest request) {
        
//        SpringContextHelper helper = 
//                new SpringContextHelper(VaadinServlet.getCurrent().getServletContext());
//         final MyBeanInterface bean = (MyBeanInterface) helper.getBean("myBean");
         
        
        
        navigator = new Navigator(this, this);
        NavigationComponent.createNavComp(navigator);

        navigator.addView("login", new LoginView(navigator));
        navigator.addView("vstupny", new EnterView());
        navigator.addView("homo", new HomoView());

        navigator.addView(ToolsNazvy.decapit(A_User.TN),
                new UniEditableTableView<>(A_User.class, NonEditableFields.A_USER.getNonEditableParams()));
        navigator.addView(ToolsNazvy.decapit(District.TN),
                new UniEditableTableView<>(District.class, NonEditableFields.DISTRICT.getNonEditableParams()));
        navigator.addView(ToolsNazvy.decapit(Region.TN),
                new UniEditableTableView<>(Region.class, NonEditableFields.REGION.getNonEditableParams()));
        navigator.addView(ToolsNazvy.decapit(Location.TN),
                new UniEditableTableView<>(Location.class, NonEditableFields.LOCATION.getNonEditableParams()));
        navigator.addView(ToolsNazvy.decapit(PersonClassification.TN),
                new UniEditableTableView<>(PersonClassification.class, NonEditableFields.PERSON_CLASS.getNonEditableParams()));
        navigator.addView(ToolsNazvy.decapit(PublicBody.TN),
                new UniEditableTableView<>(PublicBody.class, NonEditableFields.PUBLIC_BODY.getNonEditableParams()));
        navigator.addView(ToolsNazvy.decapit(PublicPerson.TN),
                new UniEditableTableView<>(PublicPerson.class, NonEditableFields.PUBLIC_PERSON.getNonEditableParams()));
        navigator.addView(ToolsNazvy.decapit(PublicRole.TN),
                new UniEditableTableView<>(PublicRole.class, NonEditableFields.PUBLIC_ROLE.getNonEditableParams()));
        navigator.addView(ToolsNazvy.decapit(A_Role.TN),
                new UniEditableTableView<>(A_Role.class, NonEditableFields.A_ROLE.getNonEditableParams()));
        navigator.addView(ToolsNazvy.decapit(Subject.TN),
                new UniEditableTableView<>(Subject.class, NonEditableFields.SUBJECT.getNonEditableParams()));
        navigator.addView(ToolsNazvy.decapit(Tenure.TN),
                new UniEditableTableView<>(Tenure.class, NonEditableFields.TENURE.getNonEditableParams()));
        navigator.addView(ToolsNazvy.decapit(Theme.TN),
                new UniEditableTableView<>(Theme.class, NonEditableFields.THEME.getNonEditableParams()));
        navigator.addView(ToolsNazvy.decapit(A_UserRole.TN),
                new UniEditableTableView<>(A_UserRole.class, NonEditableFields.A_USER_ROLE.getNonEditableParams()));
        navigator.addView(ToolsNazvy.decapit(Vote.getTN()),
                new UniEditableTableView<>(Vote.class, NonEditableFields.VOTE.getNonEditableParams()));
        navigator.addView(ToolsNazvy.decapit(VoteClassification.TN),
                new UniEditableTableView<>(VoteClassification.class, NonEditableFields.VOTE_CLASS.getNonEditableParams()));
        navigator.addView(ToolsNazvy.decapit(VoteOfRole.TN),
                new UniEditableTableView<>(VoteOfRole.class, NonEditableFields.VOTE_OF_ROLE.getNonEditableParams()));

        navigator.addView("adminview1", new AdministrationView(navigator));
        navigator.addView("docview", new DocumentView());
        navigator.addView("docskview", new DocumentSkusView());        
        navigator.addView("timeline", new TimeLineView());        
        
        
//        navigator.addView("hlasovanie", new VotingLayout());
        
        navigator.addView("filamanager", new FilaManagerView());
        navigator.addView("kos2", new Kos2View());
        navigator.addView("kos3", new Kos3View());
        navigator.addView("A_inputAll", new InputAllView(navigator));
        
        
        //pociatocna navigacia:
        navigator.navigateTo("vstupny");

    }

}
