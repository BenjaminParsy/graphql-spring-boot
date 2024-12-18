package com.benjamin.parsy.lpgraphql.book;

import com.benjamin.parsy.lpgraphql.author.Author;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

    @NotEmpty
    @Column(name = "title", nullable = false)
    private String title;

    @NotEmpty
    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "category")
    private String category;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @NotNull
    @ManyToOne(targetEntity = Author.class, fetch = FetchType.LAZY, optional = false)
    private Author author;

}
