package org.tspp.tspp_lab3;

import javafx.scene.control.CheckBox;

public class Equipment {
    private String name;
    private double price;
    private int quantity;
    private boolean availability;
    private CheckBox checkBox;

    public Equipment(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.availability = quantity > 0;
        this.checkBox = new CheckBox();
        setChecked();
    }

    private boolean isAvailability() {
        return availability;
    }

    private void setAvailability(int quantity) {
        this.availability = quantity > 0;
    }

    private void setChecked() {
        checkBox.setDisable(true);
        checkBox.setSelected(isAvailability());
    }

    public CheckBox getCheckBox() {
        setChecked();
        return checkBox;
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
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        setAvailability(quantity);
    }
}
