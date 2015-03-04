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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Map;
import org.apache.log4j.Logger;
import sk.stefan.DBconnection.DoDBconn;

/**
 * Combobox, ktory ma schopnost transformovat presentation type (tj. String - to
 * co v comboboxu vidime na type, ktery reprezentuje, napr. Boolean, ale muze
 * byt i komplikovanejsi entita) Duvod, proc neni converter navazany primo na
 * obycejny COmboBox, je ten, ze na ten nejde konverter navazat primo (resp. ne
 * jednoduse). V totmo pripade se tedy vyuziva pomocnej TextField, na ktery
 * converter navazat jde.
 *
 * @author stefan
 * @param <E> třída, představitelé které, se mají v comboBoxu zobrazit.
 */
public final class InputComboBox<E> extends ComboBox {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private static final Logger log = Logger.getLogger(InputComboBox.class);

    /**
     * Aktuálně vybraný objekt ze SQLcontaineru
     */
    private Item item;
    private Property<?> prop;

    //logger: 
    private final Logger logger = Logger.getLogger(InputComboBox.class);

    /**
     * Název comboBoxu, je zhodný s názvem proměnné(property), která je typu E.
     */
    private final String fn;

    /**
     * FieldGoup je nástroj na svázaní vaadinovské komponenty a nějakého jiného
     * objekty, který dané entitě poskytuje informace k zobrazení.
     */
    private final FieldGroup fg;

    /**
     * Slovník, ve kterém je klíčem reprezentativní název entity a hodnotou tato
     * entita sama.
     */
    private final Map<String, E> map;

    //0.
    /**
     *
     * Konstruktor
     *
     * @param fg fieldGtoup
     * @param fn Field name
     * @param map Slovník reprezentativní jméno/entita
     */
    public InputComboBox(FieldGroup fg, String fn, Map<String, E> map) {
        

        super(fn);
        this.fn = fn;

        this.map = map;
        this.fg = fg;
        this.involveFg();
        try {
            this.initCombo();
        } catch (SecurityException e) {
            logger.warn(e.getLocalizedMessage(), e);
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
        log.info("NASTAVUJEM HODNOTU");
        this.initComboVal();
        //this.setValue(fg.);

    }

//    public void refreshFg() {
//        this.initComboVal();
//    }

    // 2.
    /**
     * Inicializuje combo box.
     *
     */
    public void initCombo() {

        if (map != null) {
            for (String key : map.keySet()) {
                this.addItem(map.get(key));
                this.setItemCaption(map.get(key), key);
            }
        }
        //this.setValue(log);

        this.initComboVal();
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
    private void initComboVal() {

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
