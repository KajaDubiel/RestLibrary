package com.rest.restlibrary.domain;

import com.rest.restlibrary.data.Copy;
import com.rest.restlibrary.data.Reader;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BorrowDto {
    private LocalDate fromDate;
    private LocalDate untilDate;
    private Copy copy;
    private Reader reader;

    public BorrowDto(Copy copy, Reader reader){
        this.copy = copy;
        this.reader = reader;
    }
}
