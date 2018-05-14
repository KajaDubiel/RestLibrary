package com.rest.restlibrary.service;

import com.rest.restlibrary.data.Reader;
import com.rest.restlibrary.data.dao.ReaderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReaderService {
    @Autowired
    ReaderDao readerDao;

    public void createReader(Reader reader){
        readerDao.save(reader);
    }

    public Reader getReader(long readerId){
        return readerDao.findOne(readerId);
    }

    public List<Reader> getReaders(){
        return readerDao.findAll();
    }

    public void updateReader(Reader reader){
        readerDao.save(reader);
    }

    public void deleteReader(long readerId){
        readerDao.delete(readerId);
    }
}
