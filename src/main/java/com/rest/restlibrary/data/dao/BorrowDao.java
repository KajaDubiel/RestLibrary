package com.rest.restlibrary.data.dao;

import com.rest.restlibrary.data.Borrow;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface BorrowDao extends CrudRepository<Borrow, Long>{
    @Override
    List<Borrow> findAll();

    List<Borrow> findAllByCopyId(long copyId);

    void deleteByCopyId(long copyId);

    //List<Borrow> findAllByUntilDate();
}
