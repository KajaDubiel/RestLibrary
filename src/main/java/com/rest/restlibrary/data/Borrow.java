package com.rest.restlibrary.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @ManyToOne
    @JoinColumn(name = "copy_id")
    private Copy copy;

    @ManyToOne
    @JoinColumn(name = "reader_id")
    private Reader reader;

    public Borrow(Reader reader, Copy copy){
        this.reader = reader;
        this.copy = copy;
    }

    public void addCopy(Copy copy) {
           this.copy = copy;
    }

    public void returnCopy() {
        if (untilDate != null) {
            untilDate = null;
            System.out.println("Your return has been accepted");
        } else {
            System.out.println("Such borrowing doesn't exist");
        }
    }

    public void addReader(Reader reader) {
        this.reader = reader;
    }

}
