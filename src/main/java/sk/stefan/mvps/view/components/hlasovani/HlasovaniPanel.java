package sk.stefan.mvps.view.components.hlasovani;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import sk.stefan.enums.UserType;
import sk.stefan.enums.VoteAction;
import sk.stefan.interfaces.TabEntity;
import sk.stefan.listeners.SaveListener;
import sk.stefan.mvps.model.entity.PublicBody;
import sk.stefan.mvps.model.entity.PublicRole;
import sk.stefan.mvps.model.entity.Vote;
import sk.stefan.mvps.model.entity.VoteOfRole;
import sk.stefan.mvps.model.service.PublicRoleService;
import sk.stefan.mvps.model.service.SecurityService;
import sk.stefan.mvps.model.service.VoteService;
import sk.stefan.utils.EnhancedBeanItemContainer;
import sk.stefan.utils.EnumConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by elopin on 22.11.2015.
 */
@SpringComponent
@Scope("prototype")
@DesignRoot
public class HlasovaniPanel extends CssLayout {

    @Autowired
    private VoteService voteService;

    @Autowired
    private PublicRoleService publicRoleService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private ApplicationContext context;

    //Design
    private Button butEdit;
    private Button butCancel;
    private Button butSave;
    private VerticalLayout readLayout;
    private VerticalLayout editLayout;
    private VerticalLayout componentsLayout;
    private Grid grid;

    //data
    private Vote vote;
    private EnhancedBeanItemContainer<VoteOfRole> container = new EnhancedBeanItemContainer<>(VoteOfRole.class);
    private List<HlasovaniOsobyComponent> hlasovaniComponents = new ArrayList<>();
    private SaveListener<? extends TabEntity> saveListener;

    public HlasovaniPanel() {
        Design.read(this);

        butEdit.addClickListener(e -> setReadOnly(false));
        butCancel.addClickListener(e -> setReadOnly(true));
        butSave.addClickListener(e -> onSave());

        container.addGeneratedProperty("person", String.class,
                item -> publicRoleService.findPublicPersonFromPublicRoleId(item.getPublic_role_id()).getPresentationName());

        List<Object> arr = new ArrayList<>();
        arr.stream().filter(item -> item.getClass() == Object.class);

        grid.setContainerDataSource(container);
        grid.getColumn("person").setHeaderCaption("Verejná osoba");
        grid.getColumn("decision").setHeaderCaption("Volba");
        grid.getColumn("decision").setConverter(new EnumConverter());
        grid.setHeightMode(HeightMode.ROW);
    }

    public void setVote(Vote vote) {
        this.vote = vote;
        container.removeAllItems();
        container.addAll(voteService.findVoteOfRolesByVoteId(vote.getId()));
        grid.setHeightByRows(container.size() > 6 ? 6 : container.size() == 0 ? 1 : container.size());

        PublicBody publicBody = voteService.getVotePublicBody(vote);
        List<PublicRole> publicRoles = publicRoleService.findAllPublicRoleByPublicBodyId(publicBody.getId());

        componentsLayout.removeAllComponents();
        hlasovaniComponents.clear();
        for (PublicRole publicRole : publicRoles) {
            VoteOfRole voteOfRole = voteService.findVoteOfRoleForVoteAndPublicRole(vote, publicRole);
            if (voteOfRole == null) {
                voteOfRole = new VoteOfRole();
                voteOfRole.setVisible(true);
                voteOfRole.setVote_id(vote.getId());
                voteOfRole.setPublic_role_id(publicRole.getId());
                voteOfRole.setDecision(VoteAction.ABSENT);
            }

            HlasovaniOsobyComponent hlasovaniOsobyComponent = context.getBean(HlasovaniOsobyComponent.class);
            hlasovaniOsobyComponent.setVoteOfRole(publicRole, voteOfRole);
            componentsLayout.addComponent(hlasovaniOsobyComponent);
            hlasovaniComponents.add(hlasovaniOsobyComponent);
        }
        setReadOnly(true);
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        butEdit.setVisible(readOnly && (securityService.currentUserHasRole(UserType.ADMIN)
                || securityService.currentUserHasRole(UserType.VOLUNTEER)));
        editLayout.setVisible(!readOnly);
        readLayout.setVisible(readOnly);
    }


    private void onSave() {
        if (hlasovaniComponents.stream().allMatch(hlasovaniComponent -> hlasovaniComponent.isValid())) {
            hlasovaniComponents.forEach(hlasovani -> hlasovani.save());
            if (saveListener != null) {
                saveListener.accept(vote);
            }
        }
    }

    public void setSaveListener(SaveListener<? extends TabEntity> saveListener) {
        this.saveListener = saveListener;
    }
}
