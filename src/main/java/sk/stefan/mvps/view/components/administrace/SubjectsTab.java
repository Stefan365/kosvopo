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
import sk.stefan.mvps.model.entity.PublicRole;
import sk.stefan.mvps.model.entity.Subject;
import sk.stefan.mvps.model.entity.Theme;
import sk.stefan.mvps.model.service.VoteService;
import sk.stefan.mvps.view.tabs.TabComponent;
import sk.stefan.utils.PresentationNameConverter;

/**
 * Created by elopin on 09.12.2015.
 */
@Component
@Scope("prototype")
@ViewTab("predmetyTab")
@DesignRoot
public class SubjectsTab extends VerticalLayout implements TabComponent {

    @Autowired
    private VoteService voteService;

    //Design
    private TextField searchFd;
    private Grid grid;
    private Button butPridat;
    private SubjectPanel subjectPanel;

    //data
    private BeanItemContainer<Subject> container;

    public SubjectsTab() {
        Design.read(this);

        container = new BeanItemContainer<>(Subject.class);
        grid.setContainerDataSource(container);
        grid.getColumn("brief_description").setHeaderCaption("Názov predmetu");
        grid.getColumn("theme_id").setHeaderCaption("Téma");
        grid.getColumn("theme_id").setConverter(new PresentationNameConverter<>(Theme.class));
        grid.setHeightMode(HeightMode.ROW);

        grid.addSelectionListener(event -> showDetail((Subject) grid.getSelectedRow()));

        subjectPanel.setSaveListener(this::onSave);
        subjectPanel.setRemoveListener(this::onRemove);

        butPridat.addClickListener(event -> onPridat());
    }

    private void onRemove(Subject subject) {
        voteService.removeSubject(subject);
        subjectPanel.setVisible(false);
        show();
    }

    private void onSave(Subject subject) {
        subjectPanel.setSubject(voteService.saveSubject(subject));
        show();
    }

    private void showDetail(Subject subject) {
        subjectPanel.setSubject(subject);
        subjectPanel.setVisible(true);
    }

    private void onPridat() {
        Subject subject = new Subject();
        subject.setVisible(true);
        subjectPanel.setSubject(subject);
        subjectPanel.setVisible(true);
    }

    @Override
    public String getTabCaption() {
        return "Predmety";
    }

    @Override
    public void show() {
        container.removeAllItems();
        container.addAll(voteService.findAllSubjects());
        grid.setHeightByRows(container.size() > 6 ? 6 : container.size() == 0 ? 1 : container.size());
    }

    @Override
    public String getTabId() {
        return "predmetyTab";
    }
}
