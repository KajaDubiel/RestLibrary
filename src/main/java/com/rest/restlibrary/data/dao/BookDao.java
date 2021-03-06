package com.rest.restlibrary.data.dao;

import com.rest.restlibrary.data.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface BookDao extends CrudRepository<Book, Long>{
    @Override
    List<Book> findAll();

//    @Query(nativeQuery = true)
//    List<Book> retrieveBooks();
}
