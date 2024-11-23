package com.benjamin.parsy.learningprojectgraphql.service;

import java.util.List;
import java.util.Optional;

public interface GenericService<I> {

    I save(I obj);

    List<I> findAll();

    Optional<I> findById(Long id);

}
