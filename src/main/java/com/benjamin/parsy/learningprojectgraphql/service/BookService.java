package com.benjamin.parsy.learningprojectgraphql.service;

import com.benjamin.parsy.learningprojectgraphql.entity.Book;
import jakarta.validation.constraints.NotNull;

import java.util.Collection;
import java.util.List;

public interface BookService extends GenericService<Book> {

    List<Book> getRecentBooks(int count, int offset);

    List<Book> findAllByAuthorIdIn(Collection<@NotNull Long> authorId);

}
