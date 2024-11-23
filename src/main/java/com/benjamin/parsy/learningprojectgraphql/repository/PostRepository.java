package com.benjamin.parsy.learningprojectgraphql.repository;

import com.benjamin.parsy.learningprojectgraphql.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM post p ORDER BY p.createdDate LIMIT :count")
    List<Post> findAllByOrderByCreatedDate(@Param("count") int count);

}
