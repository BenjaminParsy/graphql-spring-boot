package com.benjamin.parsy.lpgraphql.book;

import com.benjamin.parsy.lpgraphql.AbstractTest;
import com.benjamin.parsy.lpgraphql.author.AuthorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.test.context.jdbc.Sql;

import java.util.Map;
import java.util.Objects;

@SpringBootTest
@AutoConfigureHttpGraphQlTester
class BookControllerTest extends AbstractTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    @Sql(scripts = "classpath:data-test.sql")
    @Test
    void getBooks() {

        Map<String, Object> variables = Map.of("limit", 10,
                "offset", 0);

        graphQlTester.documentName("book-test/get-books")
                .variables(variables)
                .execute()
                .errors()
                .verify()
                .path("getBooks")
                .entityList(Book.class)
                .hasSize(4);

    }

    @Sql(scripts = "classpath:data-test.sql")
    @Test
    void createBook() {

        long authorIdKnow = authorService.findAll().stream()
                .findFirst()
                .orElseThrow()
                .getId();

        Map<String, Object> variables = Map.of("title", "testTitle1",
                "text", "testText1",
                "category", "testCategory1",
                "authorId", authorIdKnow);

        graphQlTester.documentName("book-test/create-book")
                .variables(variables)
                .execute()
                .errors()
                .verify()
                .path("createBook")
                .entity(Book.class);

    }

    @Sql(scripts = "classpath:data-test.sql")
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
                        .equals(String.format("[BR1] Author with ID %s does not exist", 999)));

    }

    @Sql(scripts = "classpath:data-test.sql")
    @Test
    void deleteBook_deleteOk() {

        // Given
        long idknown = bookService.findAll().stream()
                .findFirst()
                .orElseThrow()
                .getId();

        Map<String, Object> variables = Map.of("bookId", idknown);

        graphQlTester.documentName("book-test/delete-book")
                .variables(variables)
                .execute()
                .errors()
                .verify()
                .path("deleteBook")
                .entity(Book.class);

    }

}