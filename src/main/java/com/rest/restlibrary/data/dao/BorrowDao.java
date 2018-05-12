package com.rest.restlibrary.data.dao;

import com.rest.restlibrary.data.Borrow;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface BorrowDao extends CrudRepository<Borrow, Long>{
}
