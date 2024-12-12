package com.benjamin.parsy.learningprojectgraphql.service.business;

import com.benjamin.parsy.learningprojectgraphql.entity.Book;
import jakarta.validation.constraints.NotNull;

import java.util.Collection;
import java.util.List;

public interface BookService extends GenericService<Book> {

    List<Book> findAllWithLimitAndOffset(int limit, int offset);

    List<Book> findAllByAuthorIdIn(Collection<@NotNull Long> authorId);

}
