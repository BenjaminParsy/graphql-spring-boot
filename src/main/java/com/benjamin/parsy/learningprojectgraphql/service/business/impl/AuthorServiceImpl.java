package com.benjamin.parsy.learningprojectgraphql.service.business.impl;

import com.benjamin.parsy.learningprojectgraphql.entity.Author;
import com.benjamin.parsy.learningprojectgraphql.repository.AuthorRepository;
import com.benjamin.parsy.learningprojectgraphql.service.business.AuthorService;
import com.benjamin.parsy.learningprojectgraphql.service.helper.message.MessageService;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl extends GenericServiceImpl<Author> implements AuthorService {

    protected AuthorServiceImpl(AuthorRepository repository, MessageService messageService) {
        super(repository, messageService);
    }

}
