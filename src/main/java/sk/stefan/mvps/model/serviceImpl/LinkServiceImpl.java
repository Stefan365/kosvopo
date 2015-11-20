package sk.stefan.mvps.model.serviceImpl;

import org.springframework.stereotype.Service;
import sk.stefan.annotations.ViewTab;
import sk.stefan.mvps.model.entity.TabEntity;
import sk.stefan.mvps.model.service.LinkService;
import sk.stefan.mvps.view.tabs.TabComponent;

/**
 * Created by elopin on 03.11.2015.
 */
@Service
public class LinkServiceImpl implements LinkService {
    @Override
    public String getUriFragmentForTab(Class<? extends TabComponent> menuItemTabClass) {
        ViewTab annotation = menuItemTabClass.getAnnotation(ViewTab.class);
        return "#!/tab="+annotation.value();
    }

    @Override
    public String getUriFragmentForEntity(TabEntity entity) {
        return "#!/tab=" + entity.getRelatedTabName() + "&id=" + entity.getId();
    }

    @Override
    public String getUriFragmentForTabWithParentEntity(Class<? extends TabComponent> tabComponentClass, String parentEntityName, Integer parentEntityId) {
        return getUriFragmentForTab(tabComponentClass) + "&parentName=" + parentEntityName + "&parentId=" + parentEntityId;
    }
}
