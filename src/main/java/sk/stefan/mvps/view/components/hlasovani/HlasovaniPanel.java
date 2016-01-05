package sk.stefan.mvps.view.components.hlasovani;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.VaadinPropertyDescriptor;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Panel;
import com.vaadin.ui.declarative.Design;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import sk.stefan.mvps.model.entity.PublicPerson;
import sk.stefan.mvps.model.entity.Vote;
import sk.stefan.mvps.model.entity.VoteOfRole;
import sk.stefan.mvps.model.service.PublicPersonService;
import sk.stefan.mvps.model.service.PublicRoleService;
import sk.stefan.mvps.model.service.VoteService;
import sk.stefan.utils.EnhancedBeanItemContainer;
import sk.stefan.utils.EnumConverter;
import sk.stefan.utils.PresentationNameConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by elopin on 22.11.2015.
 */
@SpringComponent
@Scope("prototype")
@DesignRoot
public class HlasovaniPanel extends Panel {

    @Autowired
    private VoteService voteService;

    @Autowired
    private PublicRoleService publicRoleService;

    //Design
    private Grid grid;

    //data
    private EnhancedBeanItemContainer<VoteOfRole> container = new EnhancedBeanItemContainer<>(VoteOfRole.class);

    public HlasovaniPanel() {
        Design.read(this);

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
        container.removeAllItems();
        container.addAll(voteService.findVoteOfRolesByVoteId(vote.getId()));
        grid.setHeightByRows(container.size() > 6 ? 6 : container.size() == 0 ? 1 : container.size());
    }
}
