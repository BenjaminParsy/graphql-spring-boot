package com.benjamin.parsy.learningprojectgraphql.service;

import com.benjamin.parsy.learningprojectgraphql.entity.Post;
import jakarta.validation.constraints.NotNull;

import java.util.Collection;
import java.util.List;

public interface PostService extends GenericService<Post> {

    List<Post> getRecentPosts(int count, int offset);

    List<Post> findAllByAuthorIdIn(Collection<@NotNull Long> authorId);

}
