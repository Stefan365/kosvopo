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
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.log4j.Logger;
import sk.stefan.DBconnection.DoDBconn;
import sk.stefan.MVP.view.components.NavigationComponent;
import sk.stefan.MVP.view.components.todo.InputFormLayout;
import sk.stefan.MVP.view.helpers.Tools;

/**
 *
 * Class for Editable environment for editing database table.
 *
 * @author stefan
 * @param <E> type of UniEditableTableView
 *
 */
public class UniEditableTableView<E> extends VerticalLayout implements View {

    private static final Logger log = Logger.getLogger(UniEditableTableView.class);
    private static final long serialVersionUID = 1L;

    /* User interface components are stored in session. */
    private Table uniTable = new Table();
    private TextField searchField = new TextField();
    private Button addNewItemButton = new Button("Nová položka");
    private Button removeItemButton = new Button("Odstráň túto položku");
    private FormLayout editorLayout = new FormLayout();
    private HorizontalLayout editorButtonsLayout;
    private FieldGroup fg = new FieldGroup();
    //split panel:
    private HorizontalSplitPanel splitPanel;
    private VerticalLayout leftLayout;
    private HorizontalLayout bottomLeftLayout;
    private Button editBt;
    private Button saveBt;

//    private String[] visibleFn = new String[]{"ID", "first_name",
//        "last_name", "company"};
//    private String[] nonEditFn = new String[]{"ID"};
    //Class specific:
    private SQLContainer sqlContainer;
    private Class<E> clsE;
    private String Tn;
    private List<String> visibleFn;
    private List<String> nonEditFn;
    private InputFormLayout<E> inputForm;
    private Item item;

    public UniEditableTableView(Class<E> cls, String[] uneditCol) {

        this.clsE = cls;
        try {
            this.visibleFn = Tools.getClassProperties(cls, true);
        } catch (NoSuchFieldException | SecurityException ex) {
            log.error(ex.getMessage());
        }

        try {
            Method getTnMethod = cls.getDeclaredMethod("getTN");
            Tn = (String) getTnMethod.invoke(null);
            sqlContainer = DoDBconn.getContainer(Tn);
        } catch (IllegalAccessException | IllegalArgumentException |
                InvocationTargetException | NoSuchMethodException |
                SecurityException | SQLException e) {
            log.error(e.getMessage());
        }
        this.nonEditFn = Arrays.asList(uneditCol);
        this.inputForm = new InputFormLayout<>(cls, null, sqlContainer, null, nonEditFn);

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

        /* Set the contents in the left of the split panel to use all the space */
        leftLayout.setSizeFull();

        /*
         * On the left side, expand the size of the contactListTable so that it uses
         * all the space left after from bottomLeftLayout
         */
        leftLayout.setExpandRatio(uniTable, 1);
        uniTable.setSizeFull();

        /*
         * In the bottomLeftLayout, searchField takes all the width there is
         * after adding addNewContactButton. The height of the layout is defined
         * by the tallest component.
         */
        bottomLeftLayout.setWidth("100%");
        searchField.setWidth("100%");
        bottomLeftLayout.setExpandRatio(searchField, 1);

        /* Put a little margin around the fields in the right side editor */
        editorLayout.setMargin(true);
        editorLayout.setVisible(false);
    }

    //2.
    private void initEditor() {

        editorLayout.addComponent(removeItemButton);
        removeItemButton.setEnabled(true);

        //main latout s komponentami podla potreb danej entity.
        editorLayout.addComponent(inputForm);
//        for (String fieldName : visibleFn) {
//            TextField field = new TextField(fieldName);
//            editorLayout.addComponent(field);
//            field.setWidth("100%");
//            //field.setTextChangeEventMode(TextChangeEventMode.LAZY);
//            fg.bind(field, fieldName);
//
//        }
        /*
         * Data can be buffered in the user interface. When doing so, commit()
         * writes the changes to the data source. Here we choose (false) to write the
         * changes automatically without calling commit().
         */
        fg.setBuffered(false);

        editBt = new Button("Edit");
        saveBt = new Button("Save");
        editBt.setEnabled(true);
        saveBt.setEnabled(false);

        //
        editorButtonsLayout = new HorizontalLayout();
        editorLayout.addComponent(editorButtonsLayout);
        editorButtonsLayout.addComponent(editBt);
        editorButtonsLayout.addComponent(saveBt);
        //editorLayout.addComponent(new NavigationComponent(navigator));

        //Pridanie tlacidiel do editorButtonsLayout-u:

        editBt.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(Button.ClickEvent event) {
                fg.setEnabled(true);
                editBt.setEnabled(false);
                saveBt.setEnabled(true);

            }
        });

        saveBt.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(Button.ClickEvent event) {
                // ulozenie zmien do DB:
                try {
                    //fg.commit(); commit not needed, setBuffered(true)
                    sqlContainer.commit();
                } catch (SQLException e) {
                    try {
                        sqlContainer.rollback();
                    } catch (SQLException e1) {
                        log.error(e1.getMessage());
                    }
                    log.error(e.getMessage());
                }
                uniTable.refreshRowCache();
                fg.setEnabled(false);
                editBt.setEnabled(true);
                saveBt.setEnabled(false);
            }
        });

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
//                    sqlContainer.addContainerFilter(new Or(
//                            new Like(visibleFn.get(2), tx),
//                            new Like(visibleFn.get(3), tx)));
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
                Object itemId = sqlContainer.addItem();
// treba zabezpecit, aby not null bunky mali defaultne hodnoty.
//                uniTable.getContainerProperty(itemId, visibleFn.get(1)).setValue("New");
//                uniTable.getContainerProperty(itemId, visibleFn.get(2)).setValue("New");

                uniTable.select(itemId);
            }
        });

        removeItemButton.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(Button.ClickEvent event) {
                Object itemId = uniTable.getValue();
                uniTable.removeItem(itemId);
                try {
                    sqlContainer.removeAllContainerFilters();
                    sqlContainer.removeItem(itemId);
                    sqlContainer.commit();

                } catch (UnsupportedOperationException e) {
                    try {
                        //sqlContainer.rollback();
                    } catch (UnsupportedOperationException e1) {
                        log.error(e1.getMessage());
                        log.error(e.getMessage());
                    }
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
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
                Object itemId = uniTable.getValue();

                if (itemId != null) {
                    inputForm.setItem(uniTable.getItem(itemId));
//                    editorFields.setItemDataSource(contactListTable.getItem(contactId));
                }
                editorLayout.setVisible(itemId != null);
                fg.setEnabled(false);//to sa ozivi az tlacitkom Edit,
                //defaultne to bude needitovatelne.
//                inputForm.setVisible(itemId != null);
//                inputForm.setEnabled(false);

                editBt.setEnabled(true);
                saveBt.setEnabled(false);
            }
        });
    }

//    @Override
//    public void markAsDirty() {
//        // TODO Auto-generated method stub
//
//    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        this.addComponent(NavigationComponent.getNavComp());
    }
}
