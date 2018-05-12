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

    //HERE
//    @Column(name = "from")
//    private LocalDate from;

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

//    public Borrow(LocalDate from) {
//        this.from = from;
//    }

    public boolean addCopy(Copy copy) {
        if (copy.isBorrowed() == false) {
            copies.add(copy);
            copy.setIsBorrowed();
            return true;
        } else {
            System.out.println("This copy is borrowed");
            return false;
        }
    }

    public void addReader(Reader reader) {
        readers.add(reader);
    }
}
