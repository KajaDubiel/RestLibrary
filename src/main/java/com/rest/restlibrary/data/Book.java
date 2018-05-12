package com.rest.restlibrary.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
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
}
