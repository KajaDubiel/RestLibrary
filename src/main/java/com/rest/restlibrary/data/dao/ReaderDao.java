package com.rest.restlibrary.data.dao;

import com.rest.restlibrary.data.Reader;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface ReaderDao extends CrudRepository<Reader, Long>{
    @Override
    List<Reader> findAll();
}
