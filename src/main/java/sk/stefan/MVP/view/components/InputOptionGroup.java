/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view.components;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.ui.OptionGroup;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author stefan
 * @param <E>
 */
public final class InputOptionGroup<E> extends OptionGroup {

    private static final Logger log = Logger.getLogger(InputOptionGroup.class);
    private static final long serialVersionUID = 1L;

    /**
     * Aktuálně vybraný objekt ze SQLcontaineru
     */
    private Item item;
    private Property<?> prop;

    /**
     * Název optionGroupu, je zhodný s názvem proměnné(property), která je typu E.
     */
    private String fn;

    /**
     * FieldGoup je nástroj na svázaní vaadinovské komponenty a nějakého jiného
     * objekty, který dané entitě poskytuje informace k zobrazení.
     */
    private FieldGroup fg;

    /**
     * Slovník, ve kterém je klíčem reprezentativní název entity a hodnotou tato
     * entita sama.
     */
    private final Map<String, E> map;

    //0.
    /**
     * Konstruktor
     *
     * @param fg fieldGtoup
     * @param fn Field name
     * @param map Slovník reprezentativní jméno/entita
     */
    public InputOptionGroup(FieldGroup fg, String fn, Map<String, E> map) {

        super(fn);
        this.fn = fn;

        this.map = map;
        this.fg = fg;
        this.involveFg();
        try {
            this.initOptionGroup();
            this.initOptionGroupVal();
        } catch (SecurityException e) {
            log.warn(e.getLocalizedMessage(), e);
        }
    }

    //0.
    /**
     * Konstruktor
     *
     * @param map Slovník reprezentativní jméno/entita
     */
    public InputOptionGroup(Map<String, E> map) {


        this.map = map;
        try {
            this.initOptionGroup();
            this.setValue(map);
        } catch (SecurityException e) {
            log.warn(e.getLocalizedMessage(), e);
        }
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
        log.info("NADSTAVUJEM HODNOTU");
        this.initOptionGroupVal();
        //this.setValue(fg.);
    }
    
    // 2.
    /**
     * Inicializuje option group box.
     *
     */
    public void initOptionGroup() {

        if (map != null) {
            for (String key : map.keySet()) {
                this.addItem(map.get(key));
                this.setItemCaption(map.get(key), key);
            }
        }

        this.setImmediate(true);
        this.setNewItemsAllowed(false);
        this.setNullSelectionAllowed(false);
        this.setVisible(true);
    }

    // 5.
    /**
     * Slouzi na nastaveni hodnoty v optionGroupe pri zobrazeni formuláře tj pokud
     * již jsou hodnoty známy, nastaví je jako výchozí hodnoty.
     */
    private void initOptionGroupVal() {

        if (fg.getItemDataSource() != null) {
            item = fg.getItemDataSource();
            prop = item.getItemProperty(fn);
            if (prop.getValue() != null) {
                this.setValue(prop.getValue());
            } else {
                //do nothing;
            }
        }
    }

}
