package com.benjamin.parsy.learningprojectgraphql.service.business.impl;

import com.benjamin.parsy.learningprojectgraphql.entity.Book;
import com.benjamin.parsy.learningprojectgraphql.repository.BookRepository;
import com.benjamin.parsy.learningprojectgraphql.service.business.BookService;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class BookServiceImpl extends GenericServiceImpl<Book> implements BookService {

    private final BookRepository repository;

    protected BookServiceImpl(BookRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public List<Book> findAllWithLimitAndOffset(int limit, int offset) {
        return repository.findAllByOrderByCreatedDateLimitAndOffset(limit, offset);
    }

    @Override
    public List<Book> findAllByAuthorIdIn(Collection<@NotNull Long> authorId) {
        return repository.findAllByAuthorIdIn(authorId);
    }

}
