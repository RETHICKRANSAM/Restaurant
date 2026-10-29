package com.rest.repository;

import com.rest.models.MenuItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MenuRepository {
    private final List<MenuItem> menuItems = new ArrayList<>();

    public MenuRepository() {
        menuItems.add(new MenuItem(1, "Fried rice", 12.50));
        menuItems.add(new MenuItem(2, "Noodle soup", 10.00));
        menuItems.add(new MenuItem(3, "Iced tea", 3.50));
    }

    public List<MenuItem> findAll() {
        return Collections.unmodifiableList(menuItems);
    }

    public MenuItem findById(int id) {
        for (MenuItem menuItem : menuItems) {
            if (menuItem.getId() == id) {
                return menuItem;
            }
        }
        return null;
    }
}
