package com.benjamin.parsy.lpgraphql.review;

import com.benjamin.parsy.lpgraphql.shared.service.MessageService;
import com.benjamin.parsy.lpgraphql.shared.service.impl.GenericServiceImpl;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.Collection;
import java.util.List;

@Service
public class ReviewServiceImpl extends GenericServiceImpl<Review> implements ReviewService {

    private final Sinks.Many<String> sink = Sinks.many().multicast().onBackpressureBuffer();

    private final ReviewRepository repository;

    protected ReviewServiceImpl(ReviewRepository repository, MessageService messageService) {
        super(repository, messageService);
        this.repository = repository;
    }

    @Override
    public Review save(Review obj) {
        Review savedReview = super.save(obj);
        emitNewAlertReview(savedReview);
        return savedReview;
    }

    @Override
    public Flux<String> getAlertNewReviewStream() {
        return sink.asFlux();
    }

    @Override
    public List<Review> findAllByBookIdIn(Collection<@NotNull Long> bookId) {
        return repository.findAllByBookIdIn(bookId);
    }

    private void emitNewAlertReview(Review review) {

        String msg = String.format("A new review has been posted by %s on the book %s",
                review.getCreatedBy(), review.getBook().getTitle());

        sink.tryEmitNext(msg);
    }

}
