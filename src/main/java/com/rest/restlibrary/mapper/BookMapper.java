package com.rest.restlibrary.mapper;

import com.rest.restlibrary.data.Book;
import com.rest.restlibrary.domain.BookDto;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {
    public Book mapToBook(final BookDto bookDto) {
        return new Book(bookDto.getId(), bookDto.getTitle(), bookDto.getAuthor(), bookDto.getReleaseYear(), bookDto.getIsbn(), bookDto.getCopies());
    }

    public BookDto mapToBookDto(final Book book) {
        return new BookDto(book.getId(), book.getTitle(), book.getAuthor(), book.getReleaseYear(), book.getIsbn(), book.getCopies());
    }
}
