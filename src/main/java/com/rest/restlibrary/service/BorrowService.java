package com.rest.restlibrary.service;

import com.rest.restlibrary.data.Borrow;
import com.rest.restlibrary.data.dao.BorrowDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class BorrowService {
    @Autowired
    BorrowDao borrowDao;

    public void createBorrow(Borrow borrow){
        borrowDao.save(borrow);
    }

    public Borrow getBorrow(long borrowId){
       return borrowDao.findOne(borrowId);
    }

    public List<Borrow> getBorrows(){
        return borrowDao.findAll();
    }

    //HERE
    public List<Borrow> getActiveBorrows(){
        return borrowDao.findAll().stream()
                .filter(b -> b.getUntilDate() != null)
                .collect(Collectors.toList());
    }

    //pytanie czy ta metoda będzie potrzebna?
    public void updateBorrow(Borrow borrow){
        borrowDao.save(borrow);
    }

    public void deleteBorrow(long borrowId){
        borrowDao.delete(borrowId);
    }
}
