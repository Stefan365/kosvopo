package sk.stefan.ui;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import javax.servlet.annotation.WebServlet;
import sk.stefan.MVP.model.entity.dao.PersonClassification;
import sk.stefan.MVP.model.entity.dao.PublicPerson;
import sk.stefan.MVP.model.entity.dao.PublicRole;
import sk.stefan.MVP.model.entity.dao.A_Role;
import sk.stefan.MVP.model.entity.dao.Subject;
import sk.stefan.MVP.model.entity.dao.Tenure;
import sk.stefan.MVP.model.entity.dao.Theme;
import sk.stefan.MVP.model.entity.dao.A_User;
import sk.stefan.MVP.model.entity.dao.A_UserRole;
import sk.stefan.MVP.model.entity.dao.District;
import sk.stefan.MVP.model.entity.dao.Location;
import sk.stefan.MVP.model.entity.dao.PublicBody;
import sk.stefan.MVP.model.entity.dao.Region;
import sk.stefan.MVP.model.entity.dao.Vote;
import sk.stefan.MVP.model.entity.dao.VoteClassification;
import sk.stefan.MVP.model.entity.dao.VoteOfRole;
import sk.stefan.MVP.view.V10_ThemeView;
import sk.stefan.MVP.view.V10s_ThemesView;
import sk.stefan.MVP.view.V1_LoginView;
import sk.stefan.MVP.view.V8_UniEditableTableView;
import sk.stefan.MVP.view.V2_EnterView;
import sk.stefan.MVP.view.components.NavigationComponent;
import sk.stefan.MVP.view.V3_PublicBodyView;
import sk.stefan.MVP.view.V3s_PublicBodiesView;
import sk.stefan.MVP.view.V4_PublicPersonView;
import sk.stefan.MVP.view.V4s_PublicPersonsView;
import sk.stefan.MVP.view.V5_PublicRoleView;
import sk.stefan.MVP.view.V6_VoteView;
import sk.stefan.MVP.view.V6s_VotesView;
import sk.stefan.MVP.view.V9_SubjectView;
import sk.stefan.MVP.view.V9s_SubjectsView;
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
         
        navigator = new Navigator(this.getUI(), this);
//        NavigationComponent.createNavComp();

        //zakladne views:
        navigator.addView("V1_LoginView", new V1_LoginView());
        navigator.addView("V2_EnterView", new V2_EnterView());
        navigator.addView("V3_PublicBodyView", new V3_PublicBodyView());
        navigator.addView("V3s_PublicBodiesView", new V3s_PublicBodiesView());
        navigator.addView("V4_PublicPersonView", new V4_PublicPersonView());
        navigator.addView("V4s_PublicPersonsView", new V4s_PublicPersonsView());
        navigator.addView("V5_PublicRoleView", new V5_PublicRoleView());
        navigator.addView("V6_VoteView", new V6_VoteView());
        navigator.addView("V6s_VotesView", new V6s_VotesView());
        navigator.addView("V9_SubjectView", new V9_SubjectView());
        navigator.addView("V9s_SubjectsView", new V9s_SubjectsView());
        navigator.addView("V10_ThemeView", new V10_ThemeView());
        navigator.addView("V10s_ThemesView", new V10s_ThemesView());
        
        
        
        //views zodpovedajuce univerzalnym editacnym tabulkam:
        navigator.addView(ToolsNazvy.decapit(A_User.TN),
                new V8_UniEditableTableView<>(A_User.class, NonEditableFields.A_USER.getNonEditableParams()));
        navigator.addView(ToolsNazvy.decapit(District.TN),
                new V8_UniEditableTableView<>(District.class, NonEditableFields.DISTRICT.getNonEditableParams()));
        navigator.addView(ToolsNazvy.decapit(Region.TN),
                new V8_UniEditableTableView<>(Region.class, NonEditableFields.REGION.getNonEditableParams()));
        navigator.addView(ToolsNazvy.decapit(Location.TN),
                new V8_UniEditableTableView<>(Location.class, NonEditableFields.LOCATION.getNonEditableParams()));
        navigator.addView(ToolsNazvy.decapit(PersonClassification.TN),
                new V8_UniEditableTableView<>(PersonClassification.class, NonEditableFields.PERSON_CLASS.getNonEditableParams()));
        navigator.addView(ToolsNazvy.decapit(PublicBody.TN),
                new V8_UniEditableTableView<>(PublicBody.class, NonEditableFields.PUBLIC_BODY.getNonEditableParams()));
        navigator.addView(ToolsNazvy.decapit(PublicPerson.TN),
                new V8_UniEditableTableView<>(PublicPerson.class, NonEditableFields.PUBLIC_PERSON.getNonEditableParams()));
        navigator.addView(ToolsNazvy.decapit(PublicRole.TN),
                new V8_UniEditableTableView<>(PublicRole.class, NonEditableFields.PUBLIC_ROLE.getNonEditableParams()));
        navigator.addView(ToolsNazvy.decapit(A_Role.TN),
                new V8_UniEditableTableView<>(A_Role.class, NonEditableFields.A_ROLE.getNonEditableParams()));
        navigator.addView(ToolsNazvy.decapit(Subject.TN),
                new V8_UniEditableTableView<>(Subject.class, NonEditableFields.SUBJECT.getNonEditableParams()));
        navigator.addView(ToolsNazvy.decapit(Tenure.TN),
                new V8_UniEditableTableView<>(Tenure.class, NonEditableFields.TENURE.getNonEditableParams()));
        navigator.addView(ToolsNazvy.decapit(Theme.TN),
                new V8_UniEditableTableView<>(Theme.class, NonEditableFields.THEME.getNonEditableParams()));
        navigator.addView(ToolsNazvy.decapit(A_UserRole.TN),
                new V8_UniEditableTableView<>(A_UserRole.class, NonEditableFields.A_USER_ROLE.getNonEditableParams()));
        navigator.addView(ToolsNazvy.decapit(Vote.getTN()),
                new V8_UniEditableTableView<>(Vote.class, NonEditableFields.VOTE.getNonEditableParams()));
        navigator.addView(ToolsNazvy.decapit(VoteClassification.TN),
                new V8_UniEditableTableView<>(VoteClassification.class, NonEditableFields.VOTE_CLASS.getNonEditableParams()));
        navigator.addView(ToolsNazvy.decapit(VoteOfRole.TN),
                new V8_UniEditableTableView<>(VoteOfRole.class, NonEditableFields.VOTE_OF_ROLE.getNonEditableParams()));

        
        //pociatocna navigacia:
        navigator.navigateTo("V2_EnterView");

    }

}
