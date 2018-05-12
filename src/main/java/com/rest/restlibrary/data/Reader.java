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

    @ManyToMany
//            (cascade = CascadeType.ALL)
    @JoinTable(name = "join_borrow_reader",
            joinColumns = @JoinColumn(name = "borrow_id"),
            inverseJoinColumns = @JoinColumn(name = "reader_id"))
    private List<Borrow> borrows;

    public Reader(String firstName, String lastName, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        borrows = new ArrayList<>();
    }

    public void addBorrow(Borrow borrow) {
        borrows.add(borrow);
    }
}
