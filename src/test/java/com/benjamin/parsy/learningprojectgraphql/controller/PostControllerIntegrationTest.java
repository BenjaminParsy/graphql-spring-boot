package com.benjamin.parsy.learningprojectgraphql.controller;

import com.benjamin.parsy.learningprojectgraphql.entity.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureHttpGraphQlTester
class PostControllerIntegrationTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @Test
    void recentPostsTest() {

        List<Post> postList = graphQlTester.documentName("recent-posts")
                .execute()
                .errors()
                .verify()
                .path("recentPosts")
                .entityList(Post.class)
                .get();

        assertEquals(4, postList.size());

    }

}