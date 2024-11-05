package org.tspp.tspp_lab3;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.CheckBox;

public class Equipment {
    private String name;
    private double price;
    private IntegerProperty quantity;
    private boolean availability;
    private CheckBox availableCheckBox;

    public Equipment(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = new SimpleIntegerProperty(Math.max(0, quantity));
    }

    private boolean isAvailability() {
        this.availability = quantity.get() > 0;
        return availability;
    }

    public CheckBox getAvailableCheckBox() {
        return availableCheckBox;
    }

    public void setAvailableCheckBox(CheckBox availableCheckBox) {
        this.availableCheckBox = availableCheckBox;
    }

    public void isCheckBoxSelected() {
        this.getAvailableCheckBox().setSelected(isAvailability());
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

    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int quantity) {
        this.quantity.set(Math.max(0, quantity));
    }
}
