package org.homemade.elo.dto;

public class Name {
    private int id;
    private String name;

    public Name(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Name(String id, String name) {
        this.id = Integer.parseInt(id);
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
