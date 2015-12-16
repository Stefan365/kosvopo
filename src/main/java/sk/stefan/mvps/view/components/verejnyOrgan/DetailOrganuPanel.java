package sk.stefan.mvps.view.components.verejnyOrgan;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import sk.stefan.listeners.RemoveListener;
import sk.stefan.listeners.SaveListener;
import sk.stefan.mvps.model.entity.Location;
import sk.stefan.mvps.model.entity.PublicBody;
import sk.stefan.mvps.model.service.LocationService;
import sk.stefan.mvps.view.components.ImageComponent;

import java.util.HashMap;
import java.util.Map;

/**
 * Panel s detailem verejného orgánu.
 * @author elopin on 09.11.2015.
 */
@SpringComponent
@Scope("prototype")
@DesignRoot
public class DetailOrganuPanel extends CssLayout {

    @Autowired
    private LocationService locationService;

    // Design
    private ImageComponent imageComponent;
    private Label lblCaption;
    private Button butEdit;
    private VerticalLayout readLayout;
    private Label lblName;
    private Label lblObec;
    private VerticalLayout editLayout;
    private TextField tfName;
    private ComboBox cbObec;
    private Button butSave;
    private Button butCancel;
    private Button butRemove;

    // data
    private PublicBody publicBody;
    private BeanFieldGroup<PublicBody> bfg = new BeanFieldGroup<>(PublicBody.class);
    private SaveListener<PublicBody> saveListener;
    private RemoveListener<PublicBody> removeListener;

    private Map<Integer, Integer> locationMap = new HashMap<>();

    public DetailOrganuPanel() {
        Design.read(this);

        butEdit.addClickListener(event -> setReadOnly(false));
        butCancel.addClickListener(event -> onCancel());
        butSave.addClickListener(event -> onSave());
        butRemove.addClickListener(event -> onRemove());

        bfg.bind(tfName, "name");
        bfg.bind(cbObec, "location_id");
    }

    public void setSaveListener(SaveListener<PublicBody> saveListener) {
        this.saveListener = saveListener;
    }

    public void setRemoveListener(RemoveListener<PublicBody> removeListener) {
        this.removeListener = removeListener;
    }

    public void setPublicBody(PublicBody publicBody) {
        this.publicBody = publicBody;
        bfg.setItemDataSource(publicBody);

        //update location combobox
        cbObec.removeAllItems();
        locationMap.clear();
        locationService.findAll().forEach(location -> {
            cbObec.addItem(location.getId());
            cbObec.setItemCaption(location.getId(), location.getPresentationName());
            locationMap.put(location.getId(), location.getId());
        });

        // fill data component
        lblName.setValue(publicBody.getName());
        lblObec.setValue(locationService.findOneLocation(publicBody.getLocation_id()).getLocation_name());
        cbObec.setValue(locationMap.get(publicBody.getLocation_id()));

        imageComponent.setImage(publicBody.getImage());
        setReadOnly(true);
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        butEdit.setVisible(readOnly);
        readLayout.setVisible(readOnly);
        imageComponent.setReadOnly(readOnly);
        editLayout.setVisible(!readOnly);
    }

    /**
     * Uloží verejný orgán.
     */
    private void onSave() {
        if (bfg.isValid()) {
            try {
                bfg.commit();
                publicBody.setImage(imageComponent.getImage());
                if (saveListener != null) {
                    saveListener.save(publicBody);
                }
                setPublicBody(publicBody);
            } catch (FieldGroup.CommitException e) {
                throw new RuntimeException("Nepodarilo sa uložiť verejný orgán!", e);
            }
        }
    }

    private void onCancel() {
        imageComponent.setImage(publicBody.getImage());
        setReadOnly(true);
    }

    /**
     * Odstraní verejný orgán.
     */
    private void onRemove() {
        if (removeListener != null) {
            removeListener.remove(publicBody);
        }
    }
}
