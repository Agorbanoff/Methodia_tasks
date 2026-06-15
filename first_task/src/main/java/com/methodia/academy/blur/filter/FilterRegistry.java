package com.methodia.academy.blur.filter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilterRegistry {
    private final Map<String, Filter> filtersByName;

    public FilterRegistry(List<Filter> filters) {
        this.filtersByName = new HashMap<>();

        for (Filter filter : filters) {
            filtersByName.put(filter.name().toLowerCase(), filter);
        }
    }

    public Filter getByName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Filter name cannot be blank.");
        }

        Filter filter = filtersByName.get(name.toLowerCase());

        if (filter == null) {
            throw new IllegalArgumentException("Unsupported filter: " + name);
        }

        return filter;
    }
}
