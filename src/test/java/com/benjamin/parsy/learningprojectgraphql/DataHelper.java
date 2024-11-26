package com.benjamin.parsy.learningprojectgraphql;

import com.benjamin.parsy.learningprojectgraphql.entity.Author;
import com.benjamin.parsy.learningprojectgraphql.entity.Post;
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

    public static Post createPost(String title, String text, String category, Long authorId, boolean fakeId) {

        Post post = new Post();
        post.setTitle(title);
        post.setText(text);
        post.setCategory(category);
        post.setCreatedDate(LocalDateTime.now());
        post.setAuthorId(authorId);

        if (fakeId) {
            post.setId(Long.valueOf(RandomStringUtils.randomNumeric(4)));
        }

        return post;
    }

}
