/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view;

import com.vaadin.data.Property;
import com.vaadin.event.FieldEvents;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import java.util.List;
import sk.stefan.MVP.model.entity.dao.District;
import sk.stefan.MVP.model.service.PublicBodyService;
import sk.stefan.MVP.model.serviceImpl.PublicBodyServiceImpl;
import sk.stefan.MVP.view.components.PublicBodiesLayout;
import sk.stefan.filtering.FilterComboBox;

/**
 *
 * @author stefan
 */
public class V3s_PublicBodiesView extends VerticalLayout implements View {

    private static final long serialVersionUID = 10903884L;
    
    private final PublicBodyService publicBodyService;
    
    private PublicBodiesLayout publicBodiesLayout;
    
//    private FilterComboBox<District> districtCb; 
    
    private TextField searchFd; 
    
    public V3s_PublicBodiesView (){
        
        this.publicBodyService = new PublicBodyServiceImpl();
        
        this.initLayout();
//        this.initComboListener();
        this.initSearchListener();
        
    }
    
    /**
     * 
     */
    private void initLayout(){
        
        this.setMargin(true);
        this.setSpacing(true);
        
        this.publicBodiesLayout = new PublicBodiesLayout(publicBodyService.findAll(), publicBodyService);
//        this.districtCb = new FilterComboBox<>(District.class);
        this.searchFd = new TextField("Vyhľadávanie");
        this.initSearch();
        
    
//        this.addComponents(districtCb, searchFd, publicBodiesComp);
        this.addComponents(searchFd, publicBodiesLayout);
        
    }
    
//    private void initComboListener(){
//        
//        this.districtCb.addValueChangeListener(new Property.ValueChangeListener() {
//            
//            private static final long serialVersionUID = 1345322L;
//
//            @Override
//            public void valueChange(Property.ValueChangeEvent event) {
//                
//                Integer distrId = (Integer)event.getProperty().getValue();
//                List<Integer> pbIds = publicBodyService.findNewPublicBodyIds(distrId);
//                publicBodiesComp.applyFilter(pbIds);
//                
//            }
//        });
//    }
    
    /**
     * Initializes listener
     */
    private void initSearchListener(){
        
        searchFd.addTextChangeListener(new FieldEvents.TextChangeListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void textChange(final FieldEvents.TextChangeEvent event) {
        
                String tx = event.getText();
                List<Integer> pbIds = publicBodyService.findNewPublicBodyIdsByFilter(tx);
                publicBodiesLayout.applyFilter(pbIds);
                
            }
        });
    }
    

    //3.
    private void initSearch() {
        
        searchFd.setWidth("40%");
        searchFd.setInputPrompt("možeš použiť vyhľadávanie podľa názvu obce");
        searchFd.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.LAZY);
        
    }

    

    public PublicBodiesLayout getPbComp() {
        return publicBodiesLayout;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        
    }
    
}
