package com.benjamin.parsy.lpgraphql.shared.service;

import com.benjamin.parsy.lpgraphql.shared.exception.CustomException;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface GenericService<I> {

    I save(I obj);

    List<I> findAll();

    Optional<I> findById(Long id);

    List<I> findAllByIdIn(List<Long> ids);

    void deleteById(@NonNull long id) throws CustomException;

}
