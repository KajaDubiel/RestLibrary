package com.rest.restlibrary.service;

import com.rest.restlibrary.controller.BookNotFoundException;
import com.rest.restlibrary.data.Book;
import com.rest.restlibrary.data.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    BookDao bookDao;

    public void createBook(Book book){
        bookDao.save(book);
    }

    public Book getBook(long bookId){
        return bookDao.findOne(bookId);
    }

    public List<Book> getAllBooks(){
        //named query
        return bookDao.findAll();
        //return bookDao.retrieveBooks();
    }

    public void updateBook(Book book){
        bookDao.save(book);
    }

    public void deleteBook(long bookId){
        bookDao.delete(bookId);
    }
    //juz tutaj dto
    //z listą kopii
    //
}
