package sk.stefan.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Cache pro parametry získané z URL.
 * @author elopin on 03.11.2015.
 */
public class ParamsCache {

    private Map<String, String> params = new HashMap<>();

    /**
     * Zpracuje URL parametry a uloží je do cache.
     * @param parameters parametry z URL odkazu
     */
    public ParamsCache(String parameters) {
        if (parameters != null && !parameters.isEmpty()) {
            if (parameters.contains("&")) {
                String[] paramParts = parameters.split("&");
                for (String part : paramParts) {
                    String[] parts = part.split("=");
                    params.put(parts[0], parts[1]);
                }
            } else {
                String[] tabPart = parameters.split("=");
                params.put(tabPart[0], tabPart[1]);
            }
        }
    }

    /**
     * Vrací název záložky získaný z URL prametrů.
     * @return název záložky
     */
    public String getTabName() {
        return params.get("tab");
    }

    /**
     * Vrací identifikátor entity získaný z URL parametrů.
     * @return identifikátor entity
     */
    public Integer getId() {
        String id = params.get("id");
        return id == null ? null : Integer.parseInt(id);
    }

    /**
     * Vrací identifikátor nadřazené entity získaný z URL.
     * @return identifikátor nadřazené entity
     */
    public Integer getParentId() {
        String parentId = params.get("parentId");
        return parentId == null ? null : Integer.parseInt(parentId);
    }

    /**
     * Vrací název nadřazené entity získaný z URL parametrů.
     * @return název nadřazené entity
     */
    public String getParentName() {
        return params.get("parentName");
    }
}
