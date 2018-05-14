package com.rest.restlibrary.data.dao;

import com.rest.restlibrary.data.Copy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface CopyDao extends CrudRepository<Copy, Long>{
    List<Copy> findAllByBookId(long bookId);
}
