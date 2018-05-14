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
}
