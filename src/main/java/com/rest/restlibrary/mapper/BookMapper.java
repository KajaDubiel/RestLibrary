package com.rest.restlibrary.mapper;

import com.rest.restlibrary.data.Book;
import com.rest.restlibrary.domain.BookDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Component
public class BookMapper {
    public Book mapToBook(final BookDto bookDto) {
        return new Book(bookDto.getId(), bookDto.getTitle(), bookDto.getAuthor(), bookDto.getReleaseYear(), bookDto.getIsbn());
    }

    public BookDto mapToBookDto(final Book book) {
        return new BookDto(book.getId(), book.getTitle(), book.getAuthor(), book.getReleaseYear(), book.getIsbn());
    }

    public List<BookDto> mapToBookDtoList(final List<Book> book){
        return book.stream()
                .map(b -> new BookDto(b.getId(), b.getTitle(), b.getAuthor(), b.getReleaseYear(), b.getIsbn())).collect(Collectors.toList());
    }
}
