package com.benjamin.parsy.lpgraphql.review;

import com.benjamin.parsy.lpgraphql.shared.service.GenericService;
import jakarta.validation.constraints.NotNull;
import reactor.core.publisher.Flux;

import java.util.Collection;
import java.util.List;

public interface ReviewService extends GenericService<Review> {

    Flux<String> getAlertNewReviewStream();

    List<Review> findAllByBookIdIn(Collection<@NotNull Long> bookId);

}
