package com.benjamin.parsy.lpgraphql.shared.service;

import com.benjamin.parsy.lpgraphql.shared.exception.GlobalException;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface GenericService<I> {

    I save(I obj);

    List<I> findAll();

    Optional<I> findById(Long id);

    List<I> findAllByIdIn(List<Long> ids);

    I deleteById(@NonNull long id) throws GlobalException;

}
