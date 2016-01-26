package sk.stefan.mvps.view.components.verejnyOrgan;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import sk.stefan.annotations.ViewTab;
import sk.stefan.listeners.SaveListener;
import sk.stefan.mvps.model.entity.Location;
import sk.stefan.mvps.model.entity.PublicBody;
import sk.stefan.interfaces.TabEntity;
import sk.stefan.mvps.model.service.LinkService;
import sk.stefan.mvps.model.service.LocationService;
import sk.stefan.mvps.model.service.PublicBodyService;
import sk.stefan.mvps.view.tabs.TabComponent;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by elopin on 07.11.2015.
 */
@SpringComponent
@Scope("prototype")
@ViewTab("novyOrgan")
@DesignRoot
public class NewPublicBodyForm extends VerticalLayout implements TabComponent {

    @Autowired
    private LocationService locationService;

    @Autowired
    private PublicBodyService publicBodyService;

    @Autowired
    private LinkService linkService;

    // Design
    private TextField tfName;
    private ComboBox cbLocation;
    private Button butSave;

    // data
    private BeanFieldGroup<PublicBody> bfg = new BeanFieldGroup<>(PublicBody.class);
    private SaveListener<TabEntity> saveListener;

    public NewPublicBodyForm() {
        Design.read(this);

        bfg.bind(tfName, "name");
        bfg.bind(cbLocation, "location_id");

        tfName.setRequiredError("Nazev musí být vyplněn!");
        cbLocation.setRequiredError("Mesto musí být vybráno!");

        butSave.addClickListener(event -> onSave());

    }

    @PostConstruct
    public void init() {
        List<Location> locations = locationService.findAll();
        locations.forEach(location -> {
            cbLocation.addItem(location.getId());
            cbLocation.setItemCaption(location.getId(), location.getLocation_name());
        });
    }

    @Override
    public void setSaveListener(SaveListener<TabEntity> saveListener) {
        this.saveListener = saveListener;
    }

    private void onSave() {
        setValidationVisible(true);
        if (bfg.isValid()) {
            try {
                bfg.commit();
                if (saveListener != null) {
                    saveListener.save(bfg.getItemDataSource().getBean());
                }
            } catch (FieldGroup.CommitException e) {
                throw new RuntimeException("Nepodarilo sa uložit orgán!");
            }
        }
    }

    @Override
    public String getTabCaption() {
        return "Novy verejný orgán";
    }

    @Override
    public void show() {
        if (bfg.getItemDataSource() == null) {
            PublicBody newBody = new PublicBody();
            newBody.setVisible(true);
            bfg.setItemDataSource(newBody);
        }
        setValidationVisible(false);
    }

    @Override
    public String getTabId() {
        return "novyOrgan";
    }

    public void setValidationVisible(boolean validationVisible) {
        tfName.setValidationVisible(validationVisible);
        cbLocation.setValidationVisible(validationVisible);
    }
}
