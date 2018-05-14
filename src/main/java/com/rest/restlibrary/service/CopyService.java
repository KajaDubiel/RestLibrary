package com.rest.restlibrary.service;

import com.rest.restlibrary.data.Copy;
import com.rest.restlibrary.data.dao.CopyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CopyService {

    @Autowired
    CopyDao copyDao;

    //?
    public void createCopy(Copy copy){
        copyDao.save(copy);
    }

    public Copy getCopy(long copyId){
        return copyDao.findOne(copyId);
    }

    public List<Copy> getCopiesByBookId(long bookId){
        return copyDao.findAllByBookId(bookId);
    }

    public void updateCopy(Copy copy){
         copyDao.save(copy);
    }

    public void deleteCopy(long copyId){
        copyDao.delete(copyId);
    }
}
