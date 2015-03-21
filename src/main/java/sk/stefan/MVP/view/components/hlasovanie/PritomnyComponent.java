package sk.stefan.MVP.view.components.hlasovanie;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import java.util.Arrays;
import java.util.List;
import sk.stefan.MVP.model.entity.dao.PublicRole;
import sk.stefan.MVP.model.entity.dao.Vote;
import sk.stefan.MVP.model.entity.dao.VoteOfRole;
import sk.stefan.enums.VoteAction;

/**
 *
 * @author stefan
 */
@SuppressWarnings("serial")
public class PritomnyComponent extends HorizontalLayout {

    private Label presentLb;
    private OptionGroup decisionOg;

    private final PublicRole pubRole;
    private final Vote hlasovanie;
    private final VoteOfRole hlasovanieVerOs;
    private VoteAction decision;

    //0. konstruktor
    /**
     * @param pr
     * @param hlas
     */
//    public PritomnyComponent(PublicRole pr, Vote hlas, PritomniLayout lisnr) {
    public PritomnyComponent(PublicRole pr, Vote hlas) {

        this.pubRole = pr;
        this.hlasovanie = hlas;
        this.hlasovanieVerOs = new VoteOfRole();

        this.initComp();
    }

    private void initComp() {

        this.initLabel();
        this.initOptionGroup();

        this.addComponents(decisionOg, presentLb);

        this.setSpacing(true);
//        this.setMargin(true);

    }

    /**
     *
     */
    private void initOptionGroup() {

        decisionOg = new OptionGroup();

        decisionOg.addStyleName("horizontal");
        
        int i = 0;
        for (VoteAction va : VoteAction.values()) {
            decisionOg.addItem(va);
            decisionOg.setItemCaption(va, VoteAction.getNames().get(i));
            i++;
        }

        //listener:
        this.decisionOg.addValueChangeListener(new ValueChangeListener() {

            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                hlasovanieVerOs.setDecision((VoteAction) event.getProperty().getValue());
            }
        });

        if (this.hlasovanieVerOs.getDecision() == null) {
            decisionOg.select(hlasovanieVerOs.getDecision());

        } else {
            decisionOg.select(0);
        }

        decisionOg.setNullSelectionAllowed(false);
        
        decisionOg.setImmediate(true);

    }

    private void initLabel() {
        
        presentLb = new Label(this.pubRole.getPresentationName2());

    }

    //Getters and Setters:
    public VoteOfRole getHlasovanieVerOs() {
        return hlasovanieVerOs;
    }

    public VoteAction getDecision() {
        return decision;
    }

    public void setDecision(VoteAction decision) {
        this.decision = decision;
    }

    public PublicRole getPubRole() {
        return pubRole;
    }

    /**
     * @return the hlasovanie
     */
    public Vote getHlasovanie() {
        return hlasovanie;
    }

}
