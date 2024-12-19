package com.benjamin.parsy.lpgraphql.author;

import com.benjamin.parsy.lpgraphql.book.Book;
import com.benjamin.parsy.lpgraphql.book.BookService;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
    public List<Author> getAuthors() {
        return new LinkedList<>(authorService.findAll());
    }

    @BatchMapping
    public Map<Author, List<Book>> books(List<Author> authorList) {

        List<Long> authorIdList = authorList.stream()
                .map(Author::getId)
                .distinct()
                .toList();

        List<Book> bookList = bookService.findAllByAuthorIdIn(authorIdList);

        return bookList.stream().collect(Collectors.groupingBy(Book::getAuthor));
    }

}
