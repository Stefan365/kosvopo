package sk.stefan.mvps.view.components.layouts;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;
import org.apache.log4j.Logger;
import sk.stefan.converters.DateConverter;
import sk.stefan.enums.VoteResult;
import sk.stefan.listeners.ObnovFilterListener;
import sk.stefan.listeners.OkCancelListener;
import sk.stefan.mvps.model.entity.A_User;
import sk.stefan.mvps.model.entity.A_UserRole;
import sk.stefan.mvps.model.service.SecurityService;
import sk.stefan.mvps.model.service.UniTableService;
import sk.stefan.mvps.model.serviceImpl.SecurityServiceImpl;
import sk.stefan.mvps.model.serviceImpl.UniTableServiceImpl;
import sk.stefan.mvps.view.components.InputClassComboBox;
import sk.stefan.mvps.view.components.InputComboBox;
import sk.stefan.mvps.view.components.OkCancelWindow;
import sk.stefan.mvps.view.components.PasswordForm;
import sk.stefan.utils.ToolsDao;
import sk.stefan.utils.ToolsNames;

/**
 *
 * Do jiste miry univerzalni formular. Pro zadanou tridu (typu T) samo pomoci
 * reflexe zjisti ake ma parametry a podle nich vytvori potrebne policka a svaze
 * je s FieldGoup-em aby se zmeny ve formulari promitali primo do prislusnich
 * entit.
 *
 * @author Stefan
 * @param <E> Daná trieda E, pre ktoru sa formulár vytvori
 */
public class InputFormLayout<E> extends FormLayout {

    private static final Logger log = Logger.getLogger(InputFormLayout.class);

    private static final long serialVersionUID = 4947104793788125920L;

    private final Class<E> clsE;

    private final SecurityService securityService;

    private Map<String, Class<?>> mapPar;//mapa: nazov paramtru vs. jeho trieda.
    private final UniTableService<E> uniTableService;// = new UniTableServiceImpl<>(clsE);
    /**
     * Spolu s uzavřením formulárě se musí vykonat další akce v základním view,
     * ze kterého se formulář otevře, na to slouží tento listener.
     */
    private OkCancelListener okCancelListener;
    private ObnovFilterListener obnovTableFilterListener;
    private final SQLContainer sqlContainer;
    private Item item; // * Vybraná položka ze SQLContaineru (řádek z tabulky)
    private Object itemId;
    private final String tn;

    /**
     * Slovník, ve kterém je klíčem název parametru a hodnotou pro něj vhodná
     * interaktivní komponenta. (např. completion_date/DateField)
     */
    private final Map<String, Component> fieldMap;
    /**
     * FieldGoup je nástroj na svázaní vaadinovské komponenty a nějakého jiného
     * objekty, který dané entitě poskytuje informace k zobrazení.
     */
    private final FieldGroup fg;
    private final Component cp;
    private PasswordForm passVl = null;
    private FormLayout fieldsFL; //     * Layout, kde budou zobrazeny interaktivní komponenty všechny kromě tlačítek OK-CANCEL.
    private HorizontalLayout buttonsHL; //* Layout pro uložení tlačítek OK-CANCEL.
    private Button saveBt, editBt;
    private final Label titleLb;
    
    private Boolean isNew;

    /**
     * Tlacitka, ktore nemaju ist do formulara.
     */
    private final List<String> nonEditFn;

    /**
     * Tlacitka, ktore nemaju byt vo formulari editovatelne, ale maju byt
     * viditelne.
     */
    private final List<String> crutialFn;

    //0.
    /**
     * Konstruktor.
     *
     * @param cls Class třídy E
     * @param item položka ze SQLContaineru
     * @param sqlCont SQL container na kterém je postavena tabulka s úkoly.
     * @param cp komponenta, ktora implementuje OkCancelListener i
     * ObnovFilterListener.
     * @param nEditFn zoznam mien parametrov, ktore budu pri tvorbe formularu
     * ignorovane.
     * @param crutFn
     */
    public InputFormLayout(Class<E> cls, Item item, SQLContainer sqlCont,
            Component cp, String[] nEditFn, String[] crutFn) {

        uniTableService = new UniTableServiceImpl<>(cls);
        this.securityService = new SecurityServiceImpl();
        
        this.isNew = false;
        
        this.clsE = cls;
        try {
            mapPar = ToolsNames.getTypParametrov(clsE, true);
        } catch (NoSuchFieldException | SecurityException ex) {
            mapPar = null;
            log.error(ex.getMessage(), ex);
        }

        //titul:
        this.titleLb = new Label(ToolsDao.getTitleName(cls));
        titleLb.setStyleName(ValoTheme.LABEL_BOLD);

//        this.addComponent(titleLb);
        this.cp = cp;
        tn = ToolsDao.getTableName(cls);

//        neviditelne stlpce:
        if (nEditFn == null) {
            this.nonEditFn = new ArrayList<>();
        } else {
            this.nonEditFn = Arrays.asList(nEditFn);
        }
//        Kriticke stlpce:
        if (crutFn == null) {
            this.crutialFn = new ArrayList<>();
        } else {
            this.crutialFn = Arrays.asList(crutFn);
        }

        this.fieldMap = new HashMap<>();

        //securityService = new SecurityServiceImpl();
        this.fg = new FieldGroup();
        this.fg.setBuffered(false);
        this.sqlContainer = sqlCont;
        this.okCancelListener = (OkCancelListener) cp;
        this.obnovTableFilterListener = (ObnovFilterListener) cp;
        this.item = item;

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
                        InputClassComboBox<?> cb;
                        Class<?> cls = ToolsNames.getClassFromName(pn);

                        cb = new InputClassComboBox<>(fg, pn, cls);
                        cb.setWidth("60%");

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
                            field.setWidth("60%");
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
//                        case "role_id":
//                            nieje potreb to nejak nastavovat, lebo 
//                             sa o to stara vlastne databaza, tj. DEFAULT= 1;
//                            
//                            break;
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

//                        if (uid != null) {
                        Button but = new Button("zmeň heslo");
                        but.addClickListener(new Button.ClickListener() {
                            private static final long serialVersionUID = 1L;

                            @Override
                            public void buttonClick(Button.ClickEvent event) {
                                openPasswordWindow();
//                                    ss
                            }
                        });
                        fieldMap.put(pn, but);
//                        } 
//                        else {
//                            fieldMap.put(pn, this.bindPasswordField(pn));
//                        }

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
                        Map<String, Integer> map = ToolsNames.makeEnumMap(names, ordinals);

                        InputComboBox<Integer> cb = new InputComboBox<>(fg, pn, map);
                        cb.setWidth("60%");

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

        String pn;
        Properties proPoradie = ToolsNames.getPoradieParams(tn);
        Properties proDepict = ToolsNames.getDepictParams(tn);

//        log.info("TN:" + tn);
        for (int i = 1; i < proPoradie.size(); i++) {

            
            pn = proPoradie.getProperty("" + i);
            if (nonEditFn.contains(pn)) {
                continue;
            }

            
//            log.info("KEY: *" + key + "*");
            String cap = proDepict.getProperty(pn);
//            log.info("CAP: *" + cap + "*");
            (fieldMap.get(pn)).setCaption(cap);
            
            fieldsFL.addComponent(fieldMap.get(pn));
        
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
        field.setWidth("60%");
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
        field.setWidth("60%");
        fg.bind(field, fn);
        return field;
    }

    // 5.
    /**
     * Vytvori checkBox a zviaze ho s FG.
     *
     * @param fn fieldname
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
//    // 7.
//    /**
//     * Vytvori pole pre password a zviaze ho s FG.
//     *
//     * @param fn
//     * @return
//     */
//    public PasswordDoubleFieldComponent bindPasswordField(String fn) {
//
//        PasswordDoubleFieldComponent pwdDoubleField = new PasswordDoubleFieldComponent();
//        
//        fg.bind(pwdDoubleField.getPw1(), fn);
//        return pwdDoubleField;
//    }

    // 8.
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

    // 9.
    /**
     * Vytvorí, inicializuje a pridá tlačítka OK-CANCEL.
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

                // ulozenie zmien do DB:
                try {

//                    sqlContainer.commit(); NIE!!! nic sa commitovat nebude, 
//                    vsetko pojde cez jdbc :
                    Integer entId = (Integer) item.getItemProperty("id").getValue();

                    if (entId == null) {
                        item.getItemProperty("visible").setValue(Boolean.TRUE);
                    }

                    E ent = uniTableService.getEntFromItem(item, mapPar);
                    if (passVl != null && passVl.getNewPassword() != null) {
                        ((A_User) ent).setPassword(securityService.encryptPassword(passVl.getNewPassword()));
                    }

                    ent = uniTableService.save(ent, true);

                    if ("a_user".equals(tn) && entId == null) {

                        A_User usr = (A_User) ent;
                        A_UserRole urole = new A_UserRole();
                        urole.setRole_id(1);
                        urole.setSince(new java.util.Date());
                        urole.setUser_id(usr.getId());
                        urole.setActual(Boolean.TRUE);
                        urole.setVisible(Boolean.TRUE);
                        uniTableService.saveUserRole(urole, true);

                    }
                    if (item.getItemProperty("id").getValue() == null) {
                        sqlContainer.removeItem(itemId);
                    }

//                    toto bolo slabe miesto celeho systemu
//                    tento sqlcontainer po ulozeni neumoznuje 
//                    vystopovat ktory item bol zmeneny, pretoze zavola metodu clear
//                    (), ktora vsetky stopy po itemoch vymaze
//                    tj.prave to, co je potrebne na zistenie id
//                    , ktore priradila databaza.ked vtacka lapaju pekne mu spievaju
//                    Kedze nastastie tento sqlcontainer pracuje len s 1 polozkou
//                    lastItemId() fungovat bude.
                    fieldsFL.setEnabled(false);
                    getFg().setEnabled(false);
                    editBt.setEnabled(true);
                    saveBt.setEnabled(false);

                    if (okCancelListener != null) {
                        String getter = "getId";
                        Method getterMethod = clsE.getMethod(getter);

                        Integer entIda = (Integer) getterMethod.invoke(ent);

                        okCancelListener.doOkAction(entIda);
                    }
                    Notification.show("Podložka bola úspešne uložená!");

                } catch (UnsupportedOperationException e) {
                    log.warn(e.getLocalizedMessage(), e);
                } catch (IllegalAccessException |
                        SecurityException | NoSuchMethodException |
                        IllegalArgumentException | InvocationTargetException e) {
//            Notification.show("Chyba, uniRepo::save(...)", Type.ERROR_MESSAGE);
                    log.error("KAROLKO" + e.getMessage(), e);
                }
            }
        });

        editBt.addClickListener(new ClickListener() {

            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(ClickEvent event) {

                getFg().setEnabled(true);
                fieldsFL.setEnabled(true);
                for (String pn : fieldMap.keySet()){
                    if (crutialFn.contains(pn) & !isNew) {
                        (fieldMap.get(pn)).setEnabled(false);
                    }
                }
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
            Integer uid = (Integer) item.getItemProperty("id").getValue();

            this.passVl = new PasswordForm(item, cp, uid);
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

    public void setItem(Object itId, Item it, Boolean isnew) {

        this.itemId = itId;
        this.item = it;
        this.isNew = isnew;
        
        if (tn.equals("a_user") && item != null) {
            Integer uid = (Integer) item.getItemProperty("id").getValue();

            for (String pn : fieldMap.keySet()) {
                if (fieldMap.get(pn) instanceof Button) {
                    String caption;
                    if (uid == null) {
                        caption = "vytvor heslo";
                    } else {
                        caption = "zmeň heslo";
                    }
                    ((Button) fieldMap.get(pn)).setCaption(caption);
                    break;
                }
            }
        }

//        refresh values in comboboxes
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

//    ************* GETTERS AND SETTERS *****************
    public Map<String, Component> getFieldMap() {
        return Collections.unmodifiableMap(fieldMap);
    }

    public Item getItem() {
        return item;
    }

    public Object getItemId() {
        return itemId;
    }

    public FieldGroup getFg() {
        return fg;
    }

    public SQLContainer getSqlContainer() {
        return sqlContainer;
    }

}
