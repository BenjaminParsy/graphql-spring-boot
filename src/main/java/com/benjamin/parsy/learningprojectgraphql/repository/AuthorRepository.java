package com.benjamin.parsy.learningprojectgraphql.repository;

import com.benjamin.parsy.learningprojectgraphql.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
