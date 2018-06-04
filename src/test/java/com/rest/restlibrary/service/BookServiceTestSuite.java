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
public class BookServiceTestSuite {

    @Autowired
    BookDao bookDao;

    @Autowired
    CopyDao copyDao;

    @Autowired
    ReaderDao readerDao;

    @Autowired
    BorrowDao borrowDao;

    @Autowired
    BookService bookService;

    @Test
    public void testSaveBook(){
        //Given
        Book book = new Book("Ogniem i mieczem", "Henryk Sienkiewicz", 1982, "813287481");

        //When
        bookService.createBook(book);
        long bookId = book.getId();

        //Then
        Assert.assertEquals(bookId, bookDao.findOne(bookId).getId());

        //CleanUp
        bookDao.delete(bookId);
    }

    @Test
    public void getBook(){
        //Given
        Book book = new Book("Ogniem i mieczem", "Henryk Sienkiewicz", 1982, "813287481");
        bookService.createBook(book);
        long bookId = book.getId();

        //When
        Book returnedBook = bookService.getBook(bookId);

        //Then
        Assert.assertEquals(bookId, returnedBook.getId());

        //CleanUp
        bookDao.delete(bookId);
    }

    @Test
    public void getAllBooks(){
        //Given
        Book book1 = new Book("Ogniem i mieczem", "Henryk Sienkiewicz", 1982, "813287481");
        Book book2 = new Book("Pan Tadeusz", "Adam Mickiewicz", 1970, "813837841");
        Book book3 = new Book("Mały książę", "Antoine Exupery", 2002, "1247481");

        bookDao.save(book1);
        bookDao.save(book2);
        bookDao.save(book3);
        long book1Id = book1.getId();
        long book2Id = book2.getId();
        long book3Id = book3.getId();

        //When
        List<Book> returnedBooks = bookService.getAllBooks();

        //Then
        Assert.assertEquals(3, returnedBooks.size());

        //CleanUp
        bookDao.delete(book1Id);
        bookDao.delete(book2Id);
        bookDao.delete(book3Id);
    }

    @Test
    public void testUpdateBook(){
        //Given
        Book book1 = new Book("Ogniem i mieczem", "Henryk Sienkiewicz", 1982, "813287481");
        Book book2 = new Book("Pan Tadeusz", "Adam Mickiewicz", 1970, "813837841");

        bookDao.save(book1);
        bookDao.save(book2);
        long book1Id = book1.getId();
        long book2Id = book2.getId();
        Book updatedBook = new Book(book1Id, "Ogniem i update", "Henryk Update", 1982, "813287481", new ArrayList<>());

        //When
        bookService.updateBook(updatedBook);

        //Then
        Assert.assertEquals("Ogniem i update", bookDao.findOne(book1Id).getTitle());

        //CleanUp
        bookDao.delete(book1Id);
        bookDao.delete(book2Id);
    }

    @Test
    public void testDeleteBook(){
        //Given
        Book book = new Book("Ogniem i mieczem", "Henryk Sienkiewicz", 1982, "813287481");
        bookDao.save(book);
        long bookId = book.getId();
        //When
        bookService.deleteBook(bookId);
        //Then
        Assert.assertNull(bookDao.findOne(bookId));
    }

    @Test
    public void testDeleteBookHavingCopyAndNotExistingBorrow() throws RuntimeException{
        //Given
        Book book = new Book("Ogniem i mieczem", "Henryk Sienkiewicz", 1982, "813287481");
        Copy copy = new Copy(book, "54321");
        Reader reader = new Reader("Adam", "Kowalski", LocalDate.of(1967, 4, 12));
        bookDao.save(book);
        copyDao.save(copy);
        readerDao.save(reader);

        long bookId = book.getId();
        long readerId = reader.getId();
        Borrow borrow = new Borrow(reader, copy);
        borrowDao.save(borrow);

        borrow.returnCopy();
        borrowDao.save(borrow);

        //When
        bookService.deleteBook(bookId);

        //Then
        Assert.assertNull(bookDao.findOne(bookId));

        //CleanUp
        readerDao.delete(readerId);
    }

    @Test
    public void testShouldNotDeleteBookBecauseBorrowExists(){
        //Given
        Book book = new Book("Ogniem i mieczem", "Henryk Sienkiewicz", 1982, "813287481");
        Copy copy = new Copy(book, "54321");
        Reader reader = new Reader("Adam", "Kowalski", LocalDate.of(1967, 4, 12));
        bookDao.save(book);
        copyDao.save(copy);
        readerDao.save(reader);

        long bookId = book.getId();
        long copyId = copy.getId();
        long readerId = reader.getId();
        Borrow borrow = new Borrow(reader, copy);
        borrowDao.save(borrow);
        long borrowId = borrow.getId();

        //When
        try{
            bookService.deleteBook(bookId);
        } catch(RuntimeException e){

        }

        //Then
        Assert.assertNotNull(bookDao.findOne(bookId));

        //CleanUp
        borrowDao.delete(borrowId);
        readerDao.delete(readerId);
        copyDao.delete(copyId);
        bookDao.delete(bookId);
    }
}
