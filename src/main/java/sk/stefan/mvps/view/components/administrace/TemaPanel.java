package sk.stefan.mvps.view.components.administrace;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sk.stefan.mvps.model.entity.Theme;

import java.util.function.Consumer;

/**
 * Created by elopin on 09.12.2015.
 */
@Component
@Scope("prototype")
@DesignRoot
public class TemaPanel extends CssLayout {

    //Design
    private Label lblCaption;
    private Button butEdit;
    private VerticalLayout readLayout;
    private Label lblNazev;
    private Label lblPopis;
    private VerticalLayout editLayout;
    private TextField tfNazev;
    private TextArea areaPopis;
    private Button butSave;
    private Button butCancel;
    private Button butRemove;

    //data
    private BeanFieldGroup<Theme> bfg;
    private Consumer<Theme> saveListener;
    private Consumer<Theme> removeListener;

    public TemaPanel() {
        Design.read(this);

        bfg = new BeanFieldGroup<>(Theme.class);
        bfg.bind(tfNazev, "brief_description");
        bfg.bind(areaPopis, "description");

        butEdit.addClickListener(event -> setReadOnly(false));
        butCancel.addClickListener(event -> onCancel());
        butSave.addClickListener(event -> onSave());
        butRemove.addClickListener(event -> onRemove());
    }

    public void setSaveListener(Consumer<Theme> saveListener) {
        this.saveListener = saveListener;
    }

    public void setRemoveListener(Consumer<Theme> removeListener) {
        this.removeListener = removeListener;
    }


    public void setTheme(Theme theme) {
        setValidationVisible(false);
        bfg.setItemDataSource(theme);

        lblNazev.setValue(theme.getBrief_description());
        lblPopis.setValue(theme.getDescription());
        lblPopis.setVisible(theme.getDescription() != null && !theme.getDescription().isEmpty());

        setReadOnly(theme.getId() != null);
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        butEdit.setVisible(readOnly);
        readLayout.setVisible(readOnly);
        editLayout.setVisible(!readOnly);
    }

    private void setValidationVisible(boolean visible) {
        tfNazev.setValidationVisible(visible);
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
                throw new RuntimeException("Nepodařilo se uložit téma!", e);
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
}
