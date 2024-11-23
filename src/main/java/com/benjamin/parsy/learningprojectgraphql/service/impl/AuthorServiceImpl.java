package com.benjamin.parsy.learningprojectgraphql.service.impl;

import com.benjamin.parsy.learningprojectgraphql.entity.Author;
import com.benjamin.parsy.learningprojectgraphql.repository.AuthorRepository;
import com.benjamin.parsy.learningprojectgraphql.service.AuthorService;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl extends GenericServiceImpl<Author> implements AuthorService {

    protected AuthorServiceImpl(AuthorRepository repository) {
        super(repository);
    }

}
