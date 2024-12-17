package com.benjamin.parsy.lpgraphql.author;

import com.benjamin.parsy.lpgraphql.shared.service.MessageService;
import com.benjamin.parsy.lpgraphql.shared.service.impl.GenericServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl extends GenericServiceImpl<Author> implements AuthorService {

    protected AuthorServiceImpl(AuthorRepository repository, MessageService messageService) {
        super(repository, messageService);
    }

}
