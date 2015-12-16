package sk.stefan.mvps.view.components.administrace;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sk.stefan.annotations.ViewTab;
import sk.stefan.mvps.model.entity.District;
import sk.stefan.mvps.model.entity.Location;
import sk.stefan.mvps.model.service.LocationService;
import sk.stefan.mvps.view.tabs.TabComponent;
import sk.stefan.utils.PresentationNameConverter;

/**
 * Záložka s místama.
 * @author elopin on 07.12.2015.
 */
@Component
@Scope("prototype")
@ViewTab("locationTab")
@DesignRoot
public class LocationTab extends VerticalLayout implements TabComponent {

    @Autowired
    private LocationService locationService;

    //Design
    private TextField searchFd;
    private Grid grid;
    private Button butPridat;
    private LocationPanel locationPanel;

    //data
    private BeanItemContainer<Location> container;

    public LocationTab() {
        Design.read(this);

        container = new BeanItemContainer<>(Location.class);
        grid.setContainerDataSource(container);
        grid.getColumn("location_name").setHeaderCaption("Názov miesta");
        grid.getColumn("town_section").setHeaderCaption("Názov čiasti miesta");
        grid.getColumn("district_id").setHeaderCaption("Okres");
        grid.getColumn("district_id").setConverter(new PresentationNameConverter<District>(District.class));
        grid.setHeightMode(HeightMode.ROW);

        grid.addSelectionListener(event -> showDetail((Location) grid.getSelectedRow()));

        locationPanel.setSaveListener(this::onSave);
        locationPanel.setRemoveListener(this::onRemove);

        butPridat.addClickListener(event -> onPridat());
    }

    private void showDetail(Location location) {
        locationPanel.setLocation(location);
        location.setVisible(true);
    }

    private void onSave(Location location) {
        locationPanel.setLocation(locationService.saveLocation(location));
        show();
    }

    private void onRemove(Location location) {
        locationService.removeLocation(location);
        locationPanel.setVisible(false);
        show();
    }

    private void onPridat() {
        Location location = new Location();
        location.setVisible(true);
        locationPanel.setLocation(location);
        locationPanel.setVisible(true);
    }

    @Override
    public String getTabCaption() {
        return "Miesta";
    }

    @Override
    public void show() {
        container.removeAllItems();
        container.addAll(locationService.findAllLocations());
        grid.setHeightByRows(container.size() > 6 ? 6 : container.size() == 0 ? 1 : container.size());
    }

    @Override
    public String getTabId() {
        return "locationTab";
    }
}
