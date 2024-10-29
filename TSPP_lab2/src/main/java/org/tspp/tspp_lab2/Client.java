package org.tspp.tspp_lab2;

public class Client {
    private String name;
    private final int id;
    private static int idCounter = 0;

    @Override
    public String toString() {
        return name;
    }

    public Client(String name) {
        this.name = name;
        this.id = idCounter++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }
}
