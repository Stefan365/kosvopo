package sk.stefan.mvps.view.components.administrace;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sk.stefan.annotations.ViewTab;
import sk.stefan.mvps.model.entity.Tenure;
import sk.stefan.mvps.model.service.TenureService;
import sk.stefan.mvps.view.tabs.TabComponent;
import sk.stefan.utils.Localizator;

/**
 * Záložka s hlasovacími obdobími.
 * @author elopin on 13.12.2015.
 */
@Component
@Scope("prototype")
@ViewTab("tenuresTab")
@DesignRoot
public class TenuresTab extends VerticalLayout implements TabComponent {

    @Autowired
    private TenureService tenureService;

    //Design
    private Panel panel;
    private Grid grid;
    private Button butPridat;
    private TenurePanel tenurePanel;

    //data
    private BeanItemContainer<Tenure> container;

    public TenuresTab() {
        Design.read(this);
        Localizator.localizeDesign(this);

        container = new BeanItemContainer<>(Tenure.class);
        grid.setContainerDataSource(container);
        grid.setHeightMode(HeightMode.ROW);
        grid.addSelectionListener(event -> showDetail((Tenure) grid.getSelectedRow()));

        tenurePanel.setSaveListener(this::onSave);
        tenurePanel.setRemoveListener(this::onRemove);

        butPridat.addClickListener(event -> onPridat());

    }

    private void onRemove(Tenure tenure) {
        tenureService.removeTenure(tenure);
        tenurePanel.setVisible(false);
        show();
    }

    private void onSave(Tenure tenure) {
        tenurePanel.setTenure(tenureService.saveTenure(tenure));
        show();
    }

    private void onPridat() {
        Tenure tenure = new Tenure();
        tenure.setVisible(true);
        tenurePanel.setTenure(tenure);
        tenurePanel.setVisible(true);
    }

    private void showDetail(Tenure tenure) {
        tenurePanel.setTenure(tenure);
        tenurePanel.setVisible(true);
    }

    @Override
    public void show() {
        container.removeAllItems();
        container.addAll(tenureService.findAllTenures());
        grid.setHeightByRows(container.size() > 6 ? 6 : container.size() == 0 ? 1 : container.size());
    }

    @Override
    public String getTabId() {
        return "tenuresTab";
    }
}