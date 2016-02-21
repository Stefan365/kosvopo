package sk.stefan.mvps.view.components.hlasovani;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.declarative.Design;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sk.stefan.enums.VoteAction;
import sk.stefan.mvps.model.entity.PublicRole;
import sk.stefan.mvps.model.entity.VoteOfRole;
import sk.stefan.mvps.model.service.VoteService;

/**
 * Komponenta pro hlasování jedné veřejné osoby.
 * @author Lukas on 13.02.2016.
 */
@Component
@Scope("prototype")
@DesignRoot
public class HlasovaniOsobyComponent extends HorizontalLayout {

    @Autowired
    private VoteService voteService;

    private Label lblMeno;
    private OptionGroup options;

    private BeanFieldGroup<VoteOfRole> bfg = new BeanFieldGroup<>(VoteOfRole.class);

    public HlasovaniOsobyComponent() {
        Design.read(this);

        for (VoteAction action : VoteAction.values()) {
            options.addItem(action);
            options.setItemCaption(action, action.getName());
        }
        bfg.bind(options, "decision");
    }

    public void setVoteOfRole(PublicRole publicRole, VoteOfRole voteOfRole) {
        lblMeno.setValue(publicRole.getPresentationName());
        bfg.setItemDataSource(voteOfRole);
    }

    public boolean isValid() {
        return bfg.isValid();
    }

    public void save() {
        try {
            bfg.commit();
            voteService.saveVoteOfRole(bfg.getItemDataSource().getBean(), false);
        } catch (FieldGroup.CommitException e) {
            throw new RuntimeException("Nepodařilo se uložit hlasování veřejné osoby!", e);
        }
    }


}
