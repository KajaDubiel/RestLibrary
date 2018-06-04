package com.rest.restlibrary.domain;


import com.rest.restlibrary.data.Borrow;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ReaderDto {
    private long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String readerEmail;
    private List<Borrow> borrows;
}
