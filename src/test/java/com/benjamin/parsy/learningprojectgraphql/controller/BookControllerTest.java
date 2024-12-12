package com.benjamin.parsy.learningprojectgraphql.controller;

import com.benjamin.parsy.learningprojectgraphql.DataHelper;
import com.benjamin.parsy.learningprojectgraphql.entity.Author;
import com.benjamin.parsy.learningprojectgraphql.entity.Book;
import com.benjamin.parsy.learningprojectgraphql.service.business.AuthorService;
import com.benjamin.parsy.learningprojectgraphql.service.business.BookService;
import com.benjamin.parsy.learningprojectgraphql.service.helper.message.MessageService;
import com.benjamin.parsy.learningprojectgraphql.service.helper.message.dto.ErrorMessage;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@GraphQlTest
class BookControllerTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private MessageService messageService;

    @Test
    void getBooks() {

        // Given
        Author author1 = DataHelper.createAuthor("John", "Smith", true);
        Book book1 = DataHelper.createBook("title1", "text1", "category1", author1.getId(), true);

        Author author2 = DataHelper.createAuthor("Jane", "Doe", true);
        Book book2 = DataHelper.createBook("title2", "text2", "category2", author2.getId(), true);

        Mockito.when(bookService.findAllWithLimitAndOffset(Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(List.of(book1, book2));

        Mockito.when(authorService.findAllByIdIn(List.of(author1.getId(), author2.getId())))
                .thenReturn(List.of(author1, author2));

        Map<String, Object> variables = Map.of("limit", 10,
                "offset", 0);

        // When and then
        graphQlTester.documentName("book-test/get-books")
                .variables(variables)
                .execute()
                .errors()
                .verify()
                .path("getBooks")
                .entityList(Book.class)
                .hasSize(2);

    }

    @Test
    void createBook() {

        Author author1 = DataHelper.createAuthor("John", "Smith", true);

        Mockito.when(authorService.findById(author1.getId()))
                .thenReturn(Optional.of(author1));

        Mockito.when(authorService.findAllByIdIn(List.of(author1.getId())))
                .thenReturn(List.of(author1));

        Mockito.when(bookService.save(Mockito.any()))
                .thenAnswer(invocation -> {
                    Book savedEntity = invocation.getArgument(0);
                    savedEntity.setId(1L);
                    return savedEntity;
                });

        Map<String, Object> variables = Map.of("title", "testTitle1",
                "text", "testText1",
                "category", "testCategory1",
                "authorId", author1.getId());

        graphQlTester.documentName("book-test/create-book")
                .variables(variables)
                .execute()
                .errors()
                .verify()
                .path("createBook")
                .entity(Book.class);

    }

    @Test
    void createBook_AuthorDoesntExist_ThrowException() {

        Author author1 = DataHelper.createAuthor("John", "Smith", true);

        Mockito.when(authorService.findById(author1.getId()))
                .thenReturn(Optional.empty());

        Mockito.when(messageService.getMessage(Mockito.any(), Mockito.any()))
                .thenReturn(new ErrorMessage("[001]", String.format("Author with ID %s does not exist", author1.getId())));

        Map<String, Object> variables = Map.of("title", "testTitle1",
                "text", "testText1",
                "category", "testCategory1",
                "authorId", author1.getId());

        graphQlTester.documentName("book-test/create-book")
                .variables(variables)
                .execute()
                .errors()
                .expect(e -> Objects.requireNonNull(e.getMessage())
                        .equals(String.format("[001] Author with ID %s does not exist", author1.getId())));

    }

}