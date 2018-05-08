package com.rest.restlibrary.data.dao;

import com.rest.restlibrary.data.Borrowing;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface BorrowingDao extends CrudRepository<Borrowing, Long> {
}
