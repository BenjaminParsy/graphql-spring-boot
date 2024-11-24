package com.benjamin.parsy.learningprojectgraphql.entity;

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
@Entity(name = "post")
public class Post {

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
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

}
