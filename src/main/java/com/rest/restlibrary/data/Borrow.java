package com.rest.restlibrary.data;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "borrow")
public class Borrow {

    @Id
    @GeneratedValue
    @NotNull
    private long id;

    @Column(name = "from_date")
    private LocalDate fromDate = LocalDate.now();

    @Column(name = "until_date")
    private LocalDate untilDate = fromDate.plusDays(30);

    @OneToMany(
            targetEntity = Copy.class,
            mappedBy = "borrow",
            fetch = FetchType.EAGER
    )
    private List<Copy> copies = new ArrayList<>();

    @OneToMany(
            targetEntity = Copy.class,
            mappedBy = "borrow",
            fetch = FetchType.EAGER
    )
    private List<Reader> readers = new ArrayList<>();

    public void addCopy(Copy copy) {
        for (Copy oneCopy : copies) {
            if (oneCopy.equals(copy) && untilDate != null) {
                System.out.println("This copy is borrowed");
            } else {
                copies.add(copy);
                System.out.println("Please take your copy");
            }
        }
    }

    public void returnCopy(Copy copy) {
        if (untilDate != null) {
            untilDate = null;
            System.out.println("Your return has been accepted");
        } else {
            System.out.println("Such borrowing doesn't exist");
        }
    }

    public void addReader(Reader reader) {
        readers.add(reader);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Borrow borrow = (Borrow) o;

        if (id != borrow.id) return false;
        if (!fromDate.equals(borrow.fromDate)) return false;
        if (untilDate != null ? !untilDate.equals(borrow.untilDate) : borrow.untilDate != null) return false;
        if (copies != null ? !copies.equals(borrow.copies) : borrow.copies != null) return false;
        return readers != null ? readers.equals(borrow.readers) : borrow.readers == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + fromDate.hashCode();
        result = 31 * result + (untilDate != null ? untilDate.hashCode() : 0);
        result = 31 * result + (copies != null ? copies.hashCode() : 0);
        result = 31 * result + (readers != null ? readers.hashCode() : 0);
        return result;
    }
}
