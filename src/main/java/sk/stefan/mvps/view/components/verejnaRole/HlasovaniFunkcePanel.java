package sk.stefan.mvps.view.components.verejnaRole;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.declarative.Design;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import sk.stefan.enums.UserType;
import sk.stefan.interfaces.TabEntity;
import sk.stefan.mvps.model.entity.PublicRole;
import sk.stefan.mvps.model.entity.Vote;
import sk.stefan.mvps.model.entity.VoteOfRole;
import sk.stefan.mvps.model.service.LinkService;
import sk.stefan.mvps.model.service.SecurityService;
import sk.stefan.mvps.model.service.VoteService;
import sk.stefan.utils.EnumConverter;
import sk.stefan.utils.PresentationNameConverter;

/**
 * Created by elopin on 15.11.2015.
 */
@SpringComponent
@Scope("prototype")
@DesignRoot
public class HlasovaniFunkcePanel extends Panel {

    @Autowired
    private VoteService voteService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private LinkService linkService;

    //Design
    private TextField tfSearch;
    private Grid grid;
    private Button addNewHlasovaniRole;

    //data
    private BeanItemContainer<VoteOfRole> container;

    public HlasovaniFunkcePanel() {
        Design.read(this);
        container = new BeanItemContainer<>(VoteOfRole.class);

        grid.setContainerDataSource(container);
        grid.getColumn("vote_id").setHeaderCaption("Hlasovanie");
        grid.getColumn("vote_id").setConverter(new PresentationNameConverter<Vote>(Vote.class));
        grid.getColumn("decision").setHeaderCaption("Rozhodnutie");
        grid.getColumn("decision").setConverter(new EnumConverter());
        grid.setHeightMode(HeightMode.ROW);

        grid.addSelectionListener(event -> {
            Vote vote = voteService.getVote((VoteOfRole) grid.getSelectedRow());
            if (vote != null) {
                Page.getCurrent().open(linkService.getUriFragmentForEntity(vote), null);
            }
        });

    }

    public void setPublicRole(PublicRole publicRole) {
        container.addAll(voteService.getAllVotesOfPublicRole(publicRole));

        grid.setHeightByRows(container.size() >= 6 ? 6 : container.size() + 1);

        addNewHlasovaniRole.setVisible(securityService.currentUserHasRole(UserType.ADMIN) || securityService.currentUserHasRole(UserType.VOLUNTEER));
    }
}
