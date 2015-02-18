/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view;

import sk.stefan.listenersImpl.RenewBackgroundListenerImpl;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.Sizeable;
import com.vaadin.server.StreamResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import sk.stefan.DBconnection.DoDBconn;
import sk.stefan.MVP.model.entity.Planet;
import sk.stefan.MVP.model.entity.dao.Location;
import sk.stefan.MVP.model.entity.dao.PersonClassification;
import sk.stefan.MVP.model.entity.dao.PublicBody;
import sk.stefan.MVP.model.entity.dao.PublicPerson;
import sk.stefan.MVP.model.entity.dao.PublicRole;
import sk.stefan.MVP.model.entity.dao.Subject;
import sk.stefan.MVP.model.entity.dao.Tenure;
import sk.stefan.MVP.model.entity.dao.Theme;
import sk.stefan.MVP.model.entity.dao.Vote;
import sk.stefan.MVP.model.entity.dao.VoteOfRole;
import sk.stefan.MVP.model.entity.jpa.ActClassification;
import sk.stefan.MVP.view.components.NavigationComponent;
import sk.stefan.MVP.view.components.todo.InputFormLayout;
import sk.stefan.MVP.view.dialogs.todo.TaskDlg;
import sk.stefan.MVP.view.helpers.ImageUploader;
import sk.stefan.MVP.view.helpers.ImageUploaderA;
import sk.stefan.MVP.view.helpers.MyImageSource;
import sk.stefan.MVP.view.helpers.Take5;
import sk.stefan.listeners.RenewBackgroundListener;
import sk.stefan.utils.InputFormWrapper;

/**
 *
 * @author stefan
 */
public class InputAllView extends VerticalLayout implements View {
    
    private static final Logger logger = Logger.getLogger(InputAllView.class.getName());
    
    private static final long serialVersionUID = 1L;

    private VerticalLayout layout;
    private final Button tenure0Bt = new Button();
    private final Button location0Bt = new Button();
    private final Button theme0Bt = new Button();
    private final Button publicPerson0Bt = new Button();
    private final Button publicBody1Bt = new Button();
    private final Button personClass1Bt = new Button();
    private final Button publicRole2Bt = new Button();
    private final Button subject3Bt = new Button();
    private final Button vote4Bt = new Button();
    private final Button voteOfRole5Bt = new Button();
    private final Button actClass6Bt = new Button();

    private List<Button> allButtons;
    private Map<Button, InputFormWrapper<Object>> allButtonsMap = new HashMap<>();
    private final String[] allButtonsNames = {"Volebné obdobie",
        "Miesto",
        "Tématický okruh hlasovaní",
        "Verejná osoba",
        "Verejný orgán",
        "Hodnotenie verejnej osoby",
        "Funkcia verejnej osoby",
        "Predmet hlasovania",
        "Hlasovanie",
        "Hlasovanie verejnej osoby",
        "Hodnotenie hlasovania"};

    public InputAllView(Navigator nav) {
        this.initLayout();
        this.initMap();
        this.initButtons();     
//        this.initButtonsListeners();     
        
        
        this.addComponent(new NavigationComponent(nav));
    }

    private void initMap() {

        allButtonsMap.put(tenure0Bt, new InputFormWrapper<>(new Tenure(), allButtonsNames[0]));
        allButtonsMap.put(location0Bt, new InputFormWrapper<>(new Location(), allButtonsNames[1]));
        allButtonsMap.put(theme0Bt, new InputFormWrapper<>(new Theme(), allButtonsNames[2]));
        allButtonsMap.put(publicPerson0Bt, new InputFormWrapper<>(new PublicPerson(), allButtonsNames[3]));
        allButtonsMap.put(publicBody1Bt, new InputFormWrapper<>(new PublicBody(), allButtonsNames[4]));
        allButtonsMap.put(personClass1Bt, new InputFormWrapper<>(new PersonClassification(), allButtonsNames[5]));
        allButtonsMap.put(publicRole2Bt, new InputFormWrapper<>(new PublicRole(), allButtonsNames[6]));
        allButtonsMap.put(subject3Bt, new InputFormWrapper<>(new Subject(), allButtonsNames[7]));
        allButtonsMap.put(vote4Bt, new InputFormWrapper<>(new Vote(), allButtonsNames[8]));
        allButtonsMap.put(voteOfRole5Bt, new InputFormWrapper<>(new VoteOfRole(), allButtonsNames[9]));
        allButtonsMap.put(actClass6Bt, new InputFormWrapper<>(new ActClassification(), allButtonsNames[10]));
    }

    private void initLayout() {

        layout = new VerticalLayout();
        layout.setMargin(true);

        allButtons = new ArrayList<>();
        allButtons.add(tenure0Bt);
        allButtons.add(location0Bt);
        allButtons.add(theme0Bt);
        allButtons.add(publicPerson0Bt);
        allButtons.add(publicBody1Bt);
        allButtons.add(personClass1Bt);
        allButtons.add(publicRole2Bt);
        allButtons.add(subject3Bt);
        allButtons.add(vote4Bt);
        allButtons.add(voteOfRole5Bt);
        allButtons.add(actClass6Bt);

        initMap();
        initButtons();
        addButtonsToLayout();

        this.addComponent(layout);

//        butEdit.addClickListener(new Button.ClickListener() {
//            @Override
//            public void buttonClick(Button.ClickEvent event) {
//                editorFields.setEnabled(true);
//                butEdit.setEnabled(false);
//                butSave.setEnabled(true);
//
//            }
//        });
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }

    private void addButtonsToLayout() {
        allButtons.stream().forEach((b) -> {
            layout.addComponent(b);
        });
    }

    private void initNames() {

        String[] buttonNames = new String[11];

        //general:
        buttonNames[1] = "miesto";

        //osoby:
        buttonNames[3] = "verejná osoba";
        buttonNames[6] = "funkcia verejnej osoby";
        buttonNames[5] = "hodnotenie verejnej osoby";

        //orgány:
        buttonNames[0] = "volebné obdobie";
        buttonNames[4] = "verejný orgán";

        //hlasovanie:
        buttonNames[2] = "tématický okruh hlasovania";
        buttonNames[7] = "predmet hlasovania";
        buttonNames[8] = "hlasovanie";
        buttonNames[9] = "hlasovanie verejnej osoby";
        buttonNames[10] = "hodnotenie hlasovania";

    }

    private void initButtons() {
        
        int i = 0;
        for (Button b : allButtonsMap.keySet()) {
            b.setCaption(allButtonsNames[i]);
            i++;
            
            b.addClickListener(null);
        }
        
    }

    private void initButtonsListeners() {
////                b = new Button(allButtonsNames[]Map.get(b).getButtonName());
//                    , new Button.ClickListener() {
//            
//                private static final long serialVersionUID = 1L;
//                private Class<?> cls;
//                @Override
//                public void buttonClick(Button.ClickEvent event) {
//                    //cls = (allButtonsMap.get(b).getClsE());
//                    try {
//                        TaskDlg tdlg;
//                        Field tnField = (allButtonsMap.get(b).getClsE()).getDeclaredField("TN");
//                        String tn = (String) tnField.get(null);
//                        SQLContainer sqlCont = DoDBconn.getContainer(tn);
//                        tdlg = new TaskDlg("Nový " + allButtonsMap.get(b).getButtonName(),
//                                sqlCont,
//                                item,
//                                new RenewBackgroundListenerImpl(sk.stefan.MVP.view.InputAllView.this));
//                        UI.getCurrent().addWindow(tdlg);
//                    } catch (NoSuchFieldException ex) {
//                        Logger.getLogger(InputAllView.class.getName()).log(Level.SEVERE, null, ex);
//                    } catch (SecurityException ex) {
//                        Logger.getLogger(InputAllView.class.getName()).log(Level.SEVERE, null, ex);
//                    } catch (IllegalArgumentException ex) {
//                        Logger.getLogger(InputAllView.class.getName()).log(Level.SEVERE, null, ex);
//                    } 
//               }
//            });
//
    }
}
