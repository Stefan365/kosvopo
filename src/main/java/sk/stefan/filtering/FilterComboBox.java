/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.filtering;

import com.vaadin.ui.ComboBox;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.log4j.Logger;
import sk.stefan.utils.Tools;

/**
 *
 * @author stefan
 * @param <E>
 */
public final class FilterComboBox<E> extends ComboBox {

    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getLogger(FilterComboBox.class);

    private final Map<String, Integer> mapAll;
    private final Class<E> clsE;

    private Map<String, Integer> map;

    //0.
    /**
     *
     * Konstruktor
     *
     * @param cls
     */
    public FilterComboBox(Class<E> cls) {

        this.clsE = cls;
        this.mapAll = Tools.findAllByClass(clsE);
        this.map = mapAll;

        this.initCombo();
        this.initComboValues(map, 1);

    }

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
     * Slouzi na novyh hodnot ve vyberu.
     *
     * @param newIds
     */
    public void setNewValues(List<Integer> newIds) {

        Map<String, Integer> newMap = new HashMap<>();
        //pre uchovanie tej istej entity
        Integer val = (Integer) this.getValue();
        if (val == null) {
            val = 1;
        }

        //tvorba novej mapy.
        for (Entry<String, Integer> e : mapAll.entrySet()) {
            if (newIds.contains(e.getValue())) {
                newMap.put(e.getKey(), e.getValue());
            }
        }
        this.map = newMap;
        
        this.initComboValues(map, val);

    }
    
    public void setNewValues() {
        
        Integer val = (Integer) this.getValue();
        if (val == null) {
            val = 1;
        }
        //zatial staci takto, keby dany combobox bol spolocny pre rozne filtre,
        //museli by sa vytvorit separatne mapy, ktore by sa aplikovali a deaplikovali.
        //napr. skrz map collector: tj. tiedu ktorej instancia by patrila 
        //do danej skupiny(podla retazca zavislosti) a na ktoru by mali odkaz comboboxy dotknute.
        this.initComboValues(mapAll , val);
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
        }
        this.setValue(val);
        
    }

    
    public String getTableName() {
        try {
            Method getTnMethod = clsE.getDeclaredMethod("getTN");
            String Tn = (String) getTnMethod.invoke(null);
            return Tn;
        } catch (IllegalAccessException | IllegalArgumentException |
                InvocationTargetException | NoSuchMethodException | SecurityException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

}
