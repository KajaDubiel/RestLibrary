package com.rest.restlibrary.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue
    @NotNull
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "release_year")
    private int releaseYear;

    @Column(name = "isbn")
    private String isbn;

    @OneToMany(
            targetEntity = Copy.class,
            mappedBy = "book",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private List<Copy> copies;

    public Book(String title, String author, int releaseYear, String isbn) {
        this.title = title;
        this.author = author;
        this.releaseYear = releaseYear;
        this.isbn = isbn;
        this.copies = new ArrayList<>();
    }

    public void addCopy(Copy copy) {
        copies.add(copy);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (id != book.id) return false;
        if (releaseYear != book.releaseYear) return false;
        if (!title.equals(book.title)) return false;
        if (!author.equals(book.author)) return false;
        if (!isbn.equals(book.isbn)) return false;
        return copies != null ? copies.equals(book.copies) : book.copies == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + title.hashCode();
        result = 31 * result + author.hashCode();
        result = 31 * result + releaseYear;
        result = 31 * result + isbn.hashCode();
        result = 31 * result + (copies != null ? copies.hashCode() : 0);
        return result;
    }
}
