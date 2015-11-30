package sk.stefan.mvps.model.service;

import sk.stefan.interfaces.TabEntity;
import sk.stefan.mvps.view.tabs.TabComponent;

/**
 * Created by elopin on 03.11.2015.
 */
public interface LinkService {

    String getUriFragmentForTab(Class<? extends TabComponent> menuItemTabClass);

    String getUriFragmentForEntity(TabEntity entity);

    String getUriFragmentForTabWithParentEntity(Class<? extends TabComponent> tabComponentClass, String parentEntityName, Integer parentEntityId);
}
