package com.benjamin.parsy.learningprojectgraphql.controller;

import com.benjamin.parsy.learningprojectgraphql.entity.Author;
import com.benjamin.parsy.learningprojectgraphql.entity.Post;
import com.benjamin.parsy.learningprojectgraphql.service.AuthorService;
import com.benjamin.parsy.learningprojectgraphql.service.PostService;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class AuthorController {

    private final AuthorService authorService;
    private final PostService postService;

    public AuthorController(AuthorService authorService, PostService postService) {
        this.authorService = authorService;
        this.postService = postService;
    }

    @QueryMapping
    public Set<Author> getAuthors() {
        List<Author> authorList = authorService.findAll();
        return new HashSet<>(authorList);
    }

    @BatchMapping
    public Map<Author, List<Post>> posts(List<Author> authorList) {

        List<Long> authorIdList = authorList.stream()
                .map(Author::getId)
                .distinct()
                .toList();

        List<Post> postList = postService.findAllByAuthorIdIn(authorIdList);

        Map<Long, List<Post>> postsByAuthorId = postList.stream()
                .collect(Collectors.groupingBy(Post::getAuthorId));

        return authorList.stream()
                .collect(Collectors.toMap(
                        author -> author,
                        author -> postsByAuthorId.getOrDefault(author.getId(), List.of())
                ));
    }

}
