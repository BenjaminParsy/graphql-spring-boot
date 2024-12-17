package com.benjamin.parsy.lpgraphql.shared.service.impl;

import com.benjamin.parsy.lpgraphql.shared.exception.CustomException;
import com.benjamin.parsy.lpgraphql.shared.exception.ErrorCode;
import com.benjamin.parsy.lpgraphql.shared.service.GenericService;
import com.benjamin.parsy.lpgraphql.shared.service.MessageService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public abstract class GenericServiceImpl<I> implements GenericService<I> {

    private final JpaRepository<I, Long> repository;
    private final MessageService messageService;

    protected GenericServiceImpl(JpaRepository<I, Long> repository, MessageService messageService) {
        this.repository = repository;
        this.messageService = messageService;
    }

    public I save(I obj) {
        return repository.save(obj);
    }

    public List<I> findAll() {
        return repository.findAll();
    }

    public Optional<I> findById(Long id) {
        return repository.findById(id);
    }

    public List<I> findAllByIdIn(List<Long> ids) {
        return repository.findAllById(ids);
    }

    public void deleteById(@NonNull long id) throws CustomException {

        if (!repository.existsById(id)) {
            throw new CustomException(messageService.getErrorMessage(ErrorCode.IE1, id));
        }

        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new CustomException(messageService.getErrorMessage(ErrorCode.IE2, id));
        }

    }

}
