package com.rest.restlibrary.service;

import com.rest.restlibrary.controller.BookNotFoundException;
import com.rest.restlibrary.data.Book;
import com.rest.restlibrary.data.Borrow;
import com.rest.restlibrary.data.Copy;
import com.rest.restlibrary.data.dao.BookDao;
import com.rest.restlibrary.data.dao.BorrowDao;
import com.rest.restlibrary.data.dao.CopyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
public class BookService {

    @Autowired
    BookDao bookDao;

    @Autowired
    BorrowDao borrowDao;

    @Autowired
    CopyDao copyDao;

    public void createBook(Book book) {
        bookDao.save(book);
    }

    public Book getBook(long bookId) {
        return bookDao.findOne(bookId);
    }

    public List<Book> getAllBooks() {
        return bookDao.findAll();
    }

    public void updateBook(Book book) {
        bookDao.save(book);
    }

    public void deleteBook(long bookId) {
        boolean hasBookBorrowedCopies = checkBookCopiesAreBorrowed(bookId);
        if (hasBookBorrowedCopies) {
            throw new RuntimeException("This book has borrowed copies, you can not delete");
        } else {
            List<Copy> copies = copyDao.findAllByBookId(bookId);
            for (Copy copy : copies) {
                borrowDao.deleteByCopyId(copy.getId());
                copyDao.delete(copy.getId());
            }
            bookDao.delete(bookId);
        }
    }

    private boolean checkBookCopiesAreBorrowed(long bookId) {
        List<Copy> existingCopies = bookDao.findOne(bookId).getCopies();
        List<Borrow> existingBorrows = new ArrayList<>();
        if (existingCopies.isEmpty()) {
            return false;
        } else {
            for (Copy copy : existingCopies) {
                List<Borrow> borrows = copy.getBorrows();

                System.out.print("Borrows: ");
                if (!borrows.isEmpty()) {
                    for (Borrow borrow : borrows) {
                        System.out.println("Id: " + borrow.getId() + " is finished? " + borrow.getUntilDate());
                        Optional<LocalDate> borrowUntilDate = ofNullable(borrow.getUntilDate());
                        if (borrowUntilDate.isPresent()) {
                            existingBorrows.add(borrow);
                        }
                    }
                }
            }
        }

        if (!existingBorrows.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
    //juz tutaj dto
    //z listą kopii?
    //
}
