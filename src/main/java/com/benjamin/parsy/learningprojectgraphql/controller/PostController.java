package com.benjamin.parsy.learningprojectgraphql.controller;

import com.benjamin.parsy.learningprojectgraphql.entity.Author;
import com.benjamin.parsy.learningprojectgraphql.entity.Post;
import com.benjamin.parsy.learningprojectgraphql.service.AuthorService;
import com.benjamin.parsy.learningprojectgraphql.service.PostService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class PostController {

    private final PostService postService;
    private final AuthorService authorService;

    public PostController(PostService postService, AuthorService authorService) {
        this.postService = postService;
        this.authorService = authorService;
    }

    @QueryMapping
    public List<Post> recentPosts(@Argument int count, @Argument int offset) {
        return postService.getRecentPosts(count, offset);
    }

    @SchemaMapping
    public Author author(Post post) throws NoSuchFieldException {

        Optional<Author> optionalAuthor = authorService.findById(post.getAuthorId());

        if (optionalAuthor.isEmpty()) {
            throw new NoSuchFieldException(String.format("Author not found for the id '%s'", post.getAuthorId()));
        }

        return optionalAuthor.get();
    }

    @MutationMapping
    public Post createPost(@Argument String title,
                           @Argument String text,
                           @Argument String category,
                           @Argument Long authorId) throws NoSuchFieldException {

        Optional<Author> optionalAuthor = authorService.findById(authorId);

        if (optionalAuthor.isEmpty()) {
            throw new NoSuchFieldException(String.format("Author not found for the id '%s'", authorId));
        }

        Post post = new Post();
        post.setTitle(title);
        post.setText(text);
        post.setCategory(category);
        post.setCreatedDate(LocalDateTime.now());
        post.setAuthorId(authorId);

        postService.save(post);

        return post;
    }

}
