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
import sk.stefan.MVP.model.entity.dao.PublicBody;
import sk.stefan.MVP.view.components.PublicRolesComponent;

/**
 *
 * @author stefan
 */
public final class V3_PublicBodyView extends VerticalLayout implements View {

    
    private static final long serialVersionUID = 121322L;
    private static final Logger log = Logger.getLogger(V3_PublicBodyView.class);

    //komponenta pre zobrazeneie aktivnych verejnych roli: 
    private PublicBody publicBody;
    
    private PublicRolesComponent publicRolesCompoment;
    
    
            
    
    
    
    //componenty pre TimeLine:
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

    /**
     * @return the publicBody
     */
    public PublicBody getPublicBody() {
        return publicBody;
    }

    /**
     * @param publicBody the publicBody to set
     */
    public void setPublicBody(PublicBody publicBody) {
        this.publicBody = publicBody;
    }

    /**
     * @return the publicRolesCompoment
     */
    public PublicRolesComponent getPublicRolesCompoment() {
        return publicRolesCompoment;
    }

    /**
     * @param publicRolesCompoment the publicRolesCompoment to set
     */
    public void setPublicRolesCompoment(PublicRolesComponent publicRolesCompoment) {
        this.publicRolesCompoment = publicRolesCompoment;
    }

}
