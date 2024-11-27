package com.benjamin.parsy.learningprojectgraphql.controller;

import com.benjamin.parsy.learningprojectgraphql.DataHelper;
import com.benjamin.parsy.learningprojectgraphql.entity.Author;
import com.benjamin.parsy.learningprojectgraphql.entity.Book;
import com.benjamin.parsy.learningprojectgraphql.service.AuthorService;
import com.benjamin.parsy.learningprojectgraphql.service.BookService;
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
    private BookService bookService;

    @Test
    void getAuthors() {

        Author author1 = DataHelper.createAuthor("John", "Smith", true);
        Book book1 = DataHelper.createBook("title1", "text1", "category1", author1.getId(), true);
        Book book2 = DataHelper.createBook("title2", "text2", "category2", author1.getId(), true);

        Author author2 = DataHelper.createAuthor("Jane", "Doe", true);
        Book book3 = DataHelper.createBook("title3", "text3", "category3", author2.getId(), true);
        Book book4 = DataHelper.createBook("title4", "text4", "category4", author2.getId(), true);

        Mockito.when(authorService.findAll())
                .thenReturn(List.of(author1, author2));

        Mockito.when(bookService.findAllByAuthorIdIn(List.of(author1.getId(), author2.getId())))
                .thenReturn(List.of(book1, book2, book3, book4));

        graphQlTester.documentName("author-test/get-authors")
                .execute()
                .errors()
                .verify()
                .path("getAuthors")
                .entityList(Author.class)
                .hasSize(2);

    }

}