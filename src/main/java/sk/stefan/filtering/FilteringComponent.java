/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.filtering;

import com.sun.jmx.remote.internal.ArrayQueue;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Property;
import com.vaadin.ui.HorizontalLayout;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import sk.stefan.MVP.model.entity.dao.A_Hierarchy;
import sk.stefan.MVP.model.entity.dao.Location;
import sk.stefan.MVP.model.entity.dao.PublicBody;
import sk.stefan.MVP.model.entity.dao.PublicPerson;
import sk.stefan.MVP.model.repo.dao.UniRepo;
import sk.stefan.MVP.view.components.InputComboBox;
import sk.stefan.MVP.view.components.SilentCheckBox;
import sk.stefan.enums.UserType;
import sk.stefan.utils.Tools;

/**
 *
 * @author stefan
 */
public class FilteringComponent extends HorizontalLayout {

    private static long serialVersionUID = 1L;

    /**
     * @return the serialVersionUID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * @param aSerialVersionUID the serialVersionUID to set
     */
    public static void setSerialVersionUID(long aSerialVersionUID) {
        serialVersionUID = aSerialVersionUID;
    }

    private String table_name;

    private SilentCheckBox locationCHb;
    private InputComboBox<Location> locationCombo;

    private SilentCheckBox pubBodyCHb;
    private InputComboBox<PublicBody> pubBodyCombo;

    private SilentCheckBox pubPersonCHb;
    private InputComboBox<PublicPerson> pubPersonCombo;

    private List<Boolean> bol;
    private List<Integer> vals;

    private List<HierarchySequence> seqs;

    public FilteringComponent() {

    }

    private void initLists() {
        this.bol = new ArrayList<>();
        this.vals = new ArrayList<>();
        this.seqs = new ArrayList<>();
    }

    private void initCheckBoxes() {
        //1.
        this.setLocationCHb(new SilentCheckBox());
        this.getLocationCHb().setCaption("Miesto");
        this.getLocationCHb().addValueChangeListener(new Property.ValueChangeListener() {

            private static final long serialVersionUID = 1L;

            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                resetPubBodyValues((Integer) event.getProperty().getValue());
            }

        });

    }

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

    public Filter generujFilter() {
        return null;
    }

    private void resetPubBodyValues(Integer integer) {
//        this.pubBodyCombocaq

//        bude sa to robit pomocou Set-z ktrych sa bude vyhadzovat/pridavat zobrazovane hodnoty.
    }

    private void checkState() {

        bol = new ArrayList<>();
        bol.add(this.locationCHb.getValue());
        bol.add(this.pubBodyCHb.getValue());
        bol.add(this.pubPersonCHb.getValue());

        vals = new ArrayList<>();
        vals.add((Integer) this.locationCombo.getValue());
        vals.add((Integer) this.pubBodyCombo.getValue());
        vals.add((Integer) this.pubPersonCombo.getValue());

        List<String> threads;
        threads = this.getAllHierarchyThreads();

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

    private List<String> getAllHierarchyThreads() {
        UniRepo<A_Hierarchy> uniRepo;
        uniRepo = new UniRepo<>(A_Hierarchy.class);

//        this.getHierarchTableSequence().add(table_name);
//        this.createAllSequences();
        return null;
    }

    private List<String> getNextHierarchLayer(String tn) {
        return null;
    }

    private List<List<String>> createAllSequences(List<List<String>> strs) {
        List<String> pom;
        String posledny;
        List<List<String>> strs2 = new ArrayList<>();
        List<List<String>> pom2;
        boolean bol = false;

        do {
            for (List<String> list : strs) {
                posledny = list.get(list.size() - 1);
                if ("FIN".equals(posledny)) {
                    continue;
                }
                pom = getNextHierarchLayer(posledny);
                if (pom != null) {
                    strs2.addAll(zmerguj(list, pom));
                } else {
                    list.add("FIN");
                    bol = true;
                }
                if (bol){
                    return strs2;
                }
            }
        } while (true);
    }

    private List<List<String>> zmerguj(List<String> list, List<String> pom) {
        List<List<String>> zmerged;
        zmerged = new ArrayList<>();
        List<String> lis;
        for (String s : pom) {
            lis = klonujList(list);
            lis.add(s);
            zmerged.add(lis);
        }
        return zmerged;
    }

    private List<String> klonujList(List<String> list) {
        List<String> cloned = new ArrayList<>(list);
        return cloned;
    }

}
