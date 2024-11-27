package com.benjamin.parsy.learningprojectgraphql.repository;

import com.benjamin.parsy.learningprojectgraphql.entity.Book;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM book b ORDER BY b.createdDate LIMIT :count")
    List<Book> findAllByOrderByCreatedDate(@Param("count") int count);

    List<Book> findAllByAuthorIdIn(Collection<@NotNull Long> authorId);

}
