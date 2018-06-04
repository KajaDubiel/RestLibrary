package com.rest.restlibrary.mapper;

import com.rest.restlibrary.data.Reader;
import com.rest.restlibrary.domain.BookDto;
import com.rest.restlibrary.domain.ReaderDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReaderMapper {
    public Reader mapToReader(final ReaderDto readerDto) {
        return new Reader(readerDto.getId(), readerDto.getFirstName(), readerDto.getLastName(), readerDto.getBirthDate(), readerDto.getReaderEmail(), readerDto.getBorrows());
    }

    public ReaderDto mapToReaderDto(final Reader reader) {
        return new ReaderDto(reader.getId(), reader.getFirstName(), reader.getLastName(), reader.getBirthDate(), reader.getReaderEmail(), reader.getBorrows());
    }

    public List<ReaderDto> mapToReaderDtoList(final List<Reader> readers) {
        return readers.stream()
                .map(r -> new ReaderDto(r.getId(), r.getFirstName(), r.getLastName(), r.getBirthDate(), r.getReaderEmail(), r.getBorrows()))
                .collect(Collectors.toList());
    }
}
