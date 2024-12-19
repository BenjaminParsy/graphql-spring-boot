package com.benjamin.parsy.lpgraphql.review;

import com.benjamin.parsy.lpgraphql.AbstractTest;
import com.benjamin.parsy.lpgraphql.book.Book;
import com.benjamin.parsy.lpgraphql.book.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureGraphQlTester
class ReviewWebSocketControllerTest extends AbstractTest {

    private static final String[] GRAPHQL_TEST_SCHEMA_PATH = {
            "review/create-review/create-review",
            "review/alert-new-review/alert-new-review"
    };

    @Autowired
    private BookService bookService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private GraphQlTester graphQlTester;

    @Test
    void alertNewReview() {

        // Given
        final StringBuilder receivedTitle = new StringBuilder();

        graphQlTester.documentName(GRAPHQL_TEST_SCHEMA_PATH[1])
                .executeSubscription()
                .toFlux()
                .subscribe(result -> {

                    String title = result.errors()
                            .verify()
                            .path("alertNewReview")
                            .entity(String.class)
                            .get();

                    receivedTitle.append(title);
                });

        // When
        Book bookKnown = bookService.findAll().stream()
                .findFirst()
                .orElseThrow();

        reviewService.save(Review.builder()
                .text("Comment by Benjamin")
                .createdBy("Benjamin")
                .book(bookKnown)
                .build());

        // Then
        assertEquals("A new review has been posted by Benjamin on the book title_1", receivedTitle.toString());
    }

}