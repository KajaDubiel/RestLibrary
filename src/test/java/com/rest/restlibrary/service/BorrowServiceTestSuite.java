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
    public void initialize(){
        book = new Book("Ogniem i mieczem", "Henryk Sienkiewicz", 1982, "813287481");
        copy = new Copy(book, "54321");
        reader = new Reader("Adam", "Kowalski", LocalDate.of(1967, 4, 12));
        borrow = new Borrow(reader, copy);

        bookDao.save(book);
        copyDao.save(copy);
        readerDao.save(reader);

        bookId = book.getId();
        copyId = copy.getId();
        readerId = reader.getId();
    }
//
   @After
    public void cleanUp(){
        readerDao.delete(readerId);
        copyDao.delete(copyId);
        bookDao.delete(bookId);
    }


    @Test
    public void testCreateBorrow() {
        //Given

        //When
        borrowService.createBorrow(borrow);

        //Then
        long borrowId = borrow.getId();

        Assert.assertEquals(borrowId, borrowDao.findOne(borrowId).getId());

        //CleanUp
        borrowDao.delete(borrowId);


    }

    @Test
    public void testGetBorrow(){
        //Given
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
    public void testGetBorrows(){
        //Given
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
    public void testGetActiveBorrows(){
        //Given
        Book book2 = new Book("Book2", "Author2", 1972, "214481");
        Copy copy2 = new Copy(book2, "321");
        Reader reader2 = new Reader("Name2", "Kowalski", LocalDate.of(1950, 1, 12));
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
    public void updateBorrow(){
        //Given
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
    public void testDeleteBorrow(){
        //Given
        borrowDao.save(borrow);
        long borrowId = borrow.getId();
        //When
        borrowService.deleteBorrow(borrowId);
        //Then
        Assert.assertNull(borrowDao.findOne(borrowId));

    }

    @Test
    public void testReturnBorrow(){
        //Given
        borrowDao.save(borrow);
        long borrowId = borrow.getId();

        //When
        borrowService.returnBorrow(borrow);

        //Then
        Assert.assertNull(borrowDao.findOne(borrowId).getUntilDate());

        //CleanUp
        borrowDao.delete(borrowId);
    }
}
