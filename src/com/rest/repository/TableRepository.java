package com.rest.repository;

import com.rest.models.Table;
import java.util.ArrayList;
import java.util.List;

public class TableRepository {
    private final List<Table> tables = new ArrayList<>();

    public TableRepository() {
        tables.add(new Table(1, 4));
        tables.add(new Table(2, 2));
        tables.add(new Table(3, 6));
    }

    public List<Table> findAll() {
        return tables;
    }
}
