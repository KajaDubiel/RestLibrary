package com.rest.restlibrary.controller;

import com.rest.restlibrary.domain.ReaderDto;
import com.rest.restlibrary.mapper.ReaderMapper;
import com.rest.restlibrary.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@CrossOrigin("*")
@RestController
@RequestMapping("/restLibrary")
public class ReaderController {

    @Autowired
    ReaderService readerService;

    @Autowired
    ReaderMapper readerMapper;

    @RequestMapping(method = RequestMethod.GET, value = "/getReaders")
    public List<ReaderDto> getReaders() {
        return readerMapper.mapToReaderDtoList(readerService.getReaders());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getReader")
    public ReaderDto getReader(@RequestParam long readerId) {
        return readerMapper.mapToReaderDto(readerService.getReader(readerId));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/createReader", consumes = APPLICATION_JSON_VALUE)
    public void createReader(@RequestBody ReaderDto readerDto) {
        readerService.createReader(readerMapper.mapToReader(readerDto));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/updateReader", consumes = APPLICATION_JSON_VALUE)
    public void updateReader(@RequestBody ReaderDto readerDto) {
        readerService.updateReader(readerMapper.mapToReader(readerDto));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteReader")
    public void deleteReader(@RequestParam long readerId) {
        readerService.deleteReader(readerId);
    }

}
