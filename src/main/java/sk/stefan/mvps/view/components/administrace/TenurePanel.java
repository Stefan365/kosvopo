package sk.stefan.mvps.view.components.administrace;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sk.stefan.mvps.model.entity.Tenure;

import java.util.function.Consumer;

/**
 * Created by elopin on 13.12.2015.
 */
@Component
@Scope("prototype")
@DesignRoot
public class TenurePanel extends CssLayout {

    //Design
    private Label lblCaption;
    private Button butEdit;
    private VerticalLayout readLayout;
    private Label lblTenure;
    private VerticalLayout editLayout;
    private PopupDateField dfSince;
    private PopupDateField dfTill;
    private Button butSave;
    private Button butCancel;
    private Button butRemove;

    //data
    private BeanFieldGroup<Tenure> bfg;
    private Consumer<Tenure> saveListener;
    private Consumer<Tenure> removeListener;

    public TenurePanel() {
        Design.read(this);

        bfg = new BeanFieldGroup<>(Tenure.class);
        bfg.bind(dfSince, "since");
        bfg.bind(dfTill, "till");

        butEdit.addClickListener(event -> setReadOnly(false));
        butCancel.addClickListener(event -> onCancel());
        butSave.addClickListener(event -> onSave());
        butRemove.addClickListener(event -> onRemove());
    }

    public void setSaveListener(Consumer<Tenure> saveListener) {
        this.saveListener = saveListener;
    }

    public void setRemoveListener(Consumer<Tenure> removeListener) {
        this.removeListener = removeListener;
    }

    public void setTenure(Tenure tenure) {

        lblTenure.setValue(tenure.getPresentationName());

        bfg.setItemDataSource(tenure);
        setValidationVisible(false);

        setReadOnly(tenure.getId() != null);
    }

    private void onCancel() {
        if (bfg.getItemDataSource().getBean().getId() == null) {
            setVisible(false);
        } else {
            setReadOnly(true);
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
                throw new RuntimeException("Nepodařilo se uložit období!", e);
            }
        }
    }

    private void onRemove() {
        if (removeListener != null) {
            removeListener.accept(bfg.getItemDataSource().getBean());
        }
    }

    private void setValidationVisible(boolean visible) {
        dfSince.setValidationVisible(visible);
        dfTill.setValidationVisible(visible);
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        butEdit.setVisible(readOnly);
        readLayout.setVisible(readOnly);
        editLayout.setVisible(!readOnly);
    }
}
