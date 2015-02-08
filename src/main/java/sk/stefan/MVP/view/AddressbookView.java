package sk.stefan.MVP.view;

import java.sql.SQLException;


import sk.stefan.DBconnection.DoDBconn;
import sk.stefan.MVP.view.components.NavigationComponent;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.filter.Like;
import com.vaadin.data.util.filter.Or;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.navigator.Navigator;
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

public class AddressbookView extends VerticalLayout implements View {
	
	Navigator navigator;
	
	/* User interface components are stored in session. */
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
	
	
	//private ApplicationContext applicationContext;

	private static final String AB_ID = "ID";
	private static final String FNAME = "FIRST_NAME";
	private static final String LNAME = "LAST_NAME";
	private static final String COMPANY = "COMPANY";
	private static final String[] fieldNames = new String[] { AB_ID, FNAME,
			LNAME, COMPANY };
	/*
	 * "Mobile Phone", "Work Phone", "Home Phone", "Work Email", "Home Email",
	 * "Street", "City", "Zip", "State", "Country" };
	 */

	private static SQLContainer sqlContainer;
	//private IndexedContainer contactContainer;// = createDummyDatasource();

	
	public AddressbookView (Navigator nav) {
               
		this.navigator = nav;
		
		try {
			//AddressbookaRepo.initContainer();
			//sqlContainer = DoDBconn.getContainer("ADDRESSBOOK_1");
                    	sqlContainer = DoDBconn.getContainer("addressbook_1");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//sqlContainer = AddressbookaRepo.getContainer();
		
		initLayout();
		initcontactListTable();
		initEditor();
		initSearch();
		initAddRemoveButtons();
		this.addComponent(new NavigationComponent(navigator));
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
		
		
		butEdit.addClickListener(new ClickListener(){
			@Override
			public void buttonClick(ClickEvent event) {
				editorFields.setEnabled(true);
				butEdit.setEnabled(false);
				butSave.setEnabled(true);
				
			}
		});
		
		butSave.addClickListener(new ClickListener(){
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

		searchField.setInputPrompt("Search contacts");
		searchField.setTextChangeEventMode(TextChangeEventMode.LAZY);

		searchField.addTextChangeListener(new TextChangeListener() {
			public void textChange(final TextChangeEvent event) {

				/* Reset the filter for the contactContainer. */
				sqlContainer.removeAllContainerFilters();
				String tx = "%" + event.getText() + "%";
				if (!tx.equals("")){
					sqlContainer.addContainerFilter(new Or(
							new Like(FNAME, tx), 
							new Like(LNAME, tx), 
							new Like(COMPANY, tx)  ));
				}
			}
		});
	}

	//4.
	private void initAddRemoveButtons() {
		addNewContactButton.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {

				sqlContainer.removeAllContainerFilters();
				Object contactId = sqlContainer.addItem();

				contactListTable.getContainerProperty(contactId, FNAME).setValue("New");
				contactListTable.getContainerProperty(contactId, LNAME).setValue("Contact");
				
				// Lets choose the newly created contact to edit it.
				contactListTable.select(contactId);
			}
		});

		removeContactButton.addClickListener(new ClickListener() {
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
						e1.printStackTrace();
					e.printStackTrace();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	//5.
	private void initcontactListTable() {
		contactListTable.setContainerDataSource(sqlContainer);
		contactListTable.setVisibleColumns(new Object[] { AB_ID, FNAME, LNAME, COMPANY });
		contactListTable.setSelectable(true);
		contactListTable.setImmediate(true);

		contactListTable.addValueChangeListener(new Property.ValueChangeListener() {
			public void valueChange(ValueChangeEvent event) {
				Object contactId = contactListTable.getValue();

				if (contactId != null){editorFields.setItemDataSource(contactListTable.getItem(contactId));}
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
		// TODO Auto-generated method stub
		
	}
}
