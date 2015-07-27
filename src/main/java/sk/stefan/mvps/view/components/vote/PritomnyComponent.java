package sk.stefan.mvps.view.components.vote;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import sk.stefan.mvps.model.entity.PublicRole;
import sk.stefan.mvps.model.entity.VoteOfRole;
import sk.stefan.mvps.model.service.PublicRoleService;
import sk.stefan.mvps.model.serviceImpl.PublicRoleServiceImpl;
import sk.stefan.enums.VoteAction;

/**
 *
 * @author stefan
 */
@SuppressWarnings("serial")
public class PritomnyComponent extends HorizontalLayout {

    private Label presentLb;
    private OptionGroup decisionOg;

    private final VoteOfRole voteOfRole;
    private VoteAction decision;

//    private final UniRepo<PublicRole> prRepo;
    private final PublicRoleService publicRoleService;
    
    private final PritomniLayout listener; 
    //0. konstruktor
    /**
     * @param vor
     * @param lisnr
     */
    public PritomnyComponent(VoteOfRole vor, PritomniLayout lisnr) {

//        prRepo = new UniRepo<>(PublicRole.class);
        this.publicRoleService = new PublicRoleServiceImpl();

        this.listener = lisnr;
        this.voteOfRole = vor;
        
        
        

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
               listener.updateResult();
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
        
        Integer prId = this.voteOfRole.getPublic_role_id();
        
        PublicRole prole = publicRoleService.findPublicRoleById(prId);
        
        presentLb = new Label(prole.getPresentationName2());

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

}
