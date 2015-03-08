package sk.stefan.utils;

import sk.stefan.utils.PomDao;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import sk.stefan.MVP.model.repo.dao.UniRepo;
import sk.stefan.MVP.view.components.TBD_ComboBox;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
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

public class TBD_PomFormLayout<T> extends FormLayout {

    /**
     * Identifikator:
     */
    private static final long serialVersionUID = 4947104793788125920L;

    private Class<?> clsT;
    private UniRepo<T> entRepo;
    private BeanFieldGroup<T> bfg;
    private T ent;

    private FormLayout fieldsFL;
    private HorizontalLayout buttonsHL;
    private Button removeBT;
    private Button saveBT, editBT;

    /**
     *
     */
    public TBD_PomFormLayout(Class<?> clsT, BeanFieldGroup<T> bfg) {

        this.clsT = clsT;
        this.entRepo = new UniRepo<>(clsT);
        this.bfg = bfg;
        if (bfg.getItemDataSource() != null) {
            ent = bfg.getItemDataSource().getBean();
            this.setEntity(ent);
            this.initFieldsLayout();
            this.addButtons();

        }
        //upravy vzhladu:
        this.setMargin(true);
        this.setSpacing(true);

    }

    /**
     *
     */
    public boolean setEntity(T ent) {
        if (ent != null) {
            this.ent = ent;
            bfg.setItemDataSource(ent);
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
                        //do nothing, pak bude odkaz na komplikovanu entitu.
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

                    TBD_ComboBox<T, Boolean> cb = new TBD_ComboBox<>(clsT,
                            Boolean.class, bfg, pn, bolList);
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
        bfg.bind(field, fn);
        return field;
    }

    // 2.
    /**
     * Vytvori pole pre textAreu a zviaze ho s BFG.
     *
     */
    public TextArea bindTextArea(String fn) {
        TextArea field = new TextArea(fn);
        bfg.bind(field, fn);
        return field;
    }

    // 3.
    /**
     * Vytvori pole pre util.Date a zviaze ho s BFG.
     *
     */
    public PopupDateField bindUtilDateField(String fn) {
        //PopupDateField field = new PopupDateField();

        PopupDateField field = new PopupDateField() {

            @Override
            protected java.util.Date handleUnparsableDateString(
                    String dateString) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat(
                            "yyyy-MM-dd hh:mm:ss");
                    Date d = (Date) sdf.parse(dateString);

                    return d;
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
        bfg.bind(field, fn);
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
        bfg.bind(field, fn);

        return field;
    }

    // prida tlacitka na pracu s danym layoutom.
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
                bfg.setEnabled(true);
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
                    bfg.commit();
                    entRepo.save(ent);
                    Notification.show("Entita úspešne uložená!");
                } catch (CommitException e1) {
                    Notification.show("Nepodarilo sa commitnut field group!",
                            Type.ERROR_MESSAGE);
                    e1.printStackTrace();
                }

                bfg.setEnabled(false);
                fieldsFL.setEnabled(false);
                editBT.setEnabled(true);
                saveBT.setEnabled(false);
            }
        });

        removeBT.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                if (entRepo.delete(ent)) {
                    Notification.show("Entita úspešne vymazaná!");
                    setVisible(false);
                } else {
                    Notification
                            .show("Nepodarilo sa vymazat entitu, skus najprv vymazat jej podentity!");
                }
            }
        });

        buttonsHL.addComponent(editBT);
        buttonsHL.addComponent(saveBT);
        buttonsHL.addComponent(removeBT);

        this.addComponent(buttonsHL);

    }

}
