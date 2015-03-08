/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view.components;

import com.vaadin.data.Container.Filter;
import com.vaadin.ui.HorizontalLayout;
import sk.stefan.MVP.model.entity.dao.Location;
import sk.stefan.MVP.model.entity.dao.PublicBody;
import sk.stefan.MVP.model.entity.dao.PublicPerson;

/**
 *
 * @author stefan
 */
public class FilteringComponent extends HorizontalLayout {
    private static final long serialVersionUID = 1L;
    
    private SilentCheckBox locationCHb;
    private InputComboBox<Location> locationCombo;
    
    private SilentCheckBox pubBodyCHb;
    private InputComboBox<PublicBody> pubBodyCombo;
    
    private SilentCheckBox pubPersonCHb;
    private InputComboBox<PublicPerson> pubPersonCombo;
    
    
    
    public FilteringComponent(){
        
    }
    
    private void initCheckBox(){
    }
    
    
    private void initComboBox(){
        
//        this.locationCombo = new InputComboBox();
        
    }
    
    public Filter generujFilter(){
        return null;
    }
    
}
