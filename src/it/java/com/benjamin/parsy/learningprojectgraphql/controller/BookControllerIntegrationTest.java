package com.benjamin.parsy.learningprojectgraphql.controller;

import com.benjamin.parsy.learningprojectgraphql.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Map;
import java.util.Objects;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureHttpGraphQlTester
class BookControllerIntegrationTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @Test
    void recentBooks() {

        Map<String, Object> variables = Map.of("count", 10,
                "offset", 0);

        graphQlTester.documentName("book-test/recent-books")
                .variables(variables)
                .execute()
                .errors()
                .verify()
                .path("recentBooks")
                .entityList(Book.class)
                .hasSize(4);

    }

    @Test
    void createBook() {

        Map<String, Object> variables = Map.of("title", "testTitle1",
                "text", "testText1",
                "category", "testCategory1",
                "authorId", 2);

        graphQlTester.documentName("book-test/create-book")
                .variables(variables)
                .execute()
                .errors()
                .verify()
                .path("createBook")
                .entity(Book.class);

    }

    @Test
    void createBook_AuthorDoesntExist_ThrowException() {

        Map<String, Object> variables = Map.of("title", "testTitle1",
                "text", "testText1",
                "category", "testCategory1",
                "authorId", 999);

        graphQlTester.documentName("book-test/create-book")
                .variables(variables)
                .execute()
                .errors()
                .expect(e -> Objects.requireNonNull(e.getMessage())
                        .equals(String.format("[001] Author with ID %s does not exist", 999)));

    }

}