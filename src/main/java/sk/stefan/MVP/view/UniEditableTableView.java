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
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import sk.stefan.DBconnection.DoDBconn;
import sk.stefan.MVP.model.repo.dao.UniRepo;
import sk.stefan.MVP.view.components.InputFormLayout;
import sk.stefan.MVP.view.components.NavigationComponent;
import sk.stefan.MVP.view.components.YesNoWindow;
import sk.stefan.listeners.OkCancelListener;
import sk.stefan.listeners.YesNoWindowListener;
import sk.stefan.utils.Tools;

/**
 *
 * Class for Editable environment for editing database table.
 *
 * @author stefan
 * @param <E> type of UniEditableTableView
 *
 */
public class UniEditableTableView<E> extends VerticalLayout implements OkCancelListener, View {

    private static final Logger log = Logger.getLogger(UniEditableTableView.class);
    private static final long serialVersionUID = 1L;

    /* User interface components are stored in session. */
    private Class<E> clsE;
    private Object itemId;
    private Item item;
    private Table uniTable = new Table();
    private TextField searchField = new TextField();
    private Button addNewItemButton = new Button("Nová podložka");
    private Button removeItemButton = new Button("Odstráň túto podložku");
    private FormLayout editorLayout = new FormLayout();
    private FieldGroup fg = new FieldGroup();
    //split panel:
    private HorizontalSplitPanel splitPanel;
    private VerticalLayout leftLayout;
    private HorizontalLayout bottomLeftLayout;

    //Class specific:
    private SQLContainer sqlContainer;
    private String Tn;
    private List<String> visibleFn;
    private final List<String> nonEditFn;
    private final InputFormLayout<E> inputForm;

    public UniEditableTableView(Class<E> clsq, String[] uneditCol) {

        this.clsE = clsq;
        try {
            this.visibleFn = Tools.getClassProperties(clsE, true);
        } catch (NoSuchFieldException | SecurityException ex) {
            log.error(ex.getMessage());
        }

        try {
            Method getTnMethod = clsE.getDeclaredMethod("getTN");
            Tn = (String) getTnMethod.invoke(null);
            sqlContainer = DoDBconn.getContainer(Tn);
        } catch (IllegalAccessException | IllegalArgumentException |
                InvocationTargetException | NoSuchMethodException |
                SecurityException | SQLException e) {
            log.error(e.getMessage());
        }
        this.nonEditFn = Arrays.asList(uneditCol);
        this.inputForm = new InputFormLayout<>(clsE, item, sqlContainer, this, nonEditFn);

        initLayout();
        initUniTable();
        initEditor();
        initSearch();
        initAddRemoveButtons();
        this.addComponent(NavigationComponent.getNavComp());
    }

    //1.
    /**
     * Zakladny layout
     */
    private void initLayout() {

        // Root of the user interface component tree is set 
        splitPanel = new HorizontalSplitPanel();
        this.addComponent(splitPanel);

        leftLayout = new VerticalLayout();
        splitPanel.addComponent(leftLayout);
        splitPanel.addComponent(editorLayout);

        leftLayout.addComponent(uniTable);
        bottomLeftLayout = new HorizontalLayout();
        leftLayout.addComponent(bottomLeftLayout);
        bottomLeftLayout.addComponent(searchField);
        bottomLeftLayout.addComponent(addNewItemButton);
        bottomLeftLayout.addComponent(removeItemButton);

        leftLayout.setSizeFull();

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
        editorLayout.addComponent(inputForm);
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
                    for (String s : visibleFn) {
                        fls.add(new Like(s, tx));
                    }
                    o = new Or(fls.toArray(new Filter[0]));
                    sqlContainer.addContainerFilter(o);
                }
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
//              uniTable.getContainerProperty(itemId, visibleFn.get(2)).setValue("New");

                uniTable.select(itemId);
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
        uniTable.setContainerDataSource(sqlContainer);

        Object[] visCol = visibleFn.toArray();
        uniTable.setVisibleColumns(visCol);
        uniTable.setSelectable(true);
        uniTable.setImmediate(true);

        uniTable.addValueChangeListener(new Property.ValueChangeListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                itemId = uniTable.getValue();
                item = uniTable.getItem(itemId);

                if (itemId != null) {
                    inputForm.setItem(uniTable.getItem(itemId));
//                    editorFields.setItemDataSource(contactListTable.getItem(contactId));
                }
                editorLayout.setVisible(itemId != null);
                fg.setEnabled(false);//to sa ozivi az tlacitkom Edit,
                inputForm.doEnableButtons();
            }
        });
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

            if (itemId != null) {
                try {
//                  item.getItemProperty("visible").setValue(Boolean.FALSE);
//                  sqlContainer.getItem(itemId).getItemProperty("visible").setValue(Boolean.FALSE);
                    UniRepo<E> unirepo = new UniRepo<>(clsE);
                    unirepo.updateParam("visible", "false", "" + item.getItemProperty("id").getValue());
                    item = null;
                    itemId = null;
                    Notification.show("Úkol úspešne vymazaný!");
                    doAdditionalOkAction();
                } catch (SQLException ex) {
                    Notification.show("Vymazanie sa nepodarilo!");
                }
            } else {
                Notification.show("Není co mazat!");
            }
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        this.addComponent(NavigationComponent.getNavComp());
    }

    @Override
    public void doAdditionalOkAction() {
        //Notification.show("KOKOSKO");
        sqlContainer.refresh();
        uniTable.refreshRowCache();
    }

    @Override
    public void doAdditionalCancelAction() {
        sqlContainer.refresh();
        uniTable.refreshRowCache();
    }

    @Override
    public void obnovFilter() {

    }
}
