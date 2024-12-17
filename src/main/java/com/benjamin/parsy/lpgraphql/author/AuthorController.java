package com.benjamin.parsy.lpgraphql.author;

import com.benjamin.parsy.lpgraphql.book.Book;
import com.benjamin.parsy.lpgraphql.book.BookService;
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
    private final BookService bookService;

    public AuthorController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @QueryMapping
    public Set<Author> getAuthors() {
        List<Author> authorList = authorService.findAll();
        return new HashSet<>(authorList);
    }

    @BatchMapping
    public Map<Author, List<Book>> books(List<Author> authorList) {

        List<Long> authorIdList = authorList.stream()
                .map(Author::getId)
                .distinct()
                .toList();

        List<Book> bookList = bookService.findAllByAuthorIdIn(authorIdList);

        Map<Long, List<Book>> booksByAuthorId = bookList.stream()
                .collect(Collectors.groupingBy(Book::getAuthorId));

        return authorList.stream()
                .collect(Collectors.toMap(
                        author -> author,
                        author -> booksByAuthorId.getOrDefault(author.getId(), List.of())
                ));
    }

}
