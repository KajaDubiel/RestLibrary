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
@Table(name = "copy")
public class Copy {

    @Id
    @GeneratedValue
    @NotNull
    private long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "inventory_number")
    private String inventoryNumber;

    @Column
    private boolean isBorrowed;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "borrow_id")
    private Borrow borrow;

    public Copy(Book book, String inventoryNumber) {
        this.book = book;
        this.inventoryNumber = inventoryNumber;
        isBorrowed = false;
    }

    public void setIsBorrowed() {
        isBorrowed = true;
    }

    public void addBorrow(Borrow borrow) {
        this.borrow = borrow;
    }
}
