package com.benjamin.parsy.learningprojectgraphql.controller;

import com.benjamin.parsy.learningprojectgraphql.entity.Author;
import com.benjamin.parsy.learningprojectgraphql.entity.Book;
import com.benjamin.parsy.learningprojectgraphql.exception.CustomException;
import com.benjamin.parsy.learningprojectgraphql.exception.ErrorCode;
import com.benjamin.parsy.learningprojectgraphql.exception.GraphQLException;
import com.benjamin.parsy.learningprojectgraphql.service.business.AuthorService;
import com.benjamin.parsy.learningprojectgraphql.service.business.BookService;
import com.benjamin.parsy.learningprojectgraphql.service.helper.message.MessageService;
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

        List<Long> authorIdList = bookList.stream()
                .map(Book::getAuthorId)
                .distinct()
                .toList();

        Map<Long, Author> authorById = authorService.findAllByIdIn(authorIdList)
                .stream()
                .collect(Collectors.toMap(Author::getId, Function.identity()));

        return bookList.stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        book -> authorById.get(book.getAuthorId()), (a, b) -> b
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
                .authorId(authorId)
                .build());
    }

    @MutationMapping
    public boolean deleteBookById(@Argument Long bookId) {

        Optional<Book> optionalBook = bookService.findById(bookId);

        if (optionalBook.isEmpty()) {
            throw new GraphQLException(messageService.getErrorMessage(ErrorCode.BR3, bookId));
        }

        try {
            bookService.deleteById(bookId);
        } catch (CustomException e) {
            throw new GraphQLException(messageService.getErrorMessage(ErrorCode.BR4, bookId));
        }

        return true;
    }

}
