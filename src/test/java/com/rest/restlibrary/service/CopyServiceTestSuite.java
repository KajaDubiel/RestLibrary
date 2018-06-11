package com.rest.restlibrary.service;

import com.rest.restlibrary.data.Book;
import com.rest.restlibrary.data.Copy;
import com.rest.restlibrary.data.dao.CopyDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CopyServiceTestSuite {
    @Autowired
    CopyDao copyDao;

    @Autowired
    CopyService copyService;

    @Autowired
    BookService bookService;

    @Test
    public void testCreateCopy() {
        //Given
        Book book = new Book("Ogniem i mieczem", "Henryk Sienkiewicz", 1982, "813287481");
        Copy copy = new Copy(book, "12345");

        //When
        bookService.createBook(book);
        long bookId = book.getId();
        copyService.createCopy(copy,bookId);
        long copyId = copy.getId();

        //Then
        Assert.assertEquals(copyId, copyDao.findOne(copyId).getId());

        //CleanUp
        copyDao.delete(copyId);
        bookService.deleteBook(bookId);
    }

    @Test
    public void getCopy() {
        //Given
        Book book = new Book("Ogniem i mieczem", "Henryk Sienkiewicz", 1982, "813287481");
        Copy copy = new Copy(book, "12345");

        bookService.createBook(book);
        long bookId = book.getId();
        copyService.createCopy(copy, bookId);

        long copyId = copy.getId();

        //When
        Copy returnedCopy = copyService.getCopy(copyId);

        //Then
        Assert.assertEquals(returnedCopy.getInventoryNumber(), copyDao.findOne(copyId).getInventoryNumber());

        //CleanUp
        copyDao.delete(copyId);
        bookService.deleteBook(bookId);
    }

    @Test
    public void testGetCopiesByBookId() {
        //Given
        Book book = new Book("Ogniem i mieczem", "Henryk Sienkiewicz", 1982, "813287481");
        Copy copy1 = new Copy(book, "54321");
        Copy copy2 = new Copy(book, "12345");

        bookService.createBook(book);
        long bookId = book.getId();
        copyService.createCopy(copy1, bookId);
        copyService.createCopy(copy2, bookId);

        long copy1Id = copy1.getId();
        long copy2Id = copy2.getId();

        //When
        List<Copy> returnedCopies = copyService.getCopiesByBookId(bookId);

        //Then
        Assert.assertEquals(2, returnedCopies.size());

        //CleanUp
        copyDao.delete(copy1Id);
        copyDao.delete(copy2Id);
        bookService.deleteBook(bookId);
    }

    @Test
    public void testUpdateCopy() {
        //Given
        Book book = new Book("Ogniem i mieczem", "Henryk Sienkiewicz", 1982, "813287481");
        Copy copy = new Copy(book, "54321");

        bookService.createBook(book);
        long bookId = book.getId();
        copyService.createCopy(copy, bookId);

        Long copyId = copy.getId();

        Copy updated = new Copy(copyId, book, "updated1324", new ArrayList<>());

        //When
        copyService.updateCopy(updated);

        //Then
        Assert.assertEquals(updated.getInventoryNumber(), copyDao.findOne(copyId).getInventoryNumber());

        //CleanUp
        copyDao.delete(copyId);
        bookService.deleteBook(bookId);
    }

    @Test
    public void testDeleteCopy() {
        //Given
        Book book = new Book("Ogniem i mieczem", "Henryk Sienkiewicz", 1982, "813287481");
        Copy copy1 = new Copy(book, "54321");
        Copy copy2 = new Copy(book, "12345");

        bookService.createBook(book);
        copyDao.save(copy1);
        copyDao.save(copy2);

        long bookId = book.getId();
        long copyId1 = copy1.getId();
        long copyId2 = copy2.getId();

        //When
        copyService.deleteCopy(copyId1);

        //Then
        copyDao.delete(copyId2);
        bookService.deleteBook(bookId);
    }
}
