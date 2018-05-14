package com.rest.restlibrary.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
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

    @OneToMany(
            targetEntity = Borrow.class,
            mappedBy = "reader",
            fetch = FetchType.EAGER
    )
    private List<Borrow> borrows;

    public Reader(String firstName, String lastName, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.borrows = new ArrayList<>();
    }

    public void addBorrow(Borrow borrow) {
        borrows.add(borrow);
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
        return borrows != null ? borrows.equals(reader.borrows) : reader.borrows == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + birthDate.hashCode();
        result = 31 * result + (borrows != null ? borrows.hashCode() : 0);
        return result;
    }
}
