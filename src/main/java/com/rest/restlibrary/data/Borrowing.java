package com.rest.restlibrary.data;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "Borrowings")
public class Borrowing {
    @Id
    @NotNull
    @GeneratedValue
    private long id;

//    @Column(name = "from")
//    @Basic
//    private Date from;
//
//    @Column(name = "to")
//    @Basic
//    private Date to;

    @OneToOne(
            //cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "reader_id")
    private Reader reader;

    @OneToMany(
            targetEntity = Copy.class,
            mappedBy = "borrowing",
            //cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private List<Copy> copies;

    public Borrowing(Reader reader, List<Copy> copies) {
        this.reader = reader;
        this.copies = copies;
//        this.from = from;
//        this.to = to;

    }

    public void setCopy(Copy copy) {
        copies.add(copy);
    }


}
