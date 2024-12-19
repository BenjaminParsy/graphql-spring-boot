package com.benjamin.parsy.lpgraphql.book;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(value = "SELECT b FROM book b " +
            "ORDER BY b.createdDate " +
            "LIMIT :limit " +
            "OFFSET :offset")
    List<Book> findAllByOrderByCreatedDateLimitAndOffset(@Param("limit") int limit, @Param("offset") int offset);

    @Query(value = "SELECT b FROM book b " +
            "ORDER BY b.createdDate " +
            "OFFSET :offset")
    List<Book> findAllByOrderByCreatedDateOffset(@Param("offset") int offset);


    List<Book> findAllByAuthorIdIn(Collection<@NotNull Long> authorId);

}
