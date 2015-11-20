package sk.stefan.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by elopin on 03.11.2015.
 */
public class ParamsCache {

    private Map<String, String> params = new HashMap<>();

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

    public String getTabName() {
        return params.get("tab");
    }

    public Integer getId() {
        String id = params.get("id");
        return id == null ? null : Integer.parseInt(id);
    }

    public Integer getParentId() {
        String parentId = params.get("parentId");
        return parentId == null ? null : Integer.parseInt(parentId);
    }

    public String getParentName() {
        return params.get("parentName");
    }
}
