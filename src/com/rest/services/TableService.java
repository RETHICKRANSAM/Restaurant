package com.rest.services;

import com.rest.models.Table;
import com.rest.repository.TableRepository;
import java.util.List;
import java.util.Optional;

public class TableService {
    private final TableRepository repository;

    public TableService() {
        repository = new TableRepository();
    }

    public List<Table> getAllTables() {
        return repository.findAll();
    }

    public Optional<Table> getTableById(int id) {
        for (Table table : repository.findAll()) {
            if (table.getId() == id) {
                return Optional.of(table);
            }
        }
        return Optional.empty();
    }
}
