package sk.stefan.mvps.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.VaadinSessionScope;
import com.vaadin.ui.TabSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import sk.stefan.mvps.model.entity.A_User;
import sk.stefan.mvps.model.entity.PublicBody;
import sk.stefan.mvps.model.entity.PublicPerson;
import sk.stefan.mvps.model.entity.PublicRole;
import sk.stefan.interfaces.TabEntity;
import sk.stefan.mvps.model.service.EntityService;
import sk.stefan.mvps.model.service.LinkService;
import sk.stefan.mvps.view.tabs.TabComponent;
import sk.stefan.mvps.view.tabs.TabFactory;
import sk.stefan.ui.KosvopoUI;
import sk.stefan.utils.ParamsCache;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by elopin on 03.11.2015.
 */
@SpringView(name = "", ui = KosvopoUI.class)
@VaadinSessionScope
public class MainTabsheet extends TabSheet implements View {

    @Autowired
    private LinkService linkService;

    @Autowired
    private TabFactory tabFactory;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private EntityService entityService;

    private Map<String, TabComponent> tabMap = new HashMap<>();

    public MainTabsheet() {
        setSizeFull();
        setCloseHandler((tabsheet, component) -> {
            TabComponent tab = (TabComponent) component;
            removeComponent(tab);
            tabMap.remove(tab.getTabId());
            if (getComponentCount() == 0) {
                showDefaultTab();
            }
        });
    }

    private void showDefaultTab() {
        TabComponent tab = context.getBean(V2_EnterView.class);
        addTab(tab, tab.getTabCaption()).setClosable(true);
        tabMap.put(tab.getTabId(), tab);
        setSelectedTab(tab);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        if (!event.getParameters().isEmpty()) {
            ParamsCache params = new ParamsCache(event.getParameters());
            String tabName = params.getTabName();
            Integer id = params.getId();
            Integer parentId = params.getParentId();
            String parentName = params.getParentName();

            String tabId = tabName + (id != null ? id : parentId != null ? parentId : "");
            TabComponent tab = tabMap.get(tabId);
            if (tab == null) {
                Class<? extends TabComponent> tabClass = tabFactory.getTabTypeByName(tabName);
                if (tabClass != null) {
                    tab = context.getBean(tabClass);
                    if (params.getId() != null) {
                        TabEntity entity = entityService.getTabEntityByTabName(params.getTabName(), params.getId());
                        tab.setEntity(entity);
                        tab.setSaveListener(this::saveEntity);
                        tab.setRemoveListener(this::removeEntity);
                    } else if (params.getParentId() != null) {
                        TabEntity entity = entityService.getTabEntityByNameAndId(params.getParentName(), params.getParentId());
                        tab.setEntity(entity);
                        tab.setSaveListener(this::saveEntity);
                    } else {
                        tab.setSaveListener(this::saveEntity);
                    }

                    addTab(tab, tab.getTabCaption()).setClosable(true);
                    tabMap.put(tab.getTabId(), tab);
                }
            }

            if (tab != null) {
                setSelectedTab(tab);
            } else {
                Page.getCurrent().open("#!/tab=enterTab", null);
            }
        } else {
            Page.getCurrent().open("#!/tab=enterTab", null);
        }
    }

    private <T> void removeEntity(T t) {
        TabEntity entity = (TabEntity) t;
        if (entityService.removeEntity(entity)) {
            String entityTabId = entityService.getTabIdByEntity(entity);
            TabComponent tab = tabMap.get(entityTabId);
            if (tab != null) {
                removeComponent(tab);
                tabMap.remove(entityTabId);
            }
        }
    }

    private <T> void saveEntity(T t) {
        TabEntity entity = entityService.saveEntity((TabEntity) t);
        TabComponent tab = tabMap.get(entityService.getTabIdByEntity(entity));
        if (tab != null) {
            tab.setEntity(entity);
        } else {
            tab = context.getBean((Class<? extends TabComponent>) tabFactory.getTabTypeByName(entity.getRelatedTabName()));
            tab.setEntity(entity);
            tab.setSaveListener(this::saveEntity);
            tab.setRemoveListener(this::removeEntity);
            addTab(tab, tab.getTabCaption()).setClosable(true);
            tabMap.put(tab.getTabId(), tab);
        }
        setSelectedTab(tab);
        checkNewFormForEntity(entity);
    }

    @Override
    protected void fireSelectedTabChange() {
        super.fireSelectedTabChange();
        TabComponent tab = ((TabComponent) getSelectedTab());
        tab.show();
        String uriFragment;
        if (tab.getEntity() != null) {
            uriFragment = linkService.getUriFragmentForEntity(tab.getEntity()).substring(1);
        } else if (tab.getParentEntity() != null) {
            uriFragment = linkService.getUriFragmentForTabWithParentEntity(tab.getClass(), tab.getParentEntity().getEntityName(), tab.getParentEntity().getId()).substring(1);
        } else {
            uriFragment = linkService.getUriFragmentForTab(tab.getClass()).substring(1);
        }
        Page.getCurrent().setUriFragment(uriFragment, false);
    }

    private void checkNewFormForEntity(TabEntity entity) {
        String tabId = null;
        if (PublicBody.class.isAssignableFrom(entity.getClass())) {
            tabId = "novyOrgan";
        } else if (PublicRole.class.isAssignableFrom(entity.getClass())) {
            tabId = "novaVerejnaRole";
        } else if (PublicPerson.class.isAssignableFrom(entity.getClass())) {
            tabId = "novaVerejnaOsoba";
        } else if (A_User.class.isAssignableFrom(entity.getClass())) {
            tabId = "novyUzivatel";
        }
        if (tabId != null) {
            TabComponent tab = tabMap.get(tabId);
            if (tab != null) {
                removeComponent(tab);
                tabMap.remove(tabId);
            }
        }
    }

    public void refresh() {
        tabMap.clear();
        removeAllComponents();
        showDefaultTab();
    }
}
