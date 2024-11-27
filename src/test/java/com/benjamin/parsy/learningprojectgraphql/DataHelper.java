package com.benjamin.parsy.learningprojectgraphql;

import com.benjamin.parsy.learningprojectgraphql.entity.Author;
import com.benjamin.parsy.learningprojectgraphql.entity.Book;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;

public class DataHelper {

    private DataHelper() {
        // Private constructor for utility class
    }

    public static Author createAuthor(String firstname, String lastname, boolean fakeId) {

        Author author = new Author();
        author.setFirstname(firstname);
        author.setLastname(lastname);

        if (fakeId) {
            author.setId(Long.valueOf(RandomStringUtils.randomNumeric(4)));
        }

        return author;
    }

    public static Book createBook(String title, String text, String category, Long authorId, boolean fakeId) {

        Book book = new Book();
        book.setTitle(title);
        book.setText(text);
        book.setCategory(category);
        book.setCreatedDate(LocalDateTime.now());
        book.setAuthorId(authorId);

        if (fakeId) {
            book.setId(Long.valueOf(RandomStringUtils.randomNumeric(4)));
        }

        return book;
    }

}
