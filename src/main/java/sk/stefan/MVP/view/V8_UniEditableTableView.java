/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view;

import com.vaadin.data.Container.Filter;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.data.util.filter.Like;
import com.vaadin.data.util.filter.Or;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.event.FieldEvents;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;
import sk.stefan.DBconnection.DoDBconn;
import sk.stefan.MVP.model.entity.A_User;
import sk.stefan.MVP.model.service.SecurityService;
import sk.stefan.MVP.model.service.UniTableService;
import sk.stefan.MVP.model.service.UserService;
import sk.stefan.MVP.model.serviceImpl.SecurityServiceImpl;
import sk.stefan.MVP.model.serviceImpl.UniTableServiceImpl;
import sk.stefan.MVP.model.serviceImpl.UserServiceImpl;
import sk.stefan.MVP.view.components.InputFormLayout;
import sk.stefan.MVP.view.components.MyTable;
import sk.stefan.MVP.view.components.YesNoWindow;
import sk.stefan.MVP.view.components.filtering.FilteringComponent;
import sk.stefan.enums.UserType;
import sk.stefan.listeners.ObnovFilterListener;
import sk.stefan.listeners.OkCancelListener;
import sk.stefan.listeners.RefreshViewListener;
import sk.stefan.listeners.YesNoWindowListener;
import sk.stefan.utils.ToolsDao;
import sk.stefan.utils.ToolsNames;

/**
 *
 * Class for Editable environment for editing database table.
 *
 * @author stefan
 * @param <E> type of UniEditableTableView
 *
 */
public final class V8_UniEditableTableView<E> extends VerticalLayout implements OkCancelListener,
        RefreshViewListener, ObnovFilterListener, View {

    private static final Logger log = Logger.getLogger(V8_UniEditableTableView.class);
    private static final long serialVersionUID = 1L;

    private final SecurityService securityService;
    private final UniTableService<E> uniTableService;
    private final UserService userService;

    private final Boolean isForAdminOnly;

    /* User interface components are stored in session. */
//    private Class<E> clsE;
    private Object itemId;
    private Item item;
    private MyTable uniTable;// = new MyTable();
    private TextField searchField;// = new TextField();
    private Button addNewItemBt;// = new Button("Nová podložka");
    private Button removeItemBt;// = new Button("Odstráň túto podložku");
    private FormLayout editorLayout;// = new FormLayout();
    private final FieldGroup fg = new FieldGroup();
    private Button backBt;

    //split panel:
    private HorizontalSplitPanel splitPanel;
    private VerticalLayout leftLayout;
    private HorizontalLayout bottomLeftLayout;
    private FilteringComponent filters;

    //Class specific:
    private SQLContainer sqlContainer;
    private final String tn;
//    private List<String> visibleFn;
    private List<String> nonEditFn;
    private String[] visibleColDepictNames;
    private String[] visibleColDbNames;

    private final InputFormLayout<E> inputForm;
    private VerticalLayout linksVl;

    private final Filter basicFilter;
    private boolean isAdmin;
    private Filter userOnlyFilter;

    //0.Konstruktor
    /**
     *
     * @param clsq
     * @param uneditCol
     * @param isAdm
     */
    public V8_UniEditableTableView(Class<E> clsq, String[] uneditCol, Boolean isAdm) {

        this.setMargin(true);
        this.setSpacing(true);
        
        tn = ToolsDao.getTableName(clsq);
        //dalsie komponenty:
        basicFilter = new Compare.Equal("visible", Boolean.TRUE);
        
        try {
            sqlContainer = DoDBconn.createSqlContainera(tn);
            obnovFilter();
        } catch (SecurityException | SQLException e) {
            log.error(e.getMessage());
        }

//        servisy:
        this.securityService = new SecurityServiceImpl();
        this.uniTableService = new UniTableServiceImpl<>(clsq);
        this.userService = new UserServiceImpl();

        //je to komponenta len pre admina?
        this.isForAdminOnly = isAdm;


        this.initTableLists(uneditCol);

        this.inputForm = new InputFormLayout<>(clsq, item, sqlContainer, this, nonEditFn);

        initAllBasic();

    }

    /**
     *
     */
    private void initAllBasic() {

        this.removeAllComponents();
        
        initLayout();
        initUniTable();
        initLinks();
        initEditor();
        initSearch();
        initAddRemoveButtons();

        this.addComponents(splitPanel, linksVl);

    }

    private void initLinks() {
        linksVl = new VerticalLayout();
        linksVl.setMargin(true);
        linksVl.setSpacing(true);
        linksVl.addComponent(backBt);
    }

    //1.
    /**
     * Zakladny layout
     */
    private void initLayout() {

        backBt = new Button("naspať", (Button.ClickEvent event) -> {
            UI.getCurrent().getNavigator().navigateTo("V7_AdministrationView");
        });

        uniTable = new MyTable();
        searchField = new TextField();
        addNewItemBt = new Button("Nová podložka");
        removeItemBt = new Button("Odstráň túto podložku");
        editorLayout = new FormLayout();

        splitPanel = new HorizontalSplitPanel();

        leftLayout = new VerticalLayout();
        splitPanel.addComponent(leftLayout);
        splitPanel.addComponent(editorLayout);

        leftLayout.addComponent(uniTable);
        bottomLeftLayout = new HorizontalLayout();
        filters = new FilteringComponent(tn, sqlContainer);

        leftLayout.addComponents(bottomLeftLayout, filters);

        bottomLeftLayout.addComponent(searchField);
        

        // leftLayout.setSizeFull();
        leftLayout.setExpandRatio(uniTable, 1);
        uniTable.setSizeFull();

        bottomLeftLayout.setWidth("100%");
        searchField.setWidth("100%");
        bottomLeftLayout.setExpandRatio(searchField, 1);

        /* Put a little margin around the fields in the right side editor */
        editorLayout.setMargin(true);
        editorLayout.setVisible(false);
        
//        if ("a_user".equals(tn)){
//            initUsersFilter();
//            if (isAdmin){
//                bottomLeftLayout.addComponent(addNewItemBt);
//                bottomLeftLayout.addComponent(removeItemBt);               
//            }
//        } else {
//            bottomLeftLayout.addComponent(addNewItemBt);
//            bottomLeftLayout.addComponent(removeItemBt);
//
//        }

    }

    //2.
    private void initEditor() {

        //editorLayout.addComponent(removeItemButton);
        removeItemBt.setEnabled(true);
//        log.info("INITEDIOTR1:" + (editorLayout == null));
//        log.info("INITEDIOTR2:" + (inputForm == null));
        if (inputForm != null) {
            editorLayout.addComponent(inputForm);
        }

        fg.setBuffered(false);
    }

    //3.
    private void initSearch() {

        searchField.setInputPrompt("možeš použiť vyhľadávanie");
        searchField.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.LAZY);

        searchField.addTextChangeListener(new FieldEvents.TextChangeListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void textChange(final FieldEvents.TextChangeEvent event) {
                Filter o;
                List<Filter> fls;

                /* Reset the filter for the contactContainer. */
                sqlContainer.removeAllContainerFilters();
                String tx = "%" + event.getText() + "%";
                if (!"".equals(tx)) {
                    fls = new ArrayList<>();
                    for (String s : visibleColDbNames) {
                        fls.add(new Like(s, tx));
                    }
                    o = new Or(fls.toArray(new Filter[0]));
                    sqlContainer.addContainerFilter(o);
                }
                obnovFilter();
            }
        });
    }

    //4.
    /**
     * Pridávanie základných tlačítiek "Pridaj" a "Odober"
     */
    private void initAddRemoveButtons() {

        addNewItemBt.addClickListener(new Button.ClickListener() {

            private static final long serialVersionUID = 1L;

            @Override
            @SuppressWarnings("unchecked")
            public void buttonClick(Button.ClickEvent event) {

                sqlContainer.removeAllContainerFilters();
                itemId = sqlContainer.addItem();
                item = sqlContainer.getItem(itemId);
                if (item.getItemProperty("visible") != null) {
                    item.getItemProperty("visible").setValue(Boolean.TRUE);
                    if ("a_user".equals(tn)){
                        initNewUser(item);
                    }
                    Notification.show("Pridal som novu podložkuou!");
                }
                inputForm.setItem(itemId, item);
                editorLayout.setVisible(itemId != null);

                obnovFilter();

//              uniTable.getContainerProperty(itemId, visibleFn.get(2)).setValue("New");
                uniTable.select(itemId);

                sqlContainer.refresh();
                uniTable.refreshRowCache();
                
            
            }
        });

        removeItemBt.addClickListener(new Button.ClickListener() {

            
            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(Button.ClickEvent event) {

                if (item != null) {
                    final YesNoWindow window = new YesNoWindow("Upozornenie",
                            "Chcete to zmazať?", new DeleteTaskListener());
                    UI.getCurrent().addWindow(window);
                } else {
                    Notification.show("Vyber nejprv riadok v tabuľke!");
                }
            }
        });
        
    }
    
    @SuppressWarnings("unchecked")
    private void initNewUser(Item item){
        
        item.getItemProperty("first_name").setValue("proto_user");
        item.getItemProperty("last_name").setValue("proto_user");
        item.getItemProperty("login").setValue("proto_user");
        item.getItemProperty("e_mail").setValue("proto_user");
        item.getItemProperty("password").setValue(securityService.encryptPassword("proto"));
        
    }
    
    private void initButtons(){
        
        bottomLeftLayout.removeComponent(addNewItemBt);
        bottomLeftLayout.removeComponent(removeItemBt);
        
        
        if ("a_user".equals(tn)){
            initUsersFilter();
            if (isAdmin){
                bottomLeftLayout.addComponent(addNewItemBt);
                bottomLeftLayout.addComponent(removeItemBt);               
            }
        } else {
            bottomLeftLayout.addComponent(addNewItemBt);
            bottomLeftLayout.addComponent(removeItemBt);

        }
    
    }

    //5.
    /**
     * Inicializuje tabulku na danu entitu.
     */
    private void initUniTable() {

        uniTable.setContainerDataSource(sqlContainer);

//        log.info("CLASS:" + uniTableService.getClassTableName());
//        for (String s : visibleColDbNames) {
//            log.info("VISIBLE COLS:" + s);
//        }
        uniTable.setVisibleColumns((Object[]) visibleColDbNames);
        uniTable.setColumnHeaders(visibleColDepictNames);
        uniTable.setSelectable(true);
        uniTable.setImmediate(true);

        uniTable.addValueChangeListener(new Property.ValueChangeListener() {

            private static final long serialVersionUID = 1L;

            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                itemId = uniTable.getValue();
                item = uniTable.getItem(itemId);

                if (itemId != null) {
                    inputForm.setItem(itemId, uniTable.getItem(itemId));
                }
                editorLayout.setVisible(itemId != null);
                fg.setEnabled(false);//to sa ozivi az tlacitkom Edit,
                inputForm.doEnableButtons();
            }
        });
    }

    /**
     * Pomocne zoznamy pre tabulku.
     */
    private void initTableLists(String[] uneditCol) {

        log.info("CLASS:" + uniTableService.getClassTableName());

        String[] pom = new String[]{};

        for (String s : uneditCol) {
            log.info("UNEDITABLE: " + s);
        }

        this.nonEditFn = Arrays.asList(uneditCol);
        List<String> databaseNames = new ArrayList<>();
        List<String> depictNames = new ArrayList<>();

//        log.debug("DEBUG tn: " + tn);
        String key;
        Properties proPoradie = ToolsNames.getPoradieParams(tn);
        Properties proDepict = ToolsNames.getDepictParams(tn);

//        log.info("PRO PORADIE: " +proPoradie.size());
//        log.info("PRO DEPICT: " +proDepict.size());
//        
        for (int i = 0; i < proPoradie.size(); i++) {
            key = proPoradie.getProperty("" + i);
            log.info("KEY: " + key);

            if (nonEditFn.contains(key)) {
                log.info("PKRACUJEM: " + key);
                continue;
            }
            log.info("DAVAM KEY: " + key);
            databaseNames.add(key);
            depictNames.add(proDepict.getProperty(key));
        }
        //pozor, tuto je uneditableCol len v pozicii zastupcu tiedy String[]!!!!
        //dvolezite je db, resp dp.
        this.visibleColDbNames = databaseNames.toArray(pom);
        this.visibleColDepictNames = depictNames.toArray(pom);

        for (String s : visibleColDbNames) {
            log.debug("VISIBLE TABLE COLUMN: *" + s + "*");
        }
        for (String s : visibleColDepictNames) {
            log.debug("DEPICT T COL: *" + s + "*");
        }

    }


    /**
     *
     */
    public class DeleteTaskListener implements YesNoWindowListener {

        public DeleteTaskListener() {
        }

        @Override
        @SuppressWarnings({"unchecked"})
        public void doYesAction(Component.Event event) {

            if (item != null) {
                if (item.getItemProperty("id").getValue() == null) {
                    sqlContainer.removeItem(itemId);
                }
                try {
                    Integer id = (Integer) item.getItemProperty("id").getValue();
                    uniTableService.deactivateById(id);
                    

                    itemId= null;
                    item = null;
                    inputForm.setItem(itemId, item);
                    editorLayout.setVisible(false);
                    
                    Notification.show("úspešne vymazaný!");
                    doOkAction();
                } catch (SQLException ex) {
                    Notification.show("Vymazanie sa nepodarilo!");
                }
            } else {
                Notification.show("Nieje čo zmazať!");
            }
        }

    }

    @Override
    public void doOkAction() {

        sqlContainer.refresh();
        uniTable.refreshRowCache();
    }

    @Override
    public void doCancelAction() {
        
        sqlContainer.refresh();
        uniTable.refreshRowCache();
    }

    @Override
    public void obnovFilter() {
        
        this.sqlContainer.addContainerFilter(basicFilter);
         if ("a_user".equals(tn)){
            initUsersFilter();
        }
    }

    @Override
    public void refreshView() {
//        Notification.show("MAKOSKO");
        sqlContainer.refresh();
        uniTable.refreshRowCache();

    }

    /**
     *
     */
    private void initUsersFilter() {

        A_User user = UI.getCurrent().getSession().getAttribute(A_User.class);

        Integer uid;
        if (user == null) {
            log.warn("This shouldnt be possible!!");
            return;
        } else {
            uid = user.getId();
        }

        List<Integer> listIds;
        if (isAdmin) {
            listIds = uniTableService.findMeAndAllVolunteers(uid);
        } else {
            listIds = new ArrayList<>();
            listIds.add(uid);
        }

//        this.userOnlyFilter = new Compare.Equal("visible", Boolean.TRUE);
        sqlContainer.removeContainerFilter(userOnlyFilter);

        List<Filter> fls = new ArrayList<>();
        for (Integer id : listIds) {
            fls.add(new Compare.Equal("id", id));
        }
        this.userOnlyFilter = new Or(fls.toArray(new Filter[0]));
        sqlContainer.addContainerFilter(userOnlyFilter);

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        A_User usr = securityService.getCurrentUser();

        if (usr != null) {

            UserType utype = userService.getUserType(usr);

            if (utype == UserType.ADMIN) {
//                log.info("V8 ADMINQ!!!");
//                log.info("V8 utype: " + utype);
                
                this.isAdmin = true;
            }
            
            this.initUsersFilter();
            this.initButtons();
            
        } else {
            UI.getCurrent().getNavigator().navigateTo("V2_EnterView");
        }

    }

}
