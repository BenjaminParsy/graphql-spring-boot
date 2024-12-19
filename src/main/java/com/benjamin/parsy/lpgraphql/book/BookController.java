package com.benjamin.parsy.lpgraphql.book;

import com.benjamin.parsy.lpgraphql.author.Author;
import com.benjamin.parsy.lpgraphql.author.AuthorService;
import com.benjamin.parsy.lpgraphql.review.Review;
import com.benjamin.parsy.lpgraphql.review.ReviewService;
import com.benjamin.parsy.lpgraphql.shared.exception.ErrorCode;
import com.benjamin.parsy.lpgraphql.shared.exception.GlobalException;
import com.benjamin.parsy.lpgraphql.shared.exception.GraphQLException;
import com.benjamin.parsy.lpgraphql.shared.service.MessageService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
    private final ReviewService reviewService;

    public BookController(BookService bookService, AuthorService authorService,
                          MessageService messageService, ReviewService reviewService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.messageService = messageService;
        this.reviewService = reviewService;
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

    @BatchMapping
    public Map<Book, List<Review>> reviews(List<Book> bookList) {

        List<Long> bookIdList = bookList.stream()
                .map(Book::getId)
                .distinct()
                .toList();

        List<Review> reviewList = reviewService.findAllByBookIdIn(bookIdList);

        return reviewList.stream()
                .collect(Collectors.groupingBy(Review::getBook));
    }

    @MutationMapping
    public Book createBook(@Argument @NotNull @Valid BookDto bookDto) {

        Optional<Author> optionalAuthor = authorService.findById(bookDto.getAuthorId());

        if (optionalAuthor.isEmpty()) {
            throw new GraphQLException(messageService.getErrorMessage(ErrorCode.BR1, bookDto.getAuthorId()));
        }

        return bookService.save(Book.builder()
                .title(bookDto.getTitle())
                .text(bookDto.getText())
                .category(bookDto.getCategory())
                .createdDate(LocalDateTime.now())
                .author(optionalAuthor.get())
                .build());
    }

    @MutationMapping
    public Book deleteBook(@Argument @Min(0) Long bookId) {

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
