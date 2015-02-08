package sk.stefan.tables;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.AbstractTextField.TextChangeEventMode;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.BaseTheme;

@SuppressWarnings("serial")
public class UniTableNav extends HorizontalSplitPanel implements View {
	//VerticalLayout {//{implements View {
	//CustomComponent { //Panel {//Window {//VerticalLayout {//AbstractComponentContainer {

	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;
	protected String FIRST = "xxx";
	protected String SECOND = "xxx";
	protected String THIRD = "xxx";
	protected String FOURTH = "xxx";
	
	//private String[] fNames = new String[] { FIRST, SECOND,	THIRD, FOURTH };
	
	/* User interface components are stored in session. */
	private Table contactListTable = new Table();
	private TextField searchField = new TextField(); 
	private Button addNewContactButton = new Button("New");
	private Button backButton = new Button("Back");
	private Navigator navigator;
	
	
	private Button removeContactButton = new Button("Remove this contact");
	private FormLayout editorLayout = new FormLayout();
	private HorizontalLayout editorLayout2 = new HorizontalLayout();
	private FieldGroup editorFields = new FieldGroup();

	private List<String> fieldNames;// = new String[] {};
	private final List<String> visibleFieldNames = new ArrayList<String>();
		
	private SQLContainer sqlContainer;
	
	
	//0. Konstruktor
	public UniTableNav(String tableName) {
		super();
		
		sqlContainer = CreateContainer.createContainer(tableName);
		fieldNames = CreateContainer.getColNames(tableName);
		for (int i = 0; i < fieldNames.size() - 0; i++){
			visibleFieldNames.add(fieldNames.get(i));
		}
		
		initColNames();
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
		//HorizontalSplitPanel splitPanel = new HorizontalSplitPanel();
		//this.addComponent(splitPanel);

		/* Build the component tree */
		VerticalLayout leftLayout = new VerticalLayout();
		this.addComponent(leftLayout);
		this.addComponent(editorLayout); //splitPanel
		
		leftLayout.addComponent(contactListTable);
		HorizontalLayout bottomLeftLayout = new HorizontalLayout();
		leftLayout.addComponent(bottomLeftLayout);
		
		bottomLeftLayout.addComponent(backButton);
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
			
			/*
			 * We use a FieldGroup to connect multiple components to a data
			 * source at once.
			 */
			editorFields.bind(field, fieldName);
			
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
		 * MVP) instead. In the end, the preferred application architecture is
		 * up to you.
		 */
		searchField.addTextChangeListener(new TextChangeListener() {
			public void textChange(final TextChangeEvent event) {

				/* Reset the filter for the contactContainer. */
				sqlContainer.removeAllContainerFilters();
				String tx = "%" + event.getText() + "%";
				if (!tx.equals("")){
					sqlContainer.addContainerFilter(new Or(
							new Like(FIRST, tx), 
							new Like(SECOND, tx), 
							new Like(THIRD, tx),
							new Like(FOURTH, tx)
							));
				}
			}
		});
	}

	//4a.
	private void initColNames() {
		FIRST  = visibleFieldNames.get(0);
		SECOND  = visibleFieldNames.get(1);
		if(visibleFieldNames.size() > 2){
			THIRD  = visibleFieldNames.get(2);
		} else if(visibleFieldNames.size() > 3){
			FOURTH  = visibleFieldNames.get(3);
		}
	}
		
	

	//4.
	private void initAddRemoveButtons() {
		backButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				//navigator.navigateTo("welcome");
			}
		});
				
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
				contactListTable.getContainerProperty(contactId, SECOND).setValue("New");
				contactListTable.getContainerProperty(contactId, THIRD).setValue("New");
				
				

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
		contactListTable.setContainerDataSource(sqlContainer, visibleFieldNames);
		//contactListTable.setVisibleColumns(visibleFieldNames);
		//contactListTable.setVisibleColumns(new Object[] { FIRST, SECOND, THIRD, FOURTH });
		
		
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
				if (contactId != null){editorFields.setItemDataSource(contactListTable.getItem(contactId));}
				editorLayout.setVisible(contactId != null);
				editorFields.setEnabled(false);
			}
		});
	}

	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		Notification.show("Showing UNITABLE page");
		navigator = event.getNavigator();
		
	}
}
