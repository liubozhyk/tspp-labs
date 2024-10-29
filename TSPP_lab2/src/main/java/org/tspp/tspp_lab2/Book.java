package org.tspp.tspp_lab2;

public class Book {
    private String name;
    private String author;
    private float price;

    public Book(String name, String author, float price) {
        this.name = name;
        this.author = author;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Книга (" + name + ')';
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public float getPrice() {
        return price;
    }
}
