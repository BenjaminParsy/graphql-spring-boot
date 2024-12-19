package com.benjamin.parsy.lpgraphql.book;

import com.benjamin.parsy.lpgraphql.author.Author;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Entity(name = "book")
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "category")
    private String category;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @ManyToOne(targetEntity = Author.class, fetch = FetchType.LAZY, optional = false)
    private Author author;

}
