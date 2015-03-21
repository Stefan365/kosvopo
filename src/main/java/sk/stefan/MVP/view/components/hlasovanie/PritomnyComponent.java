package sk.stefan.MVP.view.components.hlasovanie;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
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

//    private final PublicRole pubRole;
    private final Vote vote;
    private final VoteOfRole voteOfRole;
    private VoteAction decision;

//    private final UniRepo<VoteOfRole> vorRepo;
    
    //0. konstruktor
    /**
     * @param vor
     * @param hlas
     */
    public PritomnyComponent(VoteOfRole vor, Vote hlas) {
//    public PritomnyComponent(VoteOfRole vor) {

//        vorRepo = new UniRepo<>(VoteOfRole.class);
        
//        this.pubRole = pr;
        this.vote = hlas;
        this.voteOfRole = vor;
//        this.hlasovanieVerOs.setPublic_role_id(pr.getId());
//        this.hlasovanieVerOs.setVote_id(hlas.getId());
//        this.hlasovanieVerOs.setVisible(Boolean.TRUE);
        
        
        

        this.initComp();
    }

    private void initComp() {

        this.initLabel();
        this.initOptionGroup();

        this.addComponents(decisionOg, presentLb);

        this.setSpacing(true);

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
                voteOfRole.setDecision((VoteAction) event.getProperty().getValue());
            }
        });

        if (this.voteOfRole.getDecision() == null) {
            decisionOg.select(voteOfRole.getDecision());

        } else {
            decisionOg.select(0);
        }

        decisionOg.setNullSelectionAllowed(false);
        
        decisionOg.setImmediate(true);
        
        VoteAction va;
        if (this.voteOfRole.getDecision() != null){
            va = this.voteOfRole.getDecision();
        } else {
            va = VoteAction.ABSENT;
        }
        
        this.setValue(va);

    }

    private void initLabel() {
        
//        presentLb = new Label(this.hlasovanieVerOs.getpubRole.getPresentationName2());

    }
    
    /**
     * Nastavi hodnotu v optionGroupe.
     * 
     * @param va
//     */
    private void setValue(VoteAction va){
        
        this.decisionOg.setValue(va);
    
    }
    
    
    

    //Getters and Setters:
    public VoteOfRole getHlasovanieVerOs() {
        return voteOfRole;
    }

    public VoteAction getDecision() {
        return decision;
    }

    public void setDecision(VoteAction decision) {
        this.decision = decision;
    }

//    public PublicRole getPubRole() {
//        return pubRole;
//    }

    /**
     * @return the hlasovanie
     */
    public Vote getHlasovanie() {
        return vote;
    }

}
