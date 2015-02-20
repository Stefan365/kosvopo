package sk.stefan.MVP.view.helpers;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import sk.stefan.MVP.model.repo.dao.UniRepo;
import sk.stefan.MVP.view.components.FGMyComboBox;
import sk.stefan.MVP.view.components.MyComboBox;

import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.VerticalLayout;

public class FGPomFormLayout<T> extends FormLayout {

    /**
     * Identifikator:
     */
    private static final long serialVersionUID = 4947104793788125920L;

    private SQLContainer sqlContainer;

    private Class<?> clsT;
    private UniRepo<T> entRepo;
    private FieldGroup fg;
    private Item item;
    private Object itemId;

    private FormLayout fieldsFL;
    private HorizontalLayout buttonsHL;
    private HorizontalLayout buttonsHLW;
    private Button removeBT;
    private Button saveBT, editBT;
    private Button saveBTW, cancelBTW;

    /**
     *
     */
    public FGPomFormLayout(Class<?> clsT, FieldGroup fg, SQLContainer sqlCont) {

        this.sqlContainer = sqlCont;
        this.clsT = clsT;
        this.entRepo = new UniRepo<>(clsT);
        this.fg = fg;
        if (fg.getItemDataSource() != null) {
            item = fg.getItemDataSource();
            this.setItem(item);
            this.initFieldsLayout();
            this.addButtons();

        }
        // upravy vzhladu:
        this.setMargin(true);
        this.setSpacing(true);

    }

    /**
     *
     */
    public boolean setItem(Item item) {
        if (item != null) {
            this.item = item;
            fg.setItemDataSource(item);
            this.initFieldsLayout();
            this.addButtons();
            this.setVisible(true);
            return true;
        } else {
            this.setVisible(false);
            return false;
        }
    }

    /**
     * Získa formulár s danými políčkami, šité na mieru projektu. tj. šité na
     * mieru typov: Integer, String(TextArea/TextField), Boolean(CheckBox),
     * Date(DateField), enum(ComboBox, SelectList, TwinColSelect...)
     *
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     *
     */
    public void initFieldsLayout() {

        fieldsFL = new FormLayout();
        fieldsFL.setMargin(true);
        fieldsFL.setSpacing(true);

        String propertyTypeName; // nazov typu danej property danej ent.

        Map<String, Class<?>> mapPar;

        try {
            mapPar = PomDao.getTypParametrov(clsT);
        } catch (NoSuchFieldException | SecurityException e1) {
            e1.printStackTrace();
            return;
        }
        for (String pn : mapPar.keySet()) {

            propertyTypeName = mapPar.get(pn).getCanonicalName();

            System.out.println(propertyTypeName);

            switch (propertyTypeName) {
                case "java.lang.Integer":

                    if (pn.contains("_id")) {
                        // do nothing, pak bude odkaz na komplikovanu entitu.
                    } else {
                        Component fi = bindTextField(pn);
                        fieldsFL.addComponent(fi);
                        if ("id".equals(pn)) {
                            fi.setEnabled(false);
                        }
                    }
                    break;

                case "java.lang.String":
                    if ("name".equals(pn) || "description".equals(pn)) {
                        fieldsFL.addComponent(bindTextArea(pn));
                    } else {
                        fieldsFL.addComponent(bindTextField(pn));
                    }
                    break;

                case "java.lang.Boolean":

                    List<Boolean> bolList = new ArrayList<>();
                    bolList.add(Boolean.FALSE);
                    bolList.add(Boolean.TRUE);

                    FGMyComboBox<T, Boolean> cb = new FGMyComboBox<>(clsT,
                            Boolean.class, fg, pn, bolList);
                    fieldsFL.addComponent(cb);
                    break;

                case "java.util.Date":
                    fieldsFL.addComponent(this.bindUtilDateField(pn));
                    break;

                case "java.sql.Date":
                    fieldsFL.addComponent(this.bindSqlDateField(pn));
                    break;

                default:
                    // do nothing
                    break;
            }
        }
        fieldsFL.setEnabled(false);
        this.addComponent(fieldsFL);
    }

    // 1.
    /**
     * Vytvori pole pre textField a zviaze ho s BFG.
     *
     */
    public TextField bindTextField(String fn) {
        TextField field = new TextField(fn);
        fg.bind(field, fn);
        return field;
    }

    // 2.
    /**
     * Vytvori pole pre textAreu a zviaze ho s BFG.
     *
     */
    public TextArea bindTextArea(String fn) {
        TextArea field = new TextArea(fn);
        fg.bind(field, fn);
        return field;
    }

    // 3.
    /**
     * Vytvori pole pre util.Date a zviaze ho s BFG.
     *
     */
    public PopupDateField bindUtilDateField(String fn) {

        PopupDateField field = new PopupDateField() {

            @Override
            protected java.util.Date handleUnparsableDateString(
                    String dateString) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat(
                            "yyyy-MM-dd hh:mm:ss");
                    return (Date) sdf.parse(dateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                    return null;
                }

            }
        };
        field.setImmediate(true);
        field.setTimeZone(TimeZone.getTimeZone("GMT+1:00"));
        field.setLocale(Locale.UK);
        field.setResolution(Resolution.MINUTE);
        field.setDateFormat("yyyy-MM-dd HH:mm:ss");
        fg.bind(field, fn);
        return field;
    }

    // 4.
    /**
     * Vytvori pole pre sql.Date a zviaze ho s BFG.
     *
     */
    public PopupDateField bindSqlDateField(String fn) {

        PopupDateField field = new PopupDateField() {

            @Override
            protected java.sql.Date handleUnparsableDateString(String dateString) {

                String fields[] = dateString.split("-");

                if (fields.length >= 3) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat(
                                "yyyy-MM-dd");
                        return (java.sql.Date) sdf.parse(dateString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        return null;
                    }
                } else {
                    return null;
                }
            }
        };
        field.setImmediate(true);
        field.setTimeZone(TimeZone.getTimeZone("GMT+1:00"));
        field.setLocale(Locale.UK);
        field.setResolution(Resolution.DAY);
        field.setDateFormat("yyyy-MM-dd");
        fg.bind(field, fn);

        return field;
    }

    // prida tlacitka na pracu s danym layoutom.
    /**
     *
     *
     */
    private void addButtons() {

        editBT = new Button("Edit");
        saveBT = new Button("Save");
        removeBT = new Button("Remove");

        buttonsHL = new HorizontalLayout();
        buttonsHL.setMargin(true);
        buttonsHL.setSpacing(true);

        editBT.setEnabled(true);
        saveBT.setEnabled(false);
        removeBT.setEnabled(true);

        editBT.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                fg.setEnabled(true);
                fieldsFL.setEnabled(true);
                editBT.setEnabled(false);
                saveBT.setEnabled(true);
            }
        });

        saveBT.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                // ulozenie zmien do DB:
                try {
                    fg.commit();
                    sqlContainer.rollback();
                    Notification.show("Úkol byl úspešně uložen!");
                } catch (CommitException | SQLException | UnsupportedOperationException e) {
                    Notification.show("Nepodařilo se commitnut field group!",
                            Type.ERROR_MESSAGE);
                    e.printStackTrace();
                }
                fg.setEnabled(false);
                fieldsFL.setEnabled(false);
                editBT.setEnabled(true);
                saveBT.setEnabled(false);
            }
        });

        removeBT.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {

                sqlContainer.removeItem(itemId);

                try {
                    sqlContainer.commit();
                    Notification.show("Úkol úspešne vymazán!");
                    setVisible(false);
                } catch (UnsupportedOperationException | SQLException e) {
                    try {
                        sqlContainer.rollback();
                    } catch (UnsupportedOperationException | SQLException e1) {
                        e1.printStackTrace();
                    }
                    Notification.show("Mazanie sa nepodarilo!",
                            Type.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
        });

        buttonsHL.addComponent(editBT);
        buttonsHL.addComponent(saveBT);
        buttonsHL.addComponent(removeBT);

        this.addComponent(buttonsHL);

    }

    /**
     * Prida tlacitka na FormLayout, kde sa budu vyplnovat udaje pri vzniku
     * entity.
     *
     */
    private void addButtons2() {

        buttonsHLW = new HorizontalLayout();
        buttonsHLW.setMargin(true);
        buttonsHLW.setSpacing(true);

        saveBTW = new Button("Save");
        cancelBTW = new Button("Cancel");
        cancelBTW.setEnabled(true);
        saveBTW.setEnabled(false);

        saveBTW.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                // ulozenie zmien do DB:
                try {
                    fg.commit();
                    sqlContainer.rollback();
                    Notification.show("Úkol byl úspešně uložen!");
                } catch (CommitException | SQLException | UnsupportedOperationException e) {
                    Notification.show("Nepodařilo se commitnut field group!",
                            Type.ERROR_MESSAGE);
                    e.printStackTrace();
                }
                fg.setEnabled(false);
                fieldsFL.setEnabled(false);
                editBT.setEnabled(true);
                saveBT.setEnabled(false);
            }
        });

        removeBT.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {

                sqlContainer.removeItem(itemId);

                try {
                    sqlContainer.commit();
                    Notification.show("Úkol úspešne vymazán!");
                    setVisible(false);
                } catch (UnsupportedOperationException | SQLException e) {
                    try {
                        sqlContainer.rollback();
                    } catch (UnsupportedOperationException | SQLException e1) {
                        e1.printStackTrace();
                    }
                    Notification.show("Mazanie sa nepodarilo!",
                            Type.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
        });

        buttonsHL.addComponent(editBT);
        buttonsHL.addComponent(saveBT);
        buttonsHL.addComponent(removeBT);

        this.addComponent(buttonsHL);

    }

}
