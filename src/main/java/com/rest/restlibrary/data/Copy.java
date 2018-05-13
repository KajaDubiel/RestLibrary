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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Copy copy = (Copy) o;

        if (id != copy.id) return false;
        if (!book.equals(copy.book)) return false;
        if (!inventoryNumber.equals(copy.inventoryNumber)) return false;
        return borrows != null ? borrows.equals(copy.borrows) : copy.borrows == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + book.hashCode();
        result = 31 * result + inventoryNumber.hashCode();
        result = 31 * result + (borrows != null ? borrows.hashCode() : 0);
        return result;
    }
}
