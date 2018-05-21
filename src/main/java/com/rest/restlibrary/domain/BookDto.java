package com.rest.restlibrary.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rest.restlibrary.data.Copy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Component
//@JsonIgnoreProperties(ignoreUnknown = true)
public class BookDto {
    private long id;
    private String title;
    private String author;
    private int releaseYear;
    private String isbn;
    private List<Copy> copies;
    //hql zapytanie np graf api
    //select book from left join copy


}
