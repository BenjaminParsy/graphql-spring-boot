package com.benjamin.parsy.lpgraphql;

import com.benjamin.parsy.lpgraphql.author.Author;
import com.benjamin.parsy.lpgraphql.book.Book;
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

    public static Book createBook(String title, String text, String category, Author author, boolean fakeId) {

        Book book = new Book();
        book.setTitle(title);
        book.setText(text);
        book.setCategory(category);
        book.setCreatedDate(LocalDateTime.now());
        book.setAuthor(author);

        if (fakeId) {
            book.setId(Long.valueOf(RandomStringUtils.randomNumeric(4)));
        }

        return book;
    }

}
