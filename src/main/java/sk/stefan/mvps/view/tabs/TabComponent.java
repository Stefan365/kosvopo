package sk.stefan.mvps.view.tabs;

import com.vaadin.ui.Component;
import sk.stefan.listeners.RemoveListener;
import sk.stefan.listeners.SaveListener;
import sk.stefan.mvps.model.entity.TabEntity;

/**
 * Rozhraní pro navigovatelné záložky.
 * @author elopin on 03.11.2015.
 */
public interface TabComponent extends Component {

    /**
     * Vrací popisek záložky.
     * @return popisek záložky
     */
    String getTabCaption();

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

    default void setSaveListener(SaveListener<TabEntity> saveListener) {}

    default void setRemoveListener(RemoveListener<TabEntity> removeListener) {}
}
