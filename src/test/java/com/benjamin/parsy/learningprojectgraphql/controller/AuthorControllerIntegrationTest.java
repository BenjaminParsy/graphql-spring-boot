package com.benjamin.parsy.learningprojectgraphql.controller;

import com.benjamin.parsy.learningprojectgraphql.entity.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;

@SpringBootTest
@AutoConfigureHttpGraphQlTester
class AuthorControllerIntegrationTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @Test
    void getAuthors() {

        graphQlTester.documentName("get-authors")
                .execute()
                .errors()
                .verify()
                .path("getAuthors")
                .entityList(Author.class)
                .hasSize(2);

    }

}