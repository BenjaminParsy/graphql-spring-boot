package com.benjamin.parsy.lpgraphql.book;

import com.benjamin.parsy.lpgraphql.shared.service.MessageService;
import com.benjamin.parsy.lpgraphql.shared.service.impl.GenericServiceImpl;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class BookServiceImpl extends GenericServiceImpl<Book> implements BookService {

    private final BookRepository repository;

    protected BookServiceImpl(BookRepository repository, MessageService messageService) {
        super(repository, messageService);
        this.repository = repository;
    }

    @Override
    public List<Book> findAllWithLimitAndOffset(Integer limit, Integer offset) {

        offset = offset == null ? 0 : offset;

        return limit == null ? repository.findAllByOrderByCreatedDateOffset(offset) :
                repository.findAllByOrderByCreatedDateLimitAndOffset(limit, offset);
    }

    @Override
    public List<Book> findAllByAuthorIdIn(Collection<@NotNull Long> authorId) {
        return repository.findAllByAuthorIdIn(authorId);
    }

}
