package sk.stefan.factories;

import com.vaadin.ui.Button;
import sk.stefan.wrappers.FunctionalEditWrapper;

/**
 * Trieda, vytvara tlacitka na editaciu entit.
 *
 *
 * @author stefan
 */
public abstract class EditEntityButtonFactory {

    private static final long serialVersionUID = 1645436L;


    /**
     * @param wrap tento wrapper mas v sebe urcitu funkcionalitu  
     * aby bolo mozne vytvorit editButtonFactory ako abstraktnu triedu so statickou factory methodou/ami.
     * Inak factory musi byt genericka a tak factory methoda nemoze byt staticka.
     * 
     * @return 
     */
    public static Button createMyEditButton(FunctionalEditWrapper<?> wrap) {

        return wrap.getB();

    }

}
