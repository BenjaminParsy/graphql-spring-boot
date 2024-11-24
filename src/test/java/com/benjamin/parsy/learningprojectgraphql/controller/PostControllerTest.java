package com.benjamin.parsy.learningprojectgraphql.controller;

import com.benjamin.parsy.learningprojectgraphql.DataHelper;
import com.benjamin.parsy.learningprojectgraphql.entity.Author;
import com.benjamin.parsy.learningprojectgraphql.entity.Post;
import com.benjamin.parsy.learningprojectgraphql.service.AuthorService;
import com.benjamin.parsy.learningprojectgraphql.service.PostService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@GraphQlTest
class PostControllerTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @MockBean
    private PostService postService;

    @MockBean
    private AuthorService authorService;

    @Test
    void recentPostsTest() {

        Author author1 = DataHelper.createAuthor("John", "Smith", true);
        Post post1 = DataHelper.createPost(author1, "title1", "text1", "category1", true);

        Author author2 = DataHelper.createAuthor("Jane", "Doe", true);
        Post post2 = DataHelper.createPost(author2, "title2", "text2", "category2", true);

        Mockito.when(postService.getRecentPosts(Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(List.of(post1, post2));

        Map<String, Object> variables = Map.of("count", 10,
                "offset", 0);

        graphQlTester.documentName("recent-posts")
                .variables(variables)
                .execute()
                .errors()
                .verify()
                .path("recentPosts")
                .entityList(Post.class)
                .hasSize(2);

    }

    @Test
    void createPostTest() {

        Author author1 = DataHelper.createAuthor("John", "Smith", true);

        Mockito.when(authorService.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(author1));

        Mockito.when(postService.save(Mockito.any()))
                .thenAnswer(invocation -> {
                    Post savedEntity = invocation.getArgument(0);
                    savedEntity.setId(1L);
                    return savedEntity;
                });

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