package sk.stefan.mvps.view.components.administrace;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sk.stefan.mvps.model.entity.District;
import sk.stefan.mvps.model.entity.Location;
import sk.stefan.mvps.model.service.LocationService;
import sk.stefan.utils.Localizator;

import java.util.function.Consumer;

/**
 * Panel s detailem lokace.
 * @author elopin on 07.12.2015.
 */
@Component
@Scope("prototype")
@DesignRoot
public class LocationPanel extends CssLayout {

    @Autowired
    private LocationService locationService;

    //Design
    private Label lblCaption;
    private Button butEdit;
    private VerticalLayout readLayout;
    private Label lblOkres;
    private Label lblNazev;
    private Label lblCastMesta;
    private VerticalLayout editLayout;
    private ComboBox cbOkres;
    private TextField tfCastMesta;
    private TextField tfNazev;
    private Button butSave;
    private Button butCancel;
    private Button butRemove;

    //data
    private BeanFieldGroup<Location> bfg;
    private Consumer<Location> saveListener;
    private Consumer<Location> removeListener;

    public LocationPanel() {
        Design.read(this);
        Localizator.localizeDesign(this);

        bfg = new BeanFieldGroup<>(Location.class);
        bfg.bind(cbOkres, "district_id");
        bfg.bind(tfNazev, "location_name");
        bfg.bind(tfCastMesta, "town_section");

        butEdit.addClickListener(event -> setReadOnly(false));
        butCancel.addClickListener(event -> onCancel());
        butSave.addClickListener(event -> onSave());
        butRemove.addClickListener(event -> onRemove());
    }

    public void setSaveListener(Consumer<Location> saveListener) {
        this.saveListener = saveListener;
    }

    public void setRemoveListener(Consumer<Location> removeListener) {
        this.removeListener = removeListener;
    }

    public void setLocation(Location location) {

        cbOkres.removeAllItems();
        locationService.findAllDistricts().forEach(district -> {
            cbOkres.addItem(district.getId());
            cbOkres.setItemCaption(district.getId(), district.getPresentationName());
        });

        validationVisible(false);
        bfg.setItemDataSource(location);

        District district = locationService.findOneDistrict(location.getDistrict_id());
        lblOkres.setValue(district != null ? district.getPresentationName() : null);
        lblNazev.setValue(location.getLocation_name());
        lblCastMesta.setValue(location.getTown_section());
        lblCastMesta.setVisible(location.getTown_section() != null && !location.getTown_section().isEmpty());

        setReadOnly(location.getId() != null);
    }

    private void onCancel() {
        if (bfg.getItemDataSource().getBean().getId() == null) {
            setVisible(false);
        } else {
            setReadOnly(true);
        }
    }

    private void onSave() {
        validationVisible(true);
        if (bfg.isValid()) {
            try {
                bfg.commit();
                if (saveListener != null) {
                    saveListener.accept(bfg.getItemDataSource().getBean());
                }
            } catch (FieldGroup.CommitException e) {
                throw new RuntimeException("Nepodařilo se uložit místo!", e);
            }
        }
    }

    private void onRemove() {
        if (removeListener != null) {
            removeListener.accept(bfg.getItemDataSource().getBean());
        }
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        butEdit.setVisible(readOnly);
        readLayout.setVisible(readOnly);
        editLayout.setVisible(!readOnly);
    }

    private void validationVisible(boolean visible) {
        cbOkres.setValidationVisible(visible);
        tfNazev.setValidationVisible(visible);
        tfCastMesta.setValidationVisible(visible);
    }
}
