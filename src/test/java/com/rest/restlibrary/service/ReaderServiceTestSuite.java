package com.rest.restlibrary.service;

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
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ReaderServiceTestSuite {

    @Autowired
    ReaderDao readerDao;

    @Autowired
    ReaderService readerService;

    @Autowired
    BookDao bookDao;

    @Autowired
    CopyDao copyDao;

    @Autowired
    BorrowDao borrowDao;

    @Autowired
    BorrowService borrowService;

    @Test
    public void testCreateReader() {
        //Given
        Reader reader = new Reader("Adam", "Kowalski", LocalDate.of(1967, 4, 12));

        //When
        readerService.createReader(reader);
        long readerId = reader.getId();

        //Then
        Assert.assertEquals(readerId, readerDao.findOne(readerId).getId());

        //CleanUp
        readerDao.delete(readerId);
    }

    @Test
    public void testCreateReaderWithEmail() {
        //Given
        Reader reader = new Reader("Adam", "Kowalski", LocalDate.of(1967, 4, 12), "adam@kowalski");

        //When
        readerService.createReader(reader);
        long readerId = reader.getId();

        //Then
        Assert.assertEquals(readerId, readerDao.findOne(readerId).getId());

        //CleanUp
        readerDao.delete(readerId);
    }

    @Test
    public void testGetReader() {
        //Given
        Reader reader = new Reader("Adam", "Kowalski", LocalDate.of(1967, 4, 12));

        readerDao.save(reader);
        long readerId = reader.getId();

        //When
        Reader returnedReader = readerService.getReader(readerId);

        //Then
        Assert.assertEquals("Adam", returnedReader.getFirstName());

        //CleanUp
        readerDao.delete(readerId);
    }

    @Test
    public void testGetReaders() {
        //Given
        Reader reader1 = new Reader("Adam", "Kowalski", LocalDate.of(1967, 4, 12));
        Reader reader2 = new Reader("Tomasz", "Nowak", LocalDate.of(1961, 5, 2));

        readerDao.save(reader1);
        readerDao.save(reader2);
        long reader1ID = reader1.getId();
        long reader2ID = reader2.getId();

        //When
        List<Reader> returnedReaders = readerService.getReaders();

        //Then
        Assert.assertFalse(returnedReaders.isEmpty());

        //CleanUp
        readerDao.delete(reader1ID);
        readerDao.delete(reader2ID);
    }

    @Test
    public void testUpdateReader() {
        //Given
        Reader reader = new Reader("Adam", "Kowalski", LocalDate.of(1967, 4, 12));

        readerDao.save(reader);
        long readerId = reader.getId();

        Reader updatedReader = new Reader(readerId, "AdamUpdated", "Kowalski", LocalDate.of(1967, 4, 12), "adam@kowalski", new ArrayList<>());

        //When
        readerService.updateReader(updatedReader);

        //Then
        Assert.assertEquals("AdamUpdated", readerDao.findOne(readerId).getFirstName());

        //CleanUp
        readerDao.delete(readerId);
    }

    @Test
    public void testDeleteReader() {
        //Given
        Reader reader = new Reader("Adam", "Kowalski", LocalDate.of(1967, 4, 12));

        readerDao.save(reader);
        long readerId = reader.getId();

        //When
        readerService.deleteReader(readerId);

        //Then
        Assert.assertNull(readerDao.findOne(readerId));
    }

    @Test
    public void testDeleteReaderWhoHasActiveBorrows() throws RuntimeException {
        //Given
        Book book = new Book("Book2", "Author2", 1972, "214481");
        Copy copy = new Copy(book, "321");
        Reader reader = new Reader("Name2", "Kowalski", LocalDate.of(1950, 1, 12));
        Borrow borrow = new Borrow(reader, copy);

        bookDao.save(book);
        copyDao.save(copy);
        readerDao.save(reader);
        borrowDao.save(borrow);

        long bookId = book.getId();
        long copyId = copy.getId();
        long readerId = reader.getId();
        long borrowId = borrow.getId();

        //When
        try {
            readerService.deleteReader(readerId);
        } catch (RuntimeException e) {

        }
        //Then
        Assert.assertNotNull(readerDao.findOne(readerId));

        //CleanUp
        borrowDao.delete(borrowId);
        readerDao.delete(readerId);
        copyDao.delete(copyId);
        bookDao.delete(bookId);
    }

    @Test
    public void testDeleteReaderWhoHasNoActiveBorrows() throws RuntimeException {
        //Given
        Book book = new Book("Book2", "Author2", 1972, "214481");
        Copy copy = new Copy(book, "321");
        Reader reader = new Reader("Name2", "Kowalski", LocalDate.of(1950, 1, 12));
        Borrow borrow = new Borrow(reader, copy);

        bookDao.save(book);
        copyDao.save(copy);
        readerDao.save(reader);
        borrowDao.save(borrow);

        long bookId = book.getId();
        long copyId = copy.getId();
        long readerId = reader.getId();

        borrowService.returnBorrow(readerId, copy.getInventoryNumber());

        //When
        readerService.deleteReader(readerId);
        //Then
        Assert.assertNull(readerDao.findOne(readerId));

        //CleanUp
        copyDao.delete(copyId);
        bookDao.delete(bookId);
    }
}
