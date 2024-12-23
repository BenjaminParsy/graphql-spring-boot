package com.benjamin.parsy.lpgraphql.review;

import com.benjamin.parsy.lpgraphql.book.Book;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "review")
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(targetEntity = Book.class, fetch = FetchType.LAZY, optional = false)
    private Book book;

}
