package org.tspp.tspplab1;

public class Book {
    private String name;
    private String author;
    private String genres;


    public Book(String name, String author, String genres) {
        this.name = name;
        this.genres = genres;
        this.author = author;
    }

    @Override
    public String toString() {
        return "Книга (" + this.name + ")";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = name;
    }

}
