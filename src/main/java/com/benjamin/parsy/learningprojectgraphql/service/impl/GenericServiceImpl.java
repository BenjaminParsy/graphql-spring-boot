package com.benjamin.parsy.learningprojectgraphql.service.impl;

import com.benjamin.parsy.learningprojectgraphql.service.GenericService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public abstract class GenericServiceImpl<I> implements GenericService<I> {

    private final JpaRepository<I, Long> repository;

    protected GenericServiceImpl(JpaRepository<I, Long> repository) {
        this.repository = repository;
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

}
