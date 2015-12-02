package sk.stefan.interfaces;

/**
 * Rozhraní pro entity, jejichž detail je možné zobrazit v samostatné záložce.
 * @author  elopin on 08.11.2015.
 */
public interface TabEntity extends PresentationName{

    /**
     * Vrací název entity pro URL parametr.
     * @return název entity
     */
    String getEntityName();

    /**
     * Vrací název záložky pro detail entity.
     * @return název záložky pro entitu
     */
    String getRelatedTabName();

    /**
     * Vrací identifikátor entity.
     * @return ID entity
     */
    Integer getId();
}
