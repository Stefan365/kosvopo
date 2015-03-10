package sk.stefan.MVP.view.components;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;
import org.apache.log4j.Logger;
import sk.stefan.MVP.model.entity.dao.Kraj;
import sk.stefan.MVP.model.entity.dao.Location;
import sk.stefan.MVP.model.entity.dao.Okres;
import sk.stefan.MVP.model.entity.dao.PublicBody;
import sk.stefan.MVP.model.entity.dao.PublicPerson;
import sk.stefan.MVP.model.entity.dao.PublicRole;
import sk.stefan.MVP.model.entity.dao.Subject;
import sk.stefan.MVP.model.entity.dao.Tenure;
import sk.stefan.MVP.model.entity.dao.Theme;
import sk.stefan.MVP.model.entity.dao.Vote;
import sk.stefan.MVP.model.repo.dao.UniRepo;
import sk.stefan.MVP.view.converters.DateConverter;
import sk.stefan.enums.ZBD_TaskWarnings;
import sk.stefan.enums.VoteAction;
import sk.stefan.enums.VoteResult;
import sk.stefan.listeners.ObnovFilterListener;
import sk.stefan.listeners.OkCancelListener;
import sk.stefan.utils.Tools;

/**
 *
 * Do jiste miry univerzalni formular. Pro zadanou tridu (typu T) samo pomoci
 * reflexe zjisti ake ma parametry a podle nich vytvori potrebne policka a svaze
 * je s FieldGoup-em aby se zmeny ve formulari promitali primo do prislusnich
 * entit.
 *
 * @author Stefan
 * @param <T> Daná třída T, pro kterou se formulář vytvoří
 */
public final class InputFormLayout<T> extends FormLayout {

    private static final Logger log = Logger.getLogger(InputFormLayout.class);

    /**
     * Identifikator:
     */
    private static final long serialVersionUID = 4947104793788125920L;

    /**
     * Class dané třídy T
     */
    private final Class<?> clsT;

    /**
     * Spolu s uzavřením formulárě se musí vykonat další akce v základním view,
     * ze kterého se formulář otevře, na to slouží tento listener.
     */
    private OkCancelListener okCancelListener;
    private ObnovFilterListener obnovFilterListener;

    /**
     * SQLcontainer, na kterém je postavena tabulka s úkoly.
     */
    private final SQLContainer sqlContainer;

    /**
     * FieldGoup je nástroj na svázaní vaadinovské komponenty a nějakého jiného
     * objekty, který dané entitě poskytuje informace k zobrazení.
     */
    private final FieldGroup fg;
    private final Component cp;

    /**
     * Slovník, ve kterém je klíčem název parametru a hodnotou pro něj vhodná
     * interaktivní komponenta. (např. completion_date/DateField)
     */
    private final Map<String, Component> fieldMap;

    /**
     * Proměnná, která ukládá informaci o tom, jestli se bude upravovat již
     * existující položka(false), nebo vytvářet nová(true).
     */
    private final boolean isNew;

    /**
     * Vybraná položka ze SQLContaineru (řádek z tabulky)
     */
    private Item item;
    private PasswordForm passVl;

    /**
     * id této položky.
     */
    private Object itemId;

    private String Tn;

    /**
     * Layout, kde budou zobrazeny interaktivní komponenty všechny kromě
     * tlačítek OK-CANCEL.
     */
    private FormLayout fieldsFL;

    /**
     * Layout pro uložení tlačítek OK-CANCEL.
     */
    private HorizontalLayout buttonsHL;

    /**
     * Tlačítka pro potvrzení, resp. zrušení změn ve formuláři.
     */
    private Button okBt, cancelBt;

    /**
     * Tlacitka, ktore nemaju ist do formulara.
     */
    private final List<String> nonEditFn;

    //0.
    /**
     * Konstruktor.
     *
     * @param clsT Class třídy T
     * @param item položka ze SQLContaineru
     * @param sqlCont SQL container na kterém je postavena tabulka s úkoly.
     * @param cp komponenta, ktora implementuje OkCancelListener i
     * ObnovFilterListener.
     * @param nEditFn zoznam mien parametrov, ktore budu pri tvorbe formularu
     * ignorovane.
     */
    public InputFormLayout(Class<?> clsT, Item item, SQLContainer sqlCont,
            Component cp, List<String> nEditFn) {

        this.cp = cp;
        try {
            Method getTnMethod = clsT.getDeclaredMethod("getTN");
            Tn = (String) getTnMethod.invoke(null);
        } catch (IllegalAccessException | IllegalArgumentException |
                InvocationTargetException | NoSuchMethodException e) {
            log.error(e.getMessage());
        }

        if (nEditFn == null) {
            this.nonEditFn = new ArrayList<>();
        } else {
            this.nonEditFn = nEditFn;
        }

        this.fieldMap = new HashMap<>();
        //securityService = new SecurityServiceImpl();
        this.fg = new FieldGroup();
        this.fg.setBuffered(false);
        this.sqlContainer = sqlCont;
        this.clsT = clsT;
        this.okCancelListener = (OkCancelListener) cp;
        this.obnovFilterListener = (ObnovFilterListener) cp;
        this.item = item;
        if (item == null) {
            isNew = true;
            sqlContainer.removeAllContainerFilters();
            itemId = sqlContainer.addItem();
            this.item = sqlContainer.getItem(itemId);
            //sqlContainer.getItem(itemId).getItemProperty("title").setValue("název...");
            //okCancelListener.obnovFilter();
        } else {
            isNew = false;
        }
        fg.setItemDataSource(this.item);
        try {
            this.initFieldsLayout();
        } catch (IOException ex) {
            log.error(ex);
        }
        this.addButtons();

        // upravy vzhladu:
        this.setMargin(true);
        this.setSpacing(true);
    }

    //1.
    /**
     * Vytvoří formulář s danými políčkami, šitými na míru (šité na mieru typov:
     * Long, String(TextArea/TextField), Boolean(CheckBox), Date(DateField),
     * enum(ComboBox, SelectList, TwinColSelect...)).
     *
     * @throws java.io.IOException
     */
    @SuppressWarnings("unchecked")
    public void initFieldsLayout() throws IOException {

        fieldsFL = new FormLayout();
        fieldsFL.setMargin(true);
        fieldsFL.setSpacing(true);

        String propertyTypeName; // nazov typu danej property danej ent.

        Map<String, Class<?>> mapPar = new HashMap<>();
        try {
            try {
                mapPar = Tools.getTypParametrov(clsT);
            } catch (NoSuchFieldException ex) {
                log.error(ex.getMessage(), ex);
            }

        } catch (SecurityException ex) {
            log.warn(ex.getLocalizedMessage(), ex);
            return;
        }

        for (String pn : mapPar.keySet()) {
            if (nonEditFn.contains(pn)) {
                continue;
            }
            propertyTypeName = mapPar.get(pn).getCanonicalName();

            switch (propertyTypeName) {
                case "java.lang.Integer":
                    if (pn.contains("_id")) {
                        //POZN: parametry POJO by se meli jmenovat stejne ako 
                        // stloupce tabulky a identifikator by se mel jmenovat jen 'id'..
                        Class<?> cls = Tools.getClassFromName(pn);
                        Map<String, Integer> map = Tools.findAllByClass(cls);
                        InputComboBox<Integer> cb = new InputComboBox<>(fg, pn, map);
                        cb.setValue(itemId);
                        fieldMap.put(pn, cb);
                    } else {
                        Component fi = bindTextField(pn);
                        fieldMap.put(pn, fi);
                    }
                    break;

                case "java.lang.String":
                    switch (pn) {
                        case "description":
                            fieldMap.put(pn, bindTextArea(pn));
                            break;
                        case "title":
                            TextField field = bindTextField(pn);
                            field.setRequired(true);
                            fieldMap.put(pn, field);
                            break;
                        default:
                            fieldMap.put(pn, bindTextField(pn));
                            break;
                    }

                    break;

                case "java.lang.Boolean":
                    switch (pn) {
                        case "deleted":
                        case "completed":
                            break;
                        default:
                            fieldMap.put(pn, bindCheckBox(pn));
                    }
                    break;

                case "java.util.Date":
                    switch (pn) {
                        case "creation_date":
                        case "completion_date":
                            //do nothing. tyto datumy se vyplní automaticky.
                            break;
                        default:
                            fieldMap.put(pn, bindUtilDateField(pn));
                            break;
                    }
                    break;
                case "java.sql.Date":
                    fieldMap.put(pn, this.bindSqlDateField(pn));
                    break;

                case "byte[]":
                case "java.lang.Byte[]":
                    if ("password".equals(pn)) {
                        Button but = new Button("kok");
                        but.addClickListener(new Button.ClickListener() {
                            private static final long serialVersionUID = 1L;

                            @Override
                            public void buttonClick(Button.ClickEvent event) {
                                openPasswordWindow();
                            }
                        });
                        fieldMap.put(pn, but);
                    }
                    break;

                case "sk.stefan.enums.VoteResult":
                case "sk.stefan.enums.VoteAction":
                case "sk.stefan.enums.Stability":
                case "sk.stefan.enums.UserType":
                case "sk.stefan.enums.PublicUsefulness":

                    Class<?> cls = mapPar.get(pn);
                    try {
                        Method getNm = cls.getDeclaredMethod("getNames");
                        Method getOm = cls.getDeclaredMethod("getOrdinals");

                        List<String> names = (List<String>) getNm.invoke(null);
                        List<Integer> ordinals = (List<Integer>) getOm.invoke(null);
                        Map<String, Integer> map = Tools.makeEnumMap(names, ordinals);
                        
                        InputComboBox<Integer> cb = new InputComboBox<>(fg, pn, map);
                        if (itemId == null) {
                             Property<?> prop = item.getItemProperty(pn);
                            if (prop.getValue() != null) {
                                cb.setValue(prop.getValue());
                            } else {
                                cb.setValue(VoteResult.getOrdinals().get(0));
                            }
                            
                        }
                        fieldMap.put(pn, cb);
                    } catch (IllegalAccessException | IllegalArgumentException |
                            InvocationTargetException | NoSuchMethodException e) {
                        log.error(e.getMessage());
                    }
                    break;

                default:
                    //ignore: passwords: java.lang.Byte[], byte[], etc...
                    // do nothing
                    break;
            }
        }

        this.completeForm();
        fieldsFL.setEnabled(true);
        this.addComponent(fieldsFL);
    }

    //2.
    /**
     * Rozvhne fields na Layout podle uživatelské logiky věci: pokud logiku
     * neznáme, naháže je tam náhodně:
     *
     */
    private void completeForm() throws IOException {

        String key;
        Properties proPoradie = Tools.getPoradieParams(Tn);
        Properties proDepict = Tools.getDepictParams(Tn);

//        if ("t_tenure".equals(Tn)) {
            for (String s : proDepict.stringPropertyNames()) {
                log.info("ZOBRAZ:*" + s + "* : *" + proDepict.getProperty(s) + "*");
            }
            for (String s : proPoradie.stringPropertyNames()) {
                log.info("PORADIE:*" + s + "* : *" + proPoradie.getProperty(s) + "*");
            }
            
            log.info("TN:" + Tn);
            log.info("SIZE PORADIE:" + proPoradie.size());
            log.info("SIZE DEPICT:" + proDepict.size());
//        }
        for (int i = 1; i < proPoradie.size(); i++) {
            key = proPoradie.getProperty("" + i);
            log.info("KEY: *" + key + "*");
            String cap = proDepict.getProperty(key);
            log.info("CAP: *" + cap + "*");
            (fieldMap.get(key)).setCaption(cap);
            fieldsFL.addComponent(fieldMap.get(key));
        }
    }

    // 3.
    /**
     * Vytvori pole pre textField a zviaze ho s FG.
     *
     * @param fn
     * @return
     */
    public TextField bindTextField(String fn) {
        TextField field = new TextField(fn);
        fg.bind(field, fn);
        return field;
    }

    // 4.
    /**
     * Vytvori pole pre textAreu a zviaze ho s FG.
     *
     * @param fn
     * @return
     */
    public TextArea bindTextArea(String fn) {
        TextArea field = new TextArea(fn);
        fg.bind(field, fn);
        return field;
    }

    // 5.
    /**
     * Vytvori checkBox a zviaze ho s FG.
     *
     * @param fn
     * @return
     */
    public CheckBox bindCheckBox(String fn) {
        CheckBox field = new CheckBox(fn);
        fg.bind(field, fn);
        return field;
    }

    // 6.
    /**
     * Vytvori pole pre util.Date a zviaze ho s FG.
     *
     * @param fn
     * @return
     */
    public PopupDateField bindUtilDateField(String fn) {

        PopupDateField field = new PopupDateField(fn);
        field.setConverter(new DateConverter());
        field.setImmediate(true);
        field.setTimeZone(TimeZone.getTimeZone("GMT+1:00"));
        field.setLocale(new Locale("cz", "CZ"));

        field.setResolution(Resolution.MINUTE);

        field.setDateFormat("yyyy-MM-dd HH:mm:ss");
        fg.bind(field, fn);
        return field;
    }

    // 4.
    /**
     * Vytvori pole pre sql.Date a zviaze ho s FG.
     *
     * @param fn
     * @return
     */
    public PopupDateField bindSqlDateField(String fn) {

        PopupDateField field = new PopupDateField() {
            private static final long serialVersionUID = 1L;

            @Override
            protected java.sql.Date handleUnparsableDateString(String dateString) {

                String fields[] = dateString.split("-");

                if (fields.length >= 3) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat(
                                "yyyy-MM-dd");
                        return (java.sql.Date) sdf.parse(dateString);
                    } catch (ParseException e) {
                        log.error(e.getMessage(), e);
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

    // 7.
    /**
     * Vytvoří, inicializuje a přidá tlačítka OK-CANCEL.
     *
     */
    private void addButtons() {

        buttonsHL = new HorizontalLayout();
        buttonsHL.setMargin(true);
        buttonsHL.setSpacing(true);

        okBt = new Button("Save");
        cancelBt = new Button("Edit");
        cancelBt.setEnabled(true);
        okBt.setEnabled(true);

        okBt.addClickListener(new ClickListener() {
            private static final long serialVersionUID = 1L;

            @Override
            @SuppressWarnings("unchecked")
            public void buttonClick(ClickEvent event) {
                if (isNew) {
                    sqlContainer.removeAllContainerFilters();
                    //sqlContainer.getItem(itemId).getItemProperty("visible").setValue(Boolean.TRUE);
                    if (obnovFilterListener != null) {
                        obnovFilterListener.obnovFilter();
                    }

                }

                // ulozenie zmien do DB:
                try {
                    sqlContainer.commit();
                    fieldsFL.setEnabled(false);
                    fg.setEnabled(false);
                    cancelBt.setEnabled(true);
                    okBt.setEnabled(false);

                    if (okCancelListener != null) {
                        okCancelListener.doOkAction();
                    }
                    Notification.show("Úkol byl úspešně uložen!");

                } catch (SQLException | UnsupportedOperationException e) {
                    log.warn(e.getLocalizedMessage(), e);
                }
            }
        });

        cancelBt.addClickListener(new ClickListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(ClickEvent event) {

                okBt.setEnabled(true);

                if (isNew) {
                    sqlContainer.removeItem(itemId);
                }
                fg.setEnabled(true);
                fieldsFL.setEnabled(true);

                cancelBt.setEnabled(false);
                okBt.setEnabled(true);
                if (okCancelListener != null) {
                    okCancelListener.doCancelAction();
                }
            }
        });
        //TodosView s;
        buttonsHL.addComponent(okBt);
        buttonsHL.addComponent(cancelBt);

        this.addComponent(buttonsHL);

    }


    /**
     * Otvori okno na zmenu hesla:
     *
     *
     */
    private void openPasswordWindow() {
        if (item != null) {
            this.passVl = new PasswordForm(item, cp);
            final OkCancelWindow window = new OkCancelWindow("Zmena hesla",
                    "Zadajte prosím nové heslo", this.passVl);
            UI.getCurrent().addWindow(window);
        } else {
            Notification.show("Vyber nejdříve řádek v tabulce!");
        }

    }

    /**
     * Nastaví listener
     *
     * @param list
     */
    public void setOkCancelListener(OkCancelListener list) {
        this.okCancelListener = list;
    }

    public void setItem(Item it) {
        this.item = it;
        if (item != null) {
            fg.setItemDataSource(this.item);
        }
    }

    public void doEnableButtons() {
        fieldsFL.setEnabled(false);
        fg.setEnabled(false);
        cancelBt.setEnabled(true);
        okBt.setEnabled(false);

        //this.refreshComboboxes();
    }

}
