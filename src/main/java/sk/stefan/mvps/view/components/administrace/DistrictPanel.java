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
import java.util.function.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sk.stefan.mvps.model.entity.District;
import sk.stefan.mvps.model.entity.Region;
import sk.stefan.mvps.model.service.LocationService;

/**
 * Panel pro detail okresu.
 * @author elopin on 07.12.2015.
 */
@Component
@Scope("prototype")
@DesignRoot
public class DistrictPanel extends CssLayout {

    @Autowired
    private LocationService locationService;

    //Design
    private Label lblCaption;
    private Button butEdit;
    private VerticalLayout readLayout;
    private Label lblRegion;
    private Label lblNazev;
    private VerticalLayout editLayout;
    private ComboBox cbRegion;
    private TextField tfNazev;
    private Button butSave;
    private Button butCancel;
    private Button butRemove;

    //data
    private BeanFieldGroup<District> bfg;
    private Consumer<District> saveListener;
    private Consumer<District> removeListener;

    public DistrictPanel() {
        Design.read(this);

        bfg = new BeanFieldGroup<>(District.class);
        bfg.bind(cbRegion, "region_id");
        bfg.bind(tfNazev, "district_name");

        butEdit.addClickListener(event -> setReadOnly(false));
        butCancel.addClickListener(event -> onCancel());
        butSave.addClickListener(event -> onSave());
        butRemove.addClickListener(event -> onRemove());
    }

    public void setSaveListener(Consumer<District> saveListener) {
        this.saveListener = saveListener;
    }

    public void setRemoveListener(Consumer<District> removeListener) {
        this.removeListener = removeListener;
    }


    public void setDistrict(District district) {

        //update combobox
        cbRegion.removeAllItems();
        locationService.findAllRegions().forEach(region -> {
            cbRegion.addItem(region.getId());
            cbRegion.setItemCaption(region.getId(), region.getPresentationName());
        });

        setValidationVisible(false);
        bfg.setItemDataSource(district);

        Region region = locationService.findOneRegion(district.getRegion_id());
        lblRegion.setValue(region != null ? region.getPresentationName() : null);
        lblNazev.setValue(district.getDistrict_name());

        setReadOnly(district.getId() != null);
    }

    private void onRemove() {
        if (removeListener != null) {
            removeListener.accept(bfg.getItemDataSource().getBean());
        }
    }

    private void onSave() {
        setValidationVisible(true);
        if (bfg.isValid()) {
            try {
                bfg.commit();
                if (saveListener != null) {
                    saveListener.accept(bfg.getItemDataSource().getBean());
                }
            } catch (FieldGroup.CommitException e) {
                throw new RuntimeException("Nepodařilo se uložit okres!", e);
            }
        }
    }

    private void onCancel() {
        if (bfg.getItemDataSource().getBean().getId() == null) {
            setVisible(false);
        } else {
            setReadOnly(true);
        }
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        butEdit.setVisible(readOnly);
        readLayout.setVisible(readOnly);
        editLayout.setVisible(!readOnly);
    }

    private void setValidationVisible(boolean visible) {
        cbRegion.setValidationVisible(visible);
        tfNazev.setValidationVisible(visible);
    }
}
