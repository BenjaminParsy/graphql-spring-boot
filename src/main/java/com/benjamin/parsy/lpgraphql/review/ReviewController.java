package com.benjamin.parsy.lpgraphql.review;

import com.benjamin.parsy.lpgraphql.book.Book;
import com.benjamin.parsy.lpgraphql.book.BookService;
import com.benjamin.parsy.lpgraphql.shared.exception.ErrorCode;
import com.benjamin.parsy.lpgraphql.shared.exception.GraphQLException;
import com.benjamin.parsy.lpgraphql.shared.service.MessageService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
public class ReviewController {

    private final ReviewService reviewService;
    private final BookService bookService;
    private final MessageService messageService;

    public ReviewController(ReviewService reviewService, BookService bookService, MessageService messageService) {
        this.reviewService = reviewService;
        this.bookService = bookService;
        this.messageService = messageService;
    }

    @SubscriptionMapping
    public Flux<String> alertNewReview() {
        return reviewService.getAlertNewReviewStream();
    }

    @BatchMapping
    public Map<Review, Book> book(List<Review> reviewList) {

        return reviewList.stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        Review::getBook
                ));
    }

    @MutationMapping
    public Review createReview(@Argument String text,
                               @Argument String createdBy,
                               @Argument Long bookId) {

        Optional<Book> optionalBook = bookService.findById(bookId);

        if (optionalBook.isEmpty()) {
            throw new GraphQLException(messageService.getErrorMessage(ErrorCode.BR3, bookId));
        }

        return reviewService.save(Review.builder()
                .text(text)
                .createdBy(createdBy)
                .book(optionalBook.get())
                .build());
    }

}
