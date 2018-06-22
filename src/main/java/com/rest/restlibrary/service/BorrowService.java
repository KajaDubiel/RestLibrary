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

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    public Borrow createBorrow(long bookId, long readerId) {//???
        Reader reader = readerDao.findOne(readerId);

        List<Copy> availableCopies = findAvailableCopies(bookId);
        if (availableCopies.size() > 0) {
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

    private List<Copy> findAvailableCopies(long bookId) {//wynik wogóle nie jest wiarygodny!
        List<Copy> copies = copyDao.findAllByBookId(bookId);
        List<Copy> availableCopies = new ArrayList<>();
        //List<Borrow> activeBorrows = new ArrayList<>();

        for (Copy copy : copies) {
            System.out.println("Checking copy inventNUm: " + copy.getInventoryNumber());
            List<Borrow> borrows = copy.getBorrows();
            System.out.println("This copy has" + borrows.size() + " on borrows list");


            if (borrows.isEmpty()) {
                availableCopies.add(copy);
                System.out.println("Copy has no borrows");
            } else {
                List<Borrow> activeBorrows = checkBorrowIsActive(borrows);

                Copy fakeCopy = new Copy(copy.getBook(), copy.getInventoryNumber());
                if (activeBorrows.size() == 0 && (!availableCopies.contains(fakeCopy))) {//?
                    System.out.println("Adding copy");
                    availableCopies.add(copy);
                }

            }


        }



//        for(Copy copy: copies){
//            System.out.println("Checking copy inventNUm: " + copy.getInventoryNumber());
//            List<Borrow> borrows = copy.getBorrows();
//            System.out.println("This copy has" + borrows.size() + " on borrows list");
//            if(borrows.isEmpty()){
//                availableCopies.add(copy);
//                System.out.println("Copy has no borrows");
//            } else{
//                borrows.stream()//tego nie robi - jedną wypożycza a potem się sypie
//                        .forEach(borrow -> {
//                            Optional<LocalDate> untilDate = Optional.ofNullable(borrow.getUntilDate());
//                            if(untilDate.isPresent()){
//                                System.out.println("Checking borrow with id: " + borrow.getId() + " is this borrow finished?: " + untilDate);
//                                activeBorrows.add(borrow);
//                            } else{
//                                System.out.println("Until date is null");
//
//                            }
//                        });
//            }
//            Copy fakeCopy = new Copy(copy.getBook(), copy.getInventoryNumber());
//            if(activeBorrows.size()==0 && (!availableCopies.contains(fakeCopy))){//?
//                System.out.println("Adding copy");
//                availableCopies.add(copy);
//            }
//        }
        return availableCopies;
    }

    private List<Borrow> checkBorrowIsActive(List<Borrow> borrows) {
        List<Borrow> activeBorrows = new ArrayList<>();
        for (Borrow borrow : borrows) {
            Optional<LocalDate> untilDate = Optional.ofNullable(borrow.getUntilDate());
            if (untilDate.isPresent()) {
                System.out.println("Checking borrow with id: " + borrow.getId() + " is this borrow finished?: " + untilDate);
                activeBorrows.add(borrow);
            } else {
                System.out.println("Borrow id: " + borrow.getId() + "Until date is null");
            }
        }
        return activeBorrows;
    }

    public Borrow getBorrow(long borrowId) {
        return borrowDao.findOne(borrowId);
    }

    public List<Borrow> getBorrows() {
        return borrowDao.findAll();
    }

    public List<Borrow> getActiveBorrows() {
        return borrowDao.findAll().stream()
                .filter(b -> b.getUntilDate() != null)
                .collect(Collectors.toList());
    }

    public void updateBorrow(Borrow borrow) {
        borrowDao.save(borrow);
    }

    public void returnBorrow(long readerId, String inventoryNumber) {//borrow id?
        Borrow borrow = findBorrowByReaderIdAndInventoryNum(readerId, inventoryNumber);
        Optional<Borrow> optborrow = Optional.ofNullable(borrow);
        if (optborrow.isPresent()) {
            borrow.returnCopy();
            borrowDao.save(borrow);
            System.out.println("Your copy has been succesfully returned");
        } else {
            throw new RuntimeException("Choosen copy or reader doesn't exist");
        }

    }

    private Borrow findBorrowByReaderIdAndInventoryNum(long readerId, String inventoryNumber) {
        Reader reader = readerDao.findOne(readerId);
        System.out.println("From private: readerid: " + readerId + " invnum: " + inventoryNumber);
        List<Borrow> borrows = reader.getBorrows();
        Borrow foundBorrow = null;//nie znalazł zadnego

        for (Borrow borrow : borrows) {
            Optional<LocalDate> untilDate = Optional.ofNullable(borrow.getUntilDate());
            if (untilDate.isPresent()) {
                System.out.println("until date is present, borrow id :" + borrow.getId());

                System.out.println("Borrow equals: " + borrow.getCopy().getInventoryNumber() + " equals >>" + inventoryNumber + "<< " + ">>" +  borrow.getCopy().getInventoryNumber().equals(inventoryNumber));

                if (borrow.getCopy().getInventoryNumber().equals(inventoryNumber)) {
                    System.out.println("found borrow!");
                    foundBorrow = borrow;
                }
            }

        }
        return foundBorrow;
    }

    public void deleteBorrow(long borrowId) {
        borrowDao.delete(borrowId);
    }
}
