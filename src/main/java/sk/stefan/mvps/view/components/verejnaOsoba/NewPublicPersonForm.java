package sk.stefan.mvps.view.components.verejnaOsoba;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import sk.stefan.annotations.ViewTab;
import sk.stefan.enums.UserType;
import sk.stefan.listeners.SaveListener;
import sk.stefan.mvps.model.entity.PublicPerson;
import sk.stefan.interfaces.TabEntity;
import sk.stefan.mvps.model.service.SecurityService;
import sk.stefan.mvps.view.tabs.TabComponent;
import sk.stefan.utils.DateTimeUtils;
import sk.stefan.utils.Localizator;

/**
 * Formulář pro vytvoření nové veřejné osoby.
 * @author elopin on 18.11.2015.
 */
@SpringComponent
@Scope("prototype")
@ViewTab("novaVerejnaOsoba")
@DesignRoot
public class NewPublicPersonForm extends VerticalLayout implements TabComponent {

    @Autowired
    private SecurityService securityService;

    //Design
    private Panel panel;
    private TextField tfMeno;
    private TextField tfPrijmeni;
    private PopupDateField dfNarozeni;
    private Button butSave;

    // data
    private PublicPerson publicPerson;
    private BeanFieldGroup<PublicPerson> bfg;
    private SaveListener<TabEntity> saveListener;

    public NewPublicPersonForm() {
        Design.read(this);
        Localizator.localizeDesign(this);

        dfNarozeni.setDateFormat(DateTimeUtils.getDatePattern());

        bfg = new BeanFieldGroup<>(PublicPerson.class);
        bfg.bind(tfMeno, "first_name");
        bfg.bind(tfPrijmeni, "last_name");
        bfg.bind(dfNarozeni, "date_of_birth");

        butSave.addClickListener(event -> onSave());
    }

    @Override
    public void setSaveListener(SaveListener<TabEntity> saveListener) {
        this.saveListener = saveListener;
    }

    @Override
    public boolean isUserAccessGranted() {
        return securityService.currentUserHasRole(UserType.ADMIN) || securityService.currentUserHasRole(UserType.VOLUNTEER);

    }

    private void onSave() {
        if (bfg.isValid()) {
            try {
                bfg.commit();
                if (saveListener != null) {
                    saveListener.save(publicPerson);
                }
            } catch (FieldGroup.CommitException e) {
                throw new RuntimeException("Nepodařilo se uložit veřejnou osobu!", e);
            }
        }
    }

    @Override
    public void show() {
        if (bfg.getItemDataSource() == null) {
            publicPerson = new PublicPerson();
            publicPerson.setVisible(true);
            bfg.setItemDataSource(publicPerson);
        }
    }

    @Override
    public String getTabId() {
        return "novaVerejnaOsoba";
    }
}
