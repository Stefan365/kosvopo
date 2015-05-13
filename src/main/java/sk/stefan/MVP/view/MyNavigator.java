/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.UI;
import sk.stefan.MVP.model.entity.A_User;
import sk.stefan.MVP.model.entity.District;
import sk.stefan.MVP.model.entity.Location;
import sk.stefan.MVP.model.entity.PersonClassification;
import sk.stefan.MVP.model.entity.PublicBody;
import sk.stefan.MVP.model.entity.PublicPerson;
import sk.stefan.MVP.model.entity.PublicRole;
import sk.stefan.MVP.model.entity.Region;
import sk.stefan.MVP.model.entity.Subject;
import sk.stefan.MVP.model.entity.Tenure;
import sk.stefan.MVP.model.entity.Theme;
import sk.stefan.MVP.model.entity.Vote;
import sk.stefan.MVP.model.entity.VoteClassification;
import sk.stefan.MVP.model.entity.VoteOfRole;
import sk.stefan.enums.NonEditableFields;
import sk.stefan.utils.ToolsNames;

/**
 *
 * @author stefan
 */
public class MyNavigator extends Navigator {
    
    private static final long serialVersionUID = 13243245L;

    public MyNavigator(UI ui, ComponentContainer container) {
        
        super(ui, container);
        this.initNavigator(); 
    
    }

    private void initNavigator() {
        
        this.addView("V1_LoginView", new V1_LoginView());
        this.addView("V2_EnterView", new V2_EnterView());
        this.addView("V3_PublicBodyView", new V3_PublicBodyView());
        this.addView("V3s_PublicBodiesView", new V3s_PublicBodiesView());
        this.addView("V4_PublicPersonView", new V4_PublicPersonView());
        this.addView("V4s_PublicPersonsView", new V4s_PublicPersonsView());
        this.addView("V5_PublicRoleView", new V5_PublicRoleView());
        this.addView("V6_VoteView", new V6_VoteView());
        this.addView("V6s_VotesView", new V6s_VotesView());
        this.addView("V7_AdministrationView", new V7_AdministrationView());
//        for V8 see below
        this.addView("V9_SubjectView", new V9_SubjectView());
        this.addView("V9s_SubjectsView", new V9s_SubjectsView());
        this.addView("V10_ThemeView", new V10_ThemeView());
        this.addView("V10s_ThemesView", new V10s_ThemesView());
        
        
        
        //views zodpovedajuce univerzalnym editacnym tabulkam:
        this.addView(ToolsNames.decapit(A_User.TN),
                new V8_UniEditableTableView<>(A_User.class, 
                        NonEditableFields.A_USER.getNonEditableParams(), false));
        

//        this.addView(ToolsNazvy.decapit(A_UserRole.TN),
//                new V8_UniEditableTableView<>(A_UserRole.class, 
//                        NonEditableFields.A_USER_ROLE.getNonEditableParams(),true));
        
        //dobrovolnicke VIew
        this.addView(ToolsNames.decapit(District.TN),
                new V8_UniEditableTableView<>(District.class, 
                        NonEditableFields.DISTRICT.getNonEditableParams(), false));
        
        this.addView(ToolsNames.decapit(Region.TN),
                new V8_UniEditableTableView<>(Region.class, 
                        NonEditableFields.REGION.getNonEditableParams(), false));
        
        this.addView(ToolsNames.decapit(Location.TN),
                new V8_UniEditableTableView<>(Location.class, 
                        NonEditableFields.LOCATION.getNonEditableParams(), false));
        
        this.addView(ToolsNames.decapit(PersonClassification.TN),
                new V8_UniEditableTableView<>(PersonClassification.class, 
                        NonEditableFields.PERSON_CLASS.getNonEditableParams(), false));
        
        this.addView(ToolsNames.decapit(PublicBody.TN),
                new V8_UniEditableTableView<>(PublicBody.class, 
                        NonEditableFields.PUBLIC_BODY.getNonEditableParams(), false));
        
        this.addView(ToolsNames.decapit(PublicPerson.TN),
                new V8_UniEditableTableView<>(PublicPerson.class, 
                        NonEditableFields.PUBLIC_PERSON.getNonEditableParams(), false));
        
        this.addView(ToolsNames.decapit(PublicRole.TN),
                new V8_UniEditableTableView<>(PublicRole.class, 
                        NonEditableFields.PUBLIC_ROLE.getNonEditableParams(), false));

        this.addView(ToolsNames.decapit(Subject.TN),
                new V8_UniEditableTableView<>(Subject.class, 
                        NonEditableFields.SUBJECT.getNonEditableParams(), false));
        
        this.addView(ToolsNames.decapit(Tenure.TN),
                new V8_UniEditableTableView<>(Tenure.class, 
                        NonEditableFields.TENURE.getNonEditableParams(), false));
        
        this.addView(ToolsNames.decapit(Theme.TN),
                new V8_UniEditableTableView<>(Theme.class, 
                        NonEditableFields.THEME.getNonEditableParams(), false));
        
        this.addView(ToolsNames.decapit(Vote.getTN()),
                new V8_UniEditableTableView<>(Vote.class, 
                        NonEditableFields.VOTE.getNonEditableParams(), false));
        
        this.addView(ToolsNames.decapit(VoteClassification.TN),
                new V8_UniEditableTableView<>(VoteClassification.class, 
                        NonEditableFields.VOTE_CLASS.getNonEditableParams(), false));
        
        this.addView(ToolsNames.decapit(VoteOfRole.TN),
                new V8_UniEditableTableView<>(VoteOfRole.class, 
                        NonEditableFields.VOTE_OF_ROLE.getNonEditableParams(), false));
    }
    
}
