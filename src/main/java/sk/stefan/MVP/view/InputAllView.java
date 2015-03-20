/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import sk.stefan.MVP.model.entity.dao.Location;
import sk.stefan.MVP.model.entity.dao.PersonClassification;
import sk.stefan.MVP.model.entity.dao.PublicBody;
import sk.stefan.MVP.model.entity.dao.PublicPerson;
import sk.stefan.MVP.model.entity.dao.PublicRole;
import sk.stefan.MVP.model.entity.dao.Subject;
import sk.stefan.MVP.model.entity.dao.Tenure;
import sk.stefan.MVP.model.entity.dao.Theme;
import sk.stefan.MVP.model.entity.dao.Vote;
import sk.stefan.MVP.model.entity.dao.VoteClassification;
import sk.stefan.MVP.model.entity.dao.VoteOfRole;
import sk.stefan.MVP.view.components.NavigationComponent;
import sk.stefan.listeners.InputButClickListener;
import sk.stefan.utils.InputFormWrapper;

/**
 *
 * @author stefan
 */
public class InputAllView extends VerticalLayout implements View {

    private static final Logger log = Logger.getLogger(InputAllView.class);

    private static final long serialVersionUID = 1L;

    private VerticalLayout layout;
    private final Button tenure0Bt = new Button();
    private final Button location0Bt = new Button();
    private final Button theme0Bt = new Button();
    private final Button publicPerson0Bt = new Button();
    private final Button publicBody1Bt = new Button();
    private final Button personClass1Bt = new Button();
    private final Button publicRole2Bt = new Button();
    private final Button subject3Bt = new Button();
    private final Button vote4Bt = new Button();
    private final Button voteOfRole5Bt = new Button();
    private final Button actClass6Bt = new Button();

    private final Button skuskaBt = new Button("skuska");

    private Map<Button, InputFormWrapper<? extends Object>> allButtonsMap = new HashMap<>();

    public InputAllView(Navigator nav) {
        this.initLayout();
        this.initMap();
        this.initButtons();
        this.skusaj();
//        this.initButtonsListeners();     

        this.addComponent(NavigationComponent.getNavComp());
    }

    private void initMap() {

        allButtonsMap.put(tenure0Bt, new InputFormWrapper<>(Tenure.class, Tenure.PRES_NAME, Tenure.getTN()));
        allButtonsMap.put(location0Bt, new InputFormWrapper<>(Location.class, Location.PRES_NAME, Location.getTN()));
        allButtonsMap.put(theme0Bt, new InputFormWrapper<>(Theme.class, Theme.PRES_NAME, Theme.getTN()));
        allButtonsMap.put(publicPerson0Bt, new InputFormWrapper<>(PublicPerson.class, PublicPerson.PRES_NAME, PublicPerson.getTN()));
        allButtonsMap.put(publicBody1Bt, new InputFormWrapper<>(PublicBody.class, PublicBody.PRES_NAME, PublicBody.getTN()));
        allButtonsMap.put(personClass1Bt, new InputFormWrapper<>(PersonClassification.class, PersonClassification.PRES_NAME, PersonClassification.getTN()));
        allButtonsMap.put(publicRole2Bt, new InputFormWrapper<>(PublicRole.class, PublicRole.PRES_NAME, PublicRole.getTN()));
        allButtonsMap.put(subject3Bt, new InputFormWrapper<>(Subject.class, Subject.PRES_NAME, Subject.getTN()));
        allButtonsMap.put(vote4Bt, new InputFormWrapper<>(Vote.class, Vote.PRES_NAME, Vote.getTN()));
        allButtonsMap.put(voteOfRole5Bt, new InputFormWrapper<>(VoteOfRole.class, VoteOfRole.PRES_NAME, VoteOfRole.getTN()));
        allButtonsMap.put(actClass6Bt, new InputFormWrapper<>(VoteClassification.class, VoteClassification.PRES_NAME, VoteClassification.getTN()));

    }

    private void initLayout() {

        layout = new VerticalLayout();
        layout.setMargin(true);
        layout.setSpacing(true);

        initMap();
        initButtons();
        addButtonsToLayout();

        this.addComponent(layout);

    }

    private void addButtonsToLayout() {
        for (Button b : allButtonsMap.keySet()) {
            layout.addComponent(b);
        }
    }

    private void initButtons() {

        for (Button b : allButtonsMap.keySet()) {
            InputFormWrapper<? extends Object> wr = allButtonsMap.get(b);

            b.setCaption(wr.getButtonName());
            b.addClickListener(new InputButClickListener(wr.getClsE(), wr.getButtonName(), this));
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        this.addComponent(NavigationComponent.getNavComp());
    }

    private void skusaj() {
        this.skuskaBt.addClickListener(new MyCLickListener("Karol"));
        this.skuskaBt.addClickListener(new MyCLickListener("Peter"));
        this.skuskaBt.addClickListener(new MyCLickListener("Pavol"));
        
        this.addComponent(skuskaBt);
        
    }

    private class MyCLickListener implements ClickListener {
        private static final long serialVersionUID = 1L;
        private final String val;
        
        public MyCLickListener(String value){
            val = value;
        }

        @Override
        public void buttonClick(Button.ClickEvent event) {
            Notification.show(val);
        }
        
    }
}
