/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view.components;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.ui.ComboBox;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import sk.stefan.interfaces.Filterable;
import sk.stefan.utils.ToolsDao;

/**
 * Je to svojbytny objekt, tj. nieje napojeny na vonkajsie EntityServisy.
 * 
 * 
 * Combobox, ktory ma schopnost transformovat presentation type (tj. String - to
 * co v comboboxu vidime na type, ktery reprezentuje, napr. Boolean, ale muze
 * byt i komplikovanejsi entita) Duvod, preco nieni converter naviazany priamo na
 * obycejny ComboBox, je ten, ze na ten konverter navazat priamo jednoducho nejde 
 * (resp. nie jednoduse). V totmo pripade se tedy vyuziva pomocnej TextField, na ktory
 * converter naviazat jde.
 *
 * @author stefan
 * @param <E> třída, představitelé které, se mají v comboBoxu zobrazit.
 */
public final class InputClassComboBox<E> extends ComboBox implements Filterable {
    
    
    private static final long serialVersionUID = 1234324324L;

    private static final Logger log = Logger.getLogger(InputClassComboBox.class);

    /**
     * Název comboBoxu, je zhodný s názvem proměnné(property), která je typu E.
     */
    private final String fn;

    /**
     * FieldGoup je nástroj na svázaní vaadinovské komponenty a nějakého jiného
     * objekty, který dané entitě poskytuje informace k zobrazení.
     */
    private final FieldGroup fg;
    private final Map<String, Integer> mapAll;
    private final Class<E> clsE;

    private Map<String, Integer> map;

    //0.
    /**
     *
     * Konstruktor
     *
     * @param fg fieldGtoup
     * @param fn Field name
     * @param cls Slovník reprezentativní jméno/entita
     */
    public InputClassComboBox(FieldGroup fg, String fn, Class<E> cls) {

        super(fn);

        this.fn = fn;
        this.fg = fg;
        this.clsE = cls;
        this.mapAll = ToolsDao.findAllByClass(clsE);
        this.map = mapAll;

        this.involveFg();

        this.initCombo();
        this.initComboValues(map, getInitVal());

    }

    //1.
    /**
     * Zaplete FieldGroup do struktury:
     *
     */
    @SuppressWarnings({"unchecked", "unchecked"})
    private void involveFg() {
        //fn - fieldname is name of property in Item.
        //it corresponds with the name of property of relevant entity
        this.fg.bind(this, fn);
        fg.setBuffered(false);

    }

    // 2.
    /**
     * Inicializuje combo box.
     *
     */
    public void initCombo() {

        this.setImmediate(true);
        this.setNewItemsAllowed(false);
        this.setNullSelectionAllowed(false);
        this.setVisible(true);
    }

    // 5.
    /**
     * Slouzi na nastaveni hodnoty v comboBoxe pri zobrazeni formuláře tj pokud
     * již jsou hodnoty známy, nastaví je jako výchozí hodnoty.
     */
    private Integer getInitVal() {

        //pociatocna hodnota:
        Integer val = 1;//null - prednastavenie hodnoty.

        if (fg.getItemDataSource() != null) {
            Item item = fg.getItemDataSource();
            Property<?> prop = item.getItemProperty(fn);
            if (prop.getValue() != null) {
                val = (Integer) prop.getValue();
            } else {
                //do nothing;
            }
        }
        return val;
    }

    //6.
    /**
     * Inicializuje hodnoty v comboboxe:
     */
    private void initComboValues(Map<String, Integer> mapa, Integer val) {

        this.removeAllItems();

        if (mapa != null) {
            for (String key : mapa.keySet()) {
                this.addItem(mapa.get(key));
                this.setItemCaption(mapa.get(key), key);
            }
            if (mapa.values() != null && mapa.values().contains(val)) {
                this.setValue(val);
            }
        } else {
//            this.setValue(x);
            //pokial je to not null, tak vloz inicializacnu hodnotu
        }
    }

    // 5.
    /**
     * Slouzi na novyh hodnot ve vyberu.
     *
     * @param newIds
     */
    private void setNewValues(List<Integer> newIds) {

        
        //pre uchovanie tej istej entity
        Integer val = (Integer) this.getValue();
        if (val == null) {
            //val = 1; nastavit hodnotu pre not null polozky, neskvor komplexne vyriesit.
        }

        if (newIds != null) {
            Map<String, Integer> newMap = new HashMap<>();

            //tvorba novej mapy.
            for (Map.Entry<String, Integer> e : mapAll.entrySet()) {
                if (newIds.contains(e.getValue())) {
                    newMap.put(e.getKey(), e.getValue());
                }
            }
            this.map = newMap;
        } else {
            this.map = mapAll;
        }
        
        //zatial staci takto, keby dany combobox bol spolocny pre rozne filtre,
        //museli by sa vytvorit separatne mapy, ktore by sa aplikovali a deaplikovali.
        //napr. skrz map collector: tj. tiedu ktorej instancia by patrila 
        //do danej skupiny(podla retazca zavislosti) a na ktoru by mali odkaz comboboxy dotknute.
        this.initComboValues(map, val);

    }

    /**
     * pomocna funkcia.
     *
     * @return
     */
    public String getTableName() {
        
        return ToolsDao.getTableName(clsE);
        
    }


    @Override
    public void applyFilter(List<Integer> ids) {
        this.setNewValues(ids);
    }

}
