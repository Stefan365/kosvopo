package sk.stefan.mvps.view.tabs;

import com.vaadin.ui.Component;
import sk.stefan.interfaces.TabEntity;
import sk.stefan.listeners.RemoveListener;
import sk.stefan.listeners.SaveListener;

/**
 * Rozhraní pro navigovatelné záložky.
 * @author elopin on 03.11.2015.
 */
public interface TabComponent extends Component {

    /**
     * Vrací popisek záložky.
     * @return popisek záložky
     */
    default String getTabCaption() { return "default"; };

    /**
     * Aktualizuje data v záložce.
     */
    void show();

    /**
     * Vrací identifikátor záložky pro URL.
     * @return identifikátor záložky
     */
    String getTabId();

    /**
     * Nastaví entitu do záložky, která je určena pro zobrazení detailu entity.
     * @param tabEntity entity s daty pro záložku
     */
    default void setEntity(TabEntity tabEntity) {}

    /**
     * Vrací entitu, která poskytuje data záložky.
     * @return entitu s daty
     */
    default TabEntity getEntity() { return null; }

    /**
     * Vrací nadřazenou entitu, bez které nelze vytvořit jinou entitu.
     * @return nadřazenou entitu
     */
    default TabEntity getParentEntity() { return null; }

    /**
     * Nastaví do záložky listener tlačítka Uložit.
     * @param saveListener save listener
     */
    default void setSaveListener(SaveListener<TabEntity> saveListener) {}

    /**
     * Nastaví do komponenty listener tlačítka Odstranit.
     * @param removeListener remove listener
     */
    default void setRemoveListener(RemoveListener<TabEntity> removeListener) {}

    /**
     * Zkontroluje oprávnění pro aktuálního uživatele.
     * @return true, pokud má aktuální uživatel právo na zobrazení záložky
     */
    default boolean isUserAccessGranted() { return true; }
}
