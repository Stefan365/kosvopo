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
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;
import org.apache.log4j.Logger;
import sk.stefan.MVP.view.converters.DateConverter;
import sk.stefan.enums.VoteResult;
import sk.stefan.listeners.ObnovFilterListener;
import sk.stefan.listeners.OkCancelListener;
import sk.stefan.utils.ToolsDao;
import sk.stefan.utils.ToolsNazvy;

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
public class InputFormLayout<T> extends FormLayout {

    private static final Logger log = Logger.getLogger(InputFormLayout.class);

    private static final long serialVersionUID = 4947104793788125920L;

    private final Class<?> clsT;

    /**
     * Spolu s uzavřením formulárě se musí vykonat další akce v základním view,
     * ze kterého se formulář otevře, na to slouží tento listener.
     */
    private OkCancelListener okCancelListener;
    private ObnovFilterListener obnovFilterListener;

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
    private boolean isNew;

    /**
     * Vybraná položka ze SQLContaineru (řádek z tabulky)
     */
    private Item item;
    private PasswordForm passVl;

    /**
     * id této položky.
     */
    private Object itemId;

    private String tn;

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
    private Button saveBt, editBt;

    /**
     * Tlacitka, ktore nemaju ist do formulara.
     */
    private final List<String> nonEditFn;
    
    private final Label titleLb;

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

        //titul:
        String title = ToolsDao.getTitleName(clsT);
        this.titleLb = new Label(title);
        titleLb.setStyleName(ValoTheme.LABEL_BOLD);

//        this.addComponent(titleLb);

        this.cp = cp;
        tn = ToolsDao.getTableName(clsT);

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
        if (item != null){
            isNew = (item.getItemProperty("id").getValue() == null);
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
     *
     */
    @SuppressWarnings("unchecked")
    public void initFieldsLayout() {

        fieldsFL = new FormLayout();
        fieldsFL.setMargin(true);
        fieldsFL.setSpacing(true);

        String propertyTypeName; // nazov typu danej property danej ent.

        Map<String, Class<?>> mapPar = new HashMap<>();
        try {
            try {
                mapPar = ToolsNazvy.getTypParametrov(clsT);
            } catch (NoSuchFieldException ex) {
                log.error(ex.getMessage(), ex);
            }

        } catch (SecurityException ex) {
            log.warn(ex.getLocalizedMessage(), ex);
            return;
        }

        //vyber vhodnych komponent do form layoutu:
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
                        InputNewComboBox<?> cb;
                        Class<?> cls = ToolsNazvy.getClassFromName(pn);
//                        log.info("KOKOS1 clsT: "+clsT);
//                        log.info("KOKOS2 cls: "+cls);
//                        log.info("KOKOS3 pn: "+pn);
                        
                        cb = new InputNewComboBox<>(fg, pn, cls);
                        
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
                case "sk.stefan.enums.PublicRoleType":
                case "sk.stefan.enums.FilterType":
                case "sk.stefan.enums.NonEditableFields":


                    Class<?> cls = mapPar.get(pn);
                    try {
                        Method getNm = cls.getMethod("getNames");
                        Method getOm = cls.getMethod("getOrdinals");

                        List<String> names = (List<String>) getNm.invoke(null);
                        List<Integer> ordinals = (List<Integer>) getOm.invoke(null);
                        Map<String, Integer> map = ToolsNazvy.makeEnumMap(names, ordinals);

                        InputComboBox<Integer> cb = new InputComboBox<>(fg, pn, map);
                        
                        if (item != null) {
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
        try {
            this.completeForm();
            fieldsFL.setEnabled(true);
            this.addComponent(fieldsFL);
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    //2.
    /**
     * Rozvhne fields na Layout podle uživatelské logiky věci: pokud logiku
     * neznáme, naháže je tam náhodně:
     *
     */
    private void completeForm() throws IOException {

        String key;
        Properties proPoradie = ToolsNazvy.getPoradieParams(tn);
        Properties proDepict = ToolsNazvy.getDepictParams(tn);

//        if ("t_tenure".equals(Tn)) {
//        for (String s : proDepict.stringPropertyNames()) {
//            log.info("ZOBRAZ:*" + s + "* : *" + proDepict.getProperty(s) + "*");
//        }
//        for (String s : proPoradie.stringPropertyNames()) {
//            log.info("PORADIE:*" + s + "* : *" + proPoradie.getProperty(s) + "*");
//        }
//
//        log.info("TN:" + tn);
//        log.info("SIZE PORADIE:" + proPoradie.size());
//        log.info("SIZE DEPICT:" + proDepict.size());
//        }
        for (int i = 1; i < proPoradie.size(); i++) {
            
            key = proPoradie.getProperty("" + i);
            if (nonEditFn.contains(key)){
                continue;
            }
            
//            log.info("KEY: *" + key + "*");
            String cap = proDepict.getProperty(key);
//            log.info("CAP: *" + cap + "*");
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

        saveBt = new Button("Uložiť");
        editBt = new Button("Editovať");
        editBt.setEnabled(true);
        saveBt.setEnabled(true);

        saveBt.addClickListener(new ClickListener() {
            private static final long serialVersionUID = 1L;

            @Override
            @SuppressWarnings("unchecked")
            public void buttonClick(ClickEvent event) {
//                if (isNew) {
//                    sqlContainer.removeAllContainerFilters();
//                    dsfds
//                    sqlContainer.getItem(itemId).getItemProperty("visible").setValue(Boolean.TRUE);
//                    if (obnovFilterListener != null) {
//                        obnovFilterListener.obnovFilter();
//                    }
//
//                }

                // ulozenie zmien do DB:
                try {
                    Object ids = itemId;
                    Item ite = item;
                    sqlContainer.commit();
                    itemId = sqlContainer.lastItemId();
                    item = sqlContainer.getItem(itemId);
//                    toto je slabe miesto celeho systemu
//                    tento drbnuty sqlcontainer po ulozeni neumoznuje 
//                    vystopovat ktory item bol zmeneny, 
//                    pretoze zavola metodu clear(), ktora vsetky stopy po itemoch vymaze.
//                    tj. prave to, co je potrebne na zistenie id, ktore priradila databaza.
//                        ked vtacka lapaju pekne mu spievaju
//                    Kedze nastastie tento sqlcontainer pracuje len s 1 polozkou,
//                    lastItemId() fungovat bude.
//                    log.info("KOKOSKO ITEMID: " +  (itemId == ids));
//                    log.info("KOKOSKO ITEM: " +  (item == ite));

                    
                    fieldsFL.setEnabled(false);
                    getFg().setEnabled(false);
                    editBt.setEnabled(true);
                    saveBt.setEnabled(false);

                    if (okCancelListener != null) {
                        okCancelListener.doOkAction();
                    }
                    Notification.show("Úkol byl úspešně uložen!");

                } catch (SQLException | UnsupportedOperationException e) {
                    log.warn(e.getLocalizedMessage(), e);
                }
            }
        });

        editBt.addClickListener(new ClickListener() {
            
            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(ClickEvent event) {

//                saveBt.setEnabled(true);

//                if (isNew) {
//                    sqlContainer.removeItem(itemId);
//                }
                getFg().setEnabled(true);
                fieldsFL.setEnabled(true);

                editBt.setEnabled(false);
                saveBt.setEnabled(true);
                if (okCancelListener != null) {
                    okCancelListener.doCancelAction();
                }
            }
        });
        //TodosView s;
        buttonsHL.addComponent(saveBt);
        buttonsHL.addComponent(editBt);

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

    public void setItem(Object itId, Item it) {
        if (it != null){
            isNew = (it.getItemProperty("id").getValue() == null);
        } else {
            isNew = false;
        }

        this.itemId = itId;
        this.item = it;
        if (item != null) {
            fg.setItemDataSource(this.item);
        }
    }

    public void doEnableButtons() {
        fieldsFL.setEnabled(false);
        fg.setEnabled(false);
        editBt.setEnabled(true);
        saveBt.setEnabled(false);

        //this.refreshComboboxes();
    }

//    /**
//     * Neuniverzalna metoda len pre potreby hlasovania. 
//     * zuniverzalnit v buducnosti.
//     * 
//     * @param vot
//     */
//    public void updateVote(Vote vot){
//        
//        ToolsDao.updateVoteItem(item, vot);
//        
//    }
//    
//    /**
//     * Neuniverzalna metoda na filtrovanie subject_id po zadani publicBody objektu.
//     * Potom to zuniverzalnit.
//     * Ta neuniverzalita vyplyva z poziadavku vyplnat Vote a Role of Vote naraz.
//     * keby sa to nemuselo davat dohromady, takto by som to neprznil.
//     */
//    public void filterSubject(PublicBody pubBody){
//        
//        
//    }
    /**
     * @return the fieldMap
     */
    public Map<String, Component> getFieldMap() {
        return Collections.unmodifiableMap(fieldMap);
    }

    public Item getItem() {
        return item;
    }
    
    public Object getItemId() {
        return itemId;
    }


    /**
     * @return the fg
     */
    public FieldGroup getFg() {
        return fg;
    }

    /**
     * @return the sqlContainer
     */
    public SQLContainer getSqlContainer() {
        return sqlContainer;
    }

}
