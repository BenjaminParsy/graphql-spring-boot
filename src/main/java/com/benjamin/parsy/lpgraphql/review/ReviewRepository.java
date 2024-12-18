package com.benjamin.parsy.lpgraphql.review;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByBookIdIn(Collection<@NotNull Long> bookId);

}
