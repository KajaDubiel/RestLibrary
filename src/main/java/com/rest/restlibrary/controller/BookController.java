package com.rest.restlibrary.controller;

import com.rest.restlibrary.data.Book;
import com.rest.restlibrary.domain.BookDto;
import com.rest.restlibrary.mapper.BookMapper;
import com.rest.restlibrary.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@CrossOrigin("*")
@RestController
@RequestMapping("/restLibrary")
public class BookController {

    @Autowired
    BookService bookService;
    @Autowired
    BookMapper bookMapper;

    @RequestMapping(method = RequestMethod.GET, value="/getBooks")
    public List<BookDto> getBooks(){
        return bookMapper.mapToBookDtoList(bookService.getAllBooks());
    }

    //handle null pointer exception
    @RequestMapping(method = RequestMethod.GET, value = "/getBook")
    public BookDto getBook(@RequestParam long bookId){
            return bookMapper.mapToBookDto(bookService.getBook(bookId));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteBook")
    public void deleteBook(@RequestParam long bookId){
        bookService.deleteBook(bookId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/createBook", consumes = APPLICATION_JSON_VALUE)
    public void createBook(@RequestBody BookDto bookDto){
          bookService.createBook(bookMapper.mapToBook(bookDto));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/updateBook", consumes = APPLICATION_JSON_VALUE)
    public void updateBook(@RequestBody BookDto bookDto){
        bookService.updateBook(bookMapper.mapToBook(bookDto));
    }
}
