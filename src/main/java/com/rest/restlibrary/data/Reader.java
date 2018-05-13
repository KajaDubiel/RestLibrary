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
@Table(name = "reader")
public class Reader {

    @Id
    @GeneratedValue
    @NotNull
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @ManyToOne
//            (cascade = CascadeType.ALL)
   @JoinColumn(name = "borrow_id")
    private Borrow borrow;

    public Reader(String firstName, String lastName, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    public void addBorrow(Borrow borrow) {
        this.borrow = borrow;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reader reader = (Reader) o;

        if (id != reader.id) return false;
        if (!firstName.equals(reader.firstName)) return false;
        if (!lastName.equals(reader.lastName)) return false;
        if (!birthDate.equals(reader.birthDate)) return false;
        return borrow != null ? borrow.equals(reader.borrow) : reader.borrow == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + birthDate.hashCode();
        result = 31 * result + (borrow != null ? borrow.hashCode() : 0);
        return result;
    }
}
