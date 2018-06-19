package com.rest.restlibrary.service;

import com.rest.restlibrary.data.Book;
import com.rest.restlibrary.data.Borrow;
import com.rest.restlibrary.data.Copy;
import com.rest.restlibrary.data.Reader;
import com.rest.restlibrary.data.dao.BookDao;
import com.rest.restlibrary.data.dao.BorrowDao;
import com.rest.restlibrary.data.dao.CopyDao;
import com.rest.restlibrary.data.dao.ReaderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BorrowService {
    @Autowired
    BorrowDao borrowDao;

    @Autowired
    BookDao bookDao;

    @Autowired
    ReaderDao readerDao;

    @Autowired
    CopyDao copyDao;

    public Borrow createBorrow(long bookId, long readerId){//???
        Reader reader = readerDao.findOne(readerId);

        List<Copy> availableCopies = findAvailableCopies(bookId);
        if(availableCopies.size()>0){
            Copy copy = availableCopies.get(0);
            Borrow borrow = new Borrow(reader, copy);
            copy.addBorrow(borrow);
            reader.addBorrow(borrow);
            copyDao.save(copy);
            readerDao.save(reader);
            borrowDao.save(borrow);
            return borrow;
        } else {
            throw new RuntimeException("Cannot create borrow propably there are no copies available");
        }
    }

    private List<Copy> findAvailableCopies(long bookId){
        List<Copy> copies = copyDao.findAllByBookId(bookId);
        List<Copy> availableCopies = new ArrayList<>();
        List<Borrow> activeBorrows = new ArrayList<>();

        for(Copy copy: copies){
            System.out.println("Checking copy inventNUm: " + copy.getInventoryNumber());
            List<Borrow> borrows = copy.getBorrows();
            System.out.println("This copy has" + borrows.size() + " on borrows list");
            if(borrows.isEmpty()){
                availableCopies.add(copy);
                System.out.println("Copy has no borrows");
            } else{
                borrows.stream()
                        .forEach(borrow -> {
                            if(!borrow.getUntilDate().equals(null)){
                                System.out.println("Checking borrow with id: " + borrow.getId() + " is this borrow finished?: " + borrow.getUntilDate());
                                activeBorrows.add(borrow);
                            }
                        });
            }
            if(activeBorrows.size()==0 && !availableCopies.contains(copy)){//?
                availableCopies.add(copy);
            }
        }
        return availableCopies;
    }

    public Borrow getBorrow(long borrowId){
       return borrowDao.findOne(borrowId);
    }

    public List<Borrow> getBorrows(){
        return borrowDao.findAll();
    }

    public List<Borrow> getActiveBorrows(){
        return borrowDao.findAll().stream()
                .filter(b -> b.getUntilDate() != null)
                .collect(Collectors.toList());
    }

    public void updateBorrow(Borrow borrow){
        borrowDao.save(borrow);
    }

    public void returnBorrow(Borrow borrow){//borrow id?
        borrow.returnCopy();
        borrowDao.save(borrow);
    }

    public void deleteBorrow(long borrowId){
        borrowDao.delete(borrowId);
    }
}
