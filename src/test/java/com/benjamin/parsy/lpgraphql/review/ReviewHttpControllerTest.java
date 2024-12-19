package com.benjamin.parsy.lpgraphql.review;

import com.benjamin.parsy.lpgraphql.AbstractTest;
import com.benjamin.parsy.lpgraphql.book.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.test.context.jdbc.Sql;

import java.util.Map;

@SpringBootTest
@AutoConfigureHttpGraphQlTester
class ReviewHttpControllerTest extends AbstractTest {

    private static final String[] GRAPHQL_TEST_SCHEMA_PATH = {
            "review/create-review/create-review",
            "review/alert-new-review/alert-new-review"
    };

    @Autowired
    private BookService bookService;

    @Autowired
    private HttpGraphQlTester httpGraphQlTester;

    @Sql(scripts = "classpath:data-test.sql")
    @Test
    void createReview_ReviewOk_CreateReview() {

        // Given
        long bookIdKnown = bookService.findAll().stream()
                .findFirst()
                .orElseThrow()
                .getId();

        Map<String, Object> variables = Map.of("createdBy", "Benjamin",
                "text", "Comment by Benjamin",
                "bookId", bookIdKnown);

        // When and then
        httpGraphQlTester.documentName(GRAPHQL_TEST_SCHEMA_PATH[0])
                .operationName("createReview")
                .variables(variables)
                .execute()
                .errors()
                .verify()
                .path("createReview")
                .entity(Review.class);

    }

}