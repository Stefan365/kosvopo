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
import sk.stefan.mvps.model.entity.District;
import sk.stefan.mvps.model.entity.Region;
import sk.stefan.mvps.model.service.LocationService;
import sk.stefan.mvps.model.service.SecurityService;
import sk.stefan.mvps.view.tabs.TabComponent;
import sk.stefan.utils.Localizator;
import sk.stefan.utils.PresentationNameConverter;

/**
 * Záložka se seznamem okresů.
 * @author elopin on 07.12.2015.
 */
@Component
@Scope("prototype")
@ViewTab("districtTab")
@DesignRoot
public class DistrictTab extends VerticalLayout implements TabComponent {

    @Autowired
    private LocationService locationService;

    @Autowired
    private SecurityService securityService;

    //Design
    private Panel panel;
    private TextField searchFd;
    private Grid grid;
    private Button butPridat;
    private DistrictPanel districtPanel;

    //data
    private BeanItemContainer<District> container;

    public DistrictTab() {
        Design.read(this);
        Localizator.localizeDesign(this);

        container = new BeanItemContainer<>(District.class);
        grid.setContainerDataSource(container);
        grid.getColumn("region_id").setConverter(new PresentationNameConverter<Region>(Region.class));
        grid.setHeightMode(HeightMode.ROW);
        grid.addSelectionListener(event -> showDetail((District) grid.getSelectedRow()));

        districtPanel.setSaveListener(this::onSave);
        districtPanel.setRemoveListener(this::onRemove);

        butPridat.addClickListener(event -> onPridat());

    }

    private void onPridat() {
        District district = new District();
        district.setVisible(true);
        districtPanel.setDistrict(district);
        districtPanel.setVisible(true);
    }

    private void onSave(District district) {
        districtPanel.setDistrict(locationService.saveDistrict(district));
        show();
    }

    private void onRemove(District district) {
        locationService.removeDistrict(district);
        districtPanel.setVisible(false);
        show();
    }

    private void showDetail(District district) {
        districtPanel.setDistrict(district);
        districtPanel.setVisible(true);
    }

    @Override
    public void show() {
        container.removeAllItems();
        container.addAll(locationService.findAllDistricts());
        grid.setHeightByRows(container.size() > 6 ? 6 : container.size() == 0 ? 1 : container.size());
    }

    @Override
    public String getTabId() {
        return "districtTab";
    }

    @Override
    public boolean isUserAccessGranted() {
        return securityService.currentUserHasRole(UserType.ADMIN) || securityService.currentUserHasRole(UserType.VOLUNTEER);
    }
}
