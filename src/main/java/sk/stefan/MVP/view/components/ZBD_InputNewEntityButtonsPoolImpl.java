/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view.components;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import sk.stefan.MVP.model.entity.dao.Region;
import sk.stefan.MVP.model.entity.dao.Location;
import sk.stefan.MVP.model.entity.dao.District;
import sk.stefan.MVP.model.entity.dao.PersonClassification;
import sk.stefan.MVP.model.entity.dao.PublicBody;
import sk.stefan.MVP.model.entity.dao.PublicPerson;
import sk.stefan.MVP.model.entity.dao.PublicRole;
import sk.stefan.MVP.model.entity.dao.Subject;
import sk.stefan.MVP.model.entity.dao.Tenure;
import sk.stefan.MVP.model.entity.dao.Theme;
import sk.stefan.MVP.model.entity.dao.Vote;
import sk.stefan.MVP.model.entity.dao.VoteClassification;
import sk.stefan.MVP.model.entity.dao.VoteOfRole;
import sk.stefan.interfaces.ZBD_InputNewEntityButtonsPool;
import sk.stefan.listeners.InputButClickListener;
import sk.stefan.listeners.InputVoteButClickListener;
import sk.stefan.wrappers.InputFormWrapper;

/**
 * Trieda, ktorá uchováva v zálohe všetky tlačítka na pridávanie nových entít.
 * Má to tú výhodu, že všetko je na jednom mieste, a v prípade potreby si 
 * View, potrebné tlačítko vypýta (na jednej stránke bude max. 1 tlačítko druhu danej entity.
 * 
 *
 * @author stefan
 */
public class ZBD_InputNewEntityButtonsPoolImpl implements ZBD_InputNewEntityButtonsPool {

    private static final Logger log = Logger.getLogger(ZBD_InputNewEntityButtonsPoolImpl.class);

    private static final long serialVersionUID = 1L;

    private final Button regionBt = new Button();
    private final Button districtBt = new Button();
    private final Button locationBt = new Button();

    private final Button publicPersonBt = new Button();
    private final Button tenureBt = new Button();
    private final Button publicBodyBt = new Button();
    private final Button publicRoleBt = new Button();

    private final Button themeBt = new Button();
    private final Button subjectBt = new Button();
    private final Button voteBt = new Button();
    private final Button voteOfRoleBt = new Button();

    private final Button personClassBt = new Button();
    private final Button voteClassBt = new Button();

    private final Map<Class<?>, Button> allButtonsPoolMap = new HashMap<>();
    private final Map<Button, InputFormWrapper<? extends Object>> allButtonsInitMap = new HashMap<>();

    private static ZBD_InputNewEntityButtonsPool pool;

    static{
        createPool();
    }
    
    
    private static void createPool(){
        
        pool = new ZBD_InputNewEntityButtonsPoolImpl();
        
    }
    
    public static ZBD_InputNewEntityButtonsPool getPool(){
        
        return pool;
    
    }
    
    private ZBD_InputNewEntityButtonsPoolImpl() {

        this.initMap();
        this.initButtons();

//        this.addComponent(NavigationComponent.getNavComp());
    }

    private void initMap() {

        allButtonsPoolMap.put(Region.class, regionBt);
        allButtonsPoolMap.put(District.class, districtBt);
        allButtonsPoolMap.put(Location.class, locationBt);
        allButtonsPoolMap.put(Tenure.class, tenureBt);
        allButtonsPoolMap.put(Theme.class, themeBt);
        allButtonsPoolMap.put(PublicPerson.class, publicPersonBt);
        allButtonsPoolMap.put(PublicBody.class, publicBodyBt);
        allButtonsPoolMap.put(PersonClassification.class, personClassBt);
        allButtonsPoolMap.put(PublicRole.class, publicRoleBt);
        allButtonsPoolMap.put(Subject.class, subjectBt);
        allButtonsPoolMap.put(Vote.class, voteBt);
        allButtonsPoolMap.put(VoteOfRole.class,voteOfRoleBt);
        allButtonsPoolMap.put(VoteClassification.class, voteClassBt);
        
        
        allButtonsInitMap.put(regionBt, new InputFormWrapper<>(Region.class, District.PRES_NAME, District.TN));
        allButtonsInitMap.put(districtBt, new InputFormWrapper<>(District.class, District.PRES_NAME, District.TN));
        allButtonsInitMap.put(locationBt, new InputFormWrapper<>(Location.class, Location.PRES_NAME, Location.TN));
        allButtonsInitMap.put(tenureBt, new InputFormWrapper<>(Tenure.class, Tenure.PRES_NAME, Tenure.TN));
        allButtonsInitMap.put(themeBt, new InputFormWrapper<>(Theme.class, Theme.PRES_NAME, Theme.getTN()));
        allButtonsInitMap.put(publicPersonBt, new InputFormWrapper<>(PublicPerson.class, PublicPerson.PRES_NAME, PublicPerson.TN));
        allButtonsInitMap.put(publicBodyBt, new InputFormWrapper<>(PublicBody.class, PublicBody.PRES_NAME, PublicBody.TN));
        allButtonsInitMap.put(personClassBt, new InputFormWrapper<>(PersonClassification.class, PersonClassification.PRES_NAME, PersonClassification.TN));
        allButtonsInitMap.put(publicRoleBt, new InputFormWrapper<>(PublicRole.class, PublicRole.PRES_NAME, PublicRole.TN));
        allButtonsInitMap.put(subjectBt, new InputFormWrapper<>(Subject.class, Subject.PRES_NAME, Subject.TN));
        allButtonsInitMap.put(voteBt, new InputFormWrapper<>(Vote.class, Vote.getPRES_NAME(), Vote.getTN()));
        allButtonsInitMap.put(voteOfRoleBt, new InputFormWrapper<>(VoteOfRole.class, VoteOfRole.PRES_NAME, VoteOfRole.getTN()));
        allButtonsInitMap.put(voteClassBt, new InputFormWrapper<>(VoteClassification.class, VoteClassification.PRES_NAME, VoteClassification.TN));

        
    }


    /**
     * inicializacia tlacitiek:
     */
    private void initButtons() {
        String s;
        for (Button b : allButtonsInitMap.keySet()) {
            InputFormWrapper<? extends Object> wr = allButtonsInitMap.get(b);

            b.setCaption(wr.getButtonName());
//            log.info("MENO: " + wr.getButtonName());
            if ((Vote.class).equals(wr.getClsE())) {
//                log.info("LSTENER1: " + wr.getClsE().getCanonicalName());
                s = wr.getButtonName();
                b.addClickListener(new InputVoteButClickListener(s));
            } else {
//                log.info("LSTENER2: " + wr.getClsE().getCanonicalName());
                b.addClickListener(new InputButClickListener(wr.getClsE(), wr.getButtonName()));
            }

        }
    }
    
    

    @Override
    public synchronized Button getMyButton(Class<?> cls) {
    
        return allButtonsPoolMap.get(cls);
    
    }

}
