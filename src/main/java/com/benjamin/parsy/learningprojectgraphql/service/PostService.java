package com.benjamin.parsy.learningprojectgraphql.service;

import com.benjamin.parsy.learningprojectgraphql.entity.Post;

import java.util.List;

public interface PostService extends GenericService<Post> {

    List<Post> getRecentPosts(int count, int offset);

}
