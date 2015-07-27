/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.view;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.UI;
import sk.stefan.enums.CrutialNonEditable;
import sk.stefan.enums.NonEditableFields;
import sk.stefan.mvps.model.entity.A_User;
import sk.stefan.mvps.model.entity.District;
import sk.stefan.mvps.model.entity.Location;
import sk.stefan.mvps.model.entity.PersonClassification;
import sk.stefan.mvps.model.entity.PublicBody;
import sk.stefan.mvps.model.entity.PublicPerson;
import sk.stefan.mvps.model.entity.PublicRole;
import sk.stefan.mvps.model.entity.Region;
import sk.stefan.mvps.model.entity.Subject;
import sk.stefan.mvps.model.entity.Tenure;
import sk.stefan.mvps.model.entity.Theme;
import sk.stefan.mvps.model.entity.Vote;
import sk.stefan.mvps.model.entity.VoteClassification;
import sk.stefan.mvps.model.entity.VoteOfRole;
import sk.stefan.utils.ToolsNames;

/**
 * Navigator upraveny pre nase ucely.
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
                        NonEditableFields.A_USER.getNonEditableParams(), 
                        CrutialNonEditable.A_USER.getCrutialParams(),
                        false));
        

        
        //dobrovolnicke VIew
        this.addView(ToolsNames.decapit(District.TN),
                new V8_UniEditableTableView<>(District.class, 
                        NonEditableFields.T_DISTRICT.getNonEditableParams(), 
                        CrutialNonEditable.T_DISTRICT.getCrutialParams(),
                        false));
        
        this.addView(ToolsNames.decapit(Region.TN),
                new V8_UniEditableTableView<>(Region.class, 
                        NonEditableFields.T_REGION.getNonEditableParams(), 
                        CrutialNonEditable.T_REGION.getCrutialParams(),
                        false));
        
        this.addView(ToolsNames.decapit(Location.TN),
                new V8_UniEditableTableView<>(Location.class, 
                        NonEditableFields.T_LOCATION.getNonEditableParams(), 
                        CrutialNonEditable.T_LOCATION.getCrutialParams(),
                        false));
        
        this.addView(ToolsNames.decapit(PersonClassification.TN),
                new V8_UniEditableTableView<>(PersonClassification.class, 
                        NonEditableFields.T_PERSON_CLASSIFICATION.getNonEditableParams(),
                        CrutialNonEditable.T_PERSON_CLASSIFICATION.getCrutialParams(),
                        false));
        
        this.addView(ToolsNames.decapit(PublicBody.TN),
                new V8_UniEditableTableView<>(PublicBody.class, 
                        NonEditableFields.T_PUBLIC_BODY.getNonEditableParams(), 
                        CrutialNonEditable.T_PUBLIC_BODY.getCrutialParams(),
                        false));
        
        this.addView(ToolsNames.decapit(PublicPerson.TN),
                new V8_UniEditableTableView<>(PublicPerson.class, 
                        NonEditableFields.T_PUBLIC_PERSON.getNonEditableParams(), 
                        CrutialNonEditable.T_PUBLIC_PERSON.getCrutialParams(),
                        false));
        
        this.addView(ToolsNames.decapit(PublicRole.TN),
                new V8_UniEditableTableView<>(PublicRole.class, 
                        NonEditableFields.T_PUBLIC_ROLE.getNonEditableParams(), 
                        CrutialNonEditable.T_PUBLIC_ROLE.getCrutialParams(),
                        false));

        this.addView(ToolsNames.decapit(Subject.TN),
                new V8_UniEditableTableView<>(Subject.class, 
                        NonEditableFields.T_SUBJECT.getNonEditableParams(),
                        CrutialNonEditable.T_SUBJECT.getCrutialParams(),
                        false));
        
        this.addView(ToolsNames.decapit(Tenure.TN),
                new V8_UniEditableTableView<>(Tenure.class, 
                        NonEditableFields.T_TENURE.getNonEditableParams(),
                        CrutialNonEditable.T_TENURE.getCrutialParams(),
                        false));
        
        this.addView(ToolsNames.decapit(Theme.TN),
                new V8_UniEditableTableView<>(Theme.class, 
                        NonEditableFields.T_THEME.getNonEditableParams(),
                        CrutialNonEditable.T_THEME.getCrutialParams(),
                        false));
        
        this.addView(ToolsNames.decapit(Vote.getTN()),
                new V8_UniEditableTableView<>(Vote.class, 
                        NonEditableFields.T_VOTE.getNonEditableParams(), 
                        CrutialNonEditable.T_VOTE.getCrutialParams(),
                        false));
        
        this.addView(ToolsNames.decapit(VoteClassification.TN),
                new V8_UniEditableTableView<>(VoteClassification.class, 
                        NonEditableFields.T_VOTE_CLASSIFICATION.getNonEditableParams(), 
                        CrutialNonEditable.T_VOTE_CLASSIFICATION.getCrutialParams(),
                        false));
        
        this.addView(ToolsNames.decapit(VoteOfRole.TN),
                new V8_UniEditableTableView<>(VoteOfRole.class, 
                        NonEditableFields.T_VOTE_OF_ROLE.getNonEditableParams(), 
                        CrutialNonEditable.T_VOTE_OF_ROLE.getCrutialParams(),
                        false));
    }
    
}
