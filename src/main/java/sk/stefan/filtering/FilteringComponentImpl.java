/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.filtering;

import com.vaadin.data.Container.Filter;
import com.vaadin.data.Property;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.ui.HorizontalLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import sk.stefan.MVP.model.entity.dao.A_Hierarchy;
import sk.stefan.MVP.model.entity.dao.Location;
import sk.stefan.MVP.model.entity.dao.PublicBody;
import sk.stefan.MVP.model.entity.dao.PublicPerson;
import sk.stefan.MVP.model.repo.dao.UniRepo;
import sk.stefan.MVP.view.components.InputComboBox;
import sk.stefan.MVP.view.components.SilentCheckBox;
import sk.stefan.enums.FilterType;
import sk.stefan.utils.Tools;

/**
 *
 * @author stefan
 */
@SuppressWarnings("serial")
public class FilteringComponentImpl extends HorizontalLayout {//implements FilteringComponent {

    //do tejto mapy naloadovat properties hierarchy.properties
    private Map<String, String> map = new HashMap<>();

//    private static final List<String> relFilTns = new ArrayList<>(Arrays.asList(rFTns));    
//    private List<A_Hierarchy> hierarchies;
    private String table_name;
    private SQLContainer sqlContainer;
//    private final Object touchedObject;

    private Filter locationFilter;
    private Filter pubBodyFilter;
    private Filter pubPersonFilter;

    private UniRepo<A_Hierarchy> uniRepo;

    private SilentCheckBox locationCHb;
    private InputComboBox<Location> locationCombo;

    private SilentCheckBox pubBodyCHb;
    private InputComboBox<PublicBody> pubBodyCombo;

    private SilentCheckBox pubPersonCHb;
    private InputComboBox<PublicPerson> pubPersonCombo;

    private List<SilentCheckBox> checkboxes;
    private List<InputComboBox<?>> combos;

    private List<Boolean> bol;
    private List<Integer> vals;

    
    //0. konstruktor
    /**
     *
     * @param tn
     * @param touchedObj najcastejsie SQLCOntainer
     */
    public FilteringComponentImpl(String tn, SQLContainer sqlCont) {

        this.uniRepo = new UniRepo<>(A_Hierarchy.class);
        this.table_name = tn;
        this.sqlContainer = sqlCont;
//        this.touchedObject = touchedObj;

        this.initCheckBoxes();
        this.initCheckBoxes();
        this.initCompLists();

    }
    
    

    private void resetLocationValues(Integer integer) {

    }

    private void resetPubBodyValues(Integer integer) {
//        this.pubBodyCombocaq

//        bude sa to robit pomocou Set-z ktrych sa bude vyhadzovat/pridavat zobrazovane hodnoty.
    }

    private void resetPubPersonValues(Integer integer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     */
    private void refreshState() {

        bol = new ArrayList<>();
        bol.add(this.locationCHb.getValue());
        bol.add(this.pubBodyCHb.getValue());
        bol.add(this.pubPersonCHb.getValue());

        vals = new ArrayList<>();
        vals.add((Integer) this.locationCombo.getValue());
        vals.add((Integer) this.pubBodyCombo.getValue());
        vals.add((Integer) this.pubPersonCombo.getValue());

    }

    /**
     * Inicializuje zoznamy checkboxov a comboboxov
     */
    private void initCompLists() {
        this.bol = new ArrayList<>();
        this.vals = new ArrayList<>();
    
        this.checkboxes.add(locationCHb);
        this.checkboxes.add(pubBodyCHb);
        this.checkboxes.add(pubPersonCHb);

        this.combos.add(locationCombo);
        this.combos.add(pubBodyCombo);
        this.combos.add(pubPersonCombo);

    }

    /**
     * prida dany filter;
     */
    private void addFilter(Filter f) {
        this.sqlContainer.addContainerFilter(f);
    }

    /**
     * Odoberie dany filter
     */
    private void removeFilter(Filter f) {
        this.sqlContainer.removeContainerFilter(f);
    }

    /**
     * Obnoví daný filter.
     *
     * @param f
     * @param ft
     */
    public void refreshFilter(Filter f, FilterType ft) {
        switch (ft) {

            case LOCATION:
                removeFilter(locationFilter);
                this.locationFilter = f;
                addFilter(locationFilter);
                break;

            case PUB_BODY:
                removeFilter(pubBodyFilter);
                this.pubBodyFilter = f;
                addFilter(pubBodyFilter);
                break;

            case PUB_PERSON:
                removeFilter(pubPersonFilter);
                this.pubPersonFilter = f;
                addFilter(pubPersonFilter);
                break;

            default:
                break;
        }

    }

    private void initCheckBoxes() {
        //1.
        setLocationCHb(new SilentCheckBox());
        this.getLocationCHb().setCaption("Miesto");
        this.getLocationCHb().addValueChangeListener(new Property.ValueChangeListener() {

            private static final long serialVersionUID = 1L;

            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                resetLocationValues((Integer) event.getProperty().getValue());
            }

        });

        //2.
        this.setPubBodyCHb(new SilentCheckBox());
        this.getPubBodyCHb().setCaption("Verejný orgán");
        this.getPubPersonCHb().addValueChangeListener(new Property.ValueChangeListener() {

            private static final long serialVersionUID = 1L;

            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                resetPubBodyValues((Integer) event.getProperty().getValue());
            }
        });

        //3.
        this.setPubPersonCHb(new SilentCheckBox());
        this.getPubPersonCHb().setCaption("Verejná osoba");
        this.getPubPersonCHb().addValueChangeListener(new Property.ValueChangeListener() {

            private static final long serialVersionUID = 1L;

            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                resetPubPersonValues((Integer) event.getProperty().getValue());
            }
        });

        this.checkboxes.add(pubBodyCHb);
    }

    /**
     * Inicializuje checkboxy
     */
    private void initComboBoxes() {
        Class<?> cls = Location.class;
//        Method getNm = cls.getDeclaredMethod("getNames");
//        Method getOm = cls.getDeclaredMethod("getOrdinals");
//        
        Map<String, Integer> map = Tools.findAllByClass(cls);
        InputComboBox<Integer> cb;
        cb = new InputComboBox<>(map);

        // this.userRoleOg = new InputOptionGroup<Integer>(map);
//        this.locationCombo = new InputComboBox();
    }

    //getters and setters:
    /**
     * @return the table_name
     */
    public String getTable_name() {
        return table_name;
    }

    /**
     * @param table_name the table_name to set
     */
    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    /**
     * @return the locationCHb
     */
    public SilentCheckBox getLocationCHb() {
        return locationCHb;
    }

    /**
     * @param locationCHb the locationCHb to set
     */
    public void setLocationCHb(SilentCheckBox locationCHb) {
        this.locationCHb = locationCHb;
    }

    /**
     * @return the locationCombo
     */
    public InputComboBox<Location> getLocationCombo() {
        return locationCombo;
    }

    /**
     * @param locationCombo the locationCombo to set
     */
    public void setLocationCombo(InputComboBox<Location> locationCombo) {
        this.locationCombo = locationCombo;
    }

    /**
     * @return the pubBodyCHb
     */
    public SilentCheckBox getPubBodyCHb() {
        return pubBodyCHb;
    }

    /**
     * @param pubBodyCHb the pubBodyCHb to set
     */
    public void setPubBodyCHb(SilentCheckBox pubBodyCHb) {
        this.pubBodyCHb = pubBodyCHb;
    }

    /**
     * @return the pubBodyCombo
     */
    public InputComboBox<PublicBody> getPubBodyCombo() {
        return pubBodyCombo;
    }

    /**
     * @param pubBodyCombo the pubBodyCombo to set
     */
    public void setPubBodyCombo(InputComboBox<PublicBody> pubBodyCombo) {
        this.pubBodyCombo = pubBodyCombo;
    }

    /**
     * @return the pubPersonCHb
     */
    public SilentCheckBox getPubPersonCHb() {
        return pubPersonCHb;
    }

    /**
     * @param pubPersonCHb the pubPersonCHb to set
     */
    public void setPubPersonCHb(SilentCheckBox pubPersonCHb) {
        this.pubPersonCHb = pubPersonCHb;
    }

    /**
     * @return the pubPersonCombo
     */
    public InputComboBox<PublicPerson> getPubPersonCombo() {
        return pubPersonCombo;
    }

    /**
     * @param pubPersonCombo the pubPersonCombo to set
     */
    public void setPubPersonCombo(InputComboBox<PublicPerson> pubPersonCombo) {
        this.pubPersonCombo = pubPersonCombo;
    }

}
