/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view;

import com.vaadin.addon.timeline.Timeline;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.VerticalLayout;
import org.apache.log4j.Logger;

/**
 *
 * @author stefan
 */
public final class V3_PublicBodyView extends VerticalLayout implements View {

    
    private static final long serialVersionUID = 121322L;
    private static final Logger log = Logger.getLogger(V3_PublicBodyView.class);

    
    private IndexedContainer container;
    
    private Object timestampProperty;
    
    private Object valueProperty;
    
    private Timeline timeline;
    
    //konstruktor:
    public V3_PublicBodyView(){
        
        this.initAll();
        
    }
    
    //
    public void initAll() {
        // Construct a container which implements Container.Indexed       
        container = new IndexedContainer();

        // Add the Timestamp property to the container
        timestampProperty = "Our timestamp property";
        container.addContainerProperty(timestampProperty,
                java.util.Date.class, null);

        // Add the value property
        valueProperty = "Our value property";
        container.addContainerProperty(valueProperty, Float.class, null);

        // Our timeline
        timeline = new Timeline();

        // Add the container as a graph container
        timeline.addGraphDataSource(container, timestampProperty,
                valueProperty);
        
        this.addComponent(timeline);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        
    }

}
