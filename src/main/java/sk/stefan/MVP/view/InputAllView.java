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
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import sk.stefan.MVP.model.entity.dao.Region;
import sk.stefan.MVP.model.entity.dao.Location;
import sk.stefan.MVP.model.entity.dao.District;
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
import sk.stefan.listeners.InputVoteButClickListener;
import sk.stefan.listeners.ObnovFilterListener;
import sk.stefan.listeners.OkCancelListener;
import sk.stefan.wrappers.InputFormWrapper;

/**
 *
 * @author stefan
 */
public class InputAllView extends VerticalLayout implements View {

    private static final Logger log = Logger.getLogger(InputAllView.class);

    private static final long serialVersionUID = 1L;

    private final GridLayout mainLayout = new GridLayout(2, 2);

    private final VerticalLayout hodnoteniaLy = new VerticalLayout();
    private final VerticalLayout hlasovanieLy = new VerticalLayout();
    private final VerticalLayout miestoLy = new VerticalLayout();
    private final VerticalLayout politikaLy = new VerticalLayout();

    private final Label hodLb = new Label("Hodnotenia");
    private final Label hlasLb = new Label("Hlasovanie");
    private final Label miestLb = new Label("Miesto");
    private final Label polLb = new Label("Politika");

    private final Button krajBt = new Button();
    private final Button okresBt = new Button();
    private final Button location0Bt = new Button();

    private final Button publicPerson0Bt = new Button();
    private final Button tenure0Bt = new Button();
    private final Button publicBody1Bt = new Button();
    private final Button publicRole2Bt = new Button();

    private final Button theme0Bt = new Button();
    private final Button subject3Bt = new Button();
    private final Button vote4Bt = new Button();
//    private final Button voteOfRole5Bt = new Button();

    private final Button personClass1Bt = new Button();
    private final Button voteClass5Bt = new Button();

//
//    private final Button skuskaBt = new Button("skuska");
    private final Map<Button, InputFormWrapper<? extends Object>> allButtonsMap = new HashMap<>();

    public InputAllView(Navigator nav) {
        this.initLayout();

        this.initMap();
        this.initButtons();

        this.addComponent(NavigationComponent.getNavComp());
    }

    private void initMap() {

        allButtonsMap.put(krajBt, new InputFormWrapper<>(Region.class, Region.PRES_NAME, Region.TN));
        allButtonsMap.put(okresBt, new InputFormWrapper<>(District.class, District.PRES_NAME, District.TN));
        allButtonsMap.put(location0Bt, new InputFormWrapper<>(Location.class, Location.PRES_NAME, Location.TN));
        allButtonsMap.put(tenure0Bt, new InputFormWrapper<>(Tenure.class, Tenure.PRES_NAME, Tenure.TN));
        allButtonsMap.put(theme0Bt, new InputFormWrapper<>(Theme.class, Theme.PRES_NAME, Theme.getTN()));
        allButtonsMap.put(publicPerson0Bt, new InputFormWrapper<>(PublicPerson.class, PublicPerson.PRES_NAME, PublicPerson.TN));
        allButtonsMap.put(publicBody1Bt, new InputFormWrapper<>(PublicBody.class, PublicBody.PRES_NAME, PublicBody.TN));
        allButtonsMap.put(personClass1Bt, new InputFormWrapper<>(PersonClassification.class, PersonClassification.PRES_NAME, PersonClassification.TN));
        allButtonsMap.put(publicRole2Bt, new InputFormWrapper<>(PublicRole.class, PublicRole.PRES_NAME, PublicRole.TN));
        allButtonsMap.put(subject3Bt, new InputFormWrapper<>(Subject.class, Subject.PRES_NAME, Subject.TN));
        allButtonsMap.put(vote4Bt, new InputFormWrapper<>(Vote.class, Vote.getPRES_NAME(), Vote.getTN()));
//        allButtonsMap.put(voteOfRole5Bt, new InputFormWrapper<>(VoteOfRole.class, VoteOfRole.PRES_NAME, VoteOfRole.getTN()));
        allButtonsMap.put(voteClass5Bt, new InputFormWrapper<>(VoteClassification.class, VoteClassification.PRES_NAME, VoteClassification.TN));

    }

    private void initLayout() {

        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);

        mainLayout.addComponent(miestoLy, 0, 0);
        mainLayout.addComponent(politikaLy, 1, 0);
        mainLayout.addComponent(hlasovanieLy, 0, 1);
        mainLayout.addComponent(hodnoteniaLy, 1, 1);

        miestoLy.setMargin(true);
        miestoLy.setSpacing(true);
        miestoLy.addComponent(miestLb);
        miestoLy.addComponent(krajBt);
        miestoLy.addComponent(okresBt);
        miestoLy.addComponent(location0Bt);

        politikaLy.setMargin(true);
        politikaLy.setSpacing(true);
        politikaLy.addComponent(polLb);
        politikaLy.addComponent(publicPerson0Bt);
        politikaLy.addComponent(tenure0Bt);
        politikaLy.addComponent(publicBody1Bt);
        politikaLy.addComponent(publicRole2Bt);

        hlasovanieLy.setMargin(true);
        hlasovanieLy.setSpacing(true);
        hlasovanieLy.addComponent(hlasLb);
        hlasovanieLy.addComponent(theme0Bt);
        hlasovanieLy.addComponent(subject3Bt);
        hlasovanieLy.addComponent(vote4Bt);

        hodnoteniaLy.setMargin(true);
        hodnoteniaLy.setSpacing(true);
        hodnoteniaLy.addComponent(hodLb);
        hodnoteniaLy.addComponent(personClass1Bt);
        hodnoteniaLy.addComponent(voteClass5Bt);

        this.addComponent(mainLayout);

    }

    /**
     * inicializacia tlacitiek:
     */
    private void initButtons() {
        String s;
        for (Button b : allButtonsMap.keySet()) {
            InputFormWrapper<? extends Object> wr = allButtonsMap.get(b);

            b.setCaption(wr.getButtonName());
//            log.info("MENO: " + wr.getButtonName());
            if ((Vote.class).equals(wr.getClsE())) {
//                log.info("LSTENER1: " + wr.getClsE().getCanonicalName());
                s = wr.getButtonName();
                b.addClickListener(new InputVoteButClickListener(s));
            } else {
//                log.info("LSTENER2: " + wr.getClsE().getCanonicalName());
                b.addClickListener(new InputButClickListener(wr.getClsE(), wr.getButtonName()));
            }

        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        this.addComponent(NavigationComponent.getNavComp());
    }

}
