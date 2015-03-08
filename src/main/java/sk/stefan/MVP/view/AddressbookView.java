package sk.stefan.MVP.view;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.filter.Like;
import com.vaadin.data.util.filter.Or;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.AbstractTextField.TextChangeEventMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import sk.stefan.DBconnection.DoDBconn;
import sk.stefan.MVP.model.entity.dao.A_User;
import sk.stefan.MVP.model.service.SecurityService;
import sk.stefan.MVP.model.service.SecurityServiceImpl;
import sk.stefan.MVP.view.components.NavigationComponent;

/**
 * Class for Editable environment for editing database table.
 */
public class AddressbookView extends VerticalLayout implements View {

    private static final Logger log = Logger.getLogger(AddressbookView.class);
    private static final long serialVersionUID = 1L;

    /* User interface components are stored in session. */
    private A_User user;
    private SecurityService securityService;
    private Table contactListTable = new Table();
    private TextField searchField = new TextField();
    private Button addNewContactButton = new Button("New");
    private Button removeContactButton = new Button("Remove this contact");
    private FormLayout editorLayout = new FormLayout();
    private HorizontalLayout editorLayout2 = new HorizontalLayout();
    private FieldGroup editorFields = new FieldGroup();
    //split panel:
    private HorizontalSplitPanel splitPanel;
    private VerticalLayout leftLayout;
    private HorizontalLayout bottomLeftLayout;
    private Button butEdit;
    private Button butSave;

    private static final String[] fieldNames = new String[]{"id", "first_name",
        "last_name", "company"};
    private static final String[] nonEditFn = new String[]{"id"};
    

    private static SQLContainer sqlContainer;

    public AddressbookView() {
        
        securityService = new SecurityServiceImpl();

        try {
            sqlContainer = DoDBconn.getContainer("addressbook_1");
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        initLayout();
        initcontactListTable();
        initEditor();
        initSearch();
        initAddRemoveButtons();
        this.addComponent(NavigationComponent.getNavComp());
    }

    //1.
	/*
     * In this example layouts are programmed in Java. You may choose use a
     * visual editor, CSS or HTML templates for layout instead.
     */
    private void initLayout() {

        /* Root of the user interface component tree is set */
        splitPanel = new HorizontalSplitPanel();
        this.addComponent(splitPanel);

        /* Build the component tree */
        leftLayout = new VerticalLayout();
        splitPanel.addComponent(leftLayout);
        splitPanel.addComponent(editorLayout);

        leftLayout.addComponent(contactListTable);
        bottomLeftLayout = new HorizontalLayout();
        leftLayout.addComponent(bottomLeftLayout);
        bottomLeftLayout.addComponent(searchField);
        bottomLeftLayout.addComponent(addNewContactButton);

        /* Set the contents in the left of the split panel to use all the space */
        leftLayout.setSizeFull();

        /*
         * On the left side, expand the size of the contactListTable so that it uses
         * all the space left after from bottomLeftLayout
         */
        leftLayout.setExpandRatio(contactListTable, 1);
        contactListTable.setSizeFull();

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

        editorLayout.addComponent(removeContactButton);
        removeContactButton.setEnabled(true);

        /* User interface can be created dynamically to reflect underlying data. */
        for (String fieldName : fieldNames) {
            TextField field = new TextField(fieldName);
            editorLayout.addComponent(field);
            field.setWidth("100%");
            //field.setTextChangeEventMode(TextChangeEventMode.LAZY);
            editorFields.bind(field, fieldName);

            /*
             * Data can be buffered in the user interface. When doing so, commit()
             * writes the changes to the data source. Here we choose (false) to write the
             * changes automatically without calling commit().
             */
            editorFields.setBuffered(false);
        }

        editorLayout.addComponent(editorLayout2);
		//editorLayout.addComponent(new NavigationComponent(navigator));

        //Pridanie tlacidiel:
        butEdit = new Button("Edit");
        butSave = new Button("Save");
        butEdit.setEnabled(true);
        butSave.setEnabled(false);

        butEdit.addClickListener(new ClickListener() {
            private static final long serialVersionUID = 1L;
            @Override
            public void buttonClick(ClickEvent event) {
                editorFields.setEnabled(true);
                butEdit.setEnabled(false);
                butSave.setEnabled(true);

            }
        });

        butSave.addClickListener(new ClickListener() {
            private static final long serialVersionUID = 1L;
            @Override
            public void buttonClick(ClickEvent event) {
                // ulozenie zmien do DB:
                try {
                    sqlContainer.commit();
                } catch (SQLException e) {
                    try {
                        sqlContainer.rollback();
                    } catch (SQLException e1) {
                        log.error(e1.getMessage());
                    }
                    log.error(e.getMessage());
                }
                contactListTable.refreshRowCache();
                editorFields.setEnabled(false);
                butEdit.setEnabled(true);
                butSave.setEnabled(false);
            }
        });
        editorLayout2.addComponent(butEdit);
        editorLayout2.addComponent(butSave);
    }

    //3.
    private void initSearch() {

        searchField.setInputPrompt("Search contacts");
        searchField.setTextChangeEventMode(TextChangeEventMode.LAZY);

        searchField.addTextChangeListener(new TextChangeListener() {
            private static final long serialVersionUID = 1L;
            @Override
            public void textChange(final TextChangeEvent event) {

                /* Reset the filter for the contactContainer. */
                sqlContainer.removeAllContainerFilters();
                String tx = "%" + event.getText() + "%";
                if (!tx.equals("")) {
                    sqlContainer.addContainerFilter(new Or(
                            new Like(fieldNames[1], tx),
                            new Like(fieldNames[2], tx),
                            new Like(fieldNames[3], tx)));
                }
            }
        });
    }

    //4.
    /**
     */
    private void initAddRemoveButtons() {
        addNewContactButton.addClickListener(new ClickListener() {
            private static final long serialVersionUID = 1L;
            @Override
            @SuppressWarnings("unchecked")
            public void buttonClick(ClickEvent event) {

                sqlContainer.removeAllContainerFilters();
                Object contactId = sqlContainer.addItem();

                contactListTable.getContainerProperty(contactId, fieldNames[1]).setValue("New");
                contactListTable.getContainerProperty(contactId, fieldNames[2]).setValue("Contact");

                // Lets choose the newly created contact to edit it.
                contactListTable.select(contactId);
            }
        });

        removeContactButton.addClickListener(new ClickListener() {
            private static final long serialVersionUID = 1L;
            @Override
            public void buttonClick(ClickEvent event) {
                Object contactId = contactListTable.getValue();
                contactListTable.removeItem(contactId);
                try {
                    //sqlContainer.setAutoCommit(true);
                    sqlContainer.removeAllContainerFilters();
                    sqlContainer.removeItem(contactId);
                    //sqlContainer.setAutoCommit(false);
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
     * 
     */
    private void initcontactListTable() {
        contactListTable.setContainerDataSource(sqlContainer);
        //contactListTable.setVisibleColumns((Object[]) fieldNames);
        contactListTable.setSelectable(true);
        contactListTable.setImmediate(true);

        contactListTable.addValueChangeListener(new Property.ValueChangeListener() {
            private static final long serialVersionUID = 1L;
            @Override
            public void valueChange(ValueChangeEvent event) {
                Object contactId = contactListTable.getValue();

                if (contactId != null) {
                    editorFields.setItemDataSource(contactListTable.getItem(contactId));
                }
                editorLayout.setVisible(contactId != null);
                editorFields.setEnabled(false);
                butEdit.setEnabled(true);
                butSave.setEnabled(false);
            }
        });
    }

    @Override
    public void markAsDirty() {
        // TODO Auto-generated method stub

    }

    @Override
    public void enter(ViewChangeEvent event) {
        this.addComponent(NavigationComponent.getNavComp());
        user = securityService.getCurrentUser();
        if (user != null) {
            //do nothing
        } else {
            NavigationComponent.getNavigator().navigateTo("vstupny");
        }
    }
}
