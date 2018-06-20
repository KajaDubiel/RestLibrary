package com.rest.restlibrary.controller;

import com.rest.restlibrary.domain.BorrowDto;
import com.rest.restlibrary.mapper.BorrowMapper;
import com.rest.restlibrary.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@CrossOrigin("*")
@RestController
@RequestMapping("/restLibrary")
public class BorrowController {
    @Autowired
    BorrowService borrowService;

    @Autowired
    BorrowMapper borrowMapper;

    @RequestMapping(method = RequestMethod.POST, value = "/createBorrow")
    public void createBorrow(@RequestParam long bookId, @RequestParam long readerId){
        borrowService.createBorrow(bookId, readerId);//?
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getBorrows")
    public List<BorrowDto> getBorrows(){
        return borrowMapper.mapToBorrowDtoList(borrowService.getBorrows());
    }

    @RequestMapping(method = RequestMethod.PUT, value="/returnBorrow")
    public void returnBorrow(@RequestParam long readerId, @RequestParam String inventoryNumber){
        borrowService.returnBorrow(readerId, inventoryNumber);
    }
}
