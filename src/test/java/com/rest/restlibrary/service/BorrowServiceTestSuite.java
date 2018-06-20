package com.rest.restlibrary.service;

import com.rest.restlibrary.data.Book;
import com.rest.restlibrary.data.Borrow;
import com.rest.restlibrary.data.Copy;
import com.rest.restlibrary.data.Reader;
import com.rest.restlibrary.data.dao.BookDao;
import com.rest.restlibrary.data.dao.BorrowDao;
import com.rest.restlibrary.data.dao.CopyDao;
import com.rest.restlibrary.data.dao.ReaderDao;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BorrowServiceTestSuite {
    @Autowired
    private BorrowDao borrowDao;

    @Autowired
    private BookDao bookDao;

    @Autowired
    private CopyDao copyDao;

    @Autowired
    private ReaderDao readerDao;

    @Autowired
    private BorrowService borrowService;

    private Book book;
    private Copy copy;
    private Reader reader;
    private Borrow borrow;
    private long copyId;
    private long bookId;
    private long readerId;

    @Before
    public void initialize() {
        book = new Book("Ogniem i mieczem", "Henryk Sienkiewicz", 1982, "813287481");
        copy = new Copy(book, "54321");
        reader = new Reader("Adam", "Kowalski", LocalDate.of(1967, 4, 12));
        //borrow = new Borrow(reader, copy);

        bookDao.save(book);
        copyDao.save(copy);
        readerDao.save(reader);

        bookId = book.getId();
        copyId = copy.getId();
        readerId = reader.getId();
    }

    //
    @After
    public void cleanUp() {
        readerDao.delete(readerId);
        copyDao.delete(copyId);
        bookDao.delete(bookId);
    }


    @Test
    public void testShouldCreateBorrowCopyHasNoBorrows() {
        //Given

        Book book2 = new Book("Pan TAdeusz", "Adam Mickiewicz", 2000, "23455432");
        Copy copy2 = new Copy(book2, "321");
        Reader reader2 = new Reader("Ryszard", "Bąk", LocalDate.of(1967, 4, 12));

        book2.addCopy(copy2);
        copy2.addBook(book2);
        bookDao.save(book2);
        copyDao.save(copy2);
        readerDao.save(reader2);


        long copy2Id = copy2.getId();
        long reader2Id = reader2.getId();
        long book2Id = book2.getId();

        //When
        Borrow borrow2 = borrowService.createBorrow(book2Id, reader2Id);
        long borrow2Id = borrow2.getId();

        //Then
        Assert.assertEquals(borrow2Id, borrowDao.findOne(borrow2Id).getId());

        //CleanUp
        borrowDao.delete(borrow2Id);
        copyDao.delete(copy2Id);
        readerDao.delete(reader2Id);
        bookDao.delete(book2Id);

    }

    @Test
    public void shouldNotCreateBorrow() {
        //Given
        Reader reader2 = new Reader("Ryszard", "Bąk", LocalDate.of(1967, 4, 12));
        Book book2 = new Book("Pan TAdeusz", "Adam Mickiewicz", 2000, "23455432");
        Copy copy2 = new Copy(book2, "3211");
        Borrow borrow2 = new Borrow(reader2, copy2);

        book2.addCopy(copy2);
        copy2.addBook(book2);
        copy2.addBorrow(borrow2);
        reader2.addBorrow(borrow2);

        bookDao.save(book2);
        copyDao.save(copy2);
        readerDao.save(reader2);
        borrowDao.save(borrow2);

        long book2Id = book2.getId();
        long readerId2 = reader2.getId();
        long copy2Id = copy2.getId();
        long borrow2Id = borrow2.getId();

        //When&Then
        try {
            borrowService.createBorrow(book2Id, readerId2);
        } catch (RuntimeException e) {
            String message = e.getMessage();
            Assert.assertEquals("Cannot create borrow propably there are no copies available", message);
        } finally {
            //CleanUp
            borrowDao.delete(borrow2Id);
            readerDao.delete(readerId2);
            copyDao.delete(copy2Id);
            bookDao.delete(book2Id);
        }
    }

    @Test
    public void testShouldCreateBorrowOneCopyIsAvailable() {
        //Given
        Reader reader2 = new Reader("Ryszard", "Bąk", LocalDate.of(1967, 4, 12));
        Book book2 = new Book("Pan TAdeusz", "Adam Mickiewicz", 2000, "23455432");
        Copy copy2 = new Copy(book, "3211");
        Copy copy3 = new Copy(book, "4211");
        Borrow borrow2 = new Borrow(reader2, copy2);

        book2.addCopy(copy2);
        book2.addCopy(copy3);
        copy2.addBook(book2);
        copy3.addBook(book2);
        reader2.addBorrow(borrow2);

        bookDao.save(book2);
        copyDao.save(copy2);
        copyDao.save(copy3);
        readerDao.save(reader2);
        borrowDao.save(borrow2);

        long book2ID = book2.getId();
        long copy2ID = copy2.getId();
        long copy3ID = copy3.getId();
        long reader2ID = reader2.getId();
        long borrow2ID = borrow2.getId();

        //When
        Borrow borrow3 = borrowService.createBorrow(book2ID, reader2ID);
        long borrow3ID = borrow3.getId();
        //Then
        long newBorrowCopyID = borrow3.getCopy().getId();
        Assert.assertEquals(copy3ID, newBorrowCopyID);

        //CleanUp
        borrowDao.delete(borrow2ID);
        borrowDao.delete(borrow3ID);
        readerDao.delete(reader2ID);
        copyDao.delete(copy2ID);
        copyDao.delete(copy3ID);
        bookDao.delete(book2ID);

    }

    @Test
    public void testGetBorrow() {
        //Given
        borrow = new Borrow(reader, copy);
        borrowDao.save(borrow);
        long borrowId = borrow.getId();
        //When
        borrowService.getBorrow(borrowId);
        //Then
        Assert.assertEquals(borrowId, borrowDao.findOne(borrowId).getId());

        //CleanUp
        borrowDao.delete(borrowId);
    }

    @Test
    public void testGetBorrows() {
        //Given
        borrow = new Borrow(reader, copy);
        borrowDao.save(borrow);
        long borrowId = borrow.getId();

        //When
        List<Borrow> returnedBorrows = borrowService.getBorrows();

        //Then
        Assert.assertEquals(1, returnedBorrows.size());

        //CleanUp
        borrowDao.delete(borrowId);
    }

    //HERE
    //@Ignore
    @Test
    public void testGetActiveBorrows() {
        //Given
        Book book2 = new Book("Book2", "Author2", 1972, "214481");
        Copy copy2 = new Copy(book2, "321");
        Reader reader2 = new Reader("Name2", "Kowalski", LocalDate.of(1950, 1, 12));
        Borrow borrow = new Borrow(reader, copy);
        Borrow borrow2 = new Borrow(reader2, copy2);

        bookDao.save(book2);
        long book2ID = book2.getId();
        copyDao.save(copy2);
        long copy2ID = copy2.getId();
        readerDao.save(reader2);
        long reader2ID = reader2.getId();
        borrowDao.save(borrow);
        borrowDao.save(borrow2);

        long borrowId1 = borrow.getId();
        long borrowId2 = borrow2.getId();

        borrow2.returnCopy();
        System.out.println(borrow2.getUntilDate());
        borrowDao.save(borrow2);

        //When
        List<Borrow> returnedBorrows = borrowService.getActiveBorrows();

        //Then
        Assert.assertEquals(1, returnedBorrows.size());

        //CleanUp
        borrowDao.delete(borrowId1);
        borrowDao.delete(borrowId2);
        readerDao.delete(reader2ID);
        copyDao.delete(copy2ID);
        bookDao.delete(book2ID);
    }

    @Test
    public void updateBorrow() {
        //Given
        borrow = new Borrow(reader, copy);
        borrowDao.save(borrow);
        long borrowId = borrow.getId();
        Copy updatedCopy = new Copy(book, "0000");
        copyDao.save(updatedCopy);
        Borrow updatedBorrow = new Borrow(borrowId, borrow.getFromDate(), borrow.getUntilDate(), updatedCopy, reader);
        //When
        borrowService.updateBorrow(updatedBorrow);

        //Then
        Assert.assertEquals("0000", borrowDao.findOne(borrowId).getCopy().getInventoryNumber());

        //CleanUp
        borrowDao.delete(borrowId);
        copyDao.delete(updatedCopy);
    }

    @Test
    public void testDeleteBorrow() {
        //Given
        borrow = new Borrow(reader, copy);
        borrowDao.save(borrow);
        long borrowId = borrow.getId();
        //When
        borrowService.deleteBorrow(borrowId);
        //Then
        Assert.assertNull(borrowDao.findOne(borrowId));

    }

    @Test
    public void testReturnBorrow() {
        //Given
        borrow = new Borrow(reader, copy);
        borrowDao.save(borrow);
        long borrowId = borrow.getId();

        //When
        borrowService.returnBorrow(readerId, "54321");

        //Then
        Assert.assertNull(borrowDao.findOne(borrowId).getUntilDate());

        //CleanUp
        borrowDao.delete(borrowId);
    }
}
