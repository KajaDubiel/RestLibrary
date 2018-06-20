package com.rest.restlibrary.service;

import com.rest.restlibrary.data.Book;
import com.rest.restlibrary.data.Borrow;
import com.rest.restlibrary.data.Copy;
import com.rest.restlibrary.data.Reader;
import com.rest.restlibrary.data.dao.BookDao;
import com.rest.restlibrary.data.dao.CopyDao;
import com.rest.restlibrary.data.dao.ReaderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CopyService {

    @Autowired
    CopyDao copyDao;

    @Autowired
    BookDao bookDao;

    @Autowired
    ReaderDao readerDao;

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

    public List<Copy> getBorrowedCopiesByReaderId(long readerId){
        List<Copy> borrowedCopies = new ArrayList<>();
        Reader reader = readerDao.findOne(readerId);
        List<Borrow> borrows = reader.getBorrows();
        System.out.println("reader  borrows length: " + borrows.size());
        for(Borrow borrow: borrows){
            System.out.println("Starting for loop..., borrowId:" + borrow.getId());
            Optional<LocalDate> untilDate = Optional.ofNullable(borrow.getUntilDate());
            if(untilDate.isPresent()){
                System.out.println("Borrow is not returned");
                borrowedCopies.add(borrow.getCopy());
                System.out.println(borrowedCopies.size()+ "<--borr copies size");
            }
        }

        return borrowedCopies;
    }

    public void updateCopy(Copy copy){
         copyDao.save(copy);
    }

    public void deleteCopy(long copyId){
        copyDao.delete(copyId);
    }
}
