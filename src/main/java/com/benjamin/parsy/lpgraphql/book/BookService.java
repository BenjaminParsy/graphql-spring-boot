package com.benjamin.parsy.lpgraphql.book;

import com.benjamin.parsy.lpgraphql.shared.service.GenericService;
import jakarta.validation.constraints.NotNull;

import java.util.Collection;
import java.util.List;

public interface BookService extends GenericService<Book> {

    List<Book> findAllWithLimitAndOffset(int limit, int offset);

    List<Book> findAllByAuthorIdIn(Collection<@NotNull Long> authorId);

}
