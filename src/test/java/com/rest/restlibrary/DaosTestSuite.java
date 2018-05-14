package com.rest.restlibrary;

import com.rest.restlibrary.data.Book;
import com.rest.restlibrary.data.Borrow;
import com.rest.restlibrary.data.Copy;
import com.rest.restlibrary.data.Reader;
import com.rest.restlibrary.data.dao.BookDao;
import com.rest.restlibrary.data.dao.BorrowDao;
import com.rest.restlibrary.data.dao.CopyDao;
import com.rest.restlibrary.data.dao.ReaderDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DaosTestSuite {

    @Autowired
    BookDao bookDao;

    @Autowired
    ReaderDao readerDao;

    @Autowired
    BorrowDao borrowDao;

    @Autowired
    CopyDao copyDao;

    @Test
    public void testPersistBook() {
        //Given
        Book book = new Book("Pan Tadeusz", "Adam Mickiewicz", 1978, "893723481");

        //When
        bookDao.save(book);
        long id = book.getId();

        //Then
        Assert.assertEquals(id, bookDao.findOne(id).getId());

        //CleanUp
        bookDao.delete(id);
    }

    @Test
    public void testPersistCopy() {
        //Given
        Book book = new Book("Pan Tadeusz", "Adam Mickiewicz", 1978, "893723481");
        Copy copy = new Copy(book, "20980428104");

        book.addCopy(copy);

        //When
        bookDao.save(book);

        //Then
        long id = book.getId();
        Assert.assertEquals(id, bookDao.findOne(id).getId());

        //CleanUp
        bookDao.delete(id);
    }

    @Test
    public void testPersistReader() {
        //Given
        Book book = new Book("Pan Tadeusz", "Adam Mickiewicz", 1978, "893723481");
        Copy copy = new Copy(book, "20980428104");
        Borrow borrow = new Borrow();
        Reader reader = new Reader("Adam", "Nowak", LocalDate.of(1970, 5, 12));

        book.addCopy(copy);

        borrow.addCopy(copy);
        copy.addBorrow(borrow);

        borrow.addReader(reader);
        reader.addBorrow(borrow);
        bookDao.save(book);
        copyDao.save(copy);

        //When
        readerDao.save(reader);
        borrowDao.save(borrow);
        long bookId = book.getId();
        long readerId = reader.getId();
        long borrowId = borrow.getId();
        long copyId = copy.getId();

        //Then
        Assert.assertEquals(readerId, readerDao.findOne(readerId).getId());

        //CleanUp
        borrowDao.delete(borrowId);
        readerDao.delete(readerId);
        copyDao.delete(copyId);
        bookDao.delete(bookId);
    }

    @Test
    public void testPersistBorrow() {
        //Given
        Book book = new Book("Pan Tadeusz", "Adam Mickiewicz", 1978, "893723481");
        Copy copy = new Copy(book, "20980428104");
        Borrow borrow = new Borrow();

        book.addCopy(copy);
        borrow.addCopy(copy);
        copy.addBorrow(borrow);

        bookDao.save(book);
        copyDao.save(copy);
        //When
        borrowDao.save(borrow);
        long borrowId = borrow.getId();
        long bookId = book.getId();
        long copyId = copy.getId();

        //Then
        Assert.assertEquals(borrowId, borrowDao.findOne(borrowId).getId());

        //CleanUp
        borrowDao.delete(borrowId);
        copyDao.delete(copyId);
        bookDao.delete(bookId);
    }

    @Test
    public void testReturnCopy() {
        //Given
        Book book = new Book("Pan Tadeusz", "Adam Mickiewicz", 1978, "893723481");
        Copy copy = new Copy(book, "20980428104");
        Reader reader = new Reader("Adam", "Nowak", LocalDate.of(1970, 5, 12));
        Borrow borrow = new Borrow();

        book.addCopy(copy);

        borrow.addCopy(copy);
        copy.addBorrow(borrow);
        reader.addBorrow(borrow);
        borrow.addReader(reader);

        bookDao.save(book);
        copyDao.save(copy);
        readerDao.save(reader);
        borrowDao.save(borrow);

        long bookId = book.getId();
        long copyId = copy.getId();
        long readerId = reader.getId();
        long borrowId = borrow.getId();

        //When
        borrow.returnCopy();
        borrowDao.save(borrow);

        //Then
        Assert.assertEquals(null, borrowDao.findOne(borrowId).getUntilDate());

        //CleanUp
        borrowDao.delete(borrowId);
        readerDao.delete(readerId);
        copyDao.delete(copyId);
        bookDao.delete(bookId);
    }
}
