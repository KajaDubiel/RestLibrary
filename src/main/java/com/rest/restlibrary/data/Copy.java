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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "join_borrow_copy",
            joinColumns = @JoinColumn(name = "borrow_id"),
            inverseJoinColumns = @JoinColumn(name = "copy_id")
    )
    private List<Borrow> borrows;

    public Copy(Book book, String inventoryNumber) {
        this.book = book;
        this.inventoryNumber = inventoryNumber;
        isBorrowed = false;
        borrows = new ArrayList<>();
    }

    public void setIsBorrowed() {
        isBorrowed = true;
    }

    public void addBorrow(Borrow borrow) {
        borrows.add(borrow);
    }
}
