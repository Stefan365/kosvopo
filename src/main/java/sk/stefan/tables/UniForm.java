package sk.stefan.tables;

import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Window;

public class UniForm extends Window {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FormLayout form = new FormLayout();
	/*
	// Create a container for beans
	    final BeanItemContainer<Star> stars =
	        new BeanItemContainer<Star>(Star.class);
	    
	    // Declare the nested properties to be used in the container
	    stars.addNestedContainerProperty("equatorial.rightAscension");
	    stars.addNestedContainerProperty("equatorial.declination");
	    
	    // Add some items
	    stars.addBean(new Star("Sirius",  new EqCoord(6.75, 16.71611)));
	    stars.addBean(new Star("Polaris", new EqCoord(2.52, 89.26417)));
	    
	    // Put them in a table
	    Table table = new Table("Stars", stars);
	    table.setColumnHeader("equatorial.rightAscension", "RA");
	    table.setColumnHeader("equatorial.declination",    "Decl");
	    table.setPageLength(table.size());
	    
	    // Have to set explicitly to hide the "equatorial" property
	    table.setVisibleColumns(new Object[]{"name",
	        "equatorial.rightAscension", "equatorial.declination"});
	*/
	
}
