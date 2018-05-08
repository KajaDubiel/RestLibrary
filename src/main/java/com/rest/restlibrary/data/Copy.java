package com.rest.restlibrary.data;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "Copies")
public class Copy {
    @Id
    @NotNull
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "is_borrowed")
    private boolean borrowed;

    @ManyToOne
    @JoinColumn(name = "reader_id")
    private Reader reader;

    @ManyToOne
    @JoinColumn(name = "borrowingId")
    private Borrowing borrowing;

    public Copy(Book book, String isbn) {
        this.book = book;
        this.isbn = isbn;
        borrowed = false;
    }

    public void setIsBorrowed() {
        this.borrowed = true;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public void setBorrowing(Borrowing borrowing) {
        this.borrowing = borrowing;
    }
}
