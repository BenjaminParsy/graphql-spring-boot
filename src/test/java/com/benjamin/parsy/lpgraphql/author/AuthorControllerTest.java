package com.benjamin.parsy.lpgraphql.author;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@AutoConfigureHttpGraphQlTester
class AuthorControllerTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @Sql(scripts = "classpath:data-test.sql")
    @Test
    void getAuthors() {

        graphQlTester.documentName("author-test/get-authors")
                .execute()
                .errors()
                .verify()
                .path("getAuthors")
                .entityList(Author.class)
                .hasSize(2);

    }

}