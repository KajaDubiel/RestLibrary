package com.rest.restlibrary;

import com.rest.restlibrary.data.Book;
import com.rest.restlibrary.data.Borrowing;
import com.rest.restlibrary.data.Copy;
import com.rest.restlibrary.data.Reader;
import com.rest.restlibrary.data.dao.BookDao;
import com.rest.restlibrary.data.dao.BorrowingDao;
import com.rest.restlibrary.data.dao.CopyDao;
import com.rest.restlibrary.data.dao.ReaderDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DaosTestSuite {
    @Autowired
    private BookDao bookDao;
    @Autowired
    private CopyDao copyDao;
    @Autowired
    private ReaderDao readerDao;
    @Autowired
    private BorrowingDao borrowingDao;

    @Test
    public void testAddNewBook() {
        //Given
        Book book = new Book("Henryk Sienkiewicz", "Ogniem i mieczem", 2018);

        //When
        bookDao.save(book);
        long id = book.getId();

        //Then
        Assert.assertEquals(book.getAuthor(), bookDao.findOne(id).getAuthor());

        //CleanUp
        bookDao.delete(id);
    }

    @Test
    public void testAddNewCopy() {
        //Given
        Book book = new Book("Henryk Sienkiewicz", "Ogniem i mieczem", 2018);
        Copy copy = new Copy(book, "18726372");

        //When
        book.setCopy(copy);
        bookDao.save(book);
        copyDao.save(copy);
        long copyId = copy.getId();
        long bookId = book.getId();

        //Then
        Assert.assertEquals(1, book.getCopies().size());
        Assert.assertEquals(copyId, copyDao.findOne(copyId).getId());

        //CleanUp
        copyDao.delete(copyId);
        bookDao.delete(bookId);
    }

    @Test
    public void testAddReader() {
        //Given
        Reader reader = new Reader("John", "Smith", Date.valueOf("1990-11-12"));
        Book book = new Book("Henryk Sienkiewicz", "Ogniem i mieczem", 2018);
        Copy copy = new Copy(book, "18726372");
        Copy copy2 = new Copy(book, "18218382");

        book.setCopy(copy);
        book.setCopy(copy2);
        reader.setBorrowedCopies(copy);
        reader.setBorrowedCopies(copy2);

        bookDao.save(book);
        copyDao.save(copy);

        //When
        readerDao.save(reader);

        long bookId = book.getId();
        long copyId = copy.getId();
        long readerId = reader.getId();

        //Then
        Assert.assertEquals(reader.getBirthDate(), readerDao.findOne(readerId).getBirthDate());
        Assert.assertEquals(2, reader.getBorrowedCopies().size());

        //CleanUp
        copyDao.delete(copyId);
        bookDao.delete(bookId);
        readerDao.delete(readerId);
    }

    @Test
    public void testAddBorrowing() {
        //Given
        Reader reader = new Reader("John", "Smith", Date.valueOf("1990-11-12"));
        Book book = new Book("Henryk Sienkiewicz", "Ogniem i mieczem", 2018);
        Copy copy = new Copy(book, "18726372");
        Copy copy2 = new Copy(book, "18218382");

        book.setCopy(copy);
        book.setCopy(copy2);
        reader.setBorrowedCopies(copy);
        reader.setBorrowedCopies(copy2);
        List<Copy> copies = new ArrayList<>();
        copies.add(copy);
        copies.add(copy2);
        Borrowing borrowing = new Borrowing(reader, copies);

        //When
        bookDao.save(book);
        copyDao.save(copy);
        readerDao.save(reader);
        borrowingDao.save(borrowing);

        //Then

        //CleanUp
    }

}
