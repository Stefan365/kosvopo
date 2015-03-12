/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.filtering;

import com.google.gwt.aria.client.RelevantValue;
import com.sun.jmx.remote.internal.ArrayQueue;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Property;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.ui.HorizontalLayout;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static sk.stefan.DBconnection.PrepDBconn.db;
import sk.stefan.MVP.model.entity.dao.A_Hierarchy;
import sk.stefan.MVP.model.entity.dao.Location;
import sk.stefan.MVP.model.entity.dao.PublicBody;
import sk.stefan.MVP.model.entity.dao.PublicPerson;
import sk.stefan.MVP.model.repo.dao.UniRepo;
import sk.stefan.MVP.view.components.InputComboBox;
import sk.stefan.MVP.view.components.SilentCheckBox;
import sk.stefan.enums.FilterType;
import sk.stefan.enums.UserType;
import sk.stefan.utils.Tools;

/**
 *
 * @author stefan
 */
@SuppressWarnings("serial")
public class FilteringComponent extends HorizontalLayout {

    //do tejto mapy naloadovat properties hierarchy.properties
    private Map<String, String> map = new HashMap<>();

    private static final String[] relFilTns = new String[]{"t_location", "t_public_body", "t_public_person"};
//    private static final List<String> relFilTns = new ArrayList<>(Arrays.asList(rFTns));    

    private String table_name;
    private final SQLContainer sqlContainer;
    private Filter locationFilter;
    private Filter pubBodyFilter;
    private Filter pubPersonFilter;

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

    private List<HierarchySequence> seqs;

    public FilteringComponent(String tn, SQLContainer sqlC) {
        this.table_name = tn;
        this.sqlContainer = sqlC;

        this.initCheckBoxes();
        this.initCheckBoxes();

    }

    private void initCompLists() {

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

        List<List<String>> threads, actualThreads;
        threads = this.getAllHierarchySequencies();

        actualThreads = getActualSequencies(threads);

        List<HierarchySequence> finalSeq;
        finalSeq = getFinalSequence(actualThreads);

    }

    /**
     *
     */
    private List<List<String>> getAllHierarchySequencies() {
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
                if (bol) {
                    return strs2;
                }
            }
        } while (true);
    }

    /**
     *
     */
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

    /**
     * pomocna funkcia na klonovanie listu:
     */
    private List<String> klonujList(List<String> list) {
        List<String> cloned = new ArrayList<>(list);
        return cloned;
    }

    /**
     *
     * @param all
     * @return
     */
    private List<List<String>> getActualSequencies(List<List<String>> all) {
        List<List<String>> actual = new ArrayList<>();
        String btn;
        for (List<String> list : all) {
            btn = getBoss(list);
            checkActualFilTns();
            if (relFilTns.contains(btn)) {
                actual.add(list);
            }
        }
        return actual;
    }

    private String getBoss(List<String> list) {
        return list.get(list.size() - 1);
    }

    /**
     *
     */
    private List<HierarchySequence> getFinalSequence(List<List<String>> actualThreads) {

        List<HierarchySequence> finals = new ArrayList<>();
        HierarchySequence hs;
        ActualEntity ae;
        String btn;

        for (List<String> list : actualThreads) {

            hs = new HierarchySequence(list);
            for (String s : list) {
                ae = new ActualEntity(s);
                hs.getHierarchySequence().add(ae);
//                Collections.reverse(hs.getHierarchySequence());
            }
            createMySelect(hs);
            select id from finals
            .add(hs);

            for (int i = 1; i < hs.getHierarchySequence().size(); i++) {

            }

//            btn = getBoss(list);
//            if (relFilTns.contains(btn)){
//                finals.add(list);
//            }
        }
        return finals;
    }

    /**
     * Zostavi konecny sql dotaz, podla ktoreho sa bude nakoniec filtrovat dana
     * tabulka.
     */
    private void createMySelect(HierarchySequence hs) {

        StringBuilder sql = new StringBuilder();
        StringBuilder pom = new StringBuilder();
        String tn, tnb;
        Integer value;
        int i;
        int size = hs.getHierTableSequence().size();
        for (i = 0; i < size - 1; i++) {
            tn = hs.getHierTableSequence().get(i);
            pom.append("SELECT ID FROM ");
            pom.append(tn);
            pom.append(" WHERE ");
            pom.append(getBossReference(tn));
            pom.append(" IN (");
        }
        //tabulka, nejvyssi boss v dane posloupnosti:
        tnb = hs.getHierTableSequence().get(i + 1);

        switch (tnb) {
            case "t_location":
                value = (Integer) this.locationCombo.getValue();
                break;
            case "t_public_body":
                value = (Integer) this.pubBodyCombo.getValue();
                break;
            case "t_public_person":
                value = (Integer) this.pubPersonCombo.getValue();
                break;
            default:
                value = 0;
                break;
        }
        //pridani hodnoty, ok ktorej sa vsetko odvija:
        pom.append(value);

        //uzavretie dotazu:
        for (i = 0; i < size - 1; i++) {
            pom.append(")");
        }

    }

    private String getBossReference(String tn) {
        String bt = map.get(tn);
        String replace = bt.replace("t_", "");
        replace += "_id";
        return replace;
    }

    /**
     * Zisti, ktore checkboxu su prave zaskrtnute a podla toho zostavi aktualny
     * zoznam tabuliek, ktore budu zohladnene.
     */
    private List<String> checkActualFilTns() {
        List<String> aktualList = new ArrayList<>();
        for (int i = 0; i < relFilTns.length; i++) {
            aktualList.add();
        }

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
