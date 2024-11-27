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

@GraphQlTest
class AuthorControllerTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private PostService postService;

    @Test
    void getAuthors() {

        Author author1 = DataHelper.createAuthor("John", "Smith", true);
        Post post1 = DataHelper.createPost("title1", "text1", "category1", author1.getId(), true);
        Post post2 = DataHelper.createPost("title2", "text2", "category2", author1.getId(), true);

        Author author2 = DataHelper.createAuthor("Jane", "Doe", true);
        Post post3 = DataHelper.createPost("title3", "text3", "category3", author2.getId(), true);
        Post post4 = DataHelper.createPost("title4", "text4", "category4", author2.getId(), true);

        Mockito.when(authorService.findAll())
                .thenReturn(List.of(author1, author2));

        Mockito.when(postService.findAllByAuthorIdIn(List.of(author1.getId(), author2.getId())))
                .thenReturn(List.of(post1, post2, post3, post4));

        graphQlTester.documentName("get-authors")
                .execute()
                .errors()
                .verify()
                .path("getAuthors")
                .entityList(Author.class)
                .hasSize(2);

    }

}