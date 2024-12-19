package com.benjamin.parsy.lpgraphql.author;

import com.benjamin.parsy.lpgraphql.AbstractTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.test.context.jdbc.Sql;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@SpringBootTest
@AutoConfigureHttpGraphQlTester
class AuthorControllerTest extends AbstractTest {

    private static final String BASE_PATH_JSON_EXPECTED = "classpath:graphql-test/author";
    private static final String[] GRAPHQL_TEST_SCHEMA_PATH = {
            "author/get-authors/get-authors"
    };

    @Autowired
    private GraphQlTester graphQlTester;

    @Sql(scripts = "classpath:data-test.sql")
    @Test
    void getAuthors_AuthorsPresent_ReturnAllAuthors(@Value(BASE_PATH_JSON_EXPECTED +
            "/get-authors/authors.json") Resource jsonExpected) throws IOException {

        // When and then
        graphQlTester.documentName(GRAPHQL_TEST_SCHEMA_PATH[0])
                .operationName("getAuthors")
                .execute()
                .errors()
                .verify()
                .path("getAuthors")
                .matchesJson(jsonExpected.getContentAsString(StandardCharsets.UTF_8));

    }

}