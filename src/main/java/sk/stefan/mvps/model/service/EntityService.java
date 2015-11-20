package sk.stefan.mvps.model.service;

import sk.stefan.mvps.model.entity.TabEntity;

/**
 * Created by elopin on 08.11.2015.
 */
public interface EntityService {


    TabEntity getTabEntityByNameAndId(String entityName, Integer entityId);

    String getTabIdByEntity(TabEntity entity);

    TabEntity saveEntity(TabEntity entity);

    boolean removeEntity(TabEntity entity);

    TabEntity getTabEntityByTabName(String tabName, Integer entityId);
}
