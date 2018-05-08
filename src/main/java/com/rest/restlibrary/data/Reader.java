package com.rest.restlibrary.data;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "Readers")
public class Reader {
    @Id
    @NotNull
    @GeneratedValue
    private long id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birth_date")
    @Basic
    private Date birthDate;

    @OneToMany(
            targetEntity = Copy.class,
            mappedBy = "reader",
            //cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private List<Copy> borrowedCopies;

    public Reader(String firstName, String lastName, java.sql.Date birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        borrowedCopies = new ArrayList<>();
    }

    public void setBorrowedCopies(Copy copy) {
        copy.setIsBorrowed();
        borrowedCopies.add(copy);

    }
}
