package sk.stefan.MVP.view.components;

import com.vaadin.data.Item;
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
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
import sk.stefan.enums.TaskWarnings;
import sk.stefan.enums.VoteResults;
import sk.stefan.listeners.OkCancelListener;
import sk.stefan.listeners.YesNoWindowListener;
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
    private final OkCancelListener okCancelListener;

    /**
     * SQLcontainer, na kterém je postavena tabulka s úkoly.
     */
    private final SQLContainer sqlContainer;

    /**
     * FieldGoup je nástroj na svázaní vaadinovské komponenty a nějakého jiného
     * objekty, který dané entitě poskytuje informace k zobrazení.
     */
    private final FieldGroup fg;

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

    /**
     * id této položky.
     */
    private Object itemId;

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
     * @param okl listener pro vykonání dodatečných akcí spojených s OK-CANCEL.
     * @param nEditFn zoznam mien parametrov, ktore budu pri tvorbe formularu
     * ignorovane.
     */
    public InputFormLayout(Class<?> clsT, Item item, SQLContainer sqlCont,
            OkCancelListener okl, List<String> nEditFn) {

        if (nEditFn == null) {
            this.nonEditFn = new ArrayList<>();
        } else {
            this.nonEditFn = nEditFn;
        }

        this.fieldMap = new HashMap<>();
        //securityService = new SecurityServiceImpl();
        this.fg = new FieldGroup();
        fg.setBuffered(false);
        this.sqlContainer = sqlCont;
        this.clsT = clsT;
        this.okCancelListener = okl;
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
        this.initFieldsLayout();
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
     */
    public void initFieldsLayout() {

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
                        Class<?> cls = getClassFromName(pn);
                        Map<String, Integer> map = findAllByClass(cls);
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

                case "sk.stefan.enums.VoteResults":
                    Map<String, Integer> map;
                    map = Tools.makeEnumMap(VoteResults.getPeriodsNames(),
                            VoteResults.getOrdinals());
                    InputComboBox<Integer> cb = new InputComboBox<>(fg, pn, map);
                    if (itemId == null) {
                        cb.setValue(VoteResults.values()[1]);
//                        cb.setValue(VoteResults.APPROVED);                      
                    }
                    fieldMap.put(pn, cb);
                    break;

                case "sk.stefan.enums.TaskWarnings":
                    Map<String, Integer> map1 = Tools.makeEnumMap(TaskWarnings.getWarningNames(),
                            TaskWarnings.getOrdinals());
                    InputComboBox<Integer> cb1 = new InputComboBox<>(fg, pn, map1);
                    if (itemId == null) {
                        cb1.setValue(1);
                    }
                    fieldMap.put(pn, cb1);
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
    private void completeForm() {

        switch (clsT.getCanonicalName()) {
            case "cz.iivos.todo.model.Task":
                fieldMap.get("title").setCaption("název: ");
                fieldsFL.addComponent(fieldMap.get("title"));

                fieldMap.get("description").setCaption("popis: ");
                fieldsFL.addComponent(fieldMap.get("description"));

                fieldMap.get("id_tcy").setCaption("kategorie: ");
                fieldsFL.addComponent(fieldMap.get("id_tcy"));

                fieldMap.get("repetition_period").setCaption("opakování: ");
                fieldsFL.addComponent(fieldMap.get("repetition_period"));

                fieldMap.get("warning_period").setCaption("upozornění: ");
                fieldsFL.addComponent(fieldMap.get("warning_period"));

                fieldMap.get("deadline").setCaption("termín: ");
                fieldsFL.addComponent(fieldMap.get("deadline"));

                break;

            default:
                //náhodné rozvržení:
                for (String key : fieldMap.keySet()) {
                    fieldsFL.addComponent(fieldMap.get(key));
                }
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
                    okCancelListener.obnovFilter();
                }

                // ulozenie zmien do DB:
                try {
                    sqlContainer.commit();
                    fieldsFL.setEnabled(false);
                    fg.setEnabled(false);
                    cancelBt.setEnabled(true);
                    okBt.setEnabled(false);

                    if (okCancelListener != null) {
                        okCancelListener.doAdditionalOkAction();
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
                    okCancelListener.doAdditionalCancelAction();
                }
            }
        });
        //TodosView s;
        buttonsHL.addComponent(okBt);
        buttonsHL.addComponent(cancelBt);

        this.addComponent(buttonsHL);

    }

    /**
     * Vrati vhodnu triedu podla nazvu FK entity.
     *
     *
     */
    private Class<?> getClassFromName(String pn) {

        switch (pn) {

            case "kraj_id":
                return Kraj.class;
            case "okres_id":
                return Okres.class;
            case "public_person_id":
                return PublicPerson.class;
            case "location_id":
                return Location.class;
            case "public_role_id":
                return PublicRole.class;
            case "public_body_id":
                return PublicBody.class;
            case "vote_id":
                return Vote.class;
            case "subject_id":
                return Subject.class;
            case "theme_id":
                return Theme.class;
            case "tenure_id":
                return Tenure.class;
            default:
                return null;
        }
    }

    @SuppressWarnings({"unchecked"})
    private Map<String, Integer> findAllByClass(Class<?> cls) {

        Map<String, Integer> map = new HashMap<>();
        String repN;
        Integer id;

        try {
            Class<?> repoCls = Class.forName("sk.stefan.MVP.model.repo.dao.UniRepo");
            Constructor<UniRepo<? extends Object>> repoCtor;
            repoCtor = (Constructor<UniRepo<? extends Object>>) repoCls.getConstructor(Class.class);
            List<? extends Object> listObj;
            listObj = repoCtor.newInstance(cls).findAll();
            log.info("KARAMAZOV: " + (listObj == null));
            for (Object o : listObj) {
                Method getRepNameMethod = cls.getDeclaredMethod("getPresentationName");
                repN = (String) getRepNameMethod.invoke(o);
                Method getIdMethod = cls.getDeclaredMethod("getId");
                id = (Integer) getIdMethod.invoke(o);
                map.put(repN, id);
            }
            return map;
        } catch (NoSuchMethodException | InvocationTargetException |
                IllegalArgumentException | IllegalAccessException |
                InstantiationException | SecurityException | ClassNotFoundException ex) {
            log.error(ex.getMessage());
        }
        return null;
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
