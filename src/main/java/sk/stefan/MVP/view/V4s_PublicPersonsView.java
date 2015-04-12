/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view;

import com.vaadin.event.FieldEvents;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import java.util.List;
import sk.stefan.MVP.model.service.PublicPersonService;
import sk.stefan.MVP.model.service.PublicPersonServiceImpl;
import sk.stefan.MVP.view.components.PublicPersonsComponent;

/**
 *
 * @author stefan
 */
public class V4s_PublicPersonsView extends VerticalLayout implements View {

    
    private static final long serialVersionUID = 10903884L;
    
    private final PublicPersonService publicPersonService;
    
    private PublicPersonsComponent publicPersonsComp;
    
    private TextField searchFd; 
    
//    private TextField searchField;// = new TextField();
        
    public V4s_PublicPersonsView (){
        
        this.publicPersonService = new PublicPersonServiceImpl();
        
        this.initLayout();
        this.initListener();
        
    }
    
    /**
     * 
     */
    private void initLayout(){
        
        this.setMargin(true);
        this.setSpacing(true);
        
        this.publicPersonsComp = new PublicPersonsComponent(publicPersonService.findAll(), publicPersonService);
        this.searchFd = new TextField("Vyhľadávač");
        this.initSearch();
        
        this.addComponents(searchFd, publicPersonsComp);
        
    }
    
    /**
     * Initializes listener
     */
    private void initListener(){
        
        searchFd.addTextChangeListener(new FieldEvents.TextChangeListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void textChange(final FieldEvents.TextChangeEvent event) {
        
                String tx = event.getText();
                List<Integer> ppIds = publicPersonService.findNewPublicPersonsIds(tx);
                publicPersonsComp.applyFilter(ppIds);
                
            }
        });
    }
    

    //3.
    private void initSearch() {
        
        searchFd.setWidth("40%");
        searchFd.setInputPrompt("možeš použiť vyhľadávanie podľa mena");
        searchFd.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.LAZY);
        
    }

    
    
    public PublicPersonsComponent getPublicPersonsComponent() {
        return publicPersonsComp;
    }

    
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        
    }
    
}