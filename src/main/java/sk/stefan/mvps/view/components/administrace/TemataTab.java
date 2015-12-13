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
import sk.stefan.mvps.model.entity.Theme;
import sk.stefan.mvps.model.service.VoteService;
import sk.stefan.mvps.view.tabs.TabComponent;

/**
 * Created by elopin on 09.12.2015.
 */
@Component
@Scope("prototype")
@ViewTab("temataTab")
@DesignRoot
public class TemataTab extends VerticalLayout implements TabComponent {

    @Autowired
    private VoteService voteService;

    //Design
    private TextField searchFd;
    private Grid grid;
    private Button butPridat;
    private TemaPanel temaPanel;

    //data
    private BeanItemContainer<Theme> container;

    public TemataTab() {
        Design.read(this);

        container = new BeanItemContainer<>(Theme.class);
        grid.setContainerDataSource(container);
        grid.getColumn("brief_description").setHeaderCaption("Názov tématu");
        grid.setHeightMode(HeightMode.ROW);

        grid.addSelectionListener(event -> showDetail((Theme) grid.getSelectedRow()));

        temaPanel.setSaveListener(this::onSave);
        temaPanel.setRemoveListener(this::onRemove);

        butPridat.addClickListener(event -> onPridat());
    }

    private void showDetail(Theme theme) {
        temaPanel.setTheme(theme);
        temaPanel.setVisible(true);
    }

    private void onRemove(Theme theme) {
        voteService.removeTheme(theme);
        temaPanel.setVisible(false);
        show();
    }

    private void onSave(Theme theme) {
        temaPanel.setTheme(voteService.saveTheme(theme));
        show();
    }

    private void onPridat() {
        Theme theme = new Theme();
        theme.setVisible(true);
        temaPanel.setTheme(theme);
        temaPanel.setVisible(true);
    }

    @Override
    public String getTabCaption() {
        return "Témata";
    }

    @Override
    public void show() {

        container.removeAllItems();
        container.addAll(voteService.findAllThemes());
        grid.setHeightByRows(container.size() > 6 ? 6 : container.size() == 0 ? 1 : container.size());
    }

    @Override
    public String getTabId() {
        return "temataTab";
    }
}
