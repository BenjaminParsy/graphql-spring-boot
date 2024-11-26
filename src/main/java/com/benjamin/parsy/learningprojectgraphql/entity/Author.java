package com.benjamin.parsy.learningprojectgraphql.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity(name = "author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(name = "firstname", nullable = false)
    private String firstname;

    @NotEmpty
    @Column(name = "lastname", nullable = false)
    private String lastname;

}
