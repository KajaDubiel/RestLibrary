package com.rest.restlibrary.data;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "Books")
public class Book {
    @Id
    @NotNull
    @GeneratedValue
    private long id;

    @Column(name = "author")
    private String author;

    @Column(name = "title")
    private String title;

    @Column(name = "release_year")
    private int releaseYear;

    @Column(name = "copies")
    @OneToMany(
            targetEntity = Copy.class,
            mappedBy = "book",
            //cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private List<Copy> copies;


    public Book(String author, String title, int releaseYear) {
        this.author = author;
        this.title = title;
        this.releaseYear = releaseYear;
        copies = new ArrayList<>();
    }

    public void setCopy(Copy copy) {
        copies.add(copy);
    }

    public String getAuthor() {
        return author;
    }

    public long getId() {
        return id;
    }
}
