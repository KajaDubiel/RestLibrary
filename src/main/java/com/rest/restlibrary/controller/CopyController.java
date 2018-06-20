package com.rest.restlibrary.controller;

import com.rest.restlibrary.data.Copy;
import com.rest.restlibrary.domain.CopyDto;
import com.rest.restlibrary.mapper.CopyMapper;
import com.rest.restlibrary.service.BookService;
import com.rest.restlibrary.service.CopyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@CrossOrigin("*")
@RestController
@RequestMapping("/restLibrary")
public class CopyController {
    @Autowired
    CopyService copyService;

    @Autowired
    BookService bookService;

    @Autowired
    CopyMapper copyMapper;

    @RequestMapping(method = RequestMethod.POST, value = "/createCopy", consumes = APPLICATION_JSON_VALUE)
    public void createCopy(@RequestBody CopyDto copyDto, @RequestParam long bookId){
        copyService.createCopy(copyMapper.mapToCopy(copyDto), bookId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getCopies")
    public List<CopyDto> getCopies(@RequestParam long bookId){
        return copyMapper.mapToCopyDtosList(copyService.getCopiesByBookId(bookId));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getBorrowedCopies")
    public List<CopyDto> getBorrowedCopies(@RequestParam long readerId){
        return copyMapper.mapToCopyDtosList(copyService.getBorrowedCopiesByReaderId(readerId));
    }
}
