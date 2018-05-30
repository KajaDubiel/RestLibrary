package com.rest.restlibrary.service;

import com.rest.restlibrary.data.Reader;
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
    public void testCreateReaderWithEmail(){
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
    public void testGetReader(){
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
    public void testGetReaders(){
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
        Assert.assertEquals(2, returnedReaders.size());

        //CleanUp
        readerDao.delete(reader1ID);
        readerDao.delete(reader2ID);
    }

    @Test
    public void testUpdateReader(){
        //Given
        Reader reader = new Reader("Adam", "Kowalski", LocalDate.of(1967, 4, 12));

        readerDao.save(reader);
        long readerId = reader.getId();

        Reader updatedReader = new Reader(readerId,"AdamUpdated", "Kowalski", LocalDate.of(1967, 4, 12), "adam@kowalski", new ArrayList<>());

        //When
        readerService.updateReader(updatedReader);

        //Then
        Assert.assertEquals("AdamUpdated", readerDao.findOne(readerId).getFirstName());

        //CleanUp
        readerDao.delete(readerId);
    }

    @Test
    public void testDeleteReader(){
        //Given
        Reader reader = new Reader("Adam", "Kowalski", LocalDate.of(1967, 4, 12));

        readerDao.save(reader);
        long readerId = reader.getId();

        //When
        readerService.deleteReader(readerId);

        //Then
        Assert.assertNull(readerDao.findOne(readerId));
    }
}
