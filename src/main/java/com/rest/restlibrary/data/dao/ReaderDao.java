package com.rest.restlibrary.data.dao;

import com.rest.restlibrary.data.Reader;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface ReaderDao extends CrudRepository<Reader, Long>{
}
