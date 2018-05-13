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

//    @Column
//    private boolean isBorrowed;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "borrow_id")
    private Borrow borrow;

    public Copy(Book book, String inventoryNumber) {
        this.book = book;
        this.inventoryNumber = inventoryNumber;
        //isBorrowed = false;
    }

//    public void setIsBorrowed() {
//        isBorrowed = true;
//    }

    public void addBorrow(Borrow borrow) {
        this.borrow = borrow;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Copy copy = (Copy) o;

        if (id != copy.id) return false;
        if (!book.equals(copy.book)) return false;
        if (!inventoryNumber.equals(copy.inventoryNumber)) return false;
        return borrow != null ? borrow.equals(copy.borrow) : copy.borrow == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + book.hashCode();
        result = 31 * result + inventoryNumber.hashCode();
        result = 31 * result + (borrow != null ? borrow.hashCode() : 0);
        return result;
    }
}
