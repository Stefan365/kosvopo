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
import com.vaadin.navigator.Navigator;
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
import sk.stefan.MVP.model.entity.dao.A_User;
import sk.stefan.MVP.model.repo.dao.UniRepo;
import sk.stefan.MVP.model.service.SecurityService;
import sk.stefan.MVP.model.serviceImpl.SecurityServiceImpl;
import sk.stefan.MVP.view.components.InputFormLayout;
import sk.stefan.MVP.view.components.MyTable;
import sk.stefan.MVP.view.components.NavigationComponent;
import sk.stefan.MVP.view.components.YesNoWindow;
import sk.stefan.filtering.FilteringComponent;
import sk.stefan.listeners.ObnovFilterListener;
import sk.stefan.listeners.OkCancelListener;
import sk.stefan.listeners.RefreshViewListener;
import sk.stefan.listeners.YesNoWindowListener;
import sk.stefan.utils.ToolsDao;
import sk.stefan.utils.ToolsNazvy;

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

    private A_User user;
    private SecurityService securityService;

    /* User interface components are stored in session. */
    private Class<E> clsE;
    private Object itemId;
    private Item item;
    private MyTable uniTable;// = new MyTable();
    private TextField searchField;// = new TextField();
    private Button addNewItemButton;// = new Button("Nová podložka");
    private Button removeItemButton;// = new Button("Odstráň túto podložku");
    private FormLayout editorLayout;// = new FormLayout();
    private FieldGroup fg = new FieldGroup();
    private Button backBt;

    //split panel:
    private HorizontalSplitPanel splitPanel;
    private VerticalLayout leftLayout;
    private HorizontalLayout bottomLeftLayout;
    private FilteringComponent filters;

    //Class specific:
    private SQLContainer sqlContainer;
    private String tn;
//    private List<String> visibleFn;
    private List<String> nonEditFn;
    private String[] visibleColDepictNames;
    private String[] visibleColDbNames;

    private final InputFormLayout<E> inputForm;
    private VerticalLayout linksVl;

    private final Filter basicFilter;

    private final VerticalLayout temporaryLy;

    private final NavigationComponent navComp;

    private final Navigator nav;

    
    //0.Konstruktor
    /**
     * 
     * @param clsq
     * @param uneditCol
     */
    public V8_UniEditableTableView(Class<E> clsq, String[] uneditCol) {
        
        this.setMargin(true);
        this.setSpacing(true);


        this.nav = UI.getCurrent().getNavigator();

        navComp = NavigationComponent.createNavigationComponent();
        this.addComponent(navComp);

        temporaryLy = new VerticalLayout();
        this.addComponent(temporaryLy);
        
        basicFilter = new Compare.Equal("visible", Boolean.TRUE);
        securityService = new SecurityServiceImpl();
        
        this.clsE = clsq;
        tn = ToolsDao.getTableName(clsE);
        this.initTableLists(uneditCol);

        this.inputForm = new InputFormLayout<>(clsE, item, sqlContainer, this, nonEditFn);

        

    }

    /**
     */
    private void initTableContainer() {

    }

    private void initLinks() {
        linksVl = new VerticalLayout();
        linksVl.setMargin(true);
        linksVl.setSpacing(true);
        linksVl.addComponent(backBt);
    }

    /**
     *
     * @param isVolunteer
     */
    private void initAllBasic(Boolean isVolunteer) {

        temporaryLy.removeAllComponents();

        initLayout();
        initLinks();
        initUniTable();
        initEditor();
        initSearch();
        initAddRemoveButtons();

        temporaryLy.addComponents(splitPanel, linksVl);


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
        addNewItemButton = new Button("Nová podložka");
        removeItemButton = new Button("Odstráň túto podložku");
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
        bottomLeftLayout.addComponent(addNewItemButton);
        bottomLeftLayout.addComponent(removeItemButton);

        // leftLayout.setSizeFull();
        leftLayout.setExpandRatio(uniTable, 1);
        uniTable.setSizeFull();

        bottomLeftLayout.setWidth("100%");
        searchField.setWidth("100%");
        bottomLeftLayout.setExpandRatio(searchField, 1);

        /* Put a little margin around the fields in the right side editor */
        editorLayout.setMargin(true);
        editorLayout.setVisible(false);

    }

    //2.
    private void initEditor() {

        //editorLayout.addComponent(removeItemButton);
        removeItemButton.setEnabled(true);
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

        addNewItemButton.addClickListener(new Button.ClickListener() {

            private static final long serialVersionUID = 1L;

            @Override
            @SuppressWarnings("unchecked")
            public void buttonClick(Button.ClickEvent event) {

                sqlContainer.removeAllContainerFilters();
                itemId = sqlContainer.addItem();
                item = sqlContainer.getItem(itemId);
                if (item.getItemProperty("visible") != null) {
                    item.getItemProperty("visible").setValue(Boolean.TRUE);
                }
                //docasne riesenie:
                if (item.getItemProperty("active") != null) {
                    item.getItemProperty("active").setValue(Boolean.TRUE);
                }

                obnovFilter();

//              uniTable.getContainerProperty(itemId, visibleFn.get(2)).setValue("New");
                uniTable.select(itemId);

                sqlContainer.refresh();
                uniTable.refreshRowCache();
            }
        });

        removeItemButton.addClickListener(new Button.ClickListener() {

            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(Button.ClickEvent event) {

                if (item != null) {
                    final YesNoWindow window = new YesNoWindow("Upozornenie",
                            "Chcete úkol smazat?", new DeleteTaskListener());
                    UI.getCurrent().addWindow(window);
                } else {
                    Notification.show("Vyber nejdříve řádek v tabulce!");
                }
            }
        });
    }

    //5.
    /**
     * Inicializuje tabulku na danu entitu.
     */
    private void initUniTable() {
        

        try {

            sqlContainer = DoDBconn.getContainer(tn);
            obnovFilter();
        } catch (SecurityException | SQLException e) {
            log.error(e.getMessage());
        }


        uniTable.setContainerDataSource(sqlContainer);

        log.info("CLASS:" + clsE.getCanonicalName());
        for (String s : visibleColDbNames) {
            log.info("VISIBLE COLS:" + s);
        }
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

        this.nonEditFn = Arrays.asList(uneditCol);
        List<String> db = new ArrayList<>();
        List<String> dp = new ArrayList<>();

//        log.debug("DEBUG tn: " + tn);
        String key;
        Properties proPoradie = ToolsNazvy.getPoradieParams(tn);
        Properties proDepict = ToolsNazvy.getDepictParams(tn);

        for (int i = 0; i < proPoradie.size(); i++) {

            key = proPoradie.getProperty("" + i);
            if (nonEditFn.contains(key)) {
                continue;
            }
            db.add(key);
            dp.add(proDepict.getProperty(key));
        }
        this.visibleColDbNames = db.toArray(uneditCol);
        this.visibleColDepictNames = dp.toArray(uneditCol);

        for (String s : visibleColDbNames) {
//            log.debug("TABLE COLUMN: *"+ s +"*");
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
//                  item.getItemProperty("visible").setValue(Boolean.FALSE);
//                  sqlContainer.getItem(itemId).getItemProperty("visible").setValue(Boolean.FALSE);
//                  item.getItemProperty("active").setValue(Boolean.FALSE);
//                  sqlContainer.getItem(itemId).getItemProperty("active").setValue(Boolean.FALSE);

                    UniRepo<E> unirepo = new UniRepo<>(clsE);
                    unirepo.updateParam("visible", "false", "" + item.getItemProperty("id").getValue());
//                    item = null;
//                    itemId = null;
                    Notification.show("Úkol úspešne vymazaný!");
                    doOkAction();
                } catch (SQLException ex) {
                    Notification.show("Vymazanie sa nepodarilo!");

                }
            } else {
                Notification.show("Není co mazat!");
            }
        }

        //end of inner class:
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        
        user = securityService.getCurrentUser();
        if (user != null) {
            //do nothing
        } else {
            UI.getCurrent().getNavigator().navigateTo("V2_EnterView");
        }

    }

    @Override
    public void doOkAction() {
//        Notification.show("KOKOSKO");
        sqlContainer.refresh();
        uniTable.refreshRowCache();
    }

    @Override
    public void doCancelAction() {
//        Notification.show("PETERKO");
        sqlContainer.refresh();
        uniTable.refreshRowCache();
    }

    @Override
    public void obnovFilter() {
        this.sqlContainer.addContainerFilter(basicFilter);
    }

//    public void odstranFilter() {
//        this.sqlContainer.removeContainerFilter(basicFilter);
//    }
    @Override
    public void refreshView() {
//        Notification.show("MAKOSKO");
        sqlContainer.refresh();
        uniTable.refreshRowCache();
    }

}
