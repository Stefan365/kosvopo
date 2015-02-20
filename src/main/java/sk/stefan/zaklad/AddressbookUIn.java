package sk.stefan.zaklad;

import java.sql.SQLException;
import java.util.List;

//import javax.servlet.ServletContext;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;

//import mvp.model.repo.dao.superseded.AddressbookaRepo;
//import mvp.model.repo.dao.superseded.T_OkresRepo;
import sk.stefan.DBconnection.DoDBconn;

import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.filter.Like;
import com.vaadin.data.util.filter.Or;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.server.WrappedSession;
import com.vaadin.ui.AbstractTextField.TextChangeEventMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.WrappedHttpSession;
import com.vaadin.server.WrappedSession;
import com.vaadin.ui.UI;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

/* 
 * UI class is the starting point for your app. You may deploy it with VaadinServlet
 * or VaadinPortlet by giving your UI class name a parameter. When you browse to your
 * app a web page showing your UI is automatically generated. Or you may choose to 
 * embed your UI to an existing web page. 
 */
//@SuppressWarnings("serial")
//@Title("Addressbook")
//@PreserveOnRefresh
public class AddressbookUIn extends UI {

    /*
     @WebServlet(value = "/*", asyncSupported = true)
     @VaadinServletConfiguration(productionMode = false, ui = AddressbookUIn.class)
     public static class Servlet extends VaadinServlet {
     }

     /* User interface components are stored in session. */
    private Table contactListTable = new Table();
    private TextField searchField = new TextField();
    private Button addNewContactButton = new Button("New");
    private Button removeContactButton = new Button("Remove this contact");
    private FormLayout editorLayout = new FormLayout();
    private HorizontalLayout editorLayout2 = new HorizontalLayout();
    private FieldGroup editorFields = new FieldGroup();

    @Autowired
    private ApplicationContext applicationContext;

    private static final String AB_ID = "ID";
    private static final String FNAME = "OKRES_NAME";
    private static final String LNAME = "KRAJ_IDO";
    //private static final String COMPANY = "COMPANY";
    private static final String[] fieldNames = new String[]{AB_ID, FNAME,
        LNAME};
    /*
     * "Mobile Phone", "Work Phone", "Home Phone", "Work Email", "Home Email",
     * "Street", "City", "Zip", "State", "Country" };
     */

    /*
     * Any component can be bound to an external data source. This example uses
     * just a dummy in-memory list, but there are many more practical
     * implementations.
     */
    private static SQLContainer sqlContainer;
	//private IndexedContainer contactContainer;// = createDummyDatasource();

	//0.
	/*
     * After UI class is created, init() is executed. You should build and wire
     * up your user interface here.
     */
    protected void init(VaadinRequest request) {

        try {
            sqlContainer = DoDBconn.getContainer("T_OKRES");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Notification.show("KAROLKO" + sqlContainer.toString());// + sqlContainer == null);
        //sqlContainer = AddressbookaRepo.getContainer();

		//sqlContainer.setAutoCommit(true);
        initLayout();
        initcontactListTable();
        initEditor();
        initSearch();
        initAddRemoveButtons();

    }

	//1.
	/*
     * In this example layouts are programmed in Java. You may choose use a
     * visual editor, CSS or HTML templates for layout instead.
     */
    private void initLayout() {

        /* Root of the user interface component tree is set */
        HorizontalSplitPanel splitPanel = new HorizontalSplitPanel();
        setContent(splitPanel);

        /* Build the component tree */
        VerticalLayout leftLayout = new VerticalLayout();
        splitPanel.addComponent(leftLayout);
        splitPanel.addComponent(editorLayout);

        leftLayout.addComponent(contactListTable);
        HorizontalLayout bottomLeftLayout = new HorizontalLayout();
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
    @SuppressWarnings("serial")
    private void initEditor() {

        editorLayout.addComponent(removeContactButton);
        removeContactButton.setEnabled(true);

        /* User interface can be created dynamically to reflect underlying data. */
        for (String fieldName : fieldNames) {
            TextField field = new TextField(fieldName);

            editorLayout.addComponent(field);
            field.setWidth("100%");
            field.setTextChangeEventMode(TextChangeEventMode.LAZY);

            /*
             * We use a FieldGroup to connect multiple components to a data
             * source at once.
             */
            editorFields.bind(field, fieldName);

            /*
             //automaticke refreshovanie zmien v tabulke. 
             field.addValueChangeListener(new Property.ValueChangeListener() {
             @Override
             public void valueChange(ValueChangeEvent event) {
             contactListTable.refreshRowCache();
             }
             });*/
            /*
             * Data can be buffered in the user interface. When doing so, commit()
             * writes the changes to the data source. Here we choose (false) to write the
             * changes automatically without calling commit().
             */
            editorFields.setBuffered(false);
        }

        editorLayout.addComponent(editorLayout2);

        //Pridanie tlacidiel:
        final Button butEdit = new Button("Edit");
        final Button butSave = new Button("Save");
        butEdit.setEnabled(true);
        butSave.setEnabled(false);

        butEdit.addClickListener(new ClickListener() {

            /**
             *
             */
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
                        e1.printStackTrace();
                    }
                    e.printStackTrace();
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

        /*
         * We want to show a subtle prompt in the search field. We could also
         * set a caption that would be shown above the field or description to
         * be shown in a tooltip.
         */
        searchField.setInputPrompt("Search contacts");

        /*
         * Granularity for sending events over the wire can be controlled. By
         * default simple changes like writing a text in TextField are sent to
         * server with the next Ajax call. You can set your component to be
         * immediate to send the changes to server immediately after focus
         * leaves the field. Here we choose to send the text over the wire as
         * soon as user stops writing for a moment.
         */
        searchField.setTextChangeEventMode(TextChangeEventMode.LAZY);

        /*
         * When the event happens, we handle it in the anonymous inner class.
         * You may choose to use separate controllers (in MVC) or presenters (in
         * mvp) instead. In the end, the preferred application architecture is
         * up to you.
         */
        searchField.addTextChangeListener(new TextChangeListener() {
            public void textChange(final TextChangeEvent event) {

                /* Reset the filter for the contactContainer. */
                sqlContainer.removeAllContainerFilters();
                String tx = "%" + event.getText() + "%";
                if (!tx.equals("")) {
                    sqlContainer.addContainerFilter(new Or(
                            new Like(FNAME, tx),
                            new Like(LNAME, tx)));
                    //new Like(COMPANY, tx)  ));
                }
            }
        });
    }

    //4.
    private void initAddRemoveButtons() {
        addNewContactButton.addClickListener(new ClickListener() {
            public void buttonClick(ClickEvent event) {

                /*
                 * Rows in the Container data model are called Item. Here we add
                 * a new row in the beginning of the list.
                 */
                sqlContainer.removeAllContainerFilters();
                Object contactId = sqlContainer.addItem();

                /*
                 * Each Item has a set of Properties that hold values. Here we
                 * set a couple of those.
                 */
                contactListTable.getContainerProperty(contactId, FNAME).setValue("New");
				//contactListTable.getContainerProperty(contactId, LNAME).setValue("Contact");

                /* Lets choose the newly created contact to edit it. */
                contactListTable.select(contactId);
            }
        });

        removeContactButton.addClickListener(new ClickListener() {
            public void buttonClick(ClickEvent event) {
                Object contactId = contactListTable.getValue();
                contactListTable.removeItem(contactId);
            }
        });
    }

    //5.
    private void initcontactListTable() {
        contactListTable.setContainerDataSource(sqlContainer);
		//contactListTable.setVisibleColumns(new Object[] { AB_ID, FNAME, LNAME });

		//contactListTable.setVisibleColumns(new Object[] { AB_ID, FNAME, LNAME, COMPANY });
		//contactListTable.setImmediate(true);
/*
         contactListTable.addValueChangeListener(new Property.ValueChangeListener() {
         @Override
         public void valueChange(ValueChangeEvent event) {
         contactListTable.refreshRowCache();
         System.out.println("zmenila sa hodnota: " + event.getProperty().getValue().toString());
         }
         });
         */
        contactListTable.setSelectable(true);
        contactListTable.setImmediate(true);

        contactListTable.addValueChangeListener(new Property.ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                Object contactId = contactListTable.getValue();

                /*
                 * When a contact is selected from the list, we want to show
                 * that in our editor on the right. This is nicely done by the
                 * FieldGroup that binds all the fields to the corresponding
                 * Properties in our contact at once.
                 */
                if (contactId != null) {
                    editorFields.setItemDataSource(contactListTable.getItem(contactId));
                }
                editorLayout.setVisible(contactId != null);
                editorFields.setEnabled(false);
            }
        });
    }

	//5.
	/*
     * Generate some in-memory example data to play with. In a real application
     * we could be using SQLContainer, JPAContainer or some other to persist the
     * data.
     */
    private void createDummyDatasource() {
		// IndexedContainer ic = new IndexedContainer();

        for (int i = 0; i < 1; i++) {
            System.out.println("som tu 1");
        }

        /*
         * for (String p : fieldNames) { ic.addContainerProperty(p,
         * String.class, ""); }
         */
        /* Create dummy data by randomly combining first and last names */
        String[] fnames = {"Peter", "Stefan", "Pavol", "Michal", "Maria",
            "Koloman", "Gejza", "Dezider", "Tonik", "Andrej", "Arana",
            "Rene", "Pistik", "Frigo"};
        String[] lnames = {"Sipos", "Berki", "Sarkoci", "Mosat", "Tehla",
            "Cibula", "Olah", "Lakatos", "Puska", "Torac", "Geno", "Curka",
            "Murko", "Vransky", "Veres", "Lopata", "Kapusta"};
        String[] companies = {"CB&I", "Slovakofarma Hlohovec", "Istrochem",
            "Slovnaft", "Tesla Stropkov", "IBM", "Dell", "Oracle",
            "JZD Slusovice"};

        try {
            for (int i = 0; i < 108; i++) {
                for (int j = 0; j < 1; j++) {
                    System.out.println("som tu 3");
                }
                Object id = sqlContainer.addItem();

                //sqlContainer.getContainerProperty(id, AB_ID).setValue(i);
                sqlContainer.getContainerProperty(id, FNAME).setValue(fnames[(int) (fnames.length * Math.random())]);
                sqlContainer.getContainerProperty(id, LNAME).setValue(lnames[(int) (lnames.length * Math.random())]);
				//sqlContainer.getContainerProperty(id, COMPANY).setValue(companies[(int) (companies.length * Math.random())]);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ulozenie zmien do DB:(pokial nieje nastaveny Autocommit(true))
        try {
            //for (int i = 0; i < 1; i++){System.out.println("som tu 4");}
            sqlContainer.commit();

            //refreshnutie obsahu tabulky:
            contactListTable.refreshRowCache();

        } catch (SQLException e) {
            //rollback je tu kvoli bezespornosti obsahu SQLcontainera a obsahom DB tabulky.
            try {
                sqlContainer.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
		//for (int i = 0; i < 1; i++){System.out.println("som tu 6");}
        // return ic;
    }

    @Override
    public void markAsDirty() {
		// TODO Auto-generated method stub

    }

}
