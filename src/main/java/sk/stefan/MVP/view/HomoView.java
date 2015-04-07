package sk.stefan.MVP.view;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.AbstractSelect.NewItemHandler;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import java.util.List;
import sk.stefan.MVP.model.entity.dao.A_User;
import sk.stefan.MVP.model.entity.dao.District;
import sk.stefan.MVP.model.repo.dao.UniRepo;
import sk.stefan.MVP.model.service.SecurityService;
import sk.stefan.MVP.model.service.SecurityServiceImpl;
import sk.stefan.MVP.view.components.NavigationComponent;

public class HomoView extends VerticalLayout implements View {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private A_User user;
    private SecurityService securityService;

    private ComboBox okresBox;
    private Label lab;
    private BeanFieldGroup<District> bfg, bfg2;
    private TextField tf, tf1, tf2; // = new TextField("TOTONTO");
    private District okres, o2;
    private Property<District> property;
    private Button save, enter;
    private Button edit;
    private Button remove;

    private HorizontalLayout hl1, hl2;
    // OkresRepo okresRepo;
    private UniRepo<District> okresRepo;

    private Button secondViewButton, firstViewButton;

    //Navigator navigator;
    public HomoView() {
        
        securityService = new SecurityServiceImpl();

        okresRepo = new UniRepo<>(District.class);

        this.setMargin(true);

        this.addFuncionalitu1();

        this.addFuncionalitu2();

        this.addComponent(NavigationComponent.getNavComp());

    }

    public void initCombo(ComboBox com) {
        // List<Okres> okresy = okresRepo.findAllDao();
        List<District> okresy = okresRepo.findAll();

        com.removeAllItems();
        if (okresy != null) {
            for (District o : okresy) {
                com.addItem(o);
                com.setItemCaption(o, o.getDistrict_name());
            }
        }

    }

    private void addFuncionalitu1() {

        // combobox:
        okresBox = new ComboBox("Okresy: ");
        okresBox.setInputPrompt("Vyberte Okres");

        this.initCombo(okresBox);

        /*
         * if (okresy != null) { for (Okres o : okresy) { okresBox.addItem(o);
         * okresBox.setItemCaption(o, o.getOkres_name()); } }
         */
        okresBox.setNewItemHandler(new NewItemHandler() {

            @Override
            public void addNewItem(final String newItemCaption) {
                boolean newItem = true;
                for (final Object itemId : okresBox.getItemIds()) {
                    if (newItemCaption.equalsIgnoreCase(okresBox
                            .getItemCaption(itemId))) {
                        newItem = false;
                        break;
                    }
                }
                if (newItem) {
                    District okres = new District(newItemCaption, 1);

                    okresRepo.save(okres);
                    okresBox.addItem(okres);
                    okresBox.setItemCaption(okres, okres.getDistrict_name());
                    // okresBox.setValue(newItemCaption);

                }
            }
        });

        okresBox.addValueChangeListener(new ValueChangeListener() {

            @Override
            public void valueChange(ValueChangeEvent event) {

                okres = (District) event.getProperty().getValue();
                if (okres != null) {
                    // Notification.show(okres.toString());
                    bfg.setItemDataSource(okres);
                    bfg.bind(tf, "okres_name");
                    save.setEnabled(true);
                    edit.setEnabled(false);

                    // Notification.show("ZMENA OKRESU: " +
                    // okres.getOkres_name());
                }
            }
        });

        okresBox.setImmediate(true);
        okresBox.setNewItemsAllowed(true);
        okresBox.setTextInputAllowed(true);
        okresBox.setNullSelectionAllowed(false);

        // Text Field:
        bfg = new BeanFieldGroup<District>(District.class);

        tf = new TextField("TOTONTO");
        bfg.bind(tf, "okres_name");

        // save button:
        save = new Button("save");
        save.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                if (okres != null) {
                    try {
                        bfg.commit();
                        okresRepo.save(okres);
                        tf.setEnabled(false);
                        event.getButton().setEnabled(false);
                        edit.setEnabled(true);
                        okresBox.setItemCaption(okres, okres.getDistrict_name());

                        Notification.show("Uspesne ulozeno!");

                    } catch (CommitException e) {
                        e.printStackTrace();
                        Notification.show("Ulozenie zlyhalo!");
                    }
                }
            }
        });

        // edit button:
        edit = new Button("edit");
        edit.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                save.setEnabled(true);
                tf.setEnabled(true);
                event.getButton().setEnabled(false);

                // Notification.show("Pripraveno k editaci!");
            }
        });

        // remove button:
        remove = new Button("remove");
        remove.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                if (okres != null && okres.getId() != null) {
                    try {
                        okresRepo.delete(okres);
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    }
                    okresBox.removeItem(okres);
                    // okres = null;
                    Notification.show("Entita uspezne zmazana z DB!");
                }
            }
        });

        save.setEnabled(true);
        edit.setEnabled(false);
        remove.setEnabled(true);

        hl2 = new HorizontalLayout();
        hl2.setMargin(true);
        hl2.setSpacing(true);

        hl2.addComponent(save);
        hl2.addComponent(edit);
        hl2.addComponent(remove);

        this.setMargin(true);
        this.setSpacing(true);

        this.addComponent(okresBox);
        this.addComponent(tf);
        this.addComponent(hl2);

    }

    private void addFuncionalitu2() {

        // Text Field:
        tf1 = new TextField("Find by id or name");
        tf2 = new TextField("Okres:");

        bfg2 = new BeanFieldGroup<District>(District.class);
        bfg2.bind(tf2, "okres_name");

        // enter button:
        enter = new Button("enter");
        enter.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {

                String str = tf1.getValue();

                Integer id;

                o2 = null;

                try {
                    id = Integer.parseInt(str);
                    o2 = okresRepo.findOne(id);

                } catch (java.lang.NumberFormatException | java.util.IllegalFormatConversionException e) {
                    try {
                        o2 = okresRepo.findByParam("okres_name", str).get(0);
                    } catch (IllegalArgumentException e1) {
                        e1.printStackTrace();
                    }

                } catch (SecurityException | IllegalArgumentException e) {
                    e.printStackTrace();
                }

                if (o2 != null) {

                    bfg2.setItemDataSource(o2);
                }

            }
        });

        enter.setEnabled(true);

        hl1 = new HorizontalLayout();
        hl1.setMargin(true);
        hl1.setSpacing(true);

        hl1.addComponent(tf1);
        hl1.addComponent(enter);
        hl1.addComponent(tf2);

        this.addComponent(hl1);

    }

    @Override
    public void enter(ViewChangeEvent event) {
        this.addComponent(NavigationComponent.getNavComp());
        user = securityService.getCurrentUser();
        if (user != null) {
            //do nothing
        } else {
            NavigationComponent.getNavigator().navigateTo("vstupny");
        }

    }

}
