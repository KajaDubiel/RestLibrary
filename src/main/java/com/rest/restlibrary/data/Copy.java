package com.rest.restlibrary.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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


    @OneToMany(
            targetEntity = Borrow.class,
            mappedBy = "copy",
            fetch = FetchType.EAGER)
    private List<Borrow> borrows;

    public Copy(Book book, String inventoryNumber) {
        this.book = book;
        this.inventoryNumber = inventoryNumber;
        this.borrows = new ArrayList<>();
    }

    public void addBorrow(Borrow borrow) {
        borrows.add(borrow);
    }


}
