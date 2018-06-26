package com.rest.restlibrary.service;

import com.rest.restlibrary.data.Borrow;
import com.rest.restlibrary.data.Reader;
import com.rest.restlibrary.data.dao.BorrowDao;
import com.rest.restlibrary.data.dao.ReaderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.lang.management.OperatingSystemMXBean;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReaderService {
    @Autowired
    ReaderDao readerDao;

    @Autowired
    BorrowDao borrowDao;

    public void createReader(Reader reader) {
        readerDao.save(reader);
    }

    public Reader getReader(long readerId) {
        return readerDao.findOne(readerId);
    }

    public List<Reader> getReaders() {
        return readerDao.findAll();
    }

    public void updateReader(Reader reader) {
        readerDao.save(reader);
    }

    public void deleteReader(long readerId) {
        boolean hasReaderActiveBorrows = checkReaderHasUnfinishedBorrows(readerId);
        if (hasReaderActiveBorrows) {
            throw new RuntimeException("This reader has active borrows, you can not delete");
        } else {
            List<Borrow> borrows = readerDao.findOne(readerId).getBorrows();
            for (Borrow borrow : borrows) {
                long borrowId = borrow.getId();
                borrowDao.delete(borrowId);
            }
            readerDao.delete(readerId);
        }

    }

    public List<Borrow> getReaderActiveBorrows(Reader reader){
        List<Borrow> readerBorrows = reader.getBorrows();
        List<Borrow> activeBorrows = readerBorrows.stream()
                .filter(b-> {
                    Optional<LocalDate> untilDate = Optional.ofNullable(b.getUntilDate());
                    return untilDate.isPresent();
                })
                .collect(Collectors.toList());
        return activeBorrows;
    }

    private boolean checkReaderHasUnfinishedBorrows(long readerId) {
        List<Borrow> activeBorrows = new ArrayList();
        List<Borrow> borrows = readerDao.findOne(readerId).getBorrows();
        if (borrows.isEmpty()) {
            return false;
        } else {
            for (Borrow borrow : borrows) {
                Optional<LocalDate> untilDate = Optional.ofNullable(borrow.getUntilDate());
                if (untilDate.isPresent()) {
                    activeBorrows.add(borrow);
                }
            }
        }
        if (activeBorrows.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
}
