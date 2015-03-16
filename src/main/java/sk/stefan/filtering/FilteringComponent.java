/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.filtering;

import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.VerticalLayout;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import sk.stefan.MVP.model.entity.dao.Location;
import sk.stefan.MVP.model.entity.dao.PublicBody;
import sk.stefan.MVP.model.entity.dao.PublicPerson;
import sk.stefan.MVP.view.components.SilentCheckBox;

/**
 *
 * @author stefan
 */
@SuppressWarnings("serial")
public class FilteringComponent extends VerticalLayout {
    
    private static final Logger log = Logger.getLogger(FilteringComponent.class);
    
    private final String parentTn;
    private final SQLContainer sqlContainer;

    private SilentCheckBox locationCHb;
    private FilterComboBox<Location> locationCombo;
    private FilterListener<Location> locationLr;
    private final List<FilterComboBox<?>> locTouchedCombos = new ArrayList<>();
    private final VerticalLayout locationVl = new VerticalLayout();
    
    
    private SilentCheckBox pubBodyCHb;
    private FilterComboBox<PublicBody> pubBodyCombo;
    private FilterListener<PublicBody> pubBodyLr;
    //toto je tu len pro forma, keby sa pridavalo filtrovanie 
    //ktore bude zavisle od PublicBody
    private final List<FilterComboBox<?>> pbTouchedCombos = null;
    private final VerticalLayout pubBodyVl = new VerticalLayout();
    
    private SilentCheckBox pubPersonCHb;
    private FilterComboBox<PublicPerson> pubPersonCombo;
    private FilterListener<PublicPerson> pubPersonLr;
    private final List<FilterComboBox<?>> ppTouchedCombos = null;
    private final VerticalLayout pubPersonVl = new VerticalLayout();
        
    private final List<SilentCheckBox> checkboxes = new ArrayList<>();
    private final List<FilterComboBox<?>> combos  = new ArrayList<>();

    //0. konstruktor
    /**
     *
     * @param parTn
     * @param sqlCont
     */
    public FilteringComponent(String parTn, SQLContainer sqlCont) {

        this.parentTn = parTn;
        this.sqlContainer = sqlCont;

//        this.setSpacing(true);
//        this.setMargin(true);
        
        this.initComponents();
        this.initValues();
        
    }

    /**
     *
     */
    private void initComponents() {
        //1.
        locationCHb = new SilentCheckBox();
        locationCombo = new FilterComboBox<>(Location.class);
        pubBodyCHb = new SilentCheckBox();
        pubBodyCombo = new FilterComboBox<>(PublicBody.class);
        pubPersonCHb = new SilentCheckBox();
        pubPersonCombo = new FilterComboBox<>(PublicPerson.class);
        
        locTouchedCombos.add(pubBodyCombo);
        
        locationLr = new FilterListener<>(parentTn, 
                Location.TN, 
                locationCombo, 
                locationCHb, 
                sqlContainer, 
                locTouchedCombos);

        pubBodyLr = new FilterListener<>(parentTn, 
                PublicBody.TN, 
                pubBodyCombo, 
                pubBodyCHb, 
                sqlContainer, 
                pbTouchedCombos);
        
        pubPersonLr = new FilterListener<>(parentTn, 
                PublicPerson.TN, 
                pubPersonCombo, 
                pubPersonCHb, 
                sqlContainer, 
                ppTouchedCombos);
        
        
        locationCHb.setCaption("Miesto");
        locationCHb.addValueChangeListener(locationLr);
        locationCombo.addValueChangeListener(locationLr);
        
        pubBodyCHb.setCaption("Verejný orgán");
        pubBodyCHb.addValueChangeListener(pubBodyLr);
        pubBodyCombo.addValueChangeListener(pubBodyLr);
                
        pubPersonCHb.setCaption("Verejná osoba");
        pubPersonCHb.addValueChangeListener(pubPersonLr);
        pubPersonCombo.addValueChangeListener(pubPersonLr);
        
        this.checkboxes.add(locationCHb);
        this.checkboxes.add(pubBodyCHb);
        this.checkboxes.add(pubPersonCHb);
        
        this.combos.add(locationCombo);
        this.combos.add(pubBodyCombo);
        this.combos.add(pubPersonCombo);
        
        //rozlozenie filtracnych komponent:
        locationVl.addComponent(locationCHb);
        locationVl.addComponent(locationCombo);
//        locationVl.setSpacing(true);
        locationVl.setMargin(true);
        
        pubBodyVl.addComponent(pubBodyCHb);
        pubBodyVl.addComponent(pubBodyCombo);
//        pubBodyVl.setSpacing(true);
        pubBodyVl.setMargin(true);
        
        pubPersonVl.addComponent(pubPersonCHb);
        pubPersonVl.addComponent(pubPersonCombo);
//        pubPersonVl.setSpacing(true);
        pubPersonVl.setMargin(true);

        //put it all together:
        this.addComponents(locationVl,pubBodyVl,pubPersonVl);
        
    }

    private void initValues(){
        for (CheckBox chb: checkboxes){
            chb.setValue(Boolean.FALSE);
        }
        for (ComboBox com: combos){
            com.setEnabled(false);
        }
        
        
    }

}
