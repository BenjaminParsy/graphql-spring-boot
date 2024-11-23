package com.benjamin.parsy.learningprojectgraphql.service.impl;

import com.benjamin.parsy.learningprojectgraphql.entity.Post;
import com.benjamin.parsy.learningprojectgraphql.repository.PostRepository;
import com.benjamin.parsy.learningprojectgraphql.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl extends GenericServiceImpl<Post> implements PostService {

    private final PostRepository repository;

    protected PostServiceImpl(PostRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public List<Post> getRecentPosts(int count, int offset) {
        return repository.findAllByOrderByCreatedDate(count);
    }

}
