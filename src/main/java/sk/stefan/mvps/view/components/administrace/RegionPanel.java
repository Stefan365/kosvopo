package sk.stefan.mvps.view.components.administrace;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sk.stefan.mvps.model.entity.Region;
import sk.stefan.utils.Localizator;

import java.util.function.Consumer;

/**
 * Panel pro detail regionu.
 * @author by elopin on 06.12.2015.
 */
@Component
@Scope("prototype")
@DesignRoot
public class RegionPanel extends CssLayout {

    /**
     * Design
     */
    private Label lblCaption;
    private Button butEdit;
    private VerticalLayout readLayout;
    private Label lblNazev;
    private VerticalLayout editlayout;
    private TextField tfNazev;
    private Button butSave;
    private Button butCancel;
    private Button butRemove;

    //data
    private BeanFieldGroup<Region> bfg;
    private Consumer<Region> saveListener;
    private Consumer<Region> removeListener;

    public RegionPanel() {
        Design.read(this);
        Localizator.localizeDesign(this);

        bfg = new BeanFieldGroup<>(Region.class);
        bfg.bind(tfNazev, "region_name");

        butEdit.addClickListener(event -> setReadOnly(false));
        butCancel.addClickListener(event -> onCancel());
        butSave.addClickListener(event -> onSave());
        butRemove.addClickListener(event -> onRemove());
    }

    public void setSaveListener(Consumer<Region> saveListener) {
        this.saveListener = saveListener;
    }

    public void setRemoveListener(Consumer<Region> removeListener) {
        this.removeListener = removeListener;
    }

    public void setRegion(Region region) {
        setValidationVisible(false);
        bfg.setItemDataSource(region);

        lblNazev.setValue(region.getPresentationName());

        setReadOnly(region.getId() != null);
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        butEdit.setVisible(readOnly);
        readLayout.setVisible(readOnly);
        editlayout.setVisible(!readOnly);
        butRemove.setVisible(!readOnly && bfg.getItemDataSource().getBean().getId() != null);
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
                throw new RuntimeException("Nepodařilo se uložit kraj!", e);
            }
        }
    }

    private void onRemove() {
        if (removeListener != null) {
            removeListener.accept(bfg.getItemDataSource().getBean());
        }
    }


    private void onCancel() {
        if (bfg.getItemDataSource().getBean().getId() == null) {
            setVisible(false);
        } else {
            setReadOnly(true);
        }
    }

    private void setValidationVisible(boolean visible) {
        tfNazev.setValidationVisible(visible);
    }
}
