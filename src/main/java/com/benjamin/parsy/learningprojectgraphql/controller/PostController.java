package com.benjamin.parsy.learningprojectgraphql.controller;

import com.benjamin.parsy.learningprojectgraphql.entity.Author;
import com.benjamin.parsy.learningprojectgraphql.entity.Post;
import com.benjamin.parsy.learningprojectgraphql.service.AuthorService;
import com.benjamin.parsy.learningprojectgraphql.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@Slf4j
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

    @BatchMapping
    public Map<Post, Author> author(List<Post> postList) {

        List<Long> authorIdList = postList.stream()
                .map(Post::getAuthorId)
                .distinct()
                .toList();

        Map<Long, Author> authorById = authorService.findAllByIdIn(authorIdList)
                .stream()
                .collect(Collectors.toMap(Author::getId, Function.identity()));

        return postList.stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        post -> authorById.get(post.getAuthorId()), (a, b) -> b
                ));
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
