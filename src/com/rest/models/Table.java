package com.rest.models;

public class Table {
    public enum TableStatus {
        FREE, OCCUPIED
    }

    private int id;
    private int capacity;
    private TableStatus status;

    public Table(int id, int capacity) {
        this.id = id;
        this.capacity = capacity;
        this.status = TableStatus.FREE;
    }

    public int getId() {
        return id;
    }

    public int getCapacity() {
        return capacity;
    }

    public TableStatus getStatus() {
        return status;
    }

    public void setStatus(TableStatus status) {
        this.status = status;
    }
    public String toString(){
        return "Table ID: " + id + ",Capacity: "+ capacity + ",Status: " + status;
    }

}
