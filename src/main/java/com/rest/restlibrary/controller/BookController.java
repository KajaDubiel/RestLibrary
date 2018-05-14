package com.rest.restlibrary.controller;

import com.rest.restlibrary.domain.BookDto;
import com.rest.restlibrary.mapper.BookMapper;
import com.rest.restlibrary.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/restLibrary")
public class BookController {

    @Autowired
    BookService bookService;
    @Autowired
    BookMapper bookMapper;

    @RequestMapping(method = RequestMethod.GET, value="getBooks")
    public List<BookDto> getBooks(){
        return bookMapper.mapToBookDtoList(bookService.getAllBooks());
    }

}
