package com.rest.services;

import com.rest.models.MenuItem;
import com.rest.repository.MenuRepository;
import java.util.List;
import java.util.Optional;

public class MenuService {
    private final MenuRepository repository;

    public MenuService() {
        this.repository = new MenuRepository();
    }

    public List<MenuItem> getAllMenuItems() {
        return repository.findAll();
    }

    public Optional<MenuItem> getMenuItemById(int id) {
        return Optional.ofNullable(repository.findById(id));
    }
}
