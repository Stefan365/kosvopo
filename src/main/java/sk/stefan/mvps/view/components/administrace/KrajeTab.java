package sk.stefan.mvps.view.components.administrace;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sk.stefan.annotations.ViewTab;
import sk.stefan.enums.UserType;
import sk.stefan.mvps.model.entity.Region;
import sk.stefan.mvps.model.service.LocationService;
import sk.stefan.mvps.model.service.SecurityService;
import sk.stefan.mvps.view.tabs.TabComponent;
import sk.stefan.utils.Localizator;

/**
 * Záložka se seznamem krajů.
 * @author elopin on 06.12.2015.
 */
@Component
@ViewTab("kraje")
@Scope("prototype")
@DesignRoot
public class KrajeTab extends VerticalLayout implements TabComponent {

    @Autowired
    private LocationService locationService;

    @Autowired
    private SecurityService securityService;

    // Design
    private Panel panel;
    private TextField searchFd;
    private Button butPridat;
    private Grid grid;
    private RegionPanel regionPanel;

    // data
    private BeanItemContainer<Region> container;

    public KrajeTab() {
        Design.read(this);
        Localizator.localizeDesign(this);

        container = new BeanItemContainer<>(Region.class);

        grid.setContainerDataSource(container);
        grid.setHeightMode(HeightMode.ROW);
        grid.addSelectionListener(event -> showDetail((Region) grid.getSelectedRow()));

        butPridat.addClickListener(event -> onPridat());

        regionPanel.setSaveListener(this::onSave);
        regionPanel.setRemoveListener(this::onRemove);
    }

    private void onRemove(Region region) {
        locationService.removeRegion(region);
        regionPanel.setVisible(false);
        show();
    }

    private void onSave(Region region) {
        regionPanel.setRegion(locationService.saveRegion(region));
        show();
    }

    private void onPridat() {
        Region region = new Region();
        region.setVisible(true);
        regionPanel.setRegion(region);
        regionPanel.setVisible(true);
    }

    private void showDetail(Region region) {
        regionPanel.setRegion(region);
        regionPanel.setVisible(true);
    }

    @Override
    public void show() {
        container.removeAllItems();
        container.addAll(locationService.findAllRegions());
        grid.setHeightByRows(container.size() > 6 ? 6 : container.size() == 0 ? 1 : container.size());

    }

    @Override
    public String getTabId() {
        return "kraje";
    }

    @Override
    public boolean isUserAccessGranted() {
        return securityService.currentUserHasRole(UserType.ADMIN) || securityService.currentUserHasRole(UserType.VOLUNTEER);
    }
}
