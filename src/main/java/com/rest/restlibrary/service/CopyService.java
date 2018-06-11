package com.rest.restlibrary.service;

import com.rest.restlibrary.data.Book;
import com.rest.restlibrary.data.Copy;
import com.rest.restlibrary.data.dao.BookDao;
import com.rest.restlibrary.data.dao.CopyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CopyService {

    @Autowired
    CopyDao copyDao;

    @Autowired
    BookDao bookDao;

    //?
    public void createCopy(Copy copy, long bookId){
        Book book = bookDao.findOne(bookId);
        book.addCopy(copy);
        copy.addBook(book);
        copyDao.save(copy);
        bookDao.save(book);
    }

    public Copy getCopy(long copyId){
        return copyDao.findOne(copyId);
    }

    public List<Copy> getCopiesByBookId(long bookId){
        return copyDao.findAllByBookId(bookId);
    }

    public void updateCopy(Copy copy){
         copyDao.save(copy);
    }

    public void deleteCopy(long copyId){
        copyDao.delete(copyId);
    }
}
