package com.benjamin.parsy.lpgraphql.book;

import com.benjamin.parsy.lpgraphql.AbstractTest;
import com.benjamin.parsy.lpgraphql.shared.exception.GlobalException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookServiceTest extends AbstractTest {

    @Autowired
    private BookService bookService;

    @Sql(scripts = "classpath:data-test.sql")
    @Test
    void deleteById_deleteOk() {

        // Given
        Book book = bookService.findAll().stream()
                .findFirst()
                .orElseThrow();

        // When and then
        assertDoesNotThrow(() -> bookService.deleteById(book.getId()));

    }

    @Sql(scripts = "classpath:data-test.sql")
    @Test
    void deleteById_idNotExist_throwException() {

        // Given
        long idUnknown = bookService.findAll().stream()
                .max(Comparator.comparing(Book::getId))
                .orElseThrow()
                .getId() + 1;

        // When
        GlobalException exception = assertThrows(GlobalException.class,
                () -> bookService.deleteById(idUnknown));

        // Then
        String msg = String.format("[IE1] Item with id %s cannot be found in database", idUnknown);
        assertEquals(msg, exception.getMessage());

    }

}