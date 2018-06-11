package com.rest.restlibrary.domain;

import com.rest.restlibrary.data.Book;
import com.rest.restlibrary.data.Borrow;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CopyDto {
    private long id;
    private Book book;
    private String inventoryNumber;
    private List<Borrow> borrows;
}
