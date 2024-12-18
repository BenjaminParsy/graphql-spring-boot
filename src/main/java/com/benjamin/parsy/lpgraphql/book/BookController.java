package com.benjamin.parsy.lpgraphql.book;

import com.benjamin.parsy.lpgraphql.author.Author;
import com.benjamin.parsy.lpgraphql.author.AuthorService;
import com.benjamin.parsy.lpgraphql.shared.exception.ErrorCode;
import com.benjamin.parsy.lpgraphql.shared.exception.GlobalException;
import com.benjamin.parsy.lpgraphql.shared.exception.GraphQLException;
import com.benjamin.parsy.lpgraphql.shared.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@Slf4j
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final MessageService messageService;

    public BookController(BookService bookService, AuthorService authorService, MessageService messageService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.messageService = messageService;
    }

    @QueryMapping
    public List<Book> getBooks(@Argument int limit, @Argument int offset) {
        return bookService.findAllWithLimitAndOffset(limit, offset);
    }

    @BatchMapping
    public Map<Book, Author> author(List<Book> bookList) {

        return bookList.stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        Book::getAuthor
                ));
    }

    @MutationMapping
    public Book createBook(@Argument String title,
                           @Argument String text,
                           @Argument String category,
                           @Argument Long authorId) {

        if (authorId == null || authorId < 0) {
            throw new GraphQLException(messageService.getErrorMessage(ErrorCode.BR2, "authorId"));
        }

        Optional<Author> optionalAuthor = authorService.findById(authorId);

        if (optionalAuthor.isEmpty()) {
            throw new GraphQLException(messageService.getErrorMessage(ErrorCode.BR1, authorId));
        }

        return bookService.save(Book.builder()
                .title(title)
                .text(text)
                .category(category)
                .createdDate(LocalDateTime.now())
                .author(optionalAuthor.get())
                .build());
    }

    @MutationMapping
    public Book deleteBook(@Argument Long bookId) {

        Optional<Book> optionalBook = bookService.findById(bookId);

        if (optionalBook.isEmpty()) {
            throw new GraphQLException(messageService.getErrorMessage(ErrorCode.BR3, bookId));
        }

        try {
            return bookService.deleteById(bookId);
        } catch (GlobalException e) {
            throw new GraphQLException(e.getErrorMessage());
        }

    }

}
