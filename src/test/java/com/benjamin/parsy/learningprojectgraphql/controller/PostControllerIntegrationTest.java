package com.benjamin.parsy.learningprojectgraphql.controller;

import com.benjamin.parsy.learningprojectgraphql.entity.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.util.Map;

@SpringBootTest
@AutoConfigureHttpGraphQlTester
class PostControllerIntegrationTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @Test
    void recentPostsTest() {

        Map<String, Object> variables = Map.of("count", 10,
                "offset", 0);

        graphQlTester.documentName("recent-posts")
                .variables(variables)
                .execute()
                .errors()
                .verify()
                .path("recentPosts")
                .entityList(Post.class)
                .hasSize(4);

    }

    @Test
    void createPostTest() {

        Map<String, Object> variables = Map.of("title", "testTitle1",
                "text", "testText1",
                "category", "testCategory1",
                "authorId", 2);

        graphQlTester.documentName("create-post")
                .variables(variables)
                .execute()
                .errors()
                .verify()
                .path("createPost")
                .entity(Post.class);

    }

}