package org.tspp.tspp_lab3;

public class Equipment {
    private String name;
    private double price;
    private int amount;
    private boolean isAvailable;

    public Equipment(String name, double price, int amount) {
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.isAvailable = amount > 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public boolean setAvailable(boolean available) {
        return amount > 0;
    }
}
