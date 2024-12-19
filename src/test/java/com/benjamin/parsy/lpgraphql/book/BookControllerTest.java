package com.benjamin.parsy.lpgraphql.book;

import com.benjamin.parsy.lpgraphql.AbstractTest;
import com.benjamin.parsy.lpgraphql.author.AuthorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.test.context.jdbc.Sql;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;

@SpringBootTest
@AutoConfigureHttpGraphQlTester
class BookControllerTest extends AbstractTest {

    private static final String BASE_PATH_JSON_EXPECTED = "classpath:graphql-test/book";
    private static final String[] GRAPHQL_TEST_SCHEMA_PATH = {
            "book/get-books/get-books",
            "book/create-book/create-book",
            "book/delete-book/delete-book",
    };

    @Autowired
    private GraphQlTester graphQlTester;

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    @Sql(scripts = "classpath:data-test.sql")
    @Test
    void getBooks_BooksPresent_ReturnAllBooks(@Value(BASE_PATH_JSON_EXPECTED +
            "/get-books/books.json") Resource jsonExpected) throws IOException {

        // When and then
        graphQlTester.documentName(GRAPHQL_TEST_SCHEMA_PATH[0])
                .operationName("getBooks")
                .execute()
                .errors()
                .verify()
                .path("getBooks")
                .matchesJson(jsonExpected.getContentAsString(StandardCharsets.UTF_8));

    }

    @Sql(scripts = "classpath:data-test.sql")
    @ValueSource(ints = {0, 1, 10})
    @ParameterizedTest
    void getBooks_LimitResult_ResultLessOrEqualLimit(int limit) {

        // Given
        Map<String, Object> variables = Map.of("limit", limit);

        // When and then
        graphQlTester.documentName(GRAPHQL_TEST_SCHEMA_PATH[0])
                .variables(variables)
                .execute()
                .errors()
                .verify()
                .path("getBooks")
                .entityList(Book.class)
                .matches((list) -> list.size() <= limit);

    }

    @Sql(scripts = "classpath:data-test.sql")
    @Test
    void createBook_BookOk_CreateBook() {

        // Given
        long authorIdKnow = authorService.findAll().stream()
                .findFirst()
                .orElseThrow()
                .getId();

        Map<String, Object> variables = Map.of("title", "testTitle1",
                "text", "testText1",
                "category", "testCategory1",
                "authorId", authorIdKnow);

        // When and then
        graphQlTester.documentName(GRAPHQL_TEST_SCHEMA_PATH[1])
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

        // Given
        Map<String, Object> variables = Map.of("title", "testTitle1",
                "text", "testText1",
                "category", "testCategory1",
                "authorId", 999);

        // When and then
        graphQlTester.documentName(GRAPHQL_TEST_SCHEMA_PATH[1])
                .variables(variables)
                .execute()
                .errors()
                .expect(e -> Objects.requireNonNull(e.getMessage())
                        .equals(String.format("[BR1] Author with ID %s does not exist", 999)));

    }

    @Sql(scripts = "classpath:data-test.sql")
    @Test
    void deleteBook_deleteOk_ReturnDeletedBook() {

        // Given
        long idknown = bookService.findAll().stream()
                .findFirst()
                .orElseThrow()
                .getId();

        Map<String, Object> variables = Map.of("bookId", idknown);

        // When and then
        graphQlTester.documentName(GRAPHQL_TEST_SCHEMA_PATH[2])
                .variables(variables)
                .execute()
                .errors()
                .verify()
                .path("deleteBook")
                .entity(Book.class);

    }

    @Sql(scripts = "classpath:data-test.sql")
    @Test
    void deleteBook_BookDoesntExist_ThrowException() {

        // Given
        long idUnknown = bookService.findAll().stream()
                .max(Comparator.comparing(Book::getId))
                .orElseThrow()
                .getId() + 1;

        Map<String, Object> variables = Map.of("bookId", idUnknown);

        // When and then
        graphQlTester.documentName(GRAPHQL_TEST_SCHEMA_PATH[2])
                .variables(variables)
                .execute()
                .errors()
                .expect(e -> Objects.requireNonNull(e.getMessage())
                        .equals(String.format("[BR3] Book with ID %s does not exist", idUnknown)));

    }

}